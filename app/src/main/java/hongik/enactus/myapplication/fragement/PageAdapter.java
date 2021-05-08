package hongik.enactus.myapplication.fragement;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import hongik.enactus.myapplication.fragement.Fragment1;
import hongik.enactus.myapplication.fragement.Fragment2;

public class PageAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 4;

    List<Fragment> fragments = new ArrayList<>();

    public PageAdapter(FragmentManager fm) {
        super(fm);
        fragments.add(new Fragment1());
        fragments.add(new Fragment2());
        fragments.add(new Fragment3());
        fragments.add(new Fragment4());
        fragments.add(new Fragment5());
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