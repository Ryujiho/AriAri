package hongik.enactus.myapplication.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.Tag;
import hongik.enactus.myapplication.fragment.onboarding.Fragment1;
import hongik.enactus.myapplication.fragment.onboarding.Fragment2;
import hongik.enactus.myapplication.fragment.onboarding.FragmentWIFI;
import hongik.enactus.myapplication.fragment.onboarding.PageAdapter;

public class onBoardingActivity extends AppCompatActivity{
    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Button btn_previous;

    void viewInit(){
        viewPager = (ViewPager2) findViewById(R.id.viewPager);
        btn_previous= findViewById(R.id.btn_previous);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);
        viewInit();


        // ViewPager 와 adapter 연결
        PageAdapter pageAdapter = new PageAdapter(this);
        pageAdapter.addFragment(new FragmentWIFI());
        pageAdapter.addFragment(new Fragment1());
        pageAdapter.addFragment(new Fragment2());
        viewPager.setAdapter(pageAdapter);

        // 현재 페이지 번호 표시
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull @NotNull TabLayout.Tab tab, int position) {

            }
        }).attach();




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
                }
            }
        });
/*
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
        });*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed(); //나중에 삭제
    }
}