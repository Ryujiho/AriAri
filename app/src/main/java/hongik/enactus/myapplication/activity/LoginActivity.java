package hongik.enactus.myapplication.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.NetworkTask;
import hongik.enactus.myapplication.common.Parameter;
import hongik.enactus.myapplication.common.Tag;
import hongik.enactus.myapplication.common.URI;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    private Button btn_tmp, btn_home_tmp, btn_login, btn_kakao_login;
    private EditText txt_login_email, txt_login_pwd;
    private TextView btn_find_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = LoginActivity.this;

        // KAKAO SDK 초기화
        KakaoSdk.init(this, "c2ca5e18100afa0762eca9521e7ecb04");

        startLoginView();
    }



    private void getHashKey(){
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo == null)
            Log.e("KeyHash", "KeyHash:null");

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            } catch (NoSuchAlgorithmException e) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=" + signature, e);
            }
        }
    }


    void viewInit(){
        txt_login_email = findViewById(R.id.txt_login_email);
        txt_login_pwd = findViewById(R.id.txt_login_pwd);

        btn_find_pwd = findViewById(R.id.btn_find_pwd);

        btn_login = findViewById(R.id.btn_login);
        btn_kakao_login = findViewById(R.id.btn_kakao_login);
    }

    void startLoginView() {
        viewInit();

        btn_find_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(getApplicationContext());
                editText.setBackgroundResource(R.drawable.edittext_background);
                editText.setHint(R.string.txt_email);
                editText.setHintTextColor(Color.WHITE);
                editText.setWidth(dpToPx(280));
                editText.setHeight(dpToPx(45));
                editText.setPadding(dpToPx(25), 0, 0, 0);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(dpToPx(10), dpToPx(5), dpToPx(10), dpToPx(5));
                editText.setLayoutParams(lp);

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this, R.style.PasswordResetDialogTheme);
                builder.setMessage(R.string.alert_pwd_reset)
                        .setTitle(R.string.alert_pwd_reset_title)
                        .setView(editText)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "확인", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(mContext, "취소", Toast.LENGTH_LONG).show();
                            }
                        })
                        .setCancelable(false);
                AlertDialog alert = builder.show();


            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonValues = new JSONObject();
                    jsonValues.put(Parameter.EMAIL, txt_login_email.getText().toString());
                    jsonValues.put(Parameter.PASSWORD, txt_login_pwd.getText().toString());

                    NetworkTask networkTask = new NetworkTask(URI.hostName + URI.login, jsonValues);
                    networkTask.setContext(LoginActivity.this);
                    networkTask.setRequestType("POST");
                    networkTask.execute();

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(LoginActivity.this)){
                     UserApiClient.getInstance().loginWithKakaoTalk(LoginActivity.this, kakaoCallback);
                 } else {
                     UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, kakaoCallback);
                 }
             }
         });
    }


    Function2<OAuthToken, Throwable, Unit> kakaoCallback = new Function2<OAuthToken, Throwable, Unit>() {
        @Override
        public Unit invoke(OAuthToken oAuthToken, Throwable throwable) {
            if(oAuthToken != null){
                Log.e(Tag.KAKAO_API, "로그인 성공");

                // Get accessToken
                String []tokenInfo = oAuthToken.toString().split(",");
                String accessToken = tokenInfo[0].split("=")[1];
                Log.i(Tag.KAKAO_API, "로그인 accessToken : " + accessToken);

                // Request HttpURLConnection
                JSONObject jsonValues = new JSONObject();
                try {
                    jsonValues.put(Parameter.ACCESS_TOKEN, accessToken);
                    NetworkTask networkTask = new NetworkTask(URI.hostName + URI.kakaoLogin, jsonValues);
                    networkTask.setContext(mContext);
                    networkTask.setRequestType("POST");
                    networkTask.execute();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if(throwable != null){
                Log.e(Tag.KAKAO_API, "로그인 실패", throwable);
            }
            return null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(Tag.MAIN, "onDestroy");

        // 카카오 로그아웃
        /*UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
            @Override
            public Unit invoke(Throwable throwable) {
                Log.e(Tag.KAKAO_API, "LOGOUT");
                if (throwable != null) {
                    Log.e(Tag.KAKAO_API, "로그아웃 실패. SDK에서 토큰 삭제됨", throwable);
                }
                else {
                    Log.i(Tag.KAKAO_API, "로그아웃 성공. SDK에서 토큰 삭제됨");
                }
                return null;
            }
        });*/
    }
    public static int dpToPx(int dp)
    {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

}
