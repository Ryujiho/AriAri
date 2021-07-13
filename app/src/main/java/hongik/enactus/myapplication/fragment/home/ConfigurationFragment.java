package hongik.enactus.myapplication.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import hongik.enactus.myapplication.R;

public class ConfigurationFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_configuration, container, false);
       // final TextView textView = root.findViewById(R.id.text_notifications);
        return root;
    }
}