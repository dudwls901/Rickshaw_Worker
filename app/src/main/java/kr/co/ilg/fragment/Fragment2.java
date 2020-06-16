package kr.co.ilg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    ViewGroup viewGroup;
private Context context;
private RecyclerView recyclerView1;
//    ArrayList spinner1_array, spinner2_array;
//    ArrayAdapter spinner1_Adapter, spinner2_Adapter;
//    RecyclerView urgency_RecyclerView, usually_RecyclerView;
//    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
context = container.getContext();



        return viewGroup;
    }


}







//        urgency_RecyclerView=viewGroup.findViewById(R.id.list_selection);
//        urgency_RecyclerView.setHasFixedSize(true);
//        layoutManager=new LinearLayoutManager(context);
//        urgency_RecyclerView.setLayoutManager(layoutManager);
//
//        ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
//        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));
//
//        ListAdapter urgencyAdapter=new ListAdapter(workInfoArrayList);
//        urgency_RecyclerView.setAdapter(urgencyAdapter);
//
//        usually_RecyclerView=viewGroup.findViewById(R.id.list_usually);
//        usually_RecyclerView.setHasFixedSize(true);
//        layoutManager=new LinearLayoutManager(context);
//        usually_RecyclerView.setLayoutManager(layoutManager);
//
//        ArrayList<ListViewItem> workInfoArrayList2=new ArrayList<>();
//        workInfoArrayList2.add(new ListViewItem("레미안아파트 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));
//     //   workInfoArrayList2.add(new ListViewItem("자이아파트 건축","2020-06-30","150,000","광흥창 자이 아파트","건축","코끼리인력소","2","4"));
//
//        ListAdapter usuallyAdapter=new ListAdapter(workInfoArrayList2);
//        usually_RecyclerView.setAdapter(usuallyAdapter);