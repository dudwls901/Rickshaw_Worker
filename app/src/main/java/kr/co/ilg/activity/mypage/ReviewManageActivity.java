package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.ListAdapter1;
import kr.co.ilg.activity.findwork.ListViewItem;

public class ReviewManageActivity extends Activity {



    mypagereviewAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    Spinner spinner;
    Response.Listener rListener;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.writtenreview);

        mRecyclerView = findViewById(R.id.reviewrecycle3);
        spinner = findViewById(R.id.reviewspinner);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position==1){

                }
                else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");

                    final ArrayList<mypagereviewitem> cList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);



                        //cList.add(new mypagereviewitem("김영진","초보인데도 불구하고 일 많이 꽂아주셔서 감사합니다.","2020.02.04"));
                    } // 값넣기*/
                    myAdapter = new mypagereviewAdapter(cList);
                    mRecyclerView.setAdapter(myAdapter);


                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };







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
        });
    }
}

