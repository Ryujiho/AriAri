package hongik.enactus.myapplication.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.Session;

import hongik.enactus.myapplication.R;

public class AlarmActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        Session session = Session.getCurrentSession();

        Log.i("JIHO", session.getTokenInfo().toString());
    }
}
