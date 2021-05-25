package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

import kr.co.ilg.activity.mypage.CertificateConfirmActivity;

public class SignupUserInfoActivity extends AppCompatActivity {

    Button signUpBtn;
    String worker_email, worker_pw, worker_gender;
    EditText nameET, birthET, pnumET;
    RadioGroup rg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_userinfo);

        Intent receiver = getIntent();
        worker_email = receiver.getExtras().getString("worker_email");
        worker_pw = receiver.getExtras().getString("worker_pw");
        signUpBtn = findViewById(R.id.signUpBtn);
        nameET = findViewById(R.id.nameET);
        birthET = findViewById(R.id.birthET);
        pnumET = findViewById(R.id.pnumET);
        rg = findViewById(R.id.rg);
        worker_gender="남";
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.manRb)
                {
                    worker_gender = "남";

                }
                else if (checkedId == R.id.womanRb)
                {
                    worker_gender ="여";
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(((nameET.getText().toString()).trim()).equals("") || ((birthET.getText().toString()).trim()).equals("") || ((pnumET.getText().toString()).trim()).equals("")) {
                    Toast.makeText(SignupUserInfoActivity.this, "모든 값을 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SignupUserInfoActivity.this, CertificateConfirmActivity.class);
                    intent.putExtra("worker_email", worker_email);
                    intent.putExtra("worker_pw", worker_pw);
                    intent.putExtra("worker_gender", worker_gender);
                    intent.putExtra("worker_name", nameET.getText().toString());
                    intent.putExtra("worker_birth",birthET.getText().toString());
                    intent.putExtra("worker_phonenum", pnumET.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
