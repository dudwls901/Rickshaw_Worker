package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import kr.co.ilg.activity.findwork.Sharedpreference;
import kr.co.ilg.activity.login.LoginRequest;
import kr.co.ilg.activity.login.SplashActivity;

public class JobSelectActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, okBtn;
    Button[] job = {null, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
    int[] jobid = {0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};
    int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] job_code;
    TextView sltTV;
    String jobcheck = "", jobs = "";
    int isUpdate;  // 1 > 수정  0 > 회원가입
    int i, j = 0;
    int jobnum;
    boolean k=false;
    String[] jobnames = new String[16];

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

        Intent modifyIntent = getIntent();
        isUpdate = modifyIntent.getIntExtra("isUpdate", 0);  // modify
        jobnum =modifyIntent.getIntExtra("exjobnum", 0);

//        Toast.makeText(getApplicationContext(), "어디서 왔나~ " + isUpdate, Toast.LENGTH_SHORT).show();

        for (i = 1; i < 17; i++) {
//            Log.d("kkk", "왜 이것부터하니");
            job[i] = findViewById(jobid[i]);
            job[i].setOnClickListener(this);  // 직업버튼 인플레이션
        }

        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    JSONArray a = jResponse.getJSONArray("response");
                    Log.d("ttttttttttttttt", String.valueOf(a.length()));
                    for(int i=0; i<a.length(); i++){
                        JSONObject item=  a.getJSONObject(i);
                        jobnames[i] = item.getString("jobname");
                        job[i+1].setText(jobnames[i]);
                        Log.d("asdf",jobnames[i]);
                        k=true;

                    }
                } catch (Exception e) {
                    Log.d("mytest1111111", e.toString()); // 오류 출력
                }

            }
        };
        GetJobsRequest lRequest = new GetJobsRequest(aListener); // Request 처리 클래스
        RequestQueue queue1 = Volley.newRequestQueue(JobSelectActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue1.add(lRequest);


        sltTV = (TextView) findViewById(R.id.sltTV);

        okBtn = findViewById(R.id.okBtn); // 확인버튼

        if (isUpdate == 1)
            okBtn.setText("수정");
        else
            okBtn.setText("확인");

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(JobSelectActivity.this, CareerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                if (jobs.equals("")) {
                    Toast.makeText(JobSelectActivity.this, "직종을 한 개 이상 선택해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (isUpdate == 1) {  // 수정
                        intent.putExtra("isUpdate", isUpdate);  // 1
                        intent.putExtra("jobnum", jobnum);
                    }
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
                        intent.putExtra("job_code", job_code);
                        Log.d("kkkkkkkkkkkkk=", String.valueOf(job_code[0]) + job_code[1] + job_code[2]);
                        startActivity(intent);


                }
            }
        }); // 확인버튼 인플레이션 및 intent 구현

    }

    @Override
    public void onClick(View v) {
        jobs = "";
        int a = 0;
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

                Log.d("kkkkkkk=", String.valueOf(job_code[0]) + job_code[1] + job_code[2]);
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
