package hongik.enactus.myapplication.activity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.NetworkTask;
import hongik.enactus.myapplication.common.URI;

public class RegisterUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        EditText name = findViewById(R.id.createName);
        EditText email = findViewById(R.id.createEmail);
        EditText password1 = findViewById(R.id.createPassword);
        EditText password2 = findViewById(R.id.create_check_password);

        Button CreateBtn = findViewById(R.id.createBtn);
        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd1 = password1.getText().toString();
                String pwd2 = password2.getText().toString();

                if(pwd1.equals(pwd2)) {
                    try {
                        //비번 다섯글자이상인지
                        JSONObject jsonValues = new JSONObject();
                        jsonValues.put("name", name.getText().toString());
                        jsonValues.put("email", email.getText().toString());
                        jsonValues.put("password", pwd1);

                        NetworkTask networkTask = new NetworkTask(URI.hostName + URI.registerUsers, jsonValues);
                        networkTask.setContext(RegisterUserActivity.this);
                        networkTask.setRequestType("POST");
                        networkTask.execute();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterUserActivity.this, R.style.ErrorDialogTheme);
                    builder.setMessage(R.string.alert_register_error_pwd)
                            .setTitle(R.string.alert_register_error_title)
                            .setPositiveButton("확인", null)
                            .setCancelable(false)
                            .show();
                }
            }
        });

    }
}
