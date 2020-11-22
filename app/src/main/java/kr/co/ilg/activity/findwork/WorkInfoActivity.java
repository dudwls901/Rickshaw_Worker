package kr.co.ilg.activity.findwork;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class WorkInfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView title_tv, place_tv, office_info_tv, title_name_tv, job_tv, pay_tv, date_tv, time_tv, people_tv, contents_tv, address_tv;
    Button map_btn, apply_btn, call_btn, message_btn;
    String jp_title, field_address, manager_office_name, job_name, jp_job_cost, jp_job_date, jp_job_start_time, jp_job_finish_time, jp_job_tot_people, jp_contents, field_code,
            business_reg_num, jp_num, field_name, manager_office_telnum, manager_phonenum;
    boolean jp_is_urgency;
    Intent intent;
    String mapAddress;
    Context mContext;
    int a=0;
    String state;
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                // 해당 버튼을 눌렀을 때 적절한 액션을 넣는다. return true;
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_info);
        mContext = this;

        //ActionBar ad=getActionBar();
        //ad.setDisplayHomeAsUpEnabled(true);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title_tv = findViewById(R.id.title_tv);
        place_tv = findViewById(R.id.place_tv);
        office_info_tv = findViewById(R.id.office_info_tv);
        title_name_tv = findViewById(R.id.title_name_tv);
        job_tv = findViewById(R.id.job_tv);
        pay_tv = findViewById(R.id.pay_tv);
        date_tv = findViewById(R.id.date_tv);
        time_tv = findViewById(R.id.time_tv);
        people_tv = findViewById(R.id.people_tv);
        contents_tv = findViewById(R.id.contents_tv);
        map_btn = findViewById(R.id.map_btn);
        apply_btn = findViewById(R.id.apply_btn);
        call_btn = findViewById(R.id.call_btn);
        message_btn = findViewById(R.id.message_btn);
        address_tv = findViewById(R.id.address_tv);

        place_tv.setPaintFlags(place_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        office_info_tv.setPaintFlags(office_info_tv.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        //TODO ListAdapter에서 intent로 값만 넘기면 됨

        business_reg_num = Sharedpreference.get_anything(mContext,"business_reg_num","memberinfo");
        jp_num = Sharedpreference.get_anything(mContext,"jp_num","memberinfo");
        jp_title = Sharedpreference.get_anything(mContext,"jp_title","memberinfo");
        field_address = Sharedpreference.get_anything(mContext,"field_address","memberinfo");
        mapAddress = field_address;
        manager_office_name = Sharedpreference.get_anything(mContext,"manager_office_name","memberinfo");
        job_name = Sharedpreference.get_anything(mContext,"job_name","memberinfo");
        jp_job_cost =Sharedpreference.get_anything(mContext,"jp_job_cost","memberinfo");
        jp_job_date = Sharedpreference.get_anything(mContext,"jp_job_date","memberinfo");
        jp_job_start_time = Sharedpreference.get_anything(mContext,"jp_job_start_time","memberinfo").substring(0, 5);
        jp_job_finish_time = Sharedpreference.get_anything(mContext,"jp_job_finish_time","memberinfo").substring(0, 5);
        jp_job_tot_people = Sharedpreference.get_anything(mContext,"jp_job_tot_people","memberinfo");
        jp_contents = Sharedpreference.get_anything(mContext,"jp_contents","memberinfo");
        field_name = Sharedpreference.get_anything(mContext,"field_name","memberinfo");

        Log.d("tqtqtqqtqtqtqtqtqtq", field_name + "   " + manager_office_name + " " + business_reg_num);

        title_tv.setText(jp_title);
        place_tv.setText(field_name);
        office_info_tv.setText(manager_office_name);
        title_name_tv.setText(jp_title);
        job_tv.setText(job_name);
        pay_tv.setText(jp_job_cost + "원");
        date_tv.setText(jp_job_date);
        time_tv.setText(jp_job_start_time + "~" + jp_job_finish_time);
        people_tv.setText(jp_job_tot_people + "명");
        contents_tv.setText(jp_contents);
        address_tv.setText(field_address);
        /*if(Integer.parseInt(Sharedpreference.get_anything(mContext,"apply","memberinfo")) == 1){
            apply_btn.setText("지원취소");
        }
        else apply_btn.setText("지원하기");*/

        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    Boolean apply = jResponse.getBoolean("apply"); // 첫번째에 true면 이미 지원함
                    if(apply) {apply_btn.setText("지원취소"); state="지원취소";} else {apply_btn.setText("지원하기"); state="지원하기";}
                    Boolean done = jResponse.getBoolean("done");
                    if(done) apply_btn.setEnabled(false);
                    Log.d("asdfasdfasdfasdfasdfads", apply +"   "+ done);
                    if(a!=0){
                        if(apply) Toast.makeText(WorkInfoActivity.this,"지원되었습니다",Toast.LENGTH_SHORT).show();
                        else Toast.makeText(WorkInfoActivity.this,"지원 취소되었습니다",Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        }; // 버튼
        ApplyRequest aRequest = new ApplyRequest(Sharedpreference.get_email(WorkInfoActivity.this, "worker_email","memberinfo"), jp_num, "10",  rListener);
        RequestQueue queue = Volley.newRequestQueue(WorkInfoActivity.this);
        queue.add(aRequest);

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, WorkMapActivity.class);
                //intent.putExtra("mapAddress",mapAddress);
                startActivity(intent);
            }
        });

        place_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, FieldInfoActivity.class);
                startActivity(intent);
            }
        });

        office_info_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, OfficeInfoActivity.class);
                startActivity(intent);
            }
        });

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a++;
                ApplyRequest aRequest = new ApplyRequest(Sharedpreference.get_email(WorkInfoActivity.this, "worker_email","memberinfo"), jp_num,"0",  rListener);
                RequestQueue queue = Volley.newRequestQueue(WorkInfoActivity.this);
                queue.add(aRequest);
            }
        });

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean selectTelNum = jResponse.getBoolean("selectTelNum");
                            if (selectTelNum) {
                                manager_phonenum = jResponse.getString("manager_phonenum");
                                Uri uri = Uri.parse("tel:" + manager_phonenum);
                                intent = new Intent(Intent.ACTION_DIAL, uri);
                                startActivity(intent);
                            } else {
                                Toast.makeText(WorkInfoActivity.this, "연락처 로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                SelectTelNumRequest stnRequest = new SelectTelNumRequest(business_reg_num, rListener);

                RequestQueue queue = Volley.newRequestQueue(WorkInfoActivity.this);
                queue.add(stnRequest);
            }
        });

        message_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean selectTelNum = jResponse.getBoolean("selectTelNum");
                            if (selectTelNum) {
                                manager_phonenum = jResponse.getString("manager_phonenum");
                                intent = new Intent(Intent.ACTION_SENDTO);
                                intent.putExtra("sms_body", "인력거 보고 연락드립니다.");
                                intent.setData(Uri.parse("smsto:" + Uri.encode(manager_phonenum)));
                                startActivity(intent);
                            } else {
                                Toast.makeText(WorkInfoActivity.this, "연락처 로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                SelectTelNumRequest stnRequest = new SelectTelNumRequest(business_reg_num, rListener);

                RequestQueue queue = Volley.newRequestQueue(WorkInfoActivity.this);
                queue.add(stnRequest);
            }
        });


    }
}
