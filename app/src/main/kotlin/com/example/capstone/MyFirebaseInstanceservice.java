package com.example.capstone;

import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class MyFirebaseInstanceservice extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";
     @Override public void onTokenRefresh() { // 설치할때 여기서 토큰을 자동으로 만들어 준다
          String refreshedToken = FirebaseInstanceId.getInstance().getToken();
          Log.d(TAG, "Refreshed token:" +
                  "" +
                  "" +
                  " " + refreshedToken);
          // 생성한 토큰을 서버로 날려서 저장하기 위해서 만든거
          sendRegistrationToServer(refreshedToken);
     }
     private void sendRegistrationToServer(String token) {
         String id = Sharedpreference.get_email(getApplicationContext(),"worker_email","memberinfo");
         Log.d(TAG,id);
         Response.Listener rListener = new Response.Listener<String>() {  // Generics를 String타입으로 한정
             @Override
             public void onResponse(String response) {
                 try {

                 } catch (Exception e) {
                     Log.d("mytest", e.toString());
                 }
             }
         };
         TokenRequest tokenRequest = new TokenRequest(id,token,rListener);
         RequestQueue queue = Volley.newRequestQueue(MyFirebaseInstanceservice.this);
         queue.add(tokenRequest);

     }
}
