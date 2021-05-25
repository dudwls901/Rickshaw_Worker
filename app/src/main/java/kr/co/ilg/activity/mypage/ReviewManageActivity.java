package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.GoneFieldRequest;
import kr.co.ilg.activity.findwork.ListAdapter1;
import kr.co.ilg.activity.findwork.ListViewItem;
import kr.co.ilg.activity.findwork.Sharedpreference;

public class ReviewManageActivity extends Activity {

    ArrayList<mypagereviewitem> cList;
    mypagereviewAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Spinner spinner;
    Response.Listener rListener;
    String key[],name[], contents[], datetime[], ForOInfo[];
    String worker_email;
    Context mContext;
    TextView nonereview;
    int k, flag;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.writtenreview);

        mRecyclerView = findViewById(R.id.reviewrecycle);
        spinner = findViewById(R.id.reviewspinner);
        nonereview = findViewById(R.id.nonereview);
        worker_email = Sharedpreference.get_email(mContext,"worker_email","memberinfo");

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        String[] facilityList = {
                "현장 리뷰", "인력사무소 리뷰"
        };
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, facilityList);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        rListener = new Response.Listener<String>() { // 리뷰 채워넣기
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
                    key =  new String[k];
                    ForOInfo =  new String[k];

                    cList = new ArrayList<>();
                    int u=0;

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        name[i] = MainRequest.getString("name");
                        contents[i] = MainRequest.getString("contents");
                        datetime[i] = MainRequest.getString("datetime");
                        key[i] = MainRequest.getString("key");
                        if(key[i].equals("0")) u++;
                        ForOInfo[i] = MainRequest.getString("ForOInfo");
                        Log.d("ttttttttttttttt",key[i]+"           "+name[i]);
                        if(key[i].equals("0")) {  // 현장
                            cList.add(new mypagereviewitem(name[i], contents[i], datetime[i], ForOInfo[i]));

                        } else { //모든 리뷰를 한꺼번에 다 갖고온 후 키값을 통해서 구분하여 list에 넣어주기
                        }
                    } // 값넣기*/
                    if(u==0){
                        nonereview.setVisibility(View.VISIBLE);
                    }
                    else nonereview.setVisibility(View.INVISIBLE);
                    myAdapter = new mypagereviewAdapter(cList);
                    mRecyclerView.setAdapter(myAdapter);
                    myAdapter.setOnItemClickListener(new mypagereviewAdapter.OnItemClickListener() { // 리싸이클러뷰 속 버튼이 클릭될 시 이벤트

                        @Override
                        public void onItemClick(View view, int position, String ForOInfo, String dt) {
                            Intent intent = new Intent(getApplicationContext(), ReviewManageActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            Log.d("--------------------", String.valueOf(ForOInfo.length()));
                            Response.Listener aListener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                        boolean DeleteRVSuccess = jResponse.getBoolean("DeleteRVSuccess");
                                        if (DeleteRVSuccess) {
                                            Toast.makeText(getApplicationContext(), "리뷰가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                            startActivity(intent);

//                                    myAdapter = new mypagereviewAdapter(getApplication(), cList);
//                                    mRecyclerView.setAdapter(myAdapter);
                                        } else {
                                            Toast.makeText(getApplicationContext(), "리뷰 삭제 실패 : DB Error", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (Exception e) {
                                        Log.d("mytest", e.toString());
                                    }
                                }
                            };
                            DeleteReviewRequest deleteReviewRequest;
                            deleteReviewRequest = new DeleteReviewRequest("FR", ForOInfo, worker_email, dt, aListener); // Request 처리 클래스

                            RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                            queue1.add(deleteReviewRequest);

                        }
                    });

                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };

        getReviewRequest mainRequest = new getReviewRequest(worker_email,0, rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(ReviewManageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(mainRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==0){
                    int j=0;
                    cList = new ArrayList<>();
                    for (int i=0; i<k; i++){
                        if(key[i].equals("0")) {  // 현장
                            cList.add(new mypagereviewitem(name[i], contents[i], datetime[i], ForOInfo[i]));
                            flag = 0;
                            Log.d("asdfasdfasdf",name[i] + " " + contents[i]+ " "+ datetime[i]);
                            j++;
                        }
                    }
                    if(j==0){
                        nonereview.setVisibility(View.VISIBLE);
                    }
                    else nonereview.setVisibility(View.INVISIBLE);
                    /*if(k==0){
                        cList.add(new mypagereviewitem("", "첫 리뷰를 써주세요!!", "", ""));
                        flag=0;
                    }*/
                    myAdapter = new mypagereviewAdapter(cList);
                    mRecyclerView.setAdapter(myAdapter); // 값구분을 통해서 다른 list에 넣어준다

                }
                else{
                    cList = new ArrayList<>();
                    int j=0;
                    for (int i=0; i<k; i++){
                        if(key[i].equals("1")) {  // 사무소
                            cList.add(new mypagereviewitem(name[i], contents[i], datetime[i], ForOInfo[i]));
                            flag = 1;
                            j++;
                            Log.d("asdfasdfasdf",name[i] + " " + contents[i]+ " "+ datetime[i]);
                        }
                    }
                    if(j==0){
                        nonereview.setVisibility(View.VISIBLE);
                    }
                    else nonereview.setVisibility(View.INVISIBLE);
                    /*if(k==0){
                        cList.add(new mypagereviewitem("", "첫 리뷰를 써주세요!!", "", ""));
                        flag=1;
                    }*/
                    myAdapter = new mypagereviewAdapter(cList);// 값구분을 통해서 다른 list에 넣어준다
                    mRecyclerView.setAdapter(myAdapter);
                }
                myAdapter.setOnItemClickListener(new mypagereviewAdapter.OnItemClickListener() { // 리싸이클러뷰 속 버튼이 클릭될 시 이벤트

                    @Override
                    public void onItemClick(View view, int position, String ForOInfo, String dt) {
                        Intent intent = new Intent(getApplicationContext(), ReviewManageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        Log.d("--------------------", String.valueOf(ForOInfo.length()));
                        Response.Listener aListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                    boolean DeleteRVSuccess = jResponse.getBoolean("DeleteRVSuccess");
                                    if (DeleteRVSuccess) {
                                        Toast.makeText(getApplicationContext(), "리뷰가 삭제되었습니다.", Toast.LENGTH_SHORT).show();

                                        startActivity(intent);
//                                    myAdapter = new reviewinputinfo_adapter(getApplication(), cList);
//                                    mRecyclerView.setAdapter(myAdapter);
                                    } else {
                                        Toast.makeText(getApplicationContext(), "리뷰 삭제 실패 : DB Error", Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        DeleteReviewRequest deleteReviewRequest;
                        if(flag==0) deleteReviewRequest = new DeleteReviewRequest("FR", ForOInfo, worker_email, dt, aListener);  // Request 처리 클래스
                        else deleteReviewRequest = new DeleteReviewRequest("OR", ForOInfo, worker_email, dt, aListener);  // Request 처리 클래스

                        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                        queue1.add(deleteReviewRequest);

                    }
                });
                Log.d("++++++++++++++++++", String.valueOf(flag));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

}

