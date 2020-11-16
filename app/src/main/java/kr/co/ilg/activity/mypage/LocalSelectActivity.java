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

import org.json.JSONObject;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class LocalSelectActivity extends AppCompatActivity {

    private Context mContext;
    Button okBtn, SDMG;
    TextView sltTV;
    ListView listview, listview1;
    String local_sido = "", local_sigugun = "";
    String worker_email, worker_pw, worker_name, worker_gender, worker_birth, worker_phonenum, worker_certicipate;

    int isUpdate;  // 1 > 수정  0 > 회원가입
    int btnFlag = 0;
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

        Toast.makeText(getApplicationContext(), "어디서 왔나~ " + isUpdate, Toast.LENGTH_SHORT).show();

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

                String email = Sharedpreference.get_email(mContext, "worker_email");
                Intent intent = new Intent(LocalSelectActivity.this, JobSelectActivity.class);
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
                                    if (updateSuccess) {
                                        String local_sido = jResponse.getString("local_sido");
                                        String local_sigugun = jResponse.getString("local_sigugun");

                                        Sharedpreference.set_Hope_local_sido(mContext, "local_sido", local_sido);
                                        Sharedpreference.set_Hope_local_sigugun(mContext, "local_sigugun", local_sigugun);

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

        final String[] arrayList = {"서울", "부산", "대구", "인천", "대전", "광주", "울산", "세종", "경기",
                "강원", "충북", "충남", "전북", "전남", "경북", "경남", "제주"}; // 첫번째 지역선택에 들어갈 배열

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayList); // Adapter 생성
        listview.setAdapter(adapter); //Adapter 연결
        listview.setSelection(0); // 첫 인덱스 설정

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                local_sido = arrayList[position];
                sltTV.setText(arrayList[position]); // 선택한 지역 상단에 띄우기
                k = position;
                final String[][] arrayList1 = {{"종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구"}
                        , {"중구", "서구", "동구", "영도구", "부산진구", "동래구", "남구", "북구", "해운대구", "사하구", "금정구", "강서구", "연제구", "수영구", "사상구", "기장군"}
                        , {"중구", "서구", "동구", "남구", "북구", "수성구", "달서구", "달성군"}
                        , {"중구", "동구", "남구", "연수구", "남동구", "부평구", "계양구", "서구", "미추홀구", "강화군", "옹진군"}
                        , {"중구", "서구", "동구", "유성구", "대덕구"}
                        , {"동구", "서구", "남구", "북구", "광산구"}
                        , {"중구", "남구", "동구", "북구", "울주군"}
                        , {"세종시"}
                        , {"수원시", "성남시", "의정부시", "안양시", "부천시", "광명시", "평택시", "동두천시", "안산시", "고양시", "과천시", "구리시", "남양주시", "오산시", "시흥시", "군포시", "의왕시", "하남시", "용인시", "파주시", "이천시", "안성시", "김포시", "화성시", "광주시", "양주시", "포천시", "여주시", "경기 여주군", "연천군", "가평군", "양평군"}
                        , {"춘천시", "원주시", "강릉시", "동해시", "태백시", "속초시", "삼척시", "홍천군", "횡성군", "영월군", "평창군", "정선군", "철원군", "화천군", "양구군", "인제군", "고성군", "양양군"}
                        , {"청주시", "충주시", "제천시", "청주시", "청원군", "보은군", "옥천군", "영동군", "진천군", "괴산군", "음성군", "단양군", "증평군"}
                        , {"천안시", "공주시", "보령시", "아산시", "서산시", "논산시", "계룡시", "당진시", "금산군", "연기군", "부여군", "서천군", "청양군", "홍성군", "예산군", "태안군", "당진군"}
                        , {"전주시", "군산시", "익산시", "정읍시", "남원시", "김제시", "완주군", "진안군", "무주군", "장수군", "임실군", "순창군", "고창군", "부안군"}
                        , {"목포시", "여수시", "순천시", "나주시", "광양시", "담양군", "곡성군", "구례군", "고흥군", "보성군", "화순군", "장흥군", "강진군", "해남군", "영암군", "무안군", "함평군", "영광군", "장성군", "완도군", "진도군", "신안군"}
                        , {"포항시", "경주시", "김천시", "안동시", "구미시", "영주시", "영천시", "상주시", "문경시", "경산시", "군위군", "의성군", "청송군", "영양군", "영덕군", "청도군", "고령군", "성주군", "칠곡군", "예천군", "봉화군", "울진군", "울릉군"}
                        , {"창원시", "마산시", "진해시", "통영시", "사천시", "김해시", "밀양시", "거제시", "양산시", "의령군", "함안군", "창녕군", "고성군", "남해군", "하동군", "산청군", "함양군", "거창군", "합천군"}
                        , {"제주시", "서귀포시"}
                };
                ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1[position]); // Adapter 생성
                listview1.setAdapter(adapter2); //Adapter 연결
                listview1.setSelection(0); // 첫 인덱스 설정

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        local_sigugun = arrayList1[k][position];
                        sltTV.setText(local_sido + " " + local_sigugun);
                    }
                });


            }
        });


    }
}
