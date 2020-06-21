package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.login.LoginActivity;

public class LocalSelectActivity extends AppCompatActivity {

    Button okBtn, SDMG;
    TextView sltTV;
    int btnFlag = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_select);

        okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LocalSelectActivity.this, JobSelectActivity.class);
                startActivity(intent);
            }
        });

        sltTV = findViewById(R.id.sltTV);
        SDMG = findViewById(R.id.SDMG);
        SDMG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFlag == 0) {
                    SDMG.setBackground(getDrawable(R.drawable.custom_btn_mainclr));
                    sltTV.setText(sltTV.getText().toString() + "서대문구");
                    btnFlag = 1;
                }
                else {
                    SDMG.setBackground(getDrawable(R.drawable.custom_btn_lightclr));
                    btnFlag = 0;
                }
            }
        });

    }
}
