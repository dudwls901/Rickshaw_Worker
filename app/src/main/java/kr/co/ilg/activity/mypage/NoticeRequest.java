package kr.co.ilg.activity.mypage;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.Map;

public class NoticeRequest extends StringRequest {
    final static private String URL = "http://14.63.220.50/NoticeRequest.php";
    private Map<String, String> parameters;

    public NoticeRequest(Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!

    }

    //서버에 전송할 데이터를 Map 객체로 리턴
    //서버에 요청할 때 앱의 문자열 데이터를 Map 객체로 리턴하여 전달한다
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}
