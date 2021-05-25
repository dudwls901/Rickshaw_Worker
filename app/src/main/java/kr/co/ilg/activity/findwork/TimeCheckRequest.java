package kr.co.ilg.activity.findwork;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class TimeCheckRequest extends StringRequest {
    final static private String URL = "http://14.63.220.50/TimeCheck.php";

    private Map<String, String> parameters;

    // 출퇴근 인증 여부 확인
    public TimeCheckRequest(String key, String worker_email, String jp_num, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("worker_email", worker_email);
        parameters.put("jp_num", jp_num);
    }
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
