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
import kr.co.ilg.activity.mypage.GetJobsRequest;
import kr.co.ilg.activity.mypage.GetLocalRequest;
import kr.co.ilg.activity.mypage.JobSelectActivity;
import kr.co.ilg.activity.mypage.LocalSelectActivity;
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
    String[] localSidoList;
    int sigugunCnt[];
    String[][] localSigugunList;
    int jp_job_cost[];
    int jp_job_tot_people[], jp_job_current_people[];
    boolean is_urgency[];
    String jp_num[], field_address[], field_name[], field_code[], job_name[], business_reg_num[], manager_office_name[], jp_title[], jp_contents[], jp_job_date[], jp_job_start_time[], jp_job_finish_time[], jp_is_urgency[], jp_datetime[];
    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16, selectall;
    Button[] job = {null, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn10, btn11, btn12, btn13, btn14, btn15, btn16};
    int[] jobid = {0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10, R.id.btn11,
            R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15, R.id.btn16};
    int check[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int[] job_code = new int[]{0, 0, 0};
    String[] jobnames = new String[16];
    boolean f=false;
    String jobs = "";
    int i, j = 0, n = 0, a, p = 0;
    int q = 0, w = 0;
    TextView sltTV1;
    TextView resetjobpost;
    Response.Listener rListener;
    int y, m , d;
    Date date=null, getdate=null;
    MainBackPressCloseHandler mainBackPressCloseHandler;

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
                intent.putExtra("mapAddress","0");
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


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        Response.Listener aListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));

                    JSONArray a = jResponse.getJSONArray("response");
                    Log.d("ttttttttttttttt", String.valueOf(a.length()));
                    for(int i=0; i<a.length(); i++){
                        JSONObject item=  a.getJSONObject(i);
                        jobnames[i] = item.getString("jobname");
                        job[i+1].setText(jobnames[i]);
                        Log.d("asdf",jobnames[i]);
                        f=true;
                    }
                } catch (Exception e) {
                    Log.d("mytest1111111", e.toString()); // 오류 출력
                }

            }
        };
        GetJobsRequest lRequest = new GetJobsRequest(aListener); // Request 처리 클래스
        RequestQueue queue1 = Volley.newRequestQueue(MainActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue1.add(lRequest);

        sltTV1 = dialogview1.findViewById(R.id.sltTV);

        ListView listview = dialogview.findViewById(R.id.listview);
        ListView listview1 = dialogview.findViewById(R.id.listview1);
        TextView localsetting = findViewById(R.id.localsetting);
        TextView jobsetting = findViewById(R.id.jobsetting);
        localsetting.setText(Sharedpreference.get_Hope_local_sido(mContext, "local_sido","memberinfo") + " " + Sharedpreference.get_Hope_local_sigugun(mContext, "local_sigugun","memberinfo"));
        TextView sltTV = dialogview.findViewById(R.id.sltTV);

        // 지역 가져오기
        Response.Listener bListener = new Response.Listener<String>() {
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
                    sigugunCnt = new int[cnt];
                    localSidoList = new String[cnt+1];
                    localSidoList[0] = "전체";

                    for (int i = 0; i < cnt; i++) {
                        localSidoList[i+1] = jsonArray_sido.getJSONObject(i).getString("local_sido");
                        sigugunCnt[i] = Integer.parseInt(jsonArray_sigugunNum.getJSONObject(i).getString("singugunNum"));
                    }

                    localSigugunList = new String[cnt+1][];
                    localSigugunList[0] = new String[0];

                    int c = 0;
                    for (int i = 1; i <= cnt; i++) {
                        int n = sigugunCnt[i-1];
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
        GetLocalRequest glRequest = new GetLocalRequest(bListener); // Request 처리 클래스
        RequestQueue queue2 = Volley.newRequestQueue(MainActivity.this); // 데이터 전송에 사용할 Volley의 큐 객체 생
        queue2.add(glRequest);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                local_sido = localSidoList[position];
                sltTV.setText(localSidoList[position]); // 선택한 지역 상단에 띄우기
                k = position;
                n++;
                q = 1;
                w = 0; // local_sido만 선택했을시 제어할 변수

                ArrayAdapter adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, localSigugunList[position]); // Adapter 생성
                listview1.setAdapter(adapter2); //Adapter 연결
                listview1.setSelection(0); // 첫 인덱스 설정

                listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        w = 1;
                        if (local_sido != "전체") {
                            local_sigugun = localSigugunList[k][position];
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
