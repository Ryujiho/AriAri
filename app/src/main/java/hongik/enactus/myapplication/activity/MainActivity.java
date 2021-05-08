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
import android.widget.Toast;

import com.kakao.auth.AuthType;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.URI;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private SessionCallback sessionCallback = new SessionCallback();
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mContext = getApplicationContext();

        Button btn_start_splash = findViewById(R.id.btn_start_splash);
        btn_start_splash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                startMain();
            }
        });
    }
    private void viewInit(){
       // linearLayout = findViewById(R.id.linearLayout);
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

    void startMain() {
        sessionCallback.setContext(mContext);   //카카오 로그인용

        Button btn_tmp = findViewById(R.id.btn_tmp);
        btn_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FragmentActivity.class);
                startActivity(intent);
            }
        });
        
        Session session = Session.getCurrentSession();
        System.out.println(session.getTokenInfo());
        session.addCallback(sessionCallback);

        EditText txt_login_email = findViewById(R.id.txt_login_email);
        EditText txt_login_pwd = findViewById(R.id.txt_login_pwd);

        Button btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    JSONObject jsonValues = new JSONObject();
                    jsonValues.put("email", txt_login_email.getText().toString());
                    jsonValues.put("password", txt_login_pwd.getText().toString());

                    NetworkTask networkTask = new NetworkTask(URI.hostName + URI.login, jsonValues);
                    networkTask.setContext(mContext);
                    networkTask.execute();

                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        });

        LoginButton btn_kakao_login = findViewById(R.id.btn_kakao_login);
        btn_kakao_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.open(AuthType.KAKAO_LOGIN_ALL, MainActivity.this);

            }
        });

        Button btn_register = findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(mContext, RegisterAccountActivity.class);
                startActivity(intent3);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("JIHO_", "onActivityResult");
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("JIHO_", "DESTORY");
        // 세션 콜백 삭제
        Session.getCurrentSession().removeCallback(sessionCallback);

        //로그아웃 처리 코드 (세션 삭제랑 다른점..?)
        UserManagement.getInstance()
                .requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Toast.makeText(MainActivity.this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}
