package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class CareerActivity extends AppCompatActivity {

    private Context mContext;
    Button okBtn;
    ArrayList<CareerRVItem> cList;
    CareerRVAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RadioGroup rg;
    RadioButton year_1, year_1to3, year_3;
    String jobs;
    String worker_email, worker_pw, worker_name, worker_gender, worker_birth, worker_phonenum, hope_local_sido, hope_local_sigugun, worker_certicipate;
    String[] career;
    String[] jobarray;
    int[] job_code;
    int job_code_length = 0;
    int isUpdate;  // 1 > 수정  0 > 회원가입
    int w=0,a=0;
    int g=0,j;
    int jobnum;
    String[] jobname = new String[]{"","",""};
    String[] jobcareer = new String[]{"","",""};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career);

        mContext = this;
        Intent receiver = getIntent();
        isUpdate = receiver.getIntExtra("isUpdate", 0);
        jobnum = receiver.getIntExtra("jobnum", 0);
        worker_email = receiver.getExtras().getString("worker_email");
        worker_pw = receiver.getExtras().getString("worker_pw");
        worker_name = receiver.getExtras().getString("worker_name");
        worker_gender = receiver.getExtras().getString("worker_gender");
        worker_birth = receiver.getExtras().getString("worker_birth");
        worker_phonenum = receiver.getExtras().getString("worker_phonenum");
        worker_certicipate = receiver.getExtras().getString("worker_certicipate");
        hope_local_sido = receiver.getExtras().getString("hope_local_sido");
        hope_local_sigugun = receiver.getExtras().getString("hope_local_sigugun");
        jobs = receiver.getStringExtra("jobs");
        job_code = receiver.getIntArrayExtra("job_code");
        for (int i = 0; i < job_code.length; i++) {
            if (job_code[i] != 0)
                job_code_length++;
        }
        career = new String[job_code_length];

