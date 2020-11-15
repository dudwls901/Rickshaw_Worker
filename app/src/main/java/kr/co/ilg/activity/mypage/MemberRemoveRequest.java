package kr.co.ilg.activity.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MemberRemoveRequest extends StringRequest {

    final static private String URL = "http://14.63.162.160/MemberRemove.php";

    private Map<String, String> parameters;

    public MemberRemoveRequest(String key, String worker_email, String worker_pw, String worker_check_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("worker_email", worker_email);
        parameters.put("worker_pw", worker_pw);
        parameters.put("worker_check_pw", worker_check_pw);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
