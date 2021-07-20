package hongik.enactus.myapplication.fragment.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import hongik.enactus.myapplication.R;

public class FragmentName extends Fragment {

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
        View view = inflater.inflate(R.layout.fragment_alarm_create_name, container, false);


        List<String> arrayList = new ArrayList<>();
        arrayList.add("가나");arrayList.add("가나");
        arrayList.add("가나다");
        arrayList.add("가나라");
        arrayList.add("가나마");
        /*List<String> arrayList = DrugInfo.getList();
        Log.e(Tag.TEST, arrayList.toString());
*/
        AutoCompleteTextView autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> listAdapter =  new ArrayAdapter<>(view.getContext().getApplicationContext(),
                R.layout.support_simple_spinner_dropdown_item, arrayList);
        autoCompleteTextView.setAdapter(listAdapter);

        return view;
    }
}
