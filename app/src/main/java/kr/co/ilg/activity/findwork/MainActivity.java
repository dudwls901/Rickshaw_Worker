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
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.sdk.user.UserApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.activity.mypage.MyInfomanageActivity;
import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.activity.mypage.UpdateinfoRequest;

import static com.kakao.auth.StringSet.error;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    Toolbar toolbar;
    private Context mContext;

    View dialogview, dialogview1;
    String local_sido = "", local_sigugun = "";
    int k;
    int jp_job_cost[];
    int jp_job_tot_people[], jp_job_current_people[];
    boolean is_urgency[];
    String jp_num[], field_address[], field_name[], job_name[], business_reg_num[], manager_office_name[], jp_title[], jp_contents[], jp_job_date[], jp_job_start_time[], jp_job_finish_time[], jp_is_urgency[], jp_datetime[];
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, selectall;
    Button[] job = {null, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
    int[] jobid = {0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};
    int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] job_code = new int[]{0, 0, 0};
    ;
    String jobs = "";
    int i, j = 0, n = 0, a, p = 0;
    int q = 0, w = 0;
    TextView sltTV1;
    TextView resetjobpost;
    Response.Listener rListener;
    int y, m , d;
    final String[][] arrayList1 = {{}, {"종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구", "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구"}
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
    Date date=null,getdate=null;
    MainBackPressCloseHandler mainBackPressCloseHandler;
//    Fragment1 fragment1;
//    Fragment2 fragment2;
//    Fragment3 fragment3;


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
        local_sido = Sharedpreference.get_Hope_local_sido(mContext, "local_sido","memberinfo");
        local_sigugun = Sharedpreference.get_Hope_local_sigugun(mContext, "local_sigugun","memberinfo");
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mainBackPressCloseHandler =  new MainBackPressCloseHandler(this);


//        item1 = (MenuItem) findViewById(R.id.tab1);
//        item2 = (MenuItem)findViewById(R.id.tab2);
//        item3 = (MenuItem)findViewById(R.id.tab3);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //추가된 소스코드, Toolbar의 왼쪽에 버튼을 추가하고 버튼의 아이콘을 바꾼다.
        //     getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //     getSupportActionBar().setHomeAsUpIndicator(R.drawable.search_white_24dp);

        String r = Sharedpreference.get_numofjob(mContext, "numofjob","memberinfo");
        Log.d("ttttttttt",r);
        int numofjob = Integer.parseInt(r);
        resetjobpost = findViewById(R.id.resetjobpost);

        for (int i = numofjob - 1; i >= 0; i--) {
            job_code[i] = Integer.parseInt(Sharedpreference.get_Jobcode(mContext, "jobcode" + i,"memberinfo"));
        }


        dialogview = (View) View.inflate(MainActivity.this, R.layout.localdialog, null);

        dialogview1 = (View) View.inflate(MainActivity.this, R.layout.jobdialog, null);
        for (i = 1; i < 17; i++) {
            job[i] = dialogview1.findViewById(jobid[i]);
            job[i].setOnClickListener(this);  // 직업버튼 인플레이션
        }

        sltTV1 = dialogview1.findViewById(R.id.sltTV);

        ListView listview = dialogview.findViewById(R.id.listview);
        ListView listview1 = dialogview.findViewById(R.id.listview1);
        TextView localsetting = findViewById(R.id.localsetting);
        TextView jobsetting = findViewById(R.id.jobsetting);
        localsetting.setText(Sharedpreference.get_Hope_local_sido(mContext, "local_sido","memberinfo") + " " + Sharedpreference.get_Hope_local_sigugun(mContext, "local_sigugun","memberinfo"));
        TextView sltTV = dialogview.findViewById(R.id.sltTV);
        final String[] arrayList = {"전체", "서울", "부산", "대구", "인천", "대전", "광주", "울산", "세종", "경기",
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
                n++;
                q = 1;
                w = 0; // local_sido만 선택했을시 제어할 변수

                ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList1[position]); // Adapter 생성
                listview1.setAdapter(adapter2); //Adapter 연결
                listview1.setSelection(0); // 첫 인덱스 설정

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        w = 1;
                        if (local_sido != "전체") {
                            local_sigugun = arrayList1[k][position];
                            sltTV.setText(local_sido + " " + local_sigugun);
                        } else local_sigugun = "";

                    }
                });


            }
        });

        localsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("지역 설정");
                if (dialogview.getParent() != null) {
                    ((ViewGroup) dialogview.getParent()).removeView(dialogview); // <- fix
                }
                dlg.setView(dialogview);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (q == 1 && w == 1) {
                            localsetting.setText(local_sido + " " + local_sigugun);
                        } else if (q == 1 && w == 0) {
                            localsetting.setText(local_sido);
                        }
                        // 메인에 jobpost띄우기

                    }
                });

                dlg.setNegativeButton("취소", null);

                dlg.show();
            }
        });
        String text1 = "";
        for (int i = 0; i < numofjob; i++) {
            text1 = text1 + " " + Sharedpreference.get_Jobname(mContext, "jobname" + i,"memberinfo");
        }
        // sltTV1.setText(Sharedpreference.get_Jobname(mContext,"jobname0")+" "+ Sharedpreference.get_Jobname(mContext,"jobname1")+" "+Sharedpreference.get_Jobname(mContext,"jobname2"));
        jobsetting.setText(text1);
        jobsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("직종 설정");
                if (dialogview1.getParent() != null) {
                    ((ViewGroup) dialogview1.getParent()).removeView(dialogview1); // <- fix
                }
                dlg.setView(dialogview1);

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int j = 0;
                        String text2 = "";
                        String text3 = "";
                        for (int i = 1; i < 17; i++) {
                            if (check[i] == 1)
                                j++;
                        }
                        if (j > 0) {
                            String jobs1[] = jobs.split(" ");
                            for (int i = 0; i < jobs1.length; i++) {
                                text2 = jobs1[i] + " " + text2;
                            }
                        }
                        if (j == 0) {
                            jobsetting.setText("전체선택");
                        }
                        else jobsetting.setText(text2);
                    }
                });
                dlg.setNeutralButton("전체", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        jobsetting.setText("전체");
                        job_code[0] = 0;
                        job_code[1] = 0;
                        job_code[2] = 0;
                    }
                });
                dlg.setNegativeButton("취소", null);

                dlg.show();

            }
        });


        urgency_RecyclerView = findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        String worker_email;
        final int[] numofpost = new int[1];
        worker_email = Sharedpreference.get_email(mContext, "worker_email","memberinfo");
        Log.d("asdfasdfasdf", worker_email);


        rListener = new Response.Listener<String>() {
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
                    field_address = new String[numofpost[0]];
                    field_name = new String[numofpost[0]];
                    jp_title = new String[numofpost[0]];
                    jp_contents = new String[numofpost[0]];
                    jp_job_date = new String[numofpost[0]];
                    jp_job_start_time = new String[numofpost[0]];
                    jp_job_finish_time = new String[numofpost[0]];
                    jp_is_urgency = new String[numofpost[0]];
                    jp_job_current_people = new int[numofpost[0]];
                    jp_datetime = new String[numofpost[0]];
                    is_urgency = new boolean[numofpost[0]];
                    manager_office_name = new String[numofpost[0]];
                    //Log.d("==========", String.valueOf(numofpost[0]));
                    final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        jp_num[i] = MainRequest.getString("jp_num");
                        job_name[i] = MainRequest.getString("job_name");
                        jp_job_cost[i] = MainRequest.getInt("jp_job_cost");
                        jp_job_tot_people[i] = MainRequest.getInt("jp_job_tot_people");
                        field_address[i] = MainRequest.getString("field_address");
                        field_name[i] = MainRequest.getString("field_name");
                        business_reg_num[i] = MainRequest.getString("business_reg_num");
                        jp_title[i] = MainRequest.getString("jp_title");
                        jp_job_current_people[i] = MainRequest.getInt("current_people");
                        jp_contents[i] = MainRequest.getString("jp_contents");
                        jp_job_date[i] = MainRequest.getString("jp_job_date");
                        jp_job_start_time[i] = MainRequest.getString("jp_job_start_time");
                        jp_job_finish_time[i] = MainRequest.getString("jp_job_finish_time");
                        jp_is_urgency[i] = MainRequest.getString("jp_is_urgency");
                        if (jp_is_urgency[i].equals("0")) is_urgency[i] = false;
                        else is_urgency[i] = true;
                        jp_datetime[i] = MainRequest.getString("jp_datetime");
                        manager_office_name[i] = MainRequest.getString("manager_office_name");
                        try {
                            date = dateFormat.parse(String.format("%d",y)+"-"+String.format("%02d",(m+1))+"-"+String.format("%02d",d));
                            getdate = dateFormat.parse(jp_job_date[i]);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        int compare = date.compareTo(getdate);
                        if(compare <=0){
                            workInfoArrayList.add(new ListViewItem(business_reg_num[i], jp_num[i], jp_title[i], jp_job_date[i], jp_job_cost[i], job_name[i], field_address[i], manager_office_name[i], jp_job_current_people[i], jp_job_tot_people[i], is_urgency[i], jp_job_start_time[i], jp_job_finish_time[i], jp_contents[i], field_name[i]));
                        }

                    }
                    ListAdapter urgencyAdapter = new ListAdapter(getApplicationContext(), workInfoArrayList);
                    //urgencyAdapter.notifyDataSetChanged();
                    urgency_RecyclerView.setAdapter(urgencyAdapter);

                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };

        int[] k = new int[]{0, 0, 0};
        MainRequest mainRequest = new MainRequest(worker_email, "1", "1", k[0], k[1], k[2],"0", rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(mainRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생
        //서버DB UPDATE*/

        resetjobpost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainRequest mainRequest = new MainRequest("0", local_sido, local_sigugun, job_code[0], job_code[1], job_code[2],"0", rListener);  // Request 처리 클래스
                Log.d("asdfasdfasdfasdf", local_sido + " " + local_sigugun + " " + job_code[0] + " " + job_code[1] + " " + job_code[2]);
                mainRequest.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(mainRequest);

            }
        });


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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_maintop, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("qqqqqqqqqqq",query);
                MainRequest searchView_req = new MainRequest("0", "0", "0",0,0,0,query, rListener);  // Request 처리 클래스
                searchView_req.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); ////////값띄울때 충돌방지용

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(searchView_req);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("qqqqqqqqqnewtext", newText);
                MainRequest searchView_req = new MainRequest("0", "0", "0",0,0,0,newText, rListener);  // Request 처리 클래스
                searchView_req.setRetryPolicy(new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)); ////////값띄울때 충돌방지용

                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(searchView_req);
                return false;
            }
        });
        return true;
    }
    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    }


    @Override
    public void onClick(View v) {
        jobs = "";
        a = 0;

        for (int k = 1; k < 17; k++) {

            if (jobid[k] == v.getId()) {
                if (check[k] == 0) {

                    job[k].setBackground(getDrawable(R.drawable.custom_btn_mainclr));
                    check[k] = 1;
                    j += 1;/*

                    if(j==1){
                        Sharedpreference.removejobcode12(mContext);
                    }
                    else if(j==2){
                        Sharedpreference.removejobcode2(mContext);
                    }*/

                } else if (check[k] == 1){
                    j -= 1;
                    job[k].setBackground(getDrawable(R.drawable.custom_btn_lightclr));
                    check[k] = 0;
                }
            }

            if(check[k]==1){

                jobs = job[k].getText().toString() + "  " + jobs;
                job_code[a] = k;
                /*Sharedpreference.set_Jobcode(mContext,"jobcode"+a,String.valueOf(job_code[a]));
                if(a==0){
                    Sharedpreference.removejobcode12(mContext);
                }else if (a==1){
                    Sharedpreference.removejobcode2(mContext);
                }*/
                a++;
            }
        }
        sltTV1.setText(jobs);

        if (j == 3) {
            for (int i = 1; i < 17; i++) {
                if (check[i] == 0) job[i].setEnabled(false);
                //토스트메세지까지
            }
        } else {
            for (int i = 1; i < 17; i++) {
                if (check[i] == 0) job[i].setEnabled(true);
            }
        }

    }
}
