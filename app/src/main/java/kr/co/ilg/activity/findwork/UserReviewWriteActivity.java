package kr.co.ilg.activity.findwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

public class UserReviewWriteActivity extends AppCompatActivity {
    TextView review_object;
    EditText review_text;
    ImageButton back;
    Intent intent;
    int key;
    String review_object_name="default";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.field_manager_review);

        review_object=findViewById(R.id.review_object);
        review_text=findViewById(R.id.review_text);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        intent = getIntent();
        key = intent.getIntExtra("key",0);

        if(key==0){//현장 후기작성 클릭
            review_object_name=intent.getStringExtra("현장 후기");
            review_object.setText(review_object_name);
        }
        else if(key==1){//인력사무소 후기작성 클릭
            review_object_name=intent.getStringExtra("인력사무소 후기");
            review_object.setText(review_object_name);
        }
        else{//예외처리!

        }

    }
}