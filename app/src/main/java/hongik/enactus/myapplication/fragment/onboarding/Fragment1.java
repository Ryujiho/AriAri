package hongik.enactus.myapplication.fragment.onboarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import hongik.enactus.myapplication.R;

public class Fragment1 extends Fragment {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager viewPager;
    private TabLayout tab_onBoarding;
    private int index;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return (ViewGroup) inflater.inflate(
                R.layout.fragment_onboarding_step1, container, false);
    }
}
