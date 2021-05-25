package kr.co.ilg.activity.findwork;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.mypage.getReviewRequest;

public class OfficeInfoActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView office_name, office_address, office_manager_name, office_manager_tel, office_tel, office_introduce;
    ImageButton office_messageBtn, office_callBtn, mng_messageBtn, mng_callBtn, map_btn;
    RecyclerView review_RecyclerView;
    ReviewAdapter myAdapter;
    Response.Listener aListener;
    Intent intent;
    String officename, officetel, officemanagertel;
    int k;
    String name[], contents[], datetime[], key[];
    RecyclerView.LayoutManager layoutManager;
    WorkMapActivity workMapActivity = new WorkMapActivity();
    String mapAddress;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home: {
                finish();

            }
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.office_info);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent receiver = getIntent();
        String business_reg_num = receiver.getExtras().getString("business_reg_num");
        Log.d("mytest123123123213123",business_reg_num);
        //Toast.makeText(getApplicationContext(), business_reg_num, Toast.LENGTH_SHORT).show();
        office_introduce = findViewById(R.id.office_introduce);
        office_name = findViewById(R.id.office_name);
        office_address = findViewById(R.id.office_address);
        office_manager_name = findViewById(R.id.office_manager_name);
        office_manager_tel = findViewById(R.id.office_manager_tel);
        office_tel = findViewById(R.id.office_tel);
        office_messageBtn = findViewById(R.id.office_messageBtn);
        office_callBtn = findViewById(R.id.office_callBtn);
        mng_messageBtn = findViewById(R.id.mng_messageBtn);
        mng_callBtn = findViewById(R.id.mng_callBtn);
        map_btn = findViewById(R.id.map_btn);
        review_RecyclerView = findViewById(R.id.review_list);

        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    boolean select_Mng = jResponse.getBoolean("select_Mng");
                    if (select_Mng) {
                        officetel = jResponse.getString("manager_office_telnum");
                        officemanagertel = jResponse.getString("manager_phonenum");
                        officename = jResponse.getString("manager_office_name");
                        office_name.setText(jResponse.getString("manager_office_name"));
                        office_tel.setText(officetel);
                        office_address.setText(jResponse.getString("manager_office_address"));
                        mapAddress =jResponse.getString("manager_office_address");
                        office_manager_name.setText(jResponse.getString("manager_name"));
                        office_manager_tel.setText(officemanagertel);
                        if(jResponse.getString("manager_office_info").equals("null")) {
                            office_introduce.setText("안녕하세요. "+jResponse.getString("manager_office_name")+"입니다");
                        }
                        else {
                            office_introduce.setText(jResponse.getString("manager_office_info"));
                        }
                        //office_introduce.setText("안녕하세요 ★개미인력★입니다.\n현재 마포구 일대의 보내는 인력만 20명이 넘습니다.\n단가는 평균 15만원에서 16만원 정도이구요,\n초보 경력 상관없이 일자리 많으니까 많은 지원부탁드립니다!");
                    } else {
                        Toast.makeText(getApplicationContext(), "사무소 정보 로드 실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        OfficeInfoSelectRequest oisRequest = new OfficeInfoSelectRequest(business_reg_num, rListener);

        RequestQueue queue = Volley.newRequestQueue(OfficeInfoActivity.this);
        queue.add(oisRequest);

        aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("ttttttttttttttt", "true");

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    k = array.length();
                    name = new String[k];
                    contents = new String[k];
                    datetime = new String[k];

                    final ArrayList<ReviewItem> reviewList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        name[i] = MainRequest.getString("name");
                        contents[i] = MainRequest.getString("contents");
                        datetime[i] = MainRequest.getString("datetime");
                        reviewList.add(new ReviewItem(name[i], datetime[i],contents[i]));
                    } // 값넣기*/
                    myAdapter = new ReviewAdapter(reviewList);
                    review_RecyclerView.setAdapter(myAdapter);


                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        getReviewRequest mainRequest = new getReviewRequest(business_reg_num, 1, aListener);  // Request 처리 클래스

        RequestQueue queue1 = Volley.newRequestQueue(OfficeInfoActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue1.add(mainRequest);

        review_RecyclerView = findViewById(R.id.review_list);
        review_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        review_RecyclerView.setLayoutManager(layoutManager);

        office_messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body", "인력거 보고 연락드립니다.");
                intent.setData(Uri.parse("smsto:" + Uri.encode(officetel)));
                startActivity(intent);
            }
        });

        office_callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + officetel);
                intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        mng_messageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Intent.ACTION_SENDTO);
                intent.putExtra("sms_body", "인력거 보고 연락드립니다.");
                intent.setData(Uri.parse("smsto:" + Uri.encode(officemanagertel)));
                startActivity(intent);
            }
        });

        mng_callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + officemanagertel);
                intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mappppp",mapAddress);
                Intent intent = new Intent(OfficeInfoActivity.this, WorkMapActivity.class);
                intent.putExtra("mapAddress",mapAddress);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
    }
}
