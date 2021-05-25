package kr.co.ilg.activity.mypage;

import com.android.volley.toolbox.StringRequest;




import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HopeJobDBRequest extends StringRequest {
    final static private String URL = "http://14.63.220.50/Insert3.php";
    private Map<String, String> parameters;



    public HopeJobDBRequest(String key,String worker_email,String job_code, String hj_career, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data 응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!
        Log.d("mytest","호프잡");

        //데이터들
        Log.d("mytest","넘길때"+job_code+hj_career);
        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("worker_email", worker_email); //데이터 넣기  ≒ putextra
        parameters.put("job_code", job_code);
        parameters.put("hj_career",hj_career);

    }

    //서버에 전송할 데이터를 Map 객체로 리턴
    //서버에 요청할 때 앱의 문자열 데이터를 Map 객체로 리턴하여 전달한다
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}