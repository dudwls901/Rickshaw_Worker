package kr.co.ilg.activity.mypage;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class WorkerDBInsert extends StringRequest {
    final static private String URLL = "http://rickshaw.dothome.co.kr/Insert.php";
    private Map<String, String> parameters;

    //요청                클라이언트로 전송할 데이터(userID)
    public WorkerDBInsert(String worker_email, String worker_pw, String worker_name, String worker_gender, String worker_birth, String worker_phonenum, String worker_certicipate, String worker_bankaccount, String worker_bankname, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URLL, listener, null); //super로 가독성을 업!
        Log.d("mytest","여기되니?");

        //데이터들
        parameters = new HashMap<>();
        parameters.put("worker_email", worker_email); //데이터 넣기  ≒ putextra
        parameters.put("worker_pw", worker_pw);
        parameters.put("worker_name", worker_name);
        parameters.put("worker_gender", worker_gender);
        parameters.put("worker_birth", worker_birth);
        parameters.put("worker_phonenum", worker_phonenum);
        parameters.put("worker_certicipate", worker_certicipate);
        parameters.put("worker_bankaccount", worker_bankaccount);
        parameters.put("worker_bankname", worker_bankname);
    }


    //서버에 전송할 데이터를 Map 객체로 리턴
    //서버에 요청할 때 앱의 문자열 데이터를 Map 객체로 리턴하여 전달한다
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}