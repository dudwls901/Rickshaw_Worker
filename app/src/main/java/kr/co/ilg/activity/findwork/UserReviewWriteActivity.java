package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

public class UserReviewWriteActivity extends AppCompatActivity {
    TextView review_object;
    EditText review_text;
    ImageButton back;
    Intent intent;
    int key;
    String review_object_name="default";
    Button register;
    String worker_email,jp_num;
    Context mContext;
    long time;

    Response.Listener rListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_manager_review);
        mContext=this;

        review_object=findViewById(R.id.review_object);
        review_text=findViewById(R.id.review_text);
        back = findViewById(R.id.back);
        worker_email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
        register = findViewById(R.id.register);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        key = intent.getIntExtra("key",0);
        jp_num = intent.getStringExtra("jp_num");


        if(key==0){//현장 후기작성 클릭
            review_object_name=intent.getStringExtra("현장 후기");
            review_object.setText("To."+review_object_name);
        }
        else if(key==1){//인력사무소 후기작성 클릭
            review_object_name=intent.getStringExtra("인력사무소 후기");
            review_object.setText("To."+review_object_name);
        }
        else{//예외처리!

        }
        rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mContext, "등록완료했습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        };

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReviewRequest mainRequest = new ReviewRequest(worker_email,review_text.getText().toString(), key,jp_num,  rListener);  // Request 처리 클래스

                RequestQueue queue = Volley.newRequestQueue(UserReviewWriteActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(mainRequest);
            }
        });

    }
}