package kr.co.ilg.activity.findwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstone.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

//import kr.co.ilg.activity.mypage.MypageMainActivity;

import kr.co.ilg.activity.mypage.MypageMainActivity;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1, spinner2;
    ArrayList spinner1_array, spinner2_array;
    ArrayAdapter spinner1_Adapter, spinner2_Adapter;
    RecyclerView urgency_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    BottomNavigationView bottomNavigationView;
    Intent intent;
    Toolbar toolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_maintop, menu);
        return true;
    }


@Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.search:

                Toast.makeText(getApplicationContext(), "search 클릭", Toast.LENGTH_LONG).show();
                return true;

            case R.id.map :
                Intent intent=new Intent(MainActivity.this,WorkMapActivity.class);
                startActivity(intent);
                return true;

            default:
                return false;
        }
    }


                @Override
                protected void onCreate (Bundle savedInstanceState){
                    super.onCreate(savedInstanceState);
                    setContentView(R.layout.activity_main);

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

                    final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();
                    workInfoArrayList.add(new ListViewItem("레미안 건축", "2020-06-14", "150,000", "건축", "상수 레미안 아파트", "개미인력소", "1", "3", true));
                    workInfoArrayList.add(new ListViewItem("해모로 아파트 건축", "2020-06-17", "130,000", "건축", "광흥창 해모로 아파트", "베짱이인력소", "2", "4", false));
                    workInfoArrayList.add(new ListViewItem("자이아파트 신축", "2020-06-20", "160,000", "건축", "광흥창 자이 아파트", "사람인력소", "1", "5", false));
                    workInfoArrayList.add(new ListViewItem("마포 체육관 보수공사", "2020-07-03", "110,000", "보수", "마포구민체육관", "당근인력소", "1", "3", false));


                    ListAdapter urgencyAdapter = new ListAdapter(getApplicationContext(), workInfoArrayList);
                    urgency_RecyclerView.setAdapter(urgencyAdapter);


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
