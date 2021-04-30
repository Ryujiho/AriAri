package hongik.enactus.myapplication;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 4;

    List<Fragment> fragments = new ArrayList<>();

    public PageAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}