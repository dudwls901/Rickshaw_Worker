package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
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


        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);

        spinner1_array = new ArrayList();
        spinner2_array = new ArrayList();
        spinner1_array.add(" 서울 마포구 ");
        spinner2_array.add(" 전체 ");

        spinner1_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner1_array);
        spinner2_Adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinner2_array);

        spinner1.setAdapter(spinner1_Adapter);
        spinner2.setAdapter(spinner2_Adapter);

        urgency_RecyclerView = findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        String worker_email;
        final int[] numofpost = new int[1];
        worker_email = Sharedpreference.get_email(mContext,"worker_email");
        Log.d("asdfasdfasdf",worker_email);
/*
        int jp_num[] = {};
        int job_code[]={};
        int jp_job_cost[]={};
        int jp_job_tot_people[]={};
        String business_reg_num[]={}, local_code[]={}, jp_title[]={}, jp_contents[]={},jp_job_date[]={}, jp_job_start_time[]={}, jp_job_finish_time[]={}, jp_is_urgency[]={}, jp_datetime[]={};
*/


        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int jp_num[];
                int job_code[];
                int jp_job_cost[];
                int jp_job_tot_people[];
                String business_reg_num[], local_code[], jp_title[], jp_contents[],jp_job_date[], jp_job_start_time[], jp_job_finish_time[], jp_is_urgency[], jp_datetime[];
                try {

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    numofpost[0] = array.length();
                    jp_num = new int[numofpost[0]];
                    job_code = new int[numofpost[0]];
                    jp_job_cost= new int[numofpost[0]];
                    jp_job_tot_people= new int[numofpost[0]];
                    business_reg_num = new String[numofpost[0]];
                    local_code= new String[numofpost[0]];
                    jp_title= new String[numofpost[0]];
                    jp_contents= new String[numofpost[0]];
                    jp_job_date= new String[numofpost[0]];
                    jp_job_start_time= new String[numofpost[0]];
                    jp_job_finish_time= new String[numofpost[0]];
                    jp_is_urgency= new String[numofpost[0]];
                    jp_datetime= new String[numofpost[0]];
                    //Log.d("==========", String.valueOf(numofpost[0]));
                    final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();

                    for (int i=0; i< array.length(); i++){
                        JSONObject MainRequest = array.getJSONObject(i);
                        jp_num[i] = MainRequest.getInt("jp_num");
                        job_code[i] = MainRequest.getInt("job_code");
                        jp_job_cost[i] = MainRequest.getInt("jp_job_cost");
                        jp_job_tot_people[i] = MainRequest.getInt("jp_job_tot_people");
                        local_code[i] = MainRequest.getString("local_code");
                        business_reg_num[i] = MainRequest.getString("business_reg_num");
                        jp_title[i] = MainRequest.getString("jp_title");
                        jp_contents[i] = MainRequest.getString("jp_contents");
                        jp_job_date[i] = MainRequest.getString("jp_job_date");
                        jp_job_start_time[i] = MainRequest.getString("jp_job_start_time");
                        jp_job_finish_time[i] = MainRequest.getString("jp_job_finish_time");
                        jp_is_urgency[i] = MainRequest.getString("jp_is_urgency");
                        jp_datetime[i] = MainRequest.getString("jp_datetime");
                        Log.d("===================",jp_title[i]+" "+jp_job_finish_time[i]);

                        workInfoArrayList.add(new ListViewItem(jp_title[i], jp_job_date[i], "jp_job_cost[i]", "job_code[i]", "local_code[i]", business_reg_num[i], "1", "jp_job_tot_people[i]", true));
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
