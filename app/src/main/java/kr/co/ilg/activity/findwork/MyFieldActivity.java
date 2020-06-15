package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.fragment.Fragment1;
import kr.co.ilg.fragment.Fragment2;
import kr.co.ilg.fragment.Fragment22;
import kr.co.ilg.fragment.Fragment3;

public class MyFieldActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
//    Fragment1 fragment2;
//    Fragment2 fragment22;
RecyclerView urgency_RecyclerView, usually_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myfield);

        TabLayout tabs = findViewById(R.id.tabs);


        PagerAdapter pagerAdapter = new PagerAdapter(MyFieldActivity.this,getFragmentManager());
        FragmentPagerAdapter adapterViewpager;

        adapterViewpager = new PagerAdapter(MyFieldActivity.this,getFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

//        fragment1 = new Fragment1();
//        fragment2 = new Fragment2();
 //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commitAllowingStateLoss(); //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.

tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
});







        bottomNavigationView = findViewById(R.id.bottomNavigationView2); //프래그먼트 생성
        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.search_black);
        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.building_color);
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
public class PagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_2, R.string.tab_text_22};
    private final Context mContext;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new Fragment2();
            case 1:
                return new Fragment22();
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}