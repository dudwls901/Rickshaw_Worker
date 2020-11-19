package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;

import kr.co.ilg.activity.findwork.Sharedpreference;
import kr.co.ilg.activity.login.SignupPasswordActivity;
import kr.co.ilg.activity.login.SignupUserInfoActivity;

public class PasswordChangeActivity extends AppCompatActivity {

    Button changeBtn;
    EditText passwdET, newPasswdET, checkNewPwET;
    String worker_email, worker_pw, worker_new_pw, worker_check_new_pw;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_change);

        mContext = this;
        changeBtn = findViewById(R.id.changeBtn);
        passwdET = findViewById(R.id.passwdET);
        newPasswdET = findViewById(R.id.newPasswdET);
        checkNewPwET = findViewById(R.id.checkNewPwET);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                worker_email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
                worker_pw = passwdET.getText().toString();
                worker_new_pw = newPasswdET.getText().toString();
                worker_check_new_pw = checkNewPwET.getText().toString();

                if ((worker_pw.equals("")) || (worker_check_new_pw.equals("")) || (worker_new_pw.equals(""))) {
                    Toast.makeText(PasswordChangeActivity.this, "모든 값을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                                boolean isExistPw = jResponse.getBoolean("isExistPw");
                                boolean pwChangeSuccess = jResponse.getBoolean("pwChangeSuccess");

                                if(isExistPw) {  // 비밀번호 존재
                                    if(pwChangeSuccess) {
                                        Toast.makeText(PasswordChangeActivity.this, "변경되었습니다", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(PasswordChangeActivity.this, OptionActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(PasswordChangeActivity.this, "새 비밀번호와 새 비밀번호 확인이 다릅니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {  // 비밀번호 없음
                                    Toast.makeText(PasswordChangeActivity.this, "현재 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    PasswordChangeRequest pcRequest = new PasswordChangeRequest("worker", worker_email, worker_pw, worker_new_pw, worker_check_new_pw, rListener);

                    RequestQueue queue = Volley.newRequestQueue(PasswordChangeActivity.this);
                    queue.add(pcRequest);
                }
            }
        });
    }}
