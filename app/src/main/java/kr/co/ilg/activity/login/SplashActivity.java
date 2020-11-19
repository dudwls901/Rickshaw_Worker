package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.MainActivity;

import org.json.JSONObject;

import java.util.Queue;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class SplashActivity extends Activity {
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;


            String autoid = Sharedpreference.get_id(mContext, "worker_email", "autologin");
            String autopw = Sharedpreference.get_pw(mContext, "worker_pw", "autologin");
            boolean k = Sharedpreference.get_state(mContext,"switch1","state");

            if (autoid != null && autopw != null && k) {
                Response.Listener aListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            String[] jobname = new String[3];
                            String[] jobcareer = new String[3];
                            String[] jobcode = new String[3];
                            for (int i = 0; i < 3; i++) {
                                jobname[i] = "";
                                jobcareer[i] = "";
                                jobcode[i] = "";
                            }
                            JSONObject a = jResponse.getJSONObject("response");
                            Boolean isExistWorker = a.getBoolean("tryLogin");
                            if (isExistWorker) {  // 회원이 존재하면 로그인된 화면으로 넘어감
                                String worker_email = a.getString("worker_email");
                                String worker_name = a.getString("worker_name");
                                String password = a.getString("worker_pw");
                                String worker_gender = a.getString("worker_gender");
                                String worker_birth = a.getString("worker_birth");
                                String worker_phonenum = a.getString("worker_phonenum");
                                String worker_bankaccount = a.getString("worker_bankaccount");
                                String worker_bankname = a.getString("worker_bankname");
                                String worker_introduce = a.getString("worker_introduce"); ///////  여기까지 값들어
                                String local_sido = a.getString("local_sido");
                                String local_sigugun = a.getString("local_sigugun");
                                int j = 0;

                                String[] k = {"0", "1", "2"};
                                for (int i = 0; i < a.length() - 12; i++) {
                                    JSONObject s = a.getJSONObject(k[i]);
                                    jobname[i] = s.getString("jobname");
                                    jobcareer[i] = s.getString("jobcareer");
                                    jobcode[i] = s.getString("job_code");
                                    Sharedpreference.set_Jobcareer(mContext, "jobname" + i, jobname[i], "memberinfo");
                                    Sharedpreference.set_Jobname(mContext, "jobcareer" + i, jobcareer[i], "memberinfo");
                                    Sharedpreference.set_Jobcode(mContext, "jobcode" + i, jobcode[i], "memberinfo");
                                    j++;
                                } ///실행되다가

                                Sharedpreference.set_numofjob(mContext, "numofjob", String.valueOf(j), "memberinfo");

                                Sharedpreference.set_email(mContext, "worker_email", worker_email, "memberinfo");
                                Sharedpreference.set_Nickname(mContext, "worker_name", worker_name, "memberinfo");
                                Sharedpreference.set_Password(mContext, "worker_pw", password, "memberinfo");
                                Sharedpreference.set_Gender(mContext, "worker_gender", worker_gender, "memberinfo");
                                Sharedpreference.set_Birth(mContext, "worker_birth", worker_birth, "memberinfo");
                                Sharedpreference.set_Phonenum(mContext, "worker_phonenum", worker_phonenum, "memberinfo");
                                Sharedpreference.set_Bankaccount(mContext, "worker_bankaccount", worker_bankaccount, "memberinfo");
                                Sharedpreference.set_Bankname(mContext, "worker_bankname", worker_bankname, "memberinfo");
                                Sharedpreference.set_introduce(mContext, "worker_introduce", worker_introduce, "memberinfo");

                                Sharedpreference.set_Hope_local_sido(mContext, "local_sido", local_sido, "memberinfo");
                                Sharedpreference.set_Hope_local_sigugun(mContext, "local_sigugun", local_sigugun, "memberinfo");// 파일에 맵핑형식으로 저장


                                //Toast.makeText(FindPasswordInfoActivity.this, "등록된 "+worker_pw, Toast.LENGTH_SHORT).show();
                            } else {  // 회원이 존재하지 않는다면
                                Toast.makeText(SplashActivity.this, "로그인실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest1111111", e.toString()); // 오류 출력
                        }

                    }
                };

                LoginRequest lRequest = new LoginRequest(autoid, autopw, aListener); // Request 처리 클래스
                RequestQueue queue = Volley.newRequestQueue(SplashActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
                queue.add(lRequest);
                Intent intent1 = new Intent(SplashActivity.this, kr.co.ilg.activity.findwork.MainActivity.class);
                startActivity(intent1); //

            }
            else {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);

            }
        finish();

        }



}
