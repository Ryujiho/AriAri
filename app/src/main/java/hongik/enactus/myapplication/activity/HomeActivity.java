package hongik.enactus.myapplication.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import hongik.enactus.myapplication.R;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        openAlertDialog();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_alarmlist,R.id.navigation_connection, R.id.navigation_configuration)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        FloatingActionButton fab_add_alarm = findViewById(R.id.fab_add_alarm);
        fab_add_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateAlarmActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    void openAlertDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.ErrorDialogTheme);
        builder.setMessage(R.string.alert_home_start)
                .setTitle(R.string.alert_home_start_title)
                .setPositiveButton(R.string.alert_home_ok_btn, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), onBoardingActivity.class);
                        startActivity(intent);

                    }
                })
                .setCancelable(false);
        AlertDialog alert = builder.show();

    }
}
