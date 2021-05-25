package kr.co.ilg.activity.mypage;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;


public class NoticeActivity extends AppCompatActivity {

    noticeAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Response.Listener rListener;
    String user[], date[],title[],content[];
    int k;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notice);

        mRecyclerView = findViewById(R.id.reviewrecycle1);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);




        rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    final  ArrayList<noticeitem> cList = new ArrayList<>();
                    k = array.length();
                    user = new String[k];
                    content = new String[k];
                    date = new String[k];
                    title =  new String[k];
                    for(int i=0; i<array.length(); i++){
                        JSONObject MainRequest = array.getJSONObject(i);
                        user[i] = MainRequest.getString("board_user");
                        content[i] = MainRequest.getString("board_content");
                        date[i] = MainRequest.getString("board_date");
                        title[i] = MainRequest.getString("board_title");
                        cList.add(new noticeitem(title[i],date[i],user[i],content[i]));
                    }


                    myAdapter = new noticeAdapter(cList);
                    mRecyclerView.setAdapter(myAdapter);

                } catch (Exception e) {
                    Log.d("mytestmain", e.toString());
                }
            }
        };

        NoticeRequest mainRequest = new NoticeRequest(rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(NoticeActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(mainRequest);

        final GestureDetector gestureDetector = new GestureDetector(NoticeActivity.this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        /*mRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());

                if(child!=null&&gestureDetector.onTouchEvent(e))
                {
                    //현재 터치된 곳의 position을 가져옴
                    int position = rv.getChildAdapterPosition(child);

                    TextView bName = rv.getChildViewHolder(child).itemView.findViewById(R.id.reviewname);
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