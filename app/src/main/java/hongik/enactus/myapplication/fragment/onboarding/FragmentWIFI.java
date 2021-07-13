package hongik.enactus.myapplication.fragment.onboarding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import hongik.enactus.myapplication.R;
import hongik.enactus.myapplication.common.Tag;

public class FragmentWIFI extends Fragment implements AutoPermissionsListener {
    private List<String> arrayList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private Context mContext;
    private IntentFilter intentFilter = new IntentFilter();
    private WifiManager wifiManager;

    private Spinner spinner;
    private EditText editTxt_wifi_pwd;
    private Button btn_wifi_scan, btn_wifi_connect;

    // wifiManager.startScan()
    BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // 스캔 성공 여부 반환
            boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
            if(success){
                scanSuccess();
            } else {
                scanFailure();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding_step3_wifi,container,false);

        btn_wifi_scan = view.findViewById(R.id.btn_wifi_scan);
        spinner = view.findViewById(R.id.spinner_wifi_list);
        editTxt_wifi_pwd = view.findViewById(R.id.editTxt_wifi_pwd);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(R.color.green_textColor);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //txt_selected_wifi.setText("선택된 아이템이 없음.");
            }
        });

        // 권한에 대한 허가 요청
        AutoPermissions.Companion.loadAllPermissions(getActivity(),101);

        mContext = view.getContext().getApplicationContext();
        wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        mContext.registerReceiver(wifiScanReceiver, intentFilter);

        btn_wifi_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wifiScan();
            }
        });

        return view;
    }

    public void wifiScan(){
        boolean success = wifiManager.startScan();

        String result;
        if (!success) result = "Wifi Scan에 실패하였습니다";
        else result = "Wifi Scan에 성공하였습니다";
        Toast.makeText(mContext, result ,Toast.LENGTH_SHORT).show();

    }


    private void scanSuccess() {
        List<ScanResult> scanResult = wifiManager.getScanResults();
        Log.e(Tag.TEST, scanResult.toString());

        for (ScanResult result : scanResult) {
                arrayList.add(result.SSID);
                connectAdptToSpinner();
        }

    }
    // Connect Adapter
    public void connectAdptToSpinner() {
        // Setup spinAdpt & connect spinner to adapter
        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        List<ScanResult> results = wifiManager.getScanResults();
    }

    /*/ Permission 관련 메서드 */
    @Override
    public void onDenied(int i, @NotNull String[] strings) {
        Toast.makeText(mContext, "onDenied" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGranted(int i, @NotNull String[] strings) {
        Toast.makeText(mContext, "onGranted" ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AutoPermissions.Companion.parsePermissions(getActivity(), requestCode, permissions, this);
    }
}
