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
          // 앱 설치시, 앱의 변동이 클시 한번 호출됨.
     }
     private void sendRegistrationToServer(String token) {

         Log.d("ㅡㅡ토큰이 변경되었다ㅡㅡ",token);
         // 토큰의 변동이있을때마다 로그를 찍어줌.
         //서버에 토큰 정보 저장은 로그인할때 되게끔 연동함

     }
}
