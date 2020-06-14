package kr.co.ilg.activity.findwork;

import android.app.ActionBar;
import android.app.Activity;
import android.drm.DrmStore;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.capstone.R;

public class FieldInfoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_info);
        ActionBar ad=getActionBar();
        ad.setDisplayHomeAsUpEnabled(true);

    }


}
