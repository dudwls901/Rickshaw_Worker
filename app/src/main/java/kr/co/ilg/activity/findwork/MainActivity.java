package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
//import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.activity.mypage.MyInfomanageActivity;
import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.activity.mypage.UpdateinfoRequest;

import static com.kakao.auth.StringSet.error;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    ArrayList spinner1_array, spinner2_array;
    ArrayAdapter spinner1_Adapter, spinner2_Adapter;
    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    Toolbar toolbar;
    private Context mContext;
    View dialogview;
    String local_sido = "", local_sigugun = "";
    int k;
    int jp_job_cost[];
    int jp_job_tot_people[], jp_job_current_people[];
    boolean is_urgency[];
    String jp_num[], job_name[], business_reg_num[], local_sido1[],local_sigugun1[], jp_title[], jp_contents[], jp_job_date[], jp_job_start_time[], jp_job_finish_time[], jp_is_urgency[], jp_datetime[];

//    Fragment1 fragment1;
//    Fragment2 fragment2;
//    Fragment3 fragment3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_maintop, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.search:
                // User chose the "Settings" item, show the app settings UI...
                Toast.makeText(getApplicationContext(), "search 클릭", Toast.LENGTH_LONG).show();
                return true;

            case R.id.map:
                Intent intent = new Intent(MainActivity.this, WorkMapActivity.class);
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
//        item1 = (MenuItem) findViewById(R.id.tab1);
//        item2 = (MenuItem)findViewById(R.id.tab2);
//        item3 = (MenuItem)findViewById(R.id.tab3);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //     getSupportActionBar().setHomeAsUpIndicator(R.drawable.search_white_24dp);




        dialogview = (View) View.inflate(MainActivity.this, R.layout.localdialog, null);
        ListView listview = dialogview.findViewById(R.id.listview);
        ListView listview1 = dialogview.findViewById(R.id.listview1);
        Button localsetting = findViewById(R.id.localsetting);
        localsetting.setText(Sharedpreference.get_Hope_local_sido(mContext,"local_sido")+" "+Sharedpreference.get_Hope_local_sigugun(mContext,"local_sigugun"));
        TextView sltTV = dialogview.findViewById(R.id.sltTV);
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

        localsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("지역 설정");
                if(dialogview.getParent() != null) {
                    ((ViewGroup)dialogview.getParent()).removeView(dialogview); // <- fix
                }
                dlg.setView(dialogview);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        localsetting.setText(local_sido + " " + local_sigugun);
                        // 메인에 jobpost띄우기

                    }
                });
                dlg.setNegativeButton("취소", null);

                dlg.show();
            }
        });



        // spinner1 = findViewById(R.id.spinner1);
        //spinner2 = findViewById(R.id.spinner2);

        //spinner1_array = new ArrayList();
        //spinner2_array = new ArrayList();
        //spinner1_array.add(" 서울 마포구 ");
        //spinner2_array.add(" 전체 ");

        //spinner1_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner1_array);
        //spinner2_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner2_array);

        //spinner1.setAdapter(spinner1_Adapter);
        //spinner2.setAdapter(spinner2_Adapter);

        urgency_RecyclerView = findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        String worker_email;
        final int[] numofpost = new int[1];
        worker_email = Sharedpreference.get_email(mContext, "worker_email");
        Log.d("asdfasdfasdf", worker_email);




        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    numofpost[0] = array.length();
                    jp_num = new String[numofpost[0]];
                    job_name = new String[numofpost[0]];
                    jp_job_cost = new int[numofpost[0]];
                    jp_job_tot_people = new int[numofpost[0]];
                    business_reg_num = new String[numofpost[0]];
                    local_sido1 = new String[numofpost[0]];
                    local_sigugun1 = new String[numofpost[0]];
                    jp_title = new String[numofpost[0]];
                    jp_contents = new String[numofpost[0]];
                    jp_job_date = new String[numofpost[0]];
                    jp_job_start_time = new String[numofpost[0]];
                    jp_job_finish_time = new String[numofpost[0]];
                    jp_is_urgency = new String[numofpost[0]];
                    jp_job_current_people = new int[numofpost[0]];
                    jp_datetime = new String[numofpost[0]];
                    is_urgency = new boolean[numofpost[0]];
                    //Log.d("==========", String.valueOf(numofpost[0]));
                    final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        jp_num[i] = MainRequest.getString("jp_num");
                        job_name[i] = MainRequest.getString("job_name");
                        jp_job_cost[i] = MainRequest.getInt("jp_job_cost");
                        jp_job_tot_people[i] = MainRequest.getInt("jp_job_tot_people");
                        local_sido1[i] = MainRequest.getString("local_sido");
                        local_sigugun1[i] = MainRequest.getString("local_sigugun");
                        business_reg_num[i] = MainRequest.getString("business_reg_num");
                        jp_title[i] = MainRequest.getString("jp_title");
                        jp_job_current_people[i]= MainRequest.getInt("current_people");
                        jp_contents[i] = MainRequest.getString("jp_contents");
                        jp_job_date[i] = MainRequest.getString("jp_job_date");
                        jp_job_start_time[i] = MainRequest.getString("jp_job_start_time");
                        jp_job_finish_time[i] = MainRequest.getString("jp_job_finish_time");
                        jp_is_urgency[i] = MainRequest.getString("jp_is_urgency");
                        if(jp_is_urgency[i]=="0") is_urgency[i]=false;
                        else is_urgency[i] = true;
                        jp_datetime[i] = MainRequest.getString("jp_datetime");
                        Log.d("===================", jp_title[i] + " " + jp_job_finish_time[i]);

                        workInfoArrayList.add(new ListViewItem(jp_title[i], jp_job_date[i], jp_job_cost[i], job_name[i], local_sido1[i]+" "+local_sigugun1[i], business_reg_num[i], jp_job_current_people[i], jp_job_tot_people[i], is_urgency[i]));
                    }

                    ListAdapter urgencyAdapter = new ListAdapter(getApplicationContext(), workInfoArrayList);
                    urgency_RecyclerView.setAdapter(urgencyAdapter);


                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        MainRequest mainRequest = new MainRequest(worker_email, rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(mainRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
        //서버DB UPDATE*/


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
                        intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab2: {
                        intent = new Intent(MainActivity.this, MyFieldActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {
                        intent = new Intent(MainActivity.this, MypageMainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        });


    }
}
