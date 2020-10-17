package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;

public class FindPasswordInfoActivity extends AppCompatActivity {

    Button sendCodeBtn, codeBtn, findBtn;
    EditText emailET, codeET, nameET, phonenumET;
    String Code, worker_email;
    String worker_name = "";
    String worker_phonenum = "";
    boolean sign=false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_password_info);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        sendCodeBtn = findViewById(R.id.sendCodeBtn);
        codeBtn = findViewById(R.id.codeBtn);
        findBtn = findViewById(R.id.findBtn);
        emailET = findViewById(R.id.emailET);
        codeET = findViewById(R.id.codeET);
        nameET = findViewById(R.id.nameET);
        phonenumET = findViewById(R.id.phonenumET);

        sendCodeBtn.setText("인증번호\n발송");

        codeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Code.equals(codeET.getText().toString()) ) {
                    Toast.makeText(FindPasswordInfoActivity.this, "인증 완료", Toast.LENGTH_SHORT).show();
                    codeBtn.setEnabled(false);
                    sign = true;
                }
                else{
                    Toast.makeText(FindPasswordInfoActivity.this, "인증 오류입니다", Toast.LENGTH_SHORT).show();
                    sign = false;
                }

            }
        });

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                worker_email = emailET.getText().toString();

                Response.Listener rListener = new Response.Listener<String>() {  // Generics를 String타입으로 한정
                    @Override
                    public void onResponse(String response) {  // JSONObject보다 더 유연한 String 사용

                        // 서버연동 시 try-catch문으로 예외 처리하기
                        try {
                            //String을 JSON으로 패킹(변환)하기
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            // JSONObject jResponse = new JSONObject(response);

                            // php에서 DB처리 후 결과(응답) 얻어서 변수에 저장
                            boolean isExistEmail = jResponse.getBoolean("isExistEmail");  // String 결과 변수에서 "isExistEmail" 키의 값에 접근해 추출
                            //searchID = jResponse.getString("userID");



                            if(isExistEmail) {  // ID가 존재 하면

                                Toast.makeText(FindPasswordInfoActivity.this, "등록된 이메일입니다." + worker_email, Toast.LENGTH_SHORT).show();
/*
                                GmailSender sender = new GmailSender("sun83324@gmail.com", "wjdtjsdn2");
                                Code = sender.getEmailCode();

                                if (android.os.Build.VERSION.SDK_INT > 9) {
                                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                                    StrictMode.setThreadPolicy(policy);
                                }
                                try {
                                    sender.sendMail("인력거 인증번호 알림", "인증번호 : "+Code , worker_email);// 넣을것
                                    Toast.makeText(FindPasswordInfoActivity.this, "발송 완료", Toast.LENGTH_SHORT).show();
                                    codeBtn.setEnabled(true);
                                }
                                catch (Exception e)
                                {
                                    Log.e("SendMail", e.getMessage(), e);
                                    Toast.makeText(FindPasswordInfoActivity.this, "올바른 메일을 입력해주십시오", Toast.LENGTH_SHORT).show();
                                }

 */
                            } else {  // ID가 존재 하지 않는다면
                                Toast.makeText(FindPasswordInfoActivity.this, "등록되지 않은 이메일입니다." + worker_email, Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
 //               FindPwRequest fpRequest = new FindPwRequest(worker_email, rListener);  // Request 처리 클래스
                FindPwRequest fpRequest = new FindPwRequest("emailCheck", worker_email, worker_name, worker_phonenum, rListener);  // Request 처리 클래스
                //FindPwRequest fpRequest = new FindPwRequest("emailCheck", worker_email, rListener);  // Request 처리 클래스

                RequestQueue queue = Volley.newRequestQueue(FindPasswordInfoActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(fpRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
            }
        });

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                worker_email = emailET.getText().toString();
                worker_name = nameET.getText().toString();
                worker_phonenum = phonenumET.getText().toString();

                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                            boolean isExistWorker = jResponse.getBoolean("isExistWorker");

                            if(isExistWorker) {  // 회원이 존재하면
                                String worker_pw = jResponse.getString("worker_pw");
                                String worker_email = jResponse.getString("worker_email");

                                Intent intent = new Intent(FindPasswordInfoActivity.this, FindPasswordShowActivity.class);
                                intent.putExtra("pw", worker_pw);
                                intent.putExtra("email", worker_email);
                                startActivity(intent);

                                //Toast.makeText(FindPasswordInfoActivity.this, "등록된 "+worker_pw, Toast.LENGTH_SHORT).show();

                            } else {  // 회원이 존재하지 않는다면

                                Toast.makeText(FindPasswordInfoActivity.this, "등록되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                FindPwRequest fpRequest = new FindPwRequest("workerCheck", worker_email, worker_name, worker_phonenum, rListener);  // Request 처리 클래스

                RequestQueue queue = Volley.newRequestQueue(FindPasswordInfoActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(fpRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
            }

        });

    }
}
