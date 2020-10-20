package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;

import java.util.ArrayList;

//import kr.co.ilg.activity.findwork.MainActivity;
//import kr.co.ilg.activity.login.LoginActivity;
import com.example.capstone.MainActivity;
import com.kakao.auth.network.response.JSONArrayResponse;

public class AccountAddActivity extends AppCompatActivity {
    AlertDialog dialogg;
    Button addBtn;
    Spinner bankSelectSpn;
    ArrayList<String> bSList;
    ArrayAdapter<String> bSAdapter;
    TextView nextTimeTV;
    String worker_email, worker_pw, worker_name, worker_gender, worker_birth, worker_phonenum, hope_local_sido, hope_local_sigugun, worker_bankname,worker_bankaccount,worker_certicipate;
    String[] jobarray,careerarray;
    int[] job_code;
    EditText accountNumET,nameET;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_add);

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
        jobarray = receiver.getStringArrayExtra("jobarray");
        careerarray = receiver.getStringArrayExtra("careerarray");
        job_code = receiver.getIntArrayExtra("job_code");
        Log.d("receiver", worker_email+worker_pw + worker_name+ worker_gender + worker_birth + worker_phonenum+ hope_local_sido + hope_local_sigugun + jobarray[0]+ careerarray[0] +job_code[careerarray.length-1]);
        //certicipate 추가

        addBtn = findViewById(R.id.addBtn);
        nextTimeTV = findViewById(R.id.nextTimeTV);
        bankSelectSpn = findViewById(R.id.bankSelectSpn);
        accountNumET = findViewById(R.id.accountNumET);
        nameET = findViewById(R.id.nameET);

        bSList = new ArrayList<>();
        bSList.add("은행");
        bSList.add("KB 국민");
        bSList.add("신한");
        bSList.add("농협");
        bSList.add("하나");
        bSList.add("우리");
        bSList.add("IBK 기업");
        bSList.add("시티");
        bSList.add("KDB 산업");

        bSAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, bSList);
        bankSelectSpn.setAdapter(bSAdapter);
        bankSelectSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // bSList.get(position);
            //    Log.d("bsbsbsbs",bSList.get(position));
                worker_bankname = bSList.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountAddActivity.this,  com.example.capstone.MainActivity.class);
                //DB 넣어주기 (1.사진 테스트,2.db삽입)
                worker_bankaccount = accountNumET.getText().toString();
                worker_bankname = nameET.getText().toString();
                Log.d("kkkkk",worker_bankaccount + worker_bankname);
        Log.d("tttt", worker_email +"\n"+ worker_pw +"\n"+ worker_name+"\n"+ worker_gender+"\n"+ worker_birth+"\n"+ worker_phonenum+"\n"+ worker_certicipate+"\n"+ worker_bankaccount+"\n"+ worker_bankname);

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                    //           JSONObject jsonResponse = new JSONObject(response);
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                            Log.d("mytesstt", response);
                              Log.d("mytestlocal_code", jsonResponse.getString("local_code"));
//                                Log.d("mytestjobcoderesponse",jsonResponse.getString("job_codest"));
//                                Log.d("mytesthjcareerresponse",jsonResponse.getString("hj_career"));
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest3",e.toString());
                        }
                    }
                };
                MemberDBRequest workerInsert = new MemberDBRequest("WorkerInsert",worker_email,worker_pw,worker_name,worker_gender,worker_birth,worker_phonenum,worker_certicipate,worker_bankaccount,worker_bankname, responseListener);
                RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);
                //queue.add(workerInsert);
                //php쿼리 호출 순서를 정해줌 queue.add(workerInsert)실행되면 다음 거 실행하게
                Request<String> a=queue.add(workerInsert);
                if(a !=null) {
                    MemberDBRequest hopelocalInsert = new MemberDBRequest("HopeLocalInsert", worker_email, hope_local_sido, hope_local_sigugun, responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(AccountAddActivity.this);
                    queue1.add(hopelocalInsert);
                }
                HopeJobDBRequest hopeJobInsert = null;
                RequestQueue queue2;
                for(int i=careerarray.length-1 ,j=0; i>=0;i--) {

                    Log.d("mytestjobcode",""+job_code[i]+","+careerarray[j]);
                    hopeJobInsert = new HopeJobDBRequest("HopeJobInsert", String.valueOf(job_code.length), worker_email, String.valueOf(job_code[i]), careerarray[j],responseListener);
                     queue2 = Volley.newRequestQueue(AccountAddActivity.this);
                    queue2.add(hopeJobInsert);
                    j++;
                }
                //시도,구군 SELECT LOCAL 해서 CODE가져와서 그 코드를 HOPELOCAL에 넣기


                //hopejobinsert


                startActivity(intent);
            }
        });

        nextTimeTV.setPaintFlags(nextTimeTV.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        nextTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountAddActivity.this,  com.example.capstone.MainActivity.class);
                //DB 넣어주기 (1.사진 테스트,2.db삽입)
         //       worker_bankaccount = accountNumET.getText().toString();
           //     worker_bankname = nameET.getText().toString();
                Log.d("kkkkk",worker_bankaccount + worker_bankname);
                Log.d("tttt", worker_email +"\n"+ worker_pw +"\n"+ worker_name+"\n"+ worker_gender+"\n"+ worker_birth+"\n"+ worker_phonenum+"\n"+ worker_certicipate+"\n"+ worker_bankaccount+"\n"+ worker_bankname);

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            //           JSONObject jsonResponse = new JSONObject(response);
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                            Log.d("mytesstt", response);
                            Log.d("mytestlocal_code", jsonResponse.getString("local_code"));
//                                Log.d("mytestjobcoderesponse",jsonResponse.getString("job_codest"));
//                                Log.d("mytesthjcareerresponse",jsonResponse.getString("hj_career"));
                        } catch (Exception e) {
 //                           e.printStackTrace();
 //                           Log.d("mytest3",e.toString());
                        }
                    }
                };
                MemberDBRequest workerInsert = new MemberDBRequest("WorkerInsert",worker_email,worker_pw,worker_name,worker_gender,worker_birth,worker_phonenum,worker_certicipate,"","", responseListener);
                RequestQueue queue = Volley.newRequestQueue(AccountAddActivity.this);
                //queue.add(workerInsert);
                //php쿼리 호출 순서를 정해줌 queue.add(workerInsert)실행되면 다음 거 실행하게
                Request<String> a=queue.add(workerInsert);
                if(a !=null) {
                    MemberDBRequest hopelocalInsert = new MemberDBRequest("HopeLocalInsert", worker_email, hope_local_sido, hope_local_sigugun, responseListener);
                    RequestQueue queue1 = Volley.newRequestQueue(AccountAddActivity.this);
                    queue1.add(hopelocalInsert);
                }
                HopeJobDBRequest hopeJobInsert = null;
                RequestQueue queue2;
                for(int i=careerarray.length-1 ,j=0; i>=0;i--) {

                    Log.d("mytestjobcode",""+job_code[i]+","+careerarray[j]);
                    hopeJobInsert = new HopeJobDBRequest("HopeJobInsert", String.valueOf(job_code.length), worker_email, String.valueOf(job_code[i]), careerarray[j],responseListener);
                    queue2 = Volley.newRequestQueue(AccountAddActivity.this);
                    queue2.add(hopeJobInsert);
                    j++;
                }
                //시도,구군 SELECT LOCAL 해서 CODE가져와서 그 코드를 HOPELOCAL에 넣기


                //hopejobinsert


                startActivity(intent);
            }
        });
    }
}

//TODO 뱅크 입력할때 건너뛰기 누르면
//디비에 worker_bankaccount랑 worker_bankname
//NULL이 아닌 ""로 집어넣어놨음
//사진 건너뛰기 하면
//NULL이 아닌 noImage로 넣어놨고
//요거는 NULL로 하면 좋은데 그러면 또 PHP건드려야돼서ㅎㅎㅎㅎ
//일단 이렇게 해놓고 나중에 필요하면 NULL로 바꾸든가 해야함
//TODO 회원가입에 비밀번호, 개인정보입력은 예외처리 아직 안 해놈