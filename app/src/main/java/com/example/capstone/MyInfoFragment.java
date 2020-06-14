package kr.co.ilg.activity.findwork;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.capstone.R;

public class MyInfoFragment extends Fragment {

    private MyInfoViewModel myInfoViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myInfoViewModel =
                ViewModelProviders.of(this).get(MyInfoViewModel.class);
        View root = inflater.inflate(R.layout.fragment_myinfo, container, false);
        final TextView textView = root.findViewById(R.id.text_myinfo);
        myInfoViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
