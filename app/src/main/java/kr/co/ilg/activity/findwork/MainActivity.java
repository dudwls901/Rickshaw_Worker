package kr.co.ilg.activity.findwork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.capstone.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Spinner spinner1,spinner2;
    ArrayList spinner1_array,spinner2_array;
    ArrayAdapter spinner1_Adapter,spinner2_Adapter;
    RecyclerView urgency_RecyclerView,usually_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner1=findViewById(R.id.spinner1);
        spinner2=findViewById(R.id.spinner2);

        spinner1_array=new ArrayList();
        spinner2_array=new ArrayList();
        spinner1_array.add("스피너1");
        spinner2_array.add("스피너2");

        spinner1_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner1_array);
        spinner2_Adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,spinner2_array);

        spinner1.setAdapter(spinner1_Adapter);
        spinner2.setAdapter(spinner2_Adapter);

        urgency_RecyclerView=findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));

        ListAdapter urgencyAdapter=new ListAdapter(workInfoArrayList);
        urgency_RecyclerView.setAdapter(urgencyAdapter);

        usually_RecyclerView=findViewById(R.id.list_usually);
        usually_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        usually_RecyclerView.setLayoutManager(layoutManager);

        ArrayList<ListViewItem> workInfoArrayList2=new ArrayList<>();
        workInfoArrayList2.add(new ListViewItem("레미안아파트 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));
        workInfoArrayList2.add(new ListViewItem("자이아파트 건축","2020-06-30","150,000","광흥창 자이 아파트","건축","코끼리인력소","2","4"));

        ListAdapter usuallyAdapter=new ListAdapter(workInfoArrayList2);
        usually_RecyclerView.setAdapter(usuallyAdapter);




    }
}
