package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

import kr.co.ilg.activity.login.SignupPasswordActivity;
import kr.co.ilg.activity.login.SignupUserInfoActivity;

public class PasswordChangeActivity extends AppCompatActivity {

    Button changeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change);

        changeBtn = findViewById(R.id.changeBtn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordChangeActivity.this, OptionActivity.class);
                startActivity(intent);
            }
        });
    }
}
