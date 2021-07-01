package hongik.enactus.myapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.Tag;
import hongik.enactus.myapplication.fragment.onboarding.Fragment1;
import hongik.enactus.myapplication.fragment.onboarding.Fragment2;
import hongik.enactus.myapplication.fragment.onboarding.FragmentWIFI;
import hongik.enactus.myapplication.fragment.onboarding.PageAdapter;

public class onBoardingActivity extends AppCompatActivity{
    final int MAX_PAGE = 5;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Button btn_next, btn_previous;

    void viewInit(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        btn_next= findViewById(R.id.btn_next);
        btn_previous= findViewById(R.id.btn_previous);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewInit();

        // ViewPager 와 adapter 연결
        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager());
        pageAdapter.addItem(new FragmentWIFI());
        pageAdapter.addItem(new Fragment1());
        pageAdapter.addItem(new Fragment2());
        viewPager.setAdapter(pageAdapter);

        if(viewPager.getCurrentItem() == 0){
            btn_previous.setVisibility(View.INVISIBLE);
        }

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
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager, true);
        tabLayout.setBackgroundResource(R.drawable.tab_selector);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); //나중에 삭제
    }
}