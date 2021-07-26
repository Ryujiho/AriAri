package hongik.enactus.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.Tag;

public class MainActivity extends AppCompatActivity {
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_start);
        mContext = MainActivity.this;

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(Tag.TEST, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        Log.d(Tag.TEST, "token: " + token);
                    }
                });


        Button btn_register_main = findViewById(R.id.btn_register_main);
        btn_register_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent3 = new Intent(mContext, RegisterUserActivity.class);
                startActivity(intent3);
            }
        });

        Button btn_login_main = findViewById(R.id.btn_login_main);
        btn_login_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(mContext, LoginActivity.class);
                startActivity(intent3);
            }
        });


        Button btn_tmp = findViewById(R.id.btn_onboarding_tmp);
        Button btn_home_tmp = findViewById(R.id.btn_home_tmp);
        btn_home_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                //finish();;
            }
        });

        btn_tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), onBoardingActivity.class);
                startActivity(intent);
            }
        });

    }

}
