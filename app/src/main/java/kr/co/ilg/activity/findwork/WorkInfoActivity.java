package kr.co.ilg.activity.findwork;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

public class WorkInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_info);
        //ActionBar ad=getActionBar();
        //ad.setDisplayHomeAsUpEnabled(true);

    }
}
