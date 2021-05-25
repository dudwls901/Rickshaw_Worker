package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;
import com.example.capstone.TokenRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.findwork.MainBackPressCloseHandler;
import kr.co.ilg.activity.findwork.MyFieldActivity;
import kr.co.ilg.activity.findwork.Sharedpreference;

public class MypageMainActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    Button myinform, accountmanage, reviewmanage;
    Button[] buttons = {myinform, accountmanage, reviewmanage};
    int[] buttonsid = {R.id.myinform, R.id.accountmanage, R.id.reviewmanage};
    TextView membernickname;
    private Context mContext;
    MainBackPressCloseHandler mainBackPressCloseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);

        mContext = this;
        mainBackPressCloseHandler =  new MainBackPressCloseHandler(this);

        for(int i=0; i<3; i++){
            buttons[i] = (Button) findViewById(buttonsid[i]);
            buttons[i].setOnClickListener(this);
        }
        membernickname = findViewById(R.id.membernickname);

        membernickname.setText(Sharedpreference.get_Nickname(mContext, "worker_name","memberinfo")); // 상단의 이름 설정


        final ListView listview = (ListView) findViewById(R.id.listview);
        List<String> list = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0 : intent = new Intent(MypageMainActivity.this, OptionActivity.class);
                            startActivity(intent); break;
                    case 1 : intent = new Intent(MypageMainActivity.this, NoticeActivity.class);
                        startActivity(intent); break;
                    case 2 : intent = new Intent(MypageMainActivity.this, ilgIntrodutionActivity.class);
                        startActivity(intent); break;
                    case 3 :
                            intent = new Intent(MypageMainActivity.this, com.example.capstone.MainActivity.class);
                            Sharedpreference.clear(mContext,"autologin");
                            Sharedpreference.set_state(mContext,"switch1",false,"state");
                            String token = FirebaseInstanceId.getInstance().getToken();
                            Log.d("asdfasdf",token);
                            Response.Listener kListener = new Response.Listener<String>() {  // Generics를 String타입으로 한정
                                @Override
                                public void onResponse(String response) {
                                    try {

                                    } catch (Exception e) {
                                    Log.d("mytest", e.toString());
                                    }
                                }
                            };
                            TokenRequest tokenRequest = new TokenRequest("0",token,kListener);
                            RequestQueue queue3 = Volley.newRequestQueue(MypageMainActivity.this);
                            queue3.add(tokenRequest);

                            startActivity(intent); break; // 로그아웃시 사용자의 토큰관리, 자동로그인 관리, 스위치 정보 저장 관리 등

                }
            }
        });

        list.add("설정");
        list.add("공지사항");
        list.add("인력거안내");
        list.add("로그아웃");
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.tab3).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
//                        return true;
                         intent = new Intent(MypageMainActivity.this, MainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab2: {
                         intent = new Intent(MypageMainActivity.this, MyFieldActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {
                         intent = new Intent(MypageMainActivity.this, MypageMainActivity.class);
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
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.myinform : intent = new Intent(getApplicationContext(),MyInfomanageActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);break;
            case R.id.accountmanage : intent = new Intent(getApplicationContext(),AccountManageActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);break;
            case R.id.reviewmanage : intent = new Intent(getApplicationContext(),ReviewManageActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);break;
        }

    }
    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    }
}
