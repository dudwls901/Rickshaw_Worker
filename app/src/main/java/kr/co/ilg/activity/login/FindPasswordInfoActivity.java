package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

public class FindPasswordInfoActivity extends AppCompatActivity {

    Button sendCodeBtn, findBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_info);

        sendCodeBtn = findViewById(R.id.sendCodeBtn);
        findBtn = findViewById(R.id.findBtn);

        sendCodeBtn.setText("인증번호\n발송");

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPasswordInfoActivity.this, FindPasswordShowActivity.class);
                startActivity(intent);
            }
        });

    }
}
