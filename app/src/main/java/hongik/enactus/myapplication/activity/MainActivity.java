package hongik.enactus.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.Parameter;
import hongik.enactus.myapplication.common.Tag;
import hongik.enactus.myapplication.common.URI;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private Button btn_tmp, btn_login, btn_kakao_login, btn_register;
    private EditText txt_login_email, txt_login_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_start);
        mContext = getApplicationContext();

        // KAKAO SDK 초기화
        KakaoSdk.init(this, "c2ca5e18100afa0762eca9521e7ecb04");

        Button btn_start_splash = findViewById(R.id.btn_start_splash);
        btn_start_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                startMain();
            }
        });
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

        btn_tmp = findViewById(R.id.btn_tmp);
        btn_login = findViewById(R.id.btn_login);
        btn_kakao_login = findViewById(R.id.btn_kakao_login);
        btn_register = findViewById(R.id.btn_register);
    }

    void startMain() {
        viewInit();

        btn_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), onBoardingActivity.class);
                startActivity(intent);
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
                    networkTask.setContext(mContext);
                    networkTask.execute();

                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(UserApiClient.getInstance().isKakaoTalkLoginAvailable(MainActivity.this)){
                     UserApiClient.getInstance().loginWithKakaoTalk(MainActivity.this, kakaoCallback);
                 } else {
                     UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, kakaoCallback);
                 }
             }
         });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(mContext, RegisterAccountActivity.class);
                startActivity(intent3);
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


}
