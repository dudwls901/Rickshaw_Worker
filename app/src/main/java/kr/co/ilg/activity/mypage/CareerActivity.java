package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class CareerActivity extends AppCompatActivity {

    Button okBtn;
    //RadioButton year_1, year_3;
    ArrayList<CareerRVItem> cList;
    CareerRVAdapter myAdapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.career);

//        year_1 = findViewById(R.id.year_1);
//        year_3 = findViewById(R.id.year_3);
//        year_1.setText("1년\n이하");
//        year_3.setText("3년\n이상");

        mRecyclerView = findViewById(R.id.rcV);

        // RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);  // LayoutManager > 배치 방법 결정, LinearLayoutManager > 항목을 1차원 목록으로 정렬
        mRecyclerView.setLayoutManager(mLayoutManager);

        // 구분선 넣기
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        cList.add(new CareerRVItem("보통 인부"));
        cList.add(new CareerRVItem("용접공"));
        //cList.add(new CareerRVItem("청소"));

        myAdapter = new CareerRVAdapter(cList);
        mRecyclerView.setAdapter(myAdapter);

        okBtn = findViewById(R.id.okBtn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CareerActivity.this, AccountAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
