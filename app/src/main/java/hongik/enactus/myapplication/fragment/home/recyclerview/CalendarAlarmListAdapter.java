package hongik.enactus.myapplication.fragment.home.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import hongik.enactus.myapplication.R;

public class CalendarAlarmListAdapter extends RecyclerView.Adapter<CalendarAlarmListAdapter.ViewHolder> {

    private ArrayList<RecyclerItem> mData = null;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_alarm_time, txt_pill_name ;
        ImageView img_status_1, img_status_2;

        ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            txt_alarm_time = itemView.findViewById(R.id.txt_alarm_time) ;
            txt_pill_name = itemView.findViewById(R.id.txt_pill_name) ;
            img_status_1 = itemView.findViewById(R.id.img_status_1);
            img_status_2 = itemView.findViewById(R.id.img_status_2);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public CalendarAlarmListAdapter(ArrayList<RecyclerItem> list) {
        mData = list ;

    }

    // 뷰 홀더 객체를 생성한다.
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        ViewHolder vh = new ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        RecyclerItem item = mData.get(position) ;

        String alarmTime = item.getAlarmTime() ;
        holder.txt_alarm_time.setText(alarmTime) ;

        String pillName = item.getPillName() ;
        holder.txt_pill_name.setText(pillName) ;
    }

    @Override
    public int getItemCount() {
        return mData.size() ;
    }



}
