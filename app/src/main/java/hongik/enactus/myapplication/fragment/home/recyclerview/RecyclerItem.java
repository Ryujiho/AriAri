package hongik.enactus.myapplication.fragment.home.recyclerview;

import android.graphics.drawable.Drawable;

public class RecyclerItem {
    private Drawable status1 ;
    private Drawable status2 ;
    private String alarmTime ;
    private String pillName ;

    public void setStatus1(Drawable status1) {
        this.status1 = status1;
    }
    public void setStatus2(Drawable status2) {
        this.status2 = status2;
    }
    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
    public void setPillName(String pillName) {
        this.pillName = pillName;
    }

    public Drawable getStatus1() {
        return status1;
    }
    public Drawable getStatus2() {
        return status2;
    }
    public String getAlarmTime() {
        return alarmTime;
    }
    public String getPillName() {
        return pillName;
    }

}