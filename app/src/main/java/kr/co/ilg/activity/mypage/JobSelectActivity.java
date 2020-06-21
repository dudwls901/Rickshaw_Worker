package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

import org.w3c.dom.Text;

public class JobSelectActivity extends AppCompatActivity {

    Button yong, botong;
    TextView sltTV;
    int btnFlag = 0;
    int btnFlag2 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_select);

        yong = findViewById(R.id.yong);
        botong = findViewById(R.id.botong);
        sltTV = findViewById(R.id.sltTV);

        findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelectActivity.this, CareerActivity.class);
                startActivity(intent);
            }
        });
        yong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFlag == 0) {
                    yong.setBackground(getDrawable(R.drawable.custom_btn_mainclr));
                    sltTV.setText(sltTV.getText().toString() + "  용접공");
                    btnFlag = 1;
                }
                else {
                    yong.setBackground(getDrawable(R.drawable.custom_btn_lightclr));
                    btnFlag = 0;
                }
            }
        });
        botong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnFlag2 == 0) {
                    botong.setBackground(getDrawable(R.drawable.custom_btn_mainclr));
                    sltTV.setText(sltTV.getText().toString() + "  보통인부");
                    btnFlag2 = 1;
                }
                else {
                    botong.setBackground(getDrawable(R.drawable.custom_btn_lightclr));
                    btnFlag2 = 0;
                }
            }
        });

    }
}
