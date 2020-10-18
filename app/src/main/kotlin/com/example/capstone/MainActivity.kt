package com.example.capstone
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import kr.co.ilg.activity.findwork.MainActivity
import kr.co.ilg.activity.login.FindPasswordInfoActivity
import kr.co.ilg.activity.login.SignupEmailActivity
import org.json.JSONObject

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?)
     {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login);

        val idET = findViewById<EditText>(R.id.idET);
         val pwET = findViewById<EditText>(R.id.pwET);
        val kakaoLoginBtn = findViewById<ImageButton>(R.id.kakaoLoginBtn)

        val loginBtn = findViewById<Button>(R.id.loginBtn)
         val findPwBtn = findViewById<TextView>(R.id.findPwBtn)
         val signUpBtn = findViewById<TextView>(R.id.signUpBtn)
         fun intent1(){
             var intent = Intent(this, MainActivity::class.java)
             startActivity(intent)
         }
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                when {
                    error.toString() == AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        kakaoLoginBtn.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

         loginBtn.setOnClickListener {
             val worker_email: String = idET.getText().toString()
             val worker_pw: String = pwET.getText().toString()

             val rListener: Response.Listener<String?> = object : Response.Listener<String?> {

                 override fun onResponse(response: String?) {
                     try {
                         val jResponse = JSONObject(response!!.substring(response!!.indexOf("{"), response!!.lastIndexOf("}") + 1))
                         val isExistWorker = jResponse.getBoolean("tryLogin")
                         if (isExistWorker) {  // 회원이 존재하면

                             val worker_email = jResponse.getString("worker_email")
                             intent1()

                             //Toast.makeText(FindPasswordInfoActivity.this, "등록된 "+worker_pw, Toast.LENGTH_SHORT).show();
                         } else {  // 회원이 존재하지 않는다면
                             Toast.makeText(this@MainActivity,"없는 이메일입니다.",Toast.LENGTH_SHORT).show()
                         }
                     } catch (e: Exception) {
                         Log.d("mytest", e.toString())
                     }

                 }
             }
             val lRequest = kr.co.ilg.activity.login.LoginRequest(worker_email, worker_pw, rListener) // Request 처리 클래스
             val queue = Volley.newRequestQueue(this) // 데이터 전송에 사용할 Volley의 큐 객체 생성

             queue.add(lRequest) // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생

         }
         findPwBtn.setOnClickListener {
             val intent = Intent(this, FindPasswordInfoActivity::class.java)
             startActivity(intent)
         }

         signUpBtn.setOnClickListener {
             val intent = Intent(this, SignupEmailActivity::class.java)
             startActivity(intent)
         }
     }



}


