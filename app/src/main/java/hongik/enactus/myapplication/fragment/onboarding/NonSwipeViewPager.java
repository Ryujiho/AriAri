package hongik.enactus.myapplication.fragment.onboarding;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

// 스와이프 막은 custom View Pager
public class NonSwipeViewPager extends ViewPager {

    public NonSwipeViewPager(@NonNull Context context) {
        super(context);
    }

    public NonSwipeViewPager(@NonNull Context context,  AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
}