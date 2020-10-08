package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;


public class LocalSelectActivity extends AppCompatActivity {

    Button okBtn;
    TextView sltTV;
    private LocalSelectList localSelect;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_select);

        localSelect = (LocalSelectList)findViewById(R.id.localView);
        localSelect.setOnResultSelectListener(
                new LocalSelectList.OnResultSelectListener() {
                    @Override
                    public void onResultSelected(boolean isFinish, String city, String town) {
                        String result = city + town;
                        if (isFinish) {
                            Toast.makeText(LocalSelectActivity.this, "선택 완료 :" + result, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LocalSelectActivity.this, "선택 미완료 :" + result, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocalSelectActivity.this, JobSelectActivity.class);
                startActivity(intent);
            }
        });

        sltTV = findViewById(R.id.sltTV);

    }
}
