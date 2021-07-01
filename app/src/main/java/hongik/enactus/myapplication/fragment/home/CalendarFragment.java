package hongik.enactus.myapplication.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.michalsvec.singlerowcalendar.calendar.CalendarChangesObserver;
import com.michalsvec.singlerowcalendar.calendar.CalendarViewManager;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendar;
import com.michalsvec.singlerowcalendar.calendar.SingleRowCalendarAdapter;
import com.michalsvec.singlerowcalendar.selection.CalendarSelectionManager;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.DateUtils;
import hongik.enactus.myapplication.fragment.home.recyclerview.CalendarAlarmListAdapter;
import hongik.enactus.myapplication.fragment.home.recyclerview.RecyclerItem;

public class CalendarFragment extends Fragment {

    private SingleRowCalendar singleRowCalendar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        // 1. 이미지 뷰
        ImageView imgView = root.findViewById(R.id.testImgview);
        imgView.setImageResource(R.drawable.main);

        // 2. 알람 리스트 리사이클러뷰
        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();

        for (int i=0; i<100; i++) {
            RecyclerItem item = new RecyclerItem();
            item.setAlarmTime("Time "+i);
            item.setPillName("Pill "+i);
            mList.add(item);
        }

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView) ;
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext())) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        CalendarAlarmListAdapter adapter = new CalendarAlarmListAdapter(mList) ;
        recyclerView.setAdapter(adapter) ;


        // 3. 캘린더
        singleRowCalendar = root.findViewById(R.id.calendarView);

        // UI 로직을 설정한다.
        singleRowCalendar.setCalendarViewManager(new CalendarViewManager() {
            @Override
            public int setCalendarViewResourceId(int position, @NotNull Date date, boolean isSelected) {
                // 선택된 날짜의 시간을 가져온다.
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                // 선택된 날짜와 선택되지 않은 날짜의 레이아웃을 리턴한다.
                if (isSelected){
                    return R.layout.selected_calendar_item;
                } else {
                    return R.layout.calendar_item;
                }
            }

            @Override
            public void bindDataToCalendarView(@NotNull SingleRowCalendarAdapter.CalendarViewHolder holder, @NotNull Date date, int position, boolean isSelected) {
                // calendarView의 날짜와 요일을 설정한다.
                TextView tvDateCalendarItem = holder.itemView.findViewById(R.id.tv_date_calendar_item);
                tvDateCalendarItem.setText(DateUtils.getDayNumber(date));

                TextView tvDayCalendarItem = holder.itemView.findViewById(R.id.tv_day_calendar_item);
                tvDayCalendarItem.setText(DateUtils.getDayName(date));
            }
        });

        // 캘린더 값 변경 시
        singleRowCalendar.setCalendarChangesObserver(new CalendarChangesObserver() {
            @Override
            public void whenWeekMonthYearChanged(@NotNull String s, @NotNull String s1, @NotNull String s2, @NotNull String s3, @NotNull Date date) {

            }

            @Override
            public void whenSelectionChanged(boolean isSelected, int position, @NotNull Date date) {
                // 선택된 캘린더 날짜에 해당하는 하단 내용 변경 (RecyclerView 추가 예정)

            }

            @Override
            public void whenCalendarScrolled(int i, int i1) {

            }

            @Override
            public void whenSelectionRestored() {

            }

            @Override
            public void whenSelectionRefreshed() {

            }
        });

        // 캘린더의 선택가능여부 제어
        singleRowCalendar.setCalendarSelectionManager(new CalendarSelectionManager() {
            @Override
            public boolean canBeItemSelected(int position, @NotNull Date date) {
                return true;
            }
        });

        singleRowCalendar.setDates(getDates());
        singleRowCalendar.init();
        singleRowCalendar.select(0);

        return root;
    }
    private List getDates(){
        List<Date> list = new ArrayList<>();

        // 현재 날짜 시작
        Calendar cal = Calendar.getInstance();

        int FUTURE_DAYS_COUNT = 30;
         for (int i =0; i< FUTURE_DAYS_COUNT;i++){
             list.add(cal.getTime());
             cal.add(Calendar.DATE, +1);
        }

        return list;
    }
}