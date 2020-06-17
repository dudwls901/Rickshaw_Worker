package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

import kr.co.ilg.activity.findwork.MainActivity;

public class FindPasswordShowActivity extends AppCompatActivity {

    TextView showPwTV;
    Button goLoginBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_show);

        showPwTV = findViewById(R.id.showPwTV);
        goLoginBtn = findViewById(R.id.goLoginBtn);

        showPwTV.setText("일개미님의 비밀번호는\nantwork** 입니다.");
        goLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPasswordShowActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
