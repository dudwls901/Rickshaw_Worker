package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.fragment.Fragment2;
import kr.co.ilg.fragment.Fragment22;

public class MyFieldActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    RecyclerView urgency_RecyclerView, usually_RecyclerView;
    RecyclerView.LayoutManager layoutManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfield);







        bottomNavigationView = findViewById(R.id.bottomNavigationView2); //프래그먼트 생성
//        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.search_charcol);
//        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.building_color);
//        bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.profile_charcol);
     //   bottomNavigationView.getMenu().getItem(1).getIcon(R.id.tab2).setTintList(null);
      //  bottomNavigationView.getMenu().getItem(1).
        bottomNavigationView.setItemIconTintList(null);
        //MenuItem item = bottomNavigationView.getMenu().getItem(1);
        //item.setIconTintList(null);
        //Log.d("test",String.valueOf(item));
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
