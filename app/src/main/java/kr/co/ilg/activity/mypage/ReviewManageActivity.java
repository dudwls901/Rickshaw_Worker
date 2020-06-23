package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class ReviewManageActivity extends Activity {


    ArrayList<mypagereviewitem> cList;
    mypagereviewAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.writtenreview);

        mRecyclerView = findViewById(R.id.reviewrecycle3);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        cList = new ArrayList<>();
        cList.add(new mypagereviewitem("김영진","초보인데도 불구하고 일 많이 꽂아주셔서 감사합니다.","2020.02.04"));

        myAdapter = new mypagereviewAdapter(cList);
        mRecyclerView.setAdapter(myAdapter);

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

