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

public class JobSelectActivity extends AppCompatActivity implements View.OnClickListener{

    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16;
    Button[] job ={btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12,btn13,btn14,btn15,btn16};
    int[] jobid = {R.id.btn1,R.id.btn2,R.id.btn3,R.id.btn4,R.id.btn5,R.id.btn6,R.id.btn7,R.id.btn8,R.id.btn9,R.id.btn10,R.id.btn11,
            R.id.btn12,R.id.btn13,R.id.btn14,R.id.btn15,R.id.btn16};
    int check[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
    TextView sltTV;
    String jobcheck="",jobs="";
    int btnFlag = 0;
    int btnFlag2 = 0;
    int i,j=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_select);

        for( i=0; i<16; i++){
            job[i] = (Button)findViewById(jobid[i]);
            job[i].setOnClickListener(this);  // 직업버튼 인플레이션
        }
            sltTV = (TextView) findViewById(R.id.sltTV);


        findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelectActivity.this, CareerActivity.class);
                intent.putExtra("jobs",jobs);
                startActivity(intent);
            }
        }); // 확인버튼 인플레이션 및 intent 구현

    }
    @Override
    public void onClick(View v) {
        jobs = "";
        for (int k = 0; k < 16; k++) {
            if(jobid[k]==v.getId()) {
                if (check[k] == 0) {
                    j+=1;
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_mainclr));
                    check[k]=1;
                } else {
                    j-=1;
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_lightclr)); // wrap 텍스트뷰 3개 만들어서 상단바 제어
                    check[k]=0;
                }
            }

            if(check[k]==1) jobs = job[k].getText().toString() + "  " +jobs;

        }
        sltTV.setText(jobs);
        if(j==3) {
            for(int i=0; i<16; i++){
                if(check[i]==0) job[i].setEnabled(false);
                //토스트메세지까지
            }
        }
        else {
            for (int i = 0; i < 16; i++) {
                if (check[i] == 0) job[i].setEnabled(true);
            }
        }
    }

}
