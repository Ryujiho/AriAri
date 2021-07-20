package hongik.enactus.myapplication.activity;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.net.URLEncoder;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.NetworkTask;
import hongik.enactus.myapplication.common.URI;
import hongik.enactus.myapplication.fragment.alarm.FragmentName;
import hongik.enactus.myapplication.fragment.alarm.FragmentPeriod;
import hongik.enactus.myapplication.fragment.alarm.FragmentTime;
import hongik.enactus.myapplication.fragment.onboarding.PageAdapter;

public class CreateAlarmActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Button btn_previous;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

            viewPager = findViewById(R.id.viewPager_alarm);
            btn_previous= findViewById(R.id.btn_previous);

            try {
                StringBuilder urlBuilder = new StringBuilder(URI.getDrugInfoService);
                urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + URLEncoder.encode(URI.getDrugInfoService_key, "UTF-8")); //*Service Key*//*
                urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); //*한 페이지 결과 수*//*
                urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //*페이지 번호*//*
                urlBuilder.append("&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); //*페이지 번호*//*
                NetworkTask networkTask = new NetworkTask(urlBuilder.toString(), new JSONObject());
                networkTask.setRequestType("GET");
                networkTask.execute();

            } catch (Exception e){
                e.printStackTrace();
            }

            // ViewPager 와 adapter 연결
            PageAdapter pageAdapter = new PageAdapter(this);
            pageAdapter.addFragment(new FragmentName());
            pageAdapter.addFragment(new FragmentTime());
            pageAdapter.addFragment(new FragmentPeriod());
            viewPager.setAdapter(pageAdapter);

            // 현재 페이지 번호 표시
            tabLayout = findViewById(R.id.tabLayout_alarm);

            new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                @Override
                public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {

                }
            }).attach();


    }
}
