package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.capstone.R;

import kr.co.ilg.activity.findwork.MainActivity;

public class LoginActivity extends Activity {

    Button loginBtn;
    ImageButton kakaoLoginBtn;
    TextView findPwBtn, signUpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        loginBtn = findViewById(R.id.loginBtn);
        findPwBtn = findViewById(R.id.findPwBtn);
//        kakaoLoginBtn = findViewById(R.id.kakaoLoginBtn);
        signUpBtn = findViewById(R.id.signUpBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        findPwBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordInfoActivity.class);
                startActivity(intent);
            }
        });
//        kakaoLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this, .class);
//                startActivity(intent);
//            }
//        });
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupEmailActivity.class);
                startActivity(intent);
            }
        });
    }
}
