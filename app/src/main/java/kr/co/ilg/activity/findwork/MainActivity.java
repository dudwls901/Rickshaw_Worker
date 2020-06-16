package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Dictionary;

//import kr.co.ilg.activity.mypage.MypageMainActivity;

import kr.co.ilg.activity.mypage.MypageMainActivity;
import kr.co.ilg.fragment.Fragment1;
import kr.co.ilg.fragment.Fragment2;
import kr.co.ilg.fragment.Fragment3;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    ArrayList spinner1_array, spinner2_array;
    ArrayAdapter spinner1_Adapter, spinner2_Adapter;
    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BottomNavigationView bottomNavigationView;


//    Fragment1 fragment1;
//    Fragment2 fragment2;
//    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        item1 = (MenuItem) findViewById(R.id.tab1);
//        item2 = (MenuItem)findViewById(R.id.tab2);
//        item3 = (MenuItem)findViewById(R.id.tab3);

        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);

        spinner1_array=new ArrayList();
        spinner2_array=new ArrayList();
        spinner1_array.add(" 서울 마포구 ");
        spinner2_array.add(" 전체 ");

        spinner1_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner1_array);
        spinner2_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner2_array);

        spinner1.setAdapter(spinner1_Adapter);
        spinner2.setAdapter(spinner2_Adapter);

        urgency_RecyclerView=findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        final ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","건축","상수 레미안 아파트","개미인력소","1","3"));
        workInfoArrayList.add(new ListViewItem("해모로 아파트 건축","2020-06-17","130,000","건축","광흥창 해모로 아파트","베짱이인력소","2","4"));
        workInfoArrayList.add(new ListViewItem("자이아파트 신축","2020-06-20","160,000","건축","광흥창 자이 아파트","사람인력소","1","5"));
        workInfoArrayList.add(new ListViewItem("마포 체육관 보수공사","2020-07-03","110,000","보수","마포구민체육관","당근인력소","1","3"));



        ListAdapter urgencyAdapter=new ListAdapter(getApplicationContext(),workInfoArrayList);
        urgency_RecyclerView.setAdapter(urgencyAdapter);




        bottomNavigationView = findViewById(R.id.bottomNavigationView1); //프래그먼트 생성
//        bottomNavigationView.getMenu().getItem(0).setIcon(R.drawable.search_mint);
//        bottomNavigationView.getMenu().getItem(1).setIcon(R.drawable.building_charcol);
//        bottomNavigationView.getMenu().getItem(2).setIcon(R.drawable.profile_charcol);
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

                        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent1);
                        return false;
                    }
                    case R.id.tab2: {

//                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
//                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                        item2.setChecked(true);
//                        item1.setChecked(false);
                        Intent intent2 = new Intent(MainActivity.this, MyFieldActivity.class);
                        startActivity(intent2);
                        return false;
                    }
                    case R.id.tab3: {
//
//                        bottomNavigationView.getMenu().getItem(2).setChecked(true);
//                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
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
