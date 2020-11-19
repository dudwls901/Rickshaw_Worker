package kr.co.ilg.activity.findwork;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.fragment.Fragment2;
import kr.co.ilg.fragment.Fragment3;

public class MyFieldActivity extends AppCompatActivity {
    Intent intent;
    BottomNavigationView bottomNavigationView;
    private final int FRAGMENT2 = 2;
    private final int FRAGMENT3 = 3;
    private TabLayout tabs;
    MainBackPressCloseHandler mainBackPressCloseHandler;


    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfield);
        mainBackPressCloseHandler =  new MainBackPressCloseHandler(this);
        tabs = findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab(),true);
        tabs.addTab(tabs.newTab());
        tabs.getTabAt(0).setText("지원 현황");
        tabs.getTabAt(1).setText("지난 현장");
        callFragment(FRAGMENT2);
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    callFragment(FRAGMENT2);

                }
                else if(tab.getPosition()==1)
                {
                    callFragment(FRAGMENT3);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성
        bottomNavigationView.getMenu().findItem(R.id.tab2).setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
//                        return true;
                        intent = new Intent(MyFieldActivity.this, MainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab2: {
                        intent = new Intent(MyFieldActivity.this, MyFieldActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    case R.id.tab3: {
                        intent = new Intent(MyFieldActivity.this, MypageMainActivity.class);
                        startActivity(intent);
                        return false;
                    }
                    default:
                        return false;
                }
            }
        });


    }

    private void callFragment(int frament_no){

        // 프래그먼트 사용을 위해
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (frament_no){
            case 2:
                // '프래그먼트1' 호출
                Fragment2 fragment2 = new Fragment2();
                transaction.replace(R.id.myfieldcontainer, fragment2);
                transaction.commit();
                break;

            case 3:
                // '프래그먼트2' 호출
                Fragment3 fragment3 = new Fragment3();
                transaction.replace(R.id.myfieldcontainer, fragment3);
                transaction.commit();
                break;
        }

    }
    @Override
    public void onBackPressed() {
        mainBackPressCloseHandler.onBackPressed();
    }

}
