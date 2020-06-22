package kr.co.ilg.activity.findwork;

import android.app.ActionBar;
import android.app.Activity;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import kr.co.ilg.activity.findwork.ListAdapter;
import kr.co.ilg.activity.findwork.ListViewItem;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class FieldInfoActivity extends AppCompatActivity {
    RecyclerView work_info_RecyclerView, review_RecyclerView;
    RecyclerView.LayoutManager layoutManager, review_layoutManager;
    Toolbar toolbar;


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
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        work_info_RecyclerView = findViewById(R.id.work_list);
        work_info_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        work_info_RecyclerView.setLayoutManager(layoutManager);

        ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));

        ListAdapter workAdapter=new ListAdapter(getApplicationContext(),workInfoArrayList);
        work_info_RecyclerView.setAdapter(workAdapter);


        review_RecyclerView = findViewById(R.id.review_list);
        review_RecyclerView.setHasFixedSize(true);
        review_layoutManager=new LinearLayoutManager(this);
        review_RecyclerView.setLayoutManager(review_layoutManager);

        ArrayList<ReviewItem> reviewList=new ArrayList<>();
        reviewList.add(new ReviewItem("김영진","2020-06-14","14일 15층 철거하고 왔습니다.\n 이번 달 내로 철거 마무리될 것 같습니다.\n 현장 분위기도 좋고 환경도 좋은편인데,\n 구르마좀 말없이 갖다 쓰지 마쇼\"\n"));
        reviewList.add(new ReviewItem("정선우","2020-06-17","오늘 첫 출근한 노린이입니다.\n 구르마가 뭔가요 ㅜㅜ?????"));

        ReviewAdapter reviewAdapter=new ReviewAdapter(reviewList);
        review_RecyclerView.setAdapter(reviewAdapter);

    }


}
