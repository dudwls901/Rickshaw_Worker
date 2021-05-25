package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;
import com.example.capstone.TokenRequest;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import kr.co.ilg.activity.findwork.Sharedpreference;
import kr.co.ilg.activity.login.LoginRequest;
import kr.co.ilg.activity.login.SplashActivity;

public class OptionActivity extends Activity implements View.OnClickListener {
    Button servicepromise, changepassword, deletemb;
    Button[] buttons = {servicepromise, changepassword, deletemb};
    int[] buttonid = {R.id.servicepromise, R.id.changepassword, R.id.deletemb};
    Switch switch1, switch2;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        mContext=this;
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);

        boolean k =Sharedpreference.get_state(mContext,"switch1","state");
        switch1.setChecked(k);

        String worker_email = Sharedpreference.get_email(mContext,"worker_email","memberinfo");
        String worker_pw = Sharedpreference.get_Password(mContext,"worker_pw","memberinfo");

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sharedpreference.set_state(mContext,"switch1",isChecked,"state");
                if(isChecked){
                        Sharedpreference.set_id(mContext, "worker_email", worker_email, "autologin");
                        Sharedpreference.set_pw(mContext, "worker_pw", worker_pw, "autologin");
                }
                else Sharedpreference.clear(mContext, "autologin");
            }
        });
        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    }
                 catch (Exception e) {
                    Log.d("mytest1111111", e.toString()); // 오류 출력
                }

            }
        };
        String token = FirebaseInstanceId.getInstance().getToken();
        switch2.setChecked(Sharedpreference.get_state1(mContext,"switch2","state"));
        switch2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sharedpreference.set_state1(mContext,"switch2",isChecked,"state");
                if(isChecked){
                    TokenRequest1 lRequest = new TokenRequest1(worker_email, token, aListener); // Request 처리 클래스
                    RequestQueue queue = Volley.newRequestQueue(OptionActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
                    queue.add(lRequest);
                }
                else {
                    TokenRequest1 lRequest = new TokenRequest1("0", token, aListener); // Request 처리 클래스
                    RequestQueue queue = Volley.newRequestQueue(OptionActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
                    queue.add(lRequest);
                }
            }
        });



        for(int i=0; i<3; i++){
            buttons[i] = (Button)findViewById(buttonid[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.servicepromise : intent = new Intent(getApplicationContext(),ServiceInfoActivity.class); startActivity(intent);
                                        break;
            case R.id.changepassword : intent = new Intent(getApplicationContext(),PasswordChangeActivity.class); startActivity(intent);
                                        break;
            case R.id.deletemb : intent = new Intent(getApplicationContext(),MemberRemoveActivity.class); startActivity(intent);
                                        break;
        }
    }
}
