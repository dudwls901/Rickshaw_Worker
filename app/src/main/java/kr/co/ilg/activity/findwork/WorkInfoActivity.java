package kr.co.ilg.activity.findwork;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
    WorkMapActivity workMapActivity = new WorkMapActivity();
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
        Intent receiver = getIntent();
        business_reg_num = receiver.getExtras().getString("business_reg_num");
        jp_num = receiver.getExtras().getString("jp_num");
        jp_title = receiver.getExtras().getString("jp_title");
        field_address = receiver.getExtras().getString("field_address");
        jp_is_urgency = receiver.getExtras().getBoolean("urgency");
        mapAddress = field_address;
        manager_office_name = receiver.getExtras().getString("manager_office_name");
        job_name = receiver.getExtras().getString("job_name");
        jp_job_cost = receiver.getExtras().getString("jp_job_cost");
        jp_job_date = receiver.getExtras().getString("jp_job_date");
        jp_job_start_time = receiver.getExtras().getString("jp_job_start_time").substring(0, 5);
        jp_job_finish_time = receiver.getExtras().getString("jp_job_finish_time").substring(0, 5);
        jp_job_tot_people = receiver.getExtras().getString("jp_job_tot_people");
        jp_contents = receiver.getExtras().getString("jp_contents");
        field_name = receiver.getExtras().getString("field_name");
        Log.d("business_regggggggg",business_reg_num);

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

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, WorkMapActivity.class);
                intent.putExtra("mapAddress",mapAddress);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
                startActivity(intent);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        workMapActivity.setMapCenter(mapAddress);
                    }
                }, 100); //딜레이 타임 조절 0.1초

            }
        });

        place_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, FieldInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("jp_num", jp_num);
                intent.putExtra("field_name", field_name);
                intent.putExtra("field_address", field_address);
                intent.putExtra("jp_title", jp_title);
                intent.putExtra("jp_job_date", jp_job_date);
                intent.putExtra("jp_job_cost", jp_job_cost);
                intent.putExtra("job_name", job_name);
                intent.putExtra("manager_office_name", manager_office_name);
                intent.putExtra("jp_job_tot_people", jp_job_tot_people);
                intent.putExtra("jp_job_start_time", jp_job_start_time);
                intent.putExtra("jp_job_finish_time", jp_job_finish_time);
                intent.putExtra("jp_contents", jp_contents);
                intent.putExtra("business_reg_num",business_reg_num);
                intent.putExtra("jp_is_urgency",jp_is_urgency);
                startActivity(intent);
            }
        });

        office_info_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WorkInfoActivity.this, OfficeInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("business_reg_num", business_reg_num);
                startActivity(intent);
            }
        });

        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf ("}") + 1));
                            boolean InsertApplySuccess = jResponse.getBoolean("InsertApplySuccess");
                            boolean DeleteApplySuccess = jResponse.getBoolean("DeleteApplySuccess");
                            boolean AlreadyPicked = jResponse.getBoolean("AlreadyPicked");
                            Log.d("mytestapplyresponse", jResponse.toString());

                            if ((apply_btn.getText().toString()).equals("지원하기")) {
                                if (InsertApplySuccess) {
                                    Toast.makeText(getApplicationContext(), "지원이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                    apply_btn.setText("지원 취소");
                                } else {
                                    Toast.makeText(getApplicationContext(), "지원 실패 : DB Error", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (AlreadyPicked) {
                                    Toast.makeText(getApplicationContext(), "이미 선발되었습니다. 사무소로 취소 문의바랍니다.", Toast.LENGTH_SHORT).show();
                                } else {
                                    if (DeleteApplySuccess) {
                                        Toast.makeText(getApplicationContext(), "지원이 취소되었습니다.", Toast.LENGTH_SHORT).show();
                                        apply_btn.setText("지원하기");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "지원 취소 실패 : DB Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Log.d("mytestapply", e.toString());
                        }
                    }
                };
                ApplyRequest aRequest;
                if((apply_btn.getText().toString()).equals("지원하기")) aRequest = new ApplyRequest(Sharedpreference.get_email(WorkInfoActivity.this, "worker_email","memberinfo"), jp_num, "apply", rListener);
                else aRequest = new ApplyRequest(Sharedpreference.get_email(WorkInfoActivity.this, "worker_email","memberinfo"), jp_num, "delete", rListener);
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
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    boolean AlreadyApply = jResponse.getBoolean("AlreadyApply");
                    if (!(AlreadyApply)) apply_btn.setText("지원하기");
                    else apply_btn.setText("지원 취소");
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        ApplyRequest aRequest = new ApplyRequest(Sharedpreference.get_email(WorkInfoActivity.this, "worker_email","memberinfo"), jp_num, rListener);
        RequestQueue queue = Volley.newRequestQueue(WorkInfoActivity.this);
        queue.add(aRequest);
    }
}
