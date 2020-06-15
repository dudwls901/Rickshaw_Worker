package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

import kr.co.ilg.fragment.Fragment1;
import kr.co.ilg.fragment.Fragment2;
import kr.co.ilg.fragment.Fragment3;

public class MainActivity extends AppCompatActivity {
    Spinner spinner1, spinner2;
    ArrayList spinner1_array, spinner2_array;
    ArrayAdapter spinner1_Adapter, spinner2_Adapter;
    RecyclerView urgency_RecyclerView, usually_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BottomNavigationView bottomNavigationView;
//    Fragment1 fragment1;
//    Fragment2 fragment2;
//    Fragment3 fragment3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        spinner1=findViewById(R.id.spinner1);
//        spinner2=findViewById(R.id.spinner2);
//
//        spinner1_array=new ArrayList();
//        spinner2_array=new ArrayList();
//        spinner1_array.add("스피너1");
//        spinner2_array.add("스피너2");
//
//        spinner1_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner1_array);
//        spinner2_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner2_array);
//
//        spinner1.setAdapter(spinner1_Adapter);
//        spinner2.setAdapter(spinner2_Adapter);
//
//        urgency_RecyclerView=findViewById(R.id.list_urgency);
//        urgency_RecyclerView.setHasFixedSize(true);
//        layoutManager=new LinearLayoutManager(this);
//        urgency_RecyclerView.setLayoutManager(layoutManager);
//
//        ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
//        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));
//
//        ListAdapter urgencyAdapter=new ListAdapter(workInfoArrayList);
//        urgency_RecyclerView.setAdapter(urgencyAdapter);
//
//        usually_RecyclerView=findViewById(R.id.list_usually);
//        usually_RecyclerView.setHasFixedSize(true);
//        layoutManager=new LinearLayoutManager(this);
//        usually_RecyclerView.setLayoutManager(layoutManager);
//
//        ArrayList<ListViewItem> workInfoArrayList2=new ArrayList<>();
//        workInfoArrayList2.add(new ListViewItem("레미안아파트 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));
//        workInfoArrayList2.add(new ListViewItem("자이아파트 건축","2020-06-30","150,000","광흥창 자이 아파트","건축","코끼리인력소","2","4"));
//
//        ListAdapter usuallyAdapter=new ListAdapter(workInfoArrayList2);
//        usually_RecyclerView.setAdapter(usuallyAdapter);

        bottomNavigationView = findViewById(R.id.bottomNavigationView); //프래그먼트 생성
//        fragment1 = new Fragment1();
//        fragment2 = new Fragment2();
//        fragment3 = new Fragment3(); //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss(); //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) { //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
                    case R.id.tab1: {
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment1).commitAllowingStateLoss();
//                        return true;
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                    case R.id.tab2: {
                        Intent intent = new Intent(MainActivity.this, MyFieldActivity.class);
                        startActivity(intent);
                    }
                    case R.id.tab3: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment3).commitAllowingStateLoss();
                        return true;
                    }
                    default:
                        return false;
                }
            }
        });


    }
}
