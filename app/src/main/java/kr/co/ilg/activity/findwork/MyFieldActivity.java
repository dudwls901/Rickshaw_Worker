package kr.co.ilg.activity.findwork;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import kr.co.ilg.activity.mypage.MypageMainActivity;

public class MyFieldActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfield);

        bottomNavigationView = findViewById(R.id.bottomNavigationView2); //프래그먼트 생성

        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
//                        return true;
                        Intent intent1 = new Intent(MyFieldActivity.this, MainActivity.class);
                        startActivity(intent1);
                        return false;
                    }
                    case R.id.tab2: {
                        Intent intent2 = new Intent(MyFieldActivity.this, MyFieldActivity.class);
                        startActivity(intent2);
                        return false;
                    }
                    case R.id.tab3: {
                        Intent intent3 = new Intent(MyFieldActivity.this, MypageMainActivity.class);
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
