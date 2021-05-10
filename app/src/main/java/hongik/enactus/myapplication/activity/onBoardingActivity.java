package hongik.enactus.myapplication.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import hongik.enactus.myapplication.common.Tag;
import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.fragment.onboarding.Fragment0;
import hongik.enactus.myapplication.fragment.onboarding.Fragment1;
import hongik.enactus.myapplication.fragment.onboarding.Fragment2;
import hongik.enactus.myapplication.fragment.onboarding.Fragment3;
import hongik.enactus.myapplication.fragment.onboarding.Fragment4;
import hongik.enactus.myapplication.fragment.onboarding.PageAdapter;

public class onBoardingActivity extends AppCompatActivity{
    final int MAX_PAGE = 5;
    private ViewPager viewPager;
    //private TabLayout tabLayout;
    private Button btn_next, btn_previous, btn_close;

    void viewInit(){

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btn_next= findViewById(R.id.btn_next);
        btn_previous= findViewById(R.id.btn_previous);
        btn_close= findViewById(R.id.btn_close);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewInit();

        // ViewPager 와 adapter 연결
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        pageAdapter.addItem(new Fragment0());
        pageAdapter.addItem(new Fragment1());
        pageAdapter.addItem(new Fragment2());
        pageAdapter.addItem(new Fragment3());
        pageAdapter.addItem(new Fragment4());
        viewPager.setAdapter(pageAdapter);

        if(viewPager.getCurrentItem() == 0){
            btn_previous.setVisibility(View.INVISIBLE);
        }

        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Tag.TEST, "CLICKED btn_previous");
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        // 이전버튼 클릭 시 다음 페이지로 이동
        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Tag.TEST, "CLICKED btn_previous");
                int currentIndex = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentIndex-1);

                // 첫번재 페이지에서는 버튼 비활성화
                if(viewPager.getCurrentItem() == 0){
                    btn_previous.setVisibility(View.INVISIBLE);
                } else {
                    btn_next.setVisibility(View.VISIBLE);
                }
            }
        });

        // 다음버튼 클릭 시 다음 페이지로 이동
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(Tag.TEST, "CLICKED btn_next");
                int currentIndex = viewPager.getCurrentItem();
                viewPager.setCurrentItem(currentIndex+1);

                // 마지막 페이지에서는 btn_next 버튼 비활성화
                if(viewPager.getCurrentItem() == MAX_PAGE-1){
                    btn_next.setVisibility(View.INVISIBLE);
                } else {
                    btn_previous.setVisibility(View.VISIBLE);
                }
            }
        });

        // 현재 페이지 번호 표시
        /*tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("1"));
        tabLayout.addTab(tabLayout.newTab().setText("2"));
        tabLayout.addTab(tabLayout.newTab().setText("3"));
        tabLayout.addTab(tabLayout.newTab().setText("4"));
        tabLayout.addTab(tabLayout.newTab().setText("5"));

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

        });*/
    }
}