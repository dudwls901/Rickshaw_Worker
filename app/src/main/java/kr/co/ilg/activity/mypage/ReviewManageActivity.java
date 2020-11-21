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
    int k, flag;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        setContentView(R.layout.writtenreview);

        mRecyclerView = findViewById(R.id.reviewrecycle3);
        spinner = findViewById(R.id.reviewspinner);
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



        // 구분선 넣기


        rListener = new Response.Listener<String>() {
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

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        name[i] = MainRequest.getString("name");
                        contents[i] = MainRequest.getString("contents");
                        datetime[i] = MainRequest.getString("datetime");
                        key[i] = MainRequest.getString("key");
                        ForOInfo[i] = MainRequest.getString("ForOInfo");
                        Log.d("ttttttttttttttt",key[i]+"           "+name[i]);
                        if(key[i].equals("0")) {  // 현장
                            cList.add(new mypagereviewitem(name[i], contents[i], datetime[i], ForOInfo[i]));

                        } else {
                        }
                    } // 값넣기*/
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
                    cList = new ArrayList<>();
                    for (int i=0; i<k; i++){
                        if(key[i].equals("0")) {  // 현장
                            cList.add(new mypagereviewitem(name[i], contents[i], datetime[i], ForOInfo[i]));
                            flag = 0;
                            Log.d("asdfasdfasdf",name[i] + " " + contents[i]+ " "+ datetime[i]);
                        }
                    }
                    myAdapter = new mypagereviewAdapter(cList);
                    mRecyclerView.setAdapter(myAdapter);

                }
                else{
                    cList = new ArrayList<>();
                    for (int i=0; i<k; i++){
                        if(key[i].equals("1")) {  // 사무소
                            cList.add(new mypagereviewitem(name[i], contents[i], datetime[i], ForOInfo[i]));
                            flag = 1;
                            Log.d("asdfasdfasdf",name[i] + " " + contents[i]+ " "+ datetime[i]);
                        }
                    }
                    myAdapter = new mypagereviewAdapter(cList);
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

        /*DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        final GestureDetector gestureDetector = new GestureDetector(ReviewManageActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if(child!=null&&gestureDetector.onTouchEvent(e))
                {
                    //현재 터치된 곳의 position을 가져옴
                    int position = rv.getChildAdapterPosition(child);

                    TextView bName = rv.getChildViewHolder(child).itemView.findViewById(R.id.reviewfield);
                    Toast.makeText(getApplicationContext(), position+"번 째 항목 선택", Toast.LENGTH_SHORT).show();

                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });*/

    }

}

