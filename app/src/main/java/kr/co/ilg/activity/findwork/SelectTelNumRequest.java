package kr.co.ilg.activity.findwork;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SelectTelNumRequest extends StringRequest {
    final static private String URL = "http://14.63.162.160/SelectTelNum.php";

    private Map<String, String> parameters;

    // 출퇴근 인증 여부 확인
    public SelectTelNumRequest(String business_reg_num, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("business_reg_num", business_reg_num);
    }
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
