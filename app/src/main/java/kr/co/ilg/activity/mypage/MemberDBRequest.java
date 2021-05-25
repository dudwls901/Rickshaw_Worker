package kr.co.ilg.activity.mypage;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MemberDBRequest extends StringRequest {
    final static private String URL = "14.63.220.50/Insert2.php";
    final static private String URL1 = "http://14.63.220.50/InsertCheck.php";
    final static private String URL2 = "http://14.63.220.50/accountpermission.php";
    private Map<String, String> parameters;

    //요청                클라이언트로 전송할 데이터(userID)
    public MemberDBRequest(String key, String worker_email, String worker_pw, String worker_name, String worker_gender, String worker_birth, String worker_phonenum, String worker_certicipate, String worker_bankaccount, String worker_bankname, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!
        Log.d("mytest","여기되니?");

        //데이터들
        parameters = new HashMap<>();
        parameters.put("key", key);
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

    //요청                클라이언트로 전송할 데이터(userID)
    public MemberDBRequest(String key, String worker_email,String local_sido, String local_sigugun, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!
        Log.d("mytest","호프로컬");

        //데이터들
        Log.d("mytest","넘길때"+local_sido+local_sigugun);
        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("worker_email", worker_email); //데이터 넣기  ≒ putextra
        parameters.put("local_sido", local_sido);
        parameters.put("local_sigugun",local_sigugun);

    }

    public MemberDBRequest(String key,String num, String worker_email,String job_code, String hj_career, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
        // data            응답 처리 리스너
        super(Method.POST, URL, listener, null); //super로 가독성을 업!
        Log.d("mytest","호프잡");

        //데이터들
        Log.d("mytest","넘길때"+job_code+hj_career);
        parameters = new HashMap<>();
        parameters.put("key", key);
        parameters.put("num",num);
        parameters.put("worker_email", worker_email); //데이터 넣기  ≒ putextra
        parameters.put("job_code", job_code);
        parameters.put("hj_career",hj_career);

    }
    public MemberDBRequest(String worker_email, String token, Response.Listener<String> listener) {
        super(Method.POST, URL2, listener, null); //super로 가독성을 업!

        //데이터들
        parameters = new HashMap<>();
        parameters.put("worker_email", worker_email); //데이터 넣기  ≒ putextra
        parameters.put("token", token);

    }
//    public MemberDBRequest(String key, String worker_email, String worker_pw, String worker_name, String worker_gender, String worker_birth, String worker_phonenum, String worker_certicipate, String worker_bankaccount, String worker_bankname,String local_sido, String local_sigugun, String jobcode0, String career0,String jobcode1, String career1,String jobcode2, String career2,int k, Response.Listener<String> listener) { //생성자 부분이라 콜백메소드는 생략
//        // data            응답 처리 리스너
//        super(Method.POST, URL1, listener, null); //super로 가독성을 업!
//        Log.d("mytest","여기되니?");
//
//        //데이터들
//        parameters = new HashMap<>();
//        parameters.put("key", key);
//        parameters.put("worker_email", worker_email); //데이터 넣기  ≒ putextra
//        parameters.put("worker_pw", worker_pw);
//        parameters.put("worker_name", worker_name);
//        parameters.put("worker_gender", worker_gender);
//        parameters.put("worker_birth", worker_birth);
//        parameters.put("worker_phonenum", worker_phonenum);
//        parameters.put("worker_certicipate", worker_certicipate);
//        parameters.put("worker_bankaccount", worker_bankaccount);
//        parameters.put("worker_bankname", worker_bankname);
//        parameters.put("local_sido", local_sido);
//        parameters.put("local_sigugun", local_sigugun);
//        parameters.put("jobcode0", jobcode0);
//        parameters.put("jobcode1", jobcode1);
//        parameters.put("jobcode2", jobcode2);
//        parameters.put("career0", career0);
//        parameters.put("career1", career1);
//        parameters.put("career2", career2);
//        parameters.put("length", String.valueOf(k));
//
//    }

    //서버에 전송할 데이터를 Map 객체로 리턴
    //서버에 요청할 때 앱의 문자열 데이터를 Map 객체로 리턴하여 전달한다
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameters;
    }
}