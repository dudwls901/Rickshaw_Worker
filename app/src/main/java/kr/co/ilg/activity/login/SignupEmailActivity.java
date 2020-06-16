package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

public class SignupEmailActivity extends AppCompatActivity {

    Button sendCodeBtn, nextBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_email);

        sendCodeBtn = findViewById(R.id.sendCodeBtn);
        nextBtn = findViewById(R.id.nextBtn);

        sendCodeBtn.setText("인증번호\n발송");

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupEmailActivity.this, SignupPasswordActivity.class);
                startActivity(intent);
            }
        });
    }
}