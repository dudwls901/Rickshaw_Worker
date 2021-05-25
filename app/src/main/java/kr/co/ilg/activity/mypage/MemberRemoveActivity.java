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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.MainActivity;
import com.example.capstone.R;
import com.example.capstone.TokenRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class MemberRemoveActivity extends Activity{

    Context mContext;
    String worker_email, worker_pw, worker_check_pw;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletemb);

        mContext = this;
        Button remove = findViewById(R.id.remove);
        EditText checkPwET = findViewById(R.id.checkPwET);
        EditText passwdET = findViewById(R.id.passwdET);

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                worker_email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
                worker_pw = passwdET.getText().toString();
                worker_check_pw = checkPwET.getText().toString();

                if ((worker_pw.equals("")) || (worker_check_pw.equals(""))) {
                    Toast.makeText(MemberRemoveActivity.this, "모든 값을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Response.Listener rListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                                boolean isExistPw = jResponse.getBoolean("isExistPw");
                                boolean RemoveSuccess = jResponse.getBoolean("RemoveSuccess");

                                if(isExistPw) {  // 비밀번호 존재
                                    if(RemoveSuccess) {
                                        Toast.makeText(mContext, "삭제되었습니다", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(getApplicationContext(), com.example.capstone.MainActivity.class);
                                        startActivity(intent);
                                        Sharedpreference.clear(mContext,"autologin");
                                        Sharedpreference.set_state(mContext, "switch1",false,"state");
                                        String token = FirebaseInstanceId.getInstance().getToken();
                                        Log.d("asdfasdf",token);
                                        Response.Listener kListener = new Response.Listener<String>() {  // Generics를 String타입으로 한정
                                            @Override
                                            public void onResponse(String response) {
                                                try {

                                                } catch (Exception e) {
                                                    Log.d("mytest", e.toString());
                                                }
                                            }
                                        };
                                        TokenRequest tokenRequest = new TokenRequest("0",token,kListener);
                                        RequestQueue queue3 = Volley.newRequestQueue(MemberRemoveActivity.this);
                                        queue3.add(tokenRequest);
                                    } else {
                                        Toast.makeText(mContext, "비밀번호와 비밀번호 확인이 다릅니다.", Toast.LENGTH_SHORT).show();
                                    }
                                } else {  // 비밀번호 없음
                                    Toast.makeText(mContext, "현재 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
                                }

                            } catch (Exception e) {
                                Log.d("mytest", e.toString());
                            }
                        }
                    };
                    MemberRemoveRequest mrRequest = new MemberRemoveRequest("worker", worker_email, worker_pw, worker_check_pw, rListener);

                    RequestQueue queue = Volley.newRequestQueue(mContext);
                    queue.add(mrRequest);

                }
            }
        });
    }
}
