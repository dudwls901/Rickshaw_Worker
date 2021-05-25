package kr.co.ilg.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.capstone.R;

public class SignupEmailActivity extends AppCompatActivity {

    Button sendCodeBtn, nextBtn,codeBtn;
    String Code;
    EditText emailET, codeET;
    boolean sign=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_email);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        sendCodeBtn = findViewById(R.id.sendCodeBtn);
        nextBtn = findViewById(R.id.nextBtn);
        emailET = findViewById(R.id.emailET);
        codeET = findViewById(R.id.codeET);
        codeBtn = findViewById(R.id.codeBtn);

        sendCodeBtn.setText("인증번호\n발송");

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //  if (sign == true ) {

                    Intent intent = new Intent(SignupEmailActivity.this, SignupPasswordActivity.class);
                   intent.putExtra("worker_email",emailET.getText().toString());
                    startActivity(intent);
               //}
               // else{
                    Toast.makeText(SignupEmailActivity.this, "인증 완료해주십시오", Toast.LENGTH_SHORT).show();
              //  }


            }
        });
        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Code.equals(codeET.getText().toString()) ) {
                    Toast.makeText(SignupEmailActivity.this, "인증 완료", Toast.LENGTH_SHORT).show();
                    codeBtn.setEnabled(false);
                    emailET.setFocusable(false);
                    codeET.setFocusable(false);
                    sign = true;
                }
                else{
                    Toast.makeText(SignupEmailActivity.this, "인증 오류입니다", Toast.LENGTH_SHORT).show();
                    sign = false;
                }

            }
        });

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GmailSender sender = new GmailSender("sun83324@gmail.com", "sun0811****"); // 발신 이메일과 비밀번호 설정
                Code = sender.getEmailCode();
                Log.d("CODE : ",Code);

                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                try {
                    sender.sendMail("인력거 인증번호 알림", "인증번호 : "+Code , emailET.getText().toString());
                    Toast.makeText(SignupEmailActivity.this, "발송 완료", Toast.LENGTH_SHORT).show();
                    codeBtn.setEnabled(true);
                }
                catch (Exception e)
                {
                    Log.e("SendMail", e.getMessage(), e);
                    Toast.makeText(SignupEmailActivity.this, "올바른 메일을 입력해주십시오", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}

