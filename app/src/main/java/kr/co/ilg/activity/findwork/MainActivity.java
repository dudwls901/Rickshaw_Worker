package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

//import kr.co.ilg.activity.mypage.MypageMainActivity;

import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.fragment.Fragment1;
import kr.co.ilg.fragment.Fragment2;
import kr.co.ilg.fragment.Fragment3;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;


//    Fragment1 fragment1;
//    Fragment2 fragment2;
//    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        bottomNavigationView = findViewById(R.id.bottomNavigationView1); //프래그먼트 생성
        Log.d("chk1", String.valueOf(bottomNavigationView.getMenu().getItem(1)));
        Toast.makeText(MainActivity.this,String.valueOf(bottomNavigationView.getMenu().getItem(1)),Toast.LENGTH_LONG).show();


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //  Log.d("chk",String.valueOf(menuItem.getItemId()));
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
//                        return true;

                        return false;
                    }
                    case R.id.tab2: {

                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                        item2.setChecked(true);
//                        item1.setChecked(false);
                        Intent intent2 = new Intent(MainActivity.this, MyFieldActivity.class);
                        startActivity(intent2);
                        return false;
                    }
                    case R.id.tab3: {

                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                       item3.setChecked(true);
//                       item1.setChecked(false);
                        Intent intent3 = new Intent(MainActivity.this, MypageMainActivity.class);
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
