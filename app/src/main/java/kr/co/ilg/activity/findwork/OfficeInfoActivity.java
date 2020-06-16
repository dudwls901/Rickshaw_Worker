package kr.co.ilg.activity.findwork;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class OfficeInfoActivity extends AppCompatActivity {
    TextView office_introduce;
    RecyclerView review_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.office_info);

        office_introduce=findViewById(R.id.office_introduce);
        office_introduce.setText("안녕하세요 ★개미인력★입니다.\n현재 마포구 일대의 보내는 인력만 20명이 넘습니다.\n단가는 평균 15만원에서 16만원 정도이구요,\n초보 경력 상관없이 일자리 많으니까 많은 지원부탁드립니다!");

        review_RecyclerView = findViewById(R.id.review_list);
        review_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        review_RecyclerView.setLayoutManager(layoutManager);

        ArrayList<ReviewItem> reviewList=new ArrayList<>();
        reviewList.add(new ReviewItem("김영진","2020-06-14","초보인데도 불구하고 일 많이 보내주셔서 감사합니다 ㅎㅎ\n 이번에 철거하고 술 한잔 살게용"));
        reviewList.add(new ReviewItem("전소연","2020-06-17","레미안 아파트는 연락 안오나요?"));

        ReviewAdapter reviewAdapter=new ReviewAdapter(reviewList);
        review_RecyclerView.setAdapter(reviewAdapter);
    }
}
