package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.findwork.MyFieldActivity;

public class MypageMainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_main);

        ListView listview = (ListView) findViewById(R.id.listview);


        List<String> list = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);

        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //intent
            }
        });

        list.add("설정");
        list.add("공지사항");
        list.add("인력거안내");
        list.add("로그아웃");
        bottomNavigationView = findViewById(R.id.bottomNavigationView3);
//        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.search_charcol);
//        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.building_charcol);
//        bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.profile_mint);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
//                        return true;
                        Intent intent1 = new Intent(MypageMainActivity.this, MainActivity.class);
                        startActivity(intent1);
                        return false;
                    }
                    case R.id.tab2: {
                        Intent intent2 = new Intent(MypageMainActivity.this, MyFieldActivity.class);
                        startActivity(intent2);
                        return false;
                    }
                    case R.id.tab3: {
                        Intent intent3 = new Intent(MypageMainActivity.this, MypageMainActivity.class);
                        startActivity(intent3);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        });


    }
}
