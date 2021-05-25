package kr.co.ilg.activity.mypage;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateinfoRequest extends StringRequest {
    final static private String URL = "http://14.63.220.50/Updateinfo.php";

    private Map<String, String> parameters;  // 전송 데이터 넣을 Map 객체 선언

    // 폰 번호, 한줄 소개 수정
    public UpdateinfoRequest(String worker_email, String worker_phonenum, String worker_introduce, Response.Listener<String> listener) {  // 서버에 전송될 data, 응답(결과) 처리하는 리스너
        super(Method.POST, URL, listener, null);  // 가독성 향상을 위해 super에 선언

        // HashMap으로 데이터 정의하고 추가
        parameters = new HashMap<>();
        parameters.put("key", "pnumIntroduce");
        parameters.put("worker_email", worker_email);
        parameters.put("worker_phonenum", worker_phonenum);
        parameters.put("worker_introduce", worker_introduce);
    }

    // 희망 지역  수정, 계좌 수정
    public UpdateinfoRequest(String key, String worker_email, String local_sido_bank_name, String local_sigugun_bank_account, Response.Listener<String> listener) {  // 서버에 전송될 data, 응답(결과) 처리하는 리스너
        super(Method.POST, URL, listener, null);  // 가독성 향상을 위해 super에 선언

        // HashMap으로 데이터 정의하고 추가
        parameters = new HashMap<>();

        parameters.put("key", key);
        parameters.put("worker_email", worker_email);
        if(key.equals("hopeLocal")) {
            parameters.put("local_sido", local_sido_bank_name);
            parameters.put("local_sigugun", local_sigugun_bank_account);
        } else {
            parameters.put("worker_bankname", local_sido_bank_name);
            parameters.put("worker_bankaccount", local_sigugun_bank_account);
        }
    }

    // 희망 직종과 경력 수정
    public UpdateinfoRequest(String key, String worker_email, int k, int job_code, String hj_career, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("k", String.valueOf(k));
        parameters.put("worker_email", worker_email); //데이터 넣기  ≒ putextra
        parameters.put("job_code", String.valueOf(job_code));
        parameters.put("hj_career",hj_career);
    }

    // 희망 경력 로드, 계좌 삭제
    public UpdateinfoRequest(String key, String worker_email, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("worker_email", worker_email);
    }

    // getParams 재정의
    protected Map<String, String> getParams() throws AuthFailureError {
        // 서버에 전송할 문자 데이터들을 Map 객체로 리턴
        return parameters;
    }

}
