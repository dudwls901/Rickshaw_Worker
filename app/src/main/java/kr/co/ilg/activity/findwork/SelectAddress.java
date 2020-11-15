package kr.co.ilg.activity.findwork;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class SelectAddress extends StringRequest {

    final static private String URL = "http://14.63.162.160/SelectAddress.php";

    public SelectAddress(Response.Listener<String> listener) {  // 서버에 전송될 data, 응답(결과) 처리하는 리스너
        super(Method.POST, URL, listener, null);  // 가독성 향상을 위해 super에 선언
    }
}
