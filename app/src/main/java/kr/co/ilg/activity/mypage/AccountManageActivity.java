package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class AccountManageActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanage);

        mContext = this;

        Button accountmodify = (Button) findViewById(R.id.accountmodify);
        TextView bankaccount = findViewById(R.id.bankaccount);
        TextView bankname = findViewById(R.id.bankname);
        TextView membernickname = findViewById(R.id.membernickname);
        ImageButton accountDelete = findViewById(R.id.accountDelete);

        membernickname.setText(Sharedpreference.get_Nickname(mContext, "worker_name","memberinfo"));
        bankaccount.setText(Sharedpreference.get_bankaccount(mContext,"worker_bankaccount","memberinfo"));
        bankname.setText(Sharedpreference.get_bankname(mContext,"worker_bankname","memberinfo"));

        accountmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountAddActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("isUpdate", 1);
                startActivity(intent);
            }
        });

        accountDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String w_email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean deleteSuccess = jResponse.getBoolean("deleteSuccess");
                            if (deleteSuccess) {
                                Sharedpreference.set_Bankaccount(getApplicationContext(), "worker_bankaccount", "","memberinfo");
                                Sharedpreference.set_Bankname(getApplicationContext(), "worker_bankname", "","memberinfo");
                                bankaccount.setText("");
                                bankname.setText("");
                                Toast.makeText(AccountManageActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AccountManageActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                UpdateinfoRequest updateinfoRequest = new UpdateinfoRequest("deleteAccnt", w_email, rListener);  // Request 처리 클래스

                RequestQueue queue = Volley.newRequestQueue(AccountManageActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(updateinfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생

            }
        });
    }
}
