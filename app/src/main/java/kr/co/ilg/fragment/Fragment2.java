package kr.co.ilg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.ilg.activity.findwork.ListAdapter;
import kr.co.ilg.activity.findwork.ListWorkPickOutAdapter;
import kr.co.ilg.activity.findwork.ListViewItem;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    ViewGroup viewGroup;
    private Context context;


    RecyclerView recyclerView1, recyclerView2;
    RecyclerView.LayoutManager layoutManager1, layoutManager2;

    //    ArrayList spinner1_array, spinner2_array;
//    ArrayAdapter spinner1_Adapter, spinner2_Adapter;
//    RecyclerView urgency_RecyclerView, usually_RecyclerView;
//    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);
        context = container.getContext();
        recyclerView1 = viewGroup.findViewById(R.id.recyclerview1);
        recyclerView1.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(context);
        recyclerView1.setLayoutManager(layoutManager1);

        final ArrayList<ListViewItem> pickworkInfoArrayList = new ArrayList<>();
        pickworkInfoArrayList.add(new ListViewItem("레미안 건축", "2020-06-14", 150000, "건축", "상수 레미안 아파트", "개미인력소", true));
        pickworkInfoArrayList.add(new ListViewItem("해모로 아파트 건축", "2020-06-17", 130000, "건축", "광흥창 해모로 아파트", "베짱이인력소", false));
        pickworkInfoArrayList.add(new ListViewItem("자이아파트 신축", "2020-06-20", 160000, "건축", "광흥창 자이 아파트", "사람인력소", false));


        ListWorkPickOutAdapter myworkAdapter1 = new ListWorkPickOutAdapter(context.getApplicationContext(), pickworkInfoArrayList);
        recyclerView1.setAdapter(myworkAdapter1);

//선발대기 리스트뷰 생성
        final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();
        recyclerView2 = viewGroup.findViewById(R.id.recyclerview2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(context);
        recyclerView2.setLayoutManager(layoutManager2);

        workInfoArrayList.add(new ListViewItem("마포 체육관 보수공사", "2020-07-03", 110000, "보수", "마포구민체육관", "당근인력소", 1, 3));
        workInfoArrayList.add(new ListViewItem("명지전문대학 운동장 공사", "2020-07-04", 120000, "보통인부", "명지전문대학", "당근인력소", 2, 3));
        workInfoArrayList.add(new ListViewItem("명지대학교 기숙사 철거", "2020-07-05", 130000, "보통인부", "명지대학교", "사람인력소", 2, 3));

        ListAdapter myworkAdapter2 = new ListAdapter(context.getApplicationContext(), workInfoArrayList);
        recyclerView2.setAdapter(myworkAdapter2);

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