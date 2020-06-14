package kr.co.ilg.fragment;

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

public class MyFieldFragment extends Fragment {
    private MyFieldViewModel myFieldViewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        myFieldViewModel =
                ViewModelProviders.of(this).get(MyFieldViewModel.class);
        View root = inflater.inflate(R.layout.myfield_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_myfield);
        myFieldViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
