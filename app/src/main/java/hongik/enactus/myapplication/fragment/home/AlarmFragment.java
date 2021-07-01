package hongik.enactus.myapplication.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.fragment.home.recyclerview.AlarmListAdapter;
import hongik.enactus.myapplication.fragment.home.recyclerview.RecyclerItem;

public class AlarmFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_alarmlist, container, false);

        TextView text_alarmlist = root.findViewById(R.id.text_alarmlist);
        text_alarmlist.setText("배급 예약22");

        // 리사이클러뷰
        RecyclerView recyclerView = root.findViewById(R.id.recyclerView_alarmList);
        recyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2));

        ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();

        for (int i=0; i<5; i++) {
            RecyclerItem item = new RecyclerItem();
            item.setAlarmTime("Time "+i);
            item.setPillName("Pill "+i);
            mList.add(item);
        }

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        AlarmListAdapter adapter = new AlarmListAdapter(mList) ;
        recyclerView.setAdapter(adapter);


       return root;
    }
}