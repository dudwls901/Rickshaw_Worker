package kr.co.ilg.activity.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PasswordChangeRequest extends StringRequest {

    final static private String URL = "http://14.63.220.50/ChangePw.php";

    private Map<String, String> parameters;

    public PasswordChangeRequest(String key, String worker_email, String worker_pw, String worker_new_pw, String worker_check_new_pw, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("worker_email", worker_email);
        parameters.put("worker_pw", worker_pw);
        parameters.put("worker_new_pw", worker_new_pw);
        parameters.put("worker_check_new_pw", worker_check_new_pw);
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}