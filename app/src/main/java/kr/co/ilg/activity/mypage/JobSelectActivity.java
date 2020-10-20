package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

import org.w3c.dom.Text;

public class JobSelectActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16;
    Button[] job = {null,btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
    int[] jobid = {0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};
    int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0};
    int[] job_code;
    TextView sltTV;
    String jobcheck = "", jobs = "";
    int btnFlag = 0;
    int btnFlag2 = 0;
    int i, j = 0;

    String worker_email, worker_pw, worker_name, worker_gender, worker_birth, worker_phonenum, hope_local_sido, hope_local_sigugun, worker_certicipate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_select);

        Intent receiver = getIntent();
        worker_email = receiver.getExtras().getString("worker_email");
        worker_pw = receiver.getExtras().getString("worker_pw");
        worker_name = receiver.getExtras().getString("worker_name");
        worker_gender = receiver.getExtras().getString("worker_gender");
        worker_birth = receiver.getExtras().getString("worker_birth");
        worker_phonenum = receiver.getExtras().getString("worker_phonenum");
        worker_certicipate = receiver.getExtras().getString("worker_certicipate");
        hope_local_sido = receiver.getExtras().getString("hope_local_sido");
        hope_local_sigugun = receiver.getExtras().getString("hope_local_sigugun");

        //certicipate 추가
        for (i = 1; i < 17; i++) {
            job[i] =  findViewById(jobid[i]);
            job[i].setOnClickListener(this);  // 직업버튼 인플레이션
        }
        sltTV = (TextView) findViewById(R.id.sltTV);


        findViewById(R.id.okBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JobSelectActivity.this, CareerActivity.class);

                if(!jobs.equals(""))
                {
                intent.putExtra("worker_email", worker_email);
                intent.putExtra("worker_pw", worker_pw);
                intent.putExtra("worker_gender", worker_gender);
                intent.putExtra("worker_name", worker_name);
                intent.putExtra("worker_birth", worker_birth);
                intent.putExtra("worker_phonenum", worker_phonenum);
                intent.putExtra("worker_certicipate", worker_certicipate);
                intent.putExtra("hope_local_sido", hope_local_sido);
                intent.putExtra("hope_local_sigugun", hope_local_sigugun);
                intent.putExtra("jobs", jobs);
                //certicipate추가
                intent.putExtra("job_code",job_code);
                Log.d("kkkkkkkkkkkkk=", String.valueOf(job_code[0]) + job_code[1] + job_code[2]);
                startActivity(intent);
                }
                else
                {
                    Toast.makeText(JobSelectActivity.this, "직종을 한 개 이상 선택해주세요.",Toast.LENGTH_SHORT).show();
                }

            }
        }); // 확인버튼 인플레이션 및 intent 구현

    }

    @Override
    public void onClick(View v) {
        jobs = "";
        int a=0;
         job_code = new int[3];
        for (int k = 1; k < 17; k++) {

            if (jobid[k] == v.getId()) {
                if (check[k] == 0) {
                    j += 1;
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_mainclr));
                    check[k] = 1;

                } else {
                    j -= 1;
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_lightclr)); // wrap 텍스트뷰 3개 만들어서 상단바 제어
                    check[k] = 0;
                }
            }

            if (check[k] == 1) {
                jobs = job[k].getText().toString() + "  " + jobs;

                    job_code[a] = k;
                    a++;

                Log.d("kkkkkkk=", String.valueOf(job_code[0])+job_code[1]+job_code[2]);
                //경력코드는 반대로 되어있음
            }

        }
        sltTV.setText(jobs);
        if (j == 3) {
            for (int i = 1; i < 17; i++) {
                if (check[i] == 0) job[i].setEnabled(false);
                //토스트메세지까지
            }
        } else {
            for (int i = 1; i < 17; i++) {
                if (check[i] == 0) job[i].setEnabled(true);
            }
        }
    }

}
