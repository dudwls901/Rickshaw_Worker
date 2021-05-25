package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import kr.co.ilg.activity.findwork.Sharedpreference;

public class LocalSelectActivity extends AppCompatActivity {

    private Context mContext;
    Button okBtn;
    TextView sltTV;
    ListView listview, listview1;
    String local_sido = "", local_sigugun = "";
    String worker_email, worker_pw, worker_name, worker_gender, worker_birth, worker_phonenum, worker_certicipate;
    String[] localSidoList;
    int sigugunCnt[];
    String[][] localSigugunList;

    int isUpdate;  // 1 > 수정  0 > 회원가입
    int k;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_select);

        mContext = this;
        Intent receiver = getIntent();
        worker_email = receiver.getExtras().getString("worker_email");
        worker_pw = receiver.getExtras().getString("worker_pw");
        worker_name = receiver.getExtras().getString("worker_name");
        worker_gender = receiver.getExtras().getString("worker_gender");
        worker_birth = receiver.getExtras().getString("worker_birth");
        worker_phonenum = receiver.getExtras().getString("worker_phonenum");
        worker_certicipate = receiver.getExtras().getString("worker_certicipate");
        Log.d("receiver", worker_email + worker_pw + worker_name + worker_gender + worker_birth + worker_phonenum + worker_certicipate);

        Intent modifyIntent = getIntent();
        isUpdate = modifyIntent.getIntExtra("isUpdate", 0);  // modify

//        Toast.makeText(getApplicationContext(), "어디서 왔나~ " + isUpdate, Toast.LENGTH_SHORT).show();

        listview = findViewById(R.id.listview);
        listview1 = findViewById(R.id.listview1); // 지역 선택 리스트뷰

        sltTV = findViewById(R.id.sltTV); // 상단 텍스트

        okBtn = findViewById(R.id.okBtn); // 확인버튼

        if (isUpdate == 1)
            okBtn.setText("수 정");
        else
            okBtn.setText("확 인");

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
                Intent intent = new Intent(LocalSelectActivity.this, JobSelectActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                if (local_sido.equals("") || local_sigugun.equals(""))
                    Toast.makeText(LocalSelectActivity.this, "희망 근무 지역을 선택해주세요.", Toast.LENGTH_SHORT).show();
                else {

                    if (isUpdate == 1) {  // 수정
                        Response.Listener rListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                    boolean updateSuccess = jResponse.getBoolean("updateSuccess");
                                    Intent updateIntent = new Intent(LocalSelectActivity.this, MyInfomanageActivity.class);
                                    updateIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    if (updateSuccess) {
                                        String local_sido = jResponse.getString("local_sido");
                                        String local_sigugun = jResponse.getString("local_sigugun");

                                        Sharedpreference.set_Hope_local_sido(mContext, "local_sido", local_sido,"memberinfo");
                                        Sharedpreference.set_Hope_local_sigugun(mContext, "local_sigugun", local_sigugun,"memberinfo");

                                        Toast.makeText(LocalSelectActivity.this, "수정 완료되었습니다", Toast.LENGTH_SHORT).show();
                                    } else
                                        Toast.makeText(LocalSelectActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                                    startActivity(updateIntent);
                                } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                }
                            }
                        };
                        UpdateinfoRequest updateinfoRequest = new UpdateinfoRequest("hopeLocal", email, local_sido, local_sigugun, rListener);  // Request 처리 클래스

                        RequestQueue queue = Volley.newRequestQueue(LocalSelectActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                        queue.add(updateinfoRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
                        //서버DB UPDATE
                    } else {  // 회원가입
                        intent.putExtra("worker_email", worker_email);
                        intent.putExtra("worker_pw", worker_pw);
                        intent.putExtra("worker_gender", worker_gender);
                        intent.putExtra("worker_name", worker_name);
                        intent.putExtra("worker_birth", worker_birth);
                        intent.putExtra("worker_phonenum", worker_phonenum);
                        intent.putExtra("worker_certicipate", worker_certicipate);
                        intent.putExtra("hope_local_sido", local_sido);
                        intent.putExtra("hope_local_sigugun", local_sigugun);
                        startActivity(intent);
                    }
                }
            }
        }); // 확인버튼 눌렀을 때 화면넘김

        // 지역 가져오기
        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // 서버연동 시 try-catch문으로 예외 처리하기
                try {
                    JSONArray jsonArray_sido = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    int index_search_start = response.indexOf("[") + 1;
                    int index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_sigugun = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    int index_search_start2 = response.indexOf("[", index_search_start) + 1;
                    int index_search_end2 = response.indexOf("]", index_search_end) + 1;
                    JSONArray jsonArray_sigugunNum = new JSONArray(response.substring(response.indexOf("[", index_search_start2), response.indexOf("]", index_search_end2) + 1));

                    int cnt = jsonArray_sido.length();
                    localSidoList = new String[cnt];
                    localSigugunList = new String[cnt][];
                    sigugunCnt = new int[cnt];

                    int c = 0;
                    for (int i = 0; i < cnt; i++) {
                        localSidoList[i] = jsonArray_sido.getJSONObject(i).getString("local_sido");
                        sigugunCnt[i] = Integer.parseInt(jsonArray_sigugunNum.getJSONObject(i).getString("singugunNum"));
                        int n = sigugunCnt[i];
                        localSigugunList[i] = new String[n];
                        for (int j = 0; j < n; j++) {
                            localSigugunList[i][j] = jsonArray_sigugun.getJSONObject(c).getString("local_sigugun");
                            c++;
                        }
                    }

                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localSidoList); // Adapter 생성
                    listview.setAdapter(adapter); //Adapter 연결
                    listview.setSelection(0); // 첫 인덱스 설정

                } catch (Exception e) {
                    Log.d("mytest", e.toString() + "bbbbbbbbb" + response);
                }
            }
        };
        GetLocalRequest glRequest = new GetLocalRequest(aListener); // Request 처리 클래스
        RequestQueue queue1 = Volley.newRequestQueue(LocalSelectActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue1.add(glRequest);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                local_sido = localSidoList[position];
                sltTV.setText(localSidoList[position]); // 선택한 지역 상단에 띄우기
                k = position;

                ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localSigugunList[position]); // Adapter 생성
                listview1.setAdapter(adapter2); //Adapter 연결
                listview1.setSelection(0); // 첫 인덱스 설정

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        local_sigugun = localSigugunList[k][position];
                        sltTV.setText(local_sido + " " + local_sigugun);
                    }
                });
            }
        });
    }
}