//        Toast.makeText(getApplicationContext(), "어디서 왔나~ " + isUpdate, Toast.LENGTH_SHORT).show();
        Log.d("rrrrrrrrrr", String.valueOf(job_code[0]) + job_code[1] + job_code[2]);
        Log.d("rrrrrreceiver", worker_email + worker_pw + worker_name + worker_gender + worker_birth + worker_phonenum + hope_local_sido + hope_local_sigugun + jobs);

        jobarray = jobs.split("  ");

        mRecyclerView = findViewById(R.id.rcV);

        // RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);  // LayoutManager > 배치 방법 결정, LinearLayoutManager > 항목을 1차원 목록으로 정렬
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        cList = new ArrayList<>();

        for (int i = 1; i < jobarray.length; i++) {
            cList.add(new CareerRVItem(jobarray[i]));
        }

        myAdapter = new CareerRVAdapter(cList);
        mRecyclerView.setAdapter(myAdapter);

        //커스텀한 RecyclerTouchListener를 아이템터치리스너에설정
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, final int position) { //view로 CareerRVItem의 id들에 접근
                //       Log.d("career", career[0] + career[1] + career[2]);
                Log.d("career", String.valueOf(position));
                rg = view.findViewById(R.id.rg);
                year_1 = view.findViewById(R.id.year_1);
                year_1to3 = view.findViewById(R.id.year_1to3);
                year_3 = view.findViewById(R.id.year_3);
                Log.d("career", year_1.getText().toString());
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        for (int i = 0; i < career.length; i++) {
                            if (i == position) {
                                if (checkedId == R.id.year_1) {
                                    career[i] = year_1.getText().toString();
                                } else if (checkedId == R.id.year_1to3) {
                                    career[i] = year_1to3.getText().toString();
                                } else if (checkedId == R.id.year_3) {
                                    career[i] = year_3.getText().toString();
                                }

                            }
                            Log.d("careercc123", "i=" + i + "position=" + position + "checkedId=" + checkedId + "R.id.year_3" + R.id.year_3);
                        }
                    }
                });
            }
            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        okBtn = findViewById(R.id.okBtn);
        if (isUpdate == 1)
            okBtn.setText("수정");
        else
            okBtn.setText("확인");
        okBtn.setOnClickListener(new View.OnClickListener() { // 회원가입과 나의 정보 페이지에서의 수정 페이지를 구분해줌
            @Override
            public void onClick(View v) {
                String email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
                Intent intent = new Intent(CareerActivity.this, AccountAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                Intent updateIntent = new Intent(CareerActivity.this, MyInfomanageActivity.class);
                updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                if (isUpdate == 1) {  // 수정
                    Sharedpreference.removeinfo(mContext, jobnum,"memberinfo");
                    Sharedpreference.set_numofjob(mContext,"numofjob",String.valueOf(career.length),"memberinfo");



                    Response.Listener kListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                                    Sharedpreference.set_Jobcode(mContext, "jobcode" + a, jResponse.getString("job_code"),"memberinfo");
                                    Sharedpreference.set_Jobname(mContext, "jobname" + a, jResponse.getString("job_name"),"memberinfo");
                                    Sharedpreference.set_Jobcareer(mContext, "jobcareer" + a, jResponse.getString("hj_career"),"memberinfo");
                                    Log.d("check",a + "   " +Sharedpreference.get_Jobcode(mContext, "jobcode" + a,"memberinfo")+ " "+ Sharedpreference.get_Jobname(mContext, "jobname" + a,"memberinfo") + " " +Sharedpreference.get_Jobcareer(mContext,"jobcareer"+a,"memberinfo"));
                                    a++;

                                    Toast.makeText(CareerActivity.this, "수정 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    if(a==(career.length)){

                                        startActivity(updateIntent);
                                    }


                            } catch (Exception e) {
                                Log.d("mytest123123", e.toString());
                            }
                        }
                    };
                    RequestQueue queue;
                    queue = Volley.newRequestQueue(CareerActivity.this);
                    for (g = career.length - 1, j=0; g >= 0; g--,j++){
                        Log.d("carrer.length        ",String.valueOf(career.length-1));
                        UpdateinfoRequest updateinfoRequest = new UpdateinfoRequest("hopeJobCareer", email, j, job_code[g], career[j], kListener);
                        queue.add(updateinfoRequest);
                    }



                } else {


                    switch (career.length) {
                        case 1:
                            if (career[0] != null) {
                                intent.putExtra("worker_email", worker_email);
                                intent.putExtra("worker_pw", worker_pw);
                                intent.putExtra("worker_gender", worker_gender);
                                intent.putExtra("worker_name", worker_name);
                                intent.putExtra("worker_birth", worker_birth);
                                intent.putExtra("worker_phonenum", worker_phonenum);
                                intent.putExtra("worker_certicipate", worker_certicipate);
                                intent.putExtra("hope_local_sido", hope_local_sido);
                                intent.putExtra("hope_local_sigugun", hope_local_sigugun);
                                intent.putExtra("jobarray", jobarray);
                                intent.putExtra("job_code", job_code);
                                intent.putExtra("careerarray", career);
                                //   Log.d("careercccccc", career[0] + career[1] + career[2]);


                                startActivity(intent);
                            } else {
                                Toast.makeText(CareerActivity.this, "경력을 다시 눌러주세요.", Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 2:
                            if (career[0] != null && career[1] != null) {
                                intent.putExtra("worker_email", worker_email);
                                intent.putExtra("worker_pw", worker_pw);
                                intent.putExtra("worker_gender", worker_gender);
                                intent.putExtra("worker_name", worker_name);
                                intent.putExtra("worker_birth", worker_birth);
                                intent.putExtra("worker_phonenum", worker_phonenum);
                                intent.putExtra("worker_certicipate", worker_certicipate);
                                intent.putExtra("hope_local_sido", hope_local_sido);
                                intent.putExtra("hope_local_sigugun", hope_local_sigugun);
                                intent.putExtra("jobarray", jobarray);
                                intent.putExtra("job_code", job_code);
                                intent.putExtra("careerarray", career);
                                //   Log.d("careercccccc", career[0] + career[1] + career[2]);
                                //certicipate추가

                                startActivity(intent);
                            } else {
                                Toast.makeText(CareerActivity.this, "경력을 다시 눌러주세요.", Toast.LENGTH_SHORT).show();

                            }
                            break;

                        case 3:
                            if (career[0] != null && career[1] != null && career[2] != null) {
                                intent.putExtra("worker_email", worker_email);
                                intent.putExtra("worker_pw", worker_pw);
                                intent.putExtra("worker_gender", worker_gender);
                                intent.putExtra("worker_name", worker_name);
                                intent.putExtra("worker_birth", worker_birth);
                                intent.putExtra("worker_phonenum", worker_phonenum);
                                intent.putExtra("worker_certicipate", worker_certicipate);
                                intent.putExtra("hope_local_sido", hope_local_sido);
                                intent.putExtra("hope_local_sigugun", hope_local_sigugun);
                                intent.putExtra("jobarray", jobarray);
                                intent.putExtra("job_code", job_code);
                                intent.putExtra("careerarray", career);
                                //   Log.d("careercccccc", career[0] + career[1] + career[2]);
                                //certicipate추가
                                startActivity(intent);
                            } else {
                                Toast.makeText(CareerActivity.this, "경력을 다시 눌러주세요.", Toast.LENGTH_SHORT).show();
                            }
                            break;
                    }
                    //}
                }
            }
        });
    }

    //ClickListener 상속받아 RecyclerTouchListener 커스텀
    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }


    //RecyclerTouchListener 커스텀
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private CareerActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CareerActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        }
    }


}