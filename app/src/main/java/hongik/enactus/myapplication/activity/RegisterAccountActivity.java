package hongik.enactus.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import hongik.enactus.myapplication.R;

public class RegisterAccountActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText CreateName = findViewById(R.id.createName);
        EditText CreateEmail = findViewById(R.id.createEmail);
        EditText CreatePassword = findViewById(R.id.createPassword);

        Button CreateBtn = findViewById(R.id.createBtn);
        CreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //비번 다섯글자이상인지
                    JSONObject jsonValues = new JSONObject();
                    jsonValues.put("name", CreateName.getText().toString());
                    jsonValues.put("email", CreateEmail.getText().toString());
                    jsonValues.put("password", CreatePassword.getText().toString());

                    new NetworkTask("hongikeatme.herokuapp.com" + "/api/users/register", jsonValues).execute();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
