package hongik.enactus.myapplication.fragment.onboarding;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayout;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.activity.HomeActivity;
import hongik.enactus.myapplication.common.Tag;

public class Fragment0 extends Fragment {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager viewPager;
    private TabLayout tab_onBoarding;
    private FragmentStateAdapter pagerAdapter;
    private int index;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_screen0,container,false);
        return view;
    }
}
