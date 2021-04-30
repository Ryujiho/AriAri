package hongik.enactus.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Button StartBtn = findViewById(R.id.startBtn);
        StartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_main);
                LoginFunc();
            }
        });

    }

    void LoginFunc() {

        Button LoginBtn = findViewById(R.id.loginBtn);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*ContentValues values = new ContentValues();
            values.put("id", id);
            values.put("pw", pw);
            url, values*/
                //new NetworkTask("hongikeatme.herokuapp.com/api/users/info", "id").execute();
                Intent intent2 = new Intent(getApplicationContext(), FragmentActivity.class);
                startActivity(intent2);
                finish();
            }
        });
        Button CreateAccBtn = findViewById(R.id.createAccBtn);
        CreateAccBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(getApplicationContext(), CreateAccountActivity.class);
                startActivity(intent3);
                //finish();
            }
        });
    }
}