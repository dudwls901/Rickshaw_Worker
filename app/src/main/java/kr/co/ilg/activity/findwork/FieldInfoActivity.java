package kr.co.ilg.activity.findwork;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import kr.co.ilg.activity.mypage.GetJobsRequest;
import kr.co.ilg.activity.mypage.JobSelectActivity;
import kr.co.ilg.activity.mypage.getReviewRequest;

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

public class FieldInfoActivity extends AppCompatActivity {
    RecyclerView work_info_RecyclerView, review_RecyclerView;
    RecyclerView.LayoutManager layoutManager, review_layoutManager;
    ArrayList<ListViewItem> workInfoArrayList;
    Toolbar toolbar;
    ReviewAdapter myAdapter;
    Response.Listener aListener;
    int k, jp_job_current_people;
    String name[], contents[],datetime[];
    TextView field_nameTv, field_addressTv;
    String jp_num, field_name, field_address, jp_title, jp_job_date, jp_job_cost, job_name, manager_office_name, jp_job_tot_people, jp_job_start_time, jp_job_finish_time, jp_contents;

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
    protected void onCreate(@Nullable Bundle savedInstanceState) {//현장정보
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_info);
        field_nameTv = findViewById(R.id.field_nameTv);
        field_addressTv = findViewById(R.id.field_addressTv);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent receiver = getIntent();
        jp_num = receiver.getExtras().getString("jp_num");
        field_name = receiver.getExtras().getString("field_name");
        field_address = receiver.getExtras().getString("field_address");
        jp_title = receiver.getExtras().getString("jp_title");
        jp_job_date = receiver.getExtras().getString("jp_job_date");
        jp_job_cost = receiver.getExtras().getString("jp_job_cost");
        job_name = receiver.getExtras().getString("job_name");
        manager_office_name = receiver.getExtras().getString("manager_office_name");
        jp_job_tot_people = receiver.getExtras().getString("jp_job_tot_people");
        jp_job_start_time = receiver.getExtras().getString("jp_job_start_time");
        jp_job_finish_time = receiver.getExtras().getString("jp_job_finish_time");
        jp_contents = receiver.getExtras().getString("jp_contents");


        field_nameTv.setText(field_name);
        field_addressTv.setText(field_address);

        work_info_RecyclerView = findViewById(R.id.work_list);
        work_info_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        work_info_RecyclerView.setLayoutManager(layoutManager);

        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    jp_job_current_people = Integer.parseInt(jResponse.getString("crrntP"));
                } catch (Exception e) {
                    Log.d("mytest1111111", e.toString()); // 오류 출력
                }

            }
        };
        CrrntPRequest cpRequest = new CrrntPRequest(jp_num, aListener); // Request 처리 클래스
        RequestQueue queue1 = Volley.newRequestQueue(FieldInfoActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue1.add(cpRequest);

        Log.d("ttttttttqqqqqqqqqqq", jp_num + " " + String.valueOf(jp_job_current_people));
        workInfoArrayList = new ArrayList<>();
        workInfoArrayList.add(new ListViewItem(jp_title, jp_job_date, Integer.parseInt(jp_job_cost), job_name, field_address, manager_office_name, jp_job_current_people,
                Integer.parseInt(jp_job_tot_people), jp_job_start_time, jp_job_finish_time, jp_contents, field_name));
        //workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14",150000,"상수 레미안 아파트","건축","개미인력소",1,3));

        ListAdapter workAdapter = new ListAdapter(getApplicationContext(), workInfoArrayList);
        work_info_RecyclerView.setAdapter(workAdapter);


        review_RecyclerView = findViewById(R.id.review_list);
        review_RecyclerView.setHasFixedSize(true);
        review_layoutManager = new LinearLayoutManager(this);
        review_RecyclerView.setLayoutManager(review_layoutManager);


        aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("ttttttttttttttt","true");

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    k = array.length();
                    name = new String[k];
                    contents = new String[k];
                    datetime = new String[k];

                    final ArrayList<ReviewItem> reviewList=new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        name[i] = MainRequest.getString("name");
                        contents[i] = MainRequest.getString("contents");
                        datetime[i] = MainRequest.getString("datetime");
                        reviewList.add(new ReviewItem(name[i], contents[i], datetime[i]));
                    } // 값넣기*/
                    myAdapter = new ReviewAdapter(reviewList);
                    review_RecyclerView.setAdapter(myAdapter);


                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        getReviewRequest mainRequest = new getReviewRequest(jp_num, 2 , aListener);  // Request 처리 클래스

        RequestQueue queue2 = Volley.newRequestQueue(FieldInfoActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue2.add(mainRequest);


    }


}
