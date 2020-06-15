package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.capstone.R;

import kr.co.ilg.activity.mypage.CertificateConfirmActivity;

public class SignupUserInfoActivity extends Activity {

    Button signUpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_userinfo);

        signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupUserInfoActivity.this, CertificateConfirmActivity.class);
                startActivity(intent);
            }
        });

    }
}
