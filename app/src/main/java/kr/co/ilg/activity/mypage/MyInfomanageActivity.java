package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.Sharedpreference;
import kr.co.ilg.activity.login.FindPasswordInfoActivity;
import kr.co.ilg.activity.login.FindPasswordShowActivity;
import kr.co.ilg.activity.login.FindPwRequest;

public class MyInfomanageActivity extends Activity {

    private Context mContext;
    View dialogview;
    EditText edit_phonenum, edit_introduce;
    String worker_introduce, worker_phonenum;
    ListView listview_hope_job_career;
    int hopeJCNum;


    RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfomanage);

        mContext = this;
        String w_email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
        TextView myname = findViewById(R.id.myname);
        TextView age = findViewById(R.id.age);
        TextView phonenum = findViewById(R.id.phonenum);
        TextView email = findViewById(R.id.email);
        TextView introduce = findViewById(R.id.introduce);
        listview_hope_job_career = findViewById(R.id.listview_hope_job_career);
        TextView hope_local = findViewById(R.id.hope_local);

        TextView modifyprofile = findViewById(R.id.modifyprofile);
        TextView modifyHL = findViewById(R.id.modifyHL);
        TextView modifyHJC = findViewById(R.id.modifyHJC);

        modifyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(MyInfomanageActivity.this);
                dlg.setTitle("프로필 수정");
                dialogview = (View) View.inflate(MyInfomanageActivity.this, R.layout.updateprofile, null);

                dlg.setView(dialogview);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        edit_phonenum = dialogview.findViewById(R.id.edit_phonenum);
                        edit_introduce = dialogview.findViewById(R.id.edit_introduce);
                        worker_introduce = edit_introduce.getText().toString();
                        worker_phonenum = edit_phonenum.getText().toString();
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                    boolean reponse = jResponse.getBoolean("a");
                                    if (reponse) {
                                        Sharedpreference.set_Phonenum(mContext, "worker_phonenum", worker_phonenum,"memberinfo");
                                        Sharedpreference.set_introduce(mContext, "worker_introduce", worker_introduce,"memberinfo");
                                        phonenum.setText(worker_phonenum);
                                        introduce.setText(worker_introduce);
                                        Toast.makeText(MyInfomanageActivity.this, "수정 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        UpdateinfoRequest updateinfoRequest = new UpdateinfoRequest(w_email, worker_phonenum, worker_introduce, rListener);  // Request 처리 클래스

                        RequestQueue queue = Volley.newRequestQueue(MyInfomanageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                        queue.add(updateinfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
                        //서버DB UPDATE
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

            }
        });

        modifyHL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), kr.co.ilg.activity.mypage.LocalSelectActivity.class);
                intent.putExtra("isUpdate", 1);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //TODO
                // 수정 필요할시 false 값과 함께 intent
                // 희망 직종까지 선택 후 원래 activity(MyinformanageActivity)로 돌아와야함.

            }
        });

        modifyHJC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), kr.co.ilg.activity.mypage.JobSelectActivity.class);
                intent.putExtra("isUpdate", 1);
                intent.putExtra("exjobnum", hopeJCNum);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }
        });

        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    final ArrayList<HopeJobCareerLVItem> hopeJobCareerLVItems = new ArrayList<>();
                    boolean select_hopeJCNum = jResponse.getBoolean("select_hopeJCNum");
                    if (select_hopeJCNum) {
                        hopeJCNum = jResponse.getInt("hopeJCNum");
                        for (int i = 0; i < hopeJCNum; i++) {
                            Log.d("check hopeasdfasdf",String.valueOf(hopeJCNum));
                            hopeJobCareerLVItems.add(new HopeJobCareerLVItem(Sharedpreference.get_Jobname(mContext, "jobname" + i,"memberinfo"), Sharedpreference.get_Jobcareer(mContext, "jobcareer" + i,"memberinfo")));
                        }
                    }
                    HopeJobCareerLVAdapter hopeJobCareerLVAdapter = new HopeJobCareerLVAdapter(getApplicationContext(), hopeJobCareerLVItems);
                    listview_hope_job_career.setAdapter(hopeJobCareerLVAdapter);

                } catch (Exception e) {
                    Log.d("mytest manage", e.toString());
                }
            }
        };
        UpdateinfoRequest updateinfoRequest = new UpdateinfoRequest("loadJC", w_email, rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(MyInfomanageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(updateinfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생


        myname.setText(Sharedpreference.get_Nickname(mContext, "worker_name","memberinfo"));
        age.setText(Sharedpreference.get_Birth(mContext, "worker_birth","memberinfo"));
        phonenum.setText(Sharedpreference.get_Phonenum(mContext, "worker_phonenum","memberinfo"));
        email.setText(Sharedpreference.get_email(mContext, "worker_email","memberinfo"));
        introduce.setText(Sharedpreference.get_introduce(mContext, "worker_introduce","memberinfo"));



        /*hope_job_career.setText(Sharedpreference.get_Jobname(mContext,"jobname0") + " " + Sharedpreference.get_Jobcareer(mContext,"jobcareer0")+"\n"
                +Sharedpreference.get_Jobname(mContext,"jobname1") + " " + Sharedpreference.get_Jobcareer(mContext,"jobcareer1")+"\n"
                +Sharedpreference.get_Jobname(mContext,"jobname2") + " " + Sharedpreference.get_Jobcareer(mContext,"jobcareer2"));*/

        hope_local.setText(Sharedpreference.get_Hope_local_sido(mContext, "local_sido","memberinfo") + " " + Sharedpreference.get_Hope_local_sigugun(mContext, "local_sigugun","memberinfo"));
    }
}
