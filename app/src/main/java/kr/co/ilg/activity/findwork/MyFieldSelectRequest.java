package kr.co.ilg.activity.findwork;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyFieldSelectRequest extends StringRequest {
    final static private String URL = "http://rickshaw.dothome.co.kr/MyFieldSelect.php";

    private Map<String, String> parameters;

    // 출퇴근 인증 여부 확인
    public MyFieldSelectRequest(String worker_email, String jp_num, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("worker_email", worker_email);
        parameters.put("jp_num", jp_num);
    }
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}