package hongik.enactus.myapplication.fragment.onboarding;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import hongik.enactus.myapplication.R;

public class Fragment3 extends Fragment {

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

        // layout 의 view 정보 가져오기
        View view = inflater.inflate(R.layout.fragment_screen3,container,false);

        ImageView imgView_loading = (ImageView) view.findViewById(R.id.imgView_loading);
        Drawable drawable = getResources().getDrawable(R.drawable.onboard_selected_dot, null);
        imgView_loading.setBackground(drawable);

        return view;
    }
}
