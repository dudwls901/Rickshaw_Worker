package kr.co.ilg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.co.ilg.activity.findwork.ListAdapter;
import kr.co.ilg.activity.findwork.ListWorkPickOutAdapter;
import kr.co.ilg.activity.findwork.ListViewItem;
import kr.co.ilg.activity.findwork.MainActivity;
import kr.co.ilg.activity.findwork.MainRequest;
import kr.co.ilg.activity.findwork.Sharedpreference;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    ViewGroup viewGroup;
    private Context context;

    String worker_email;
    RecyclerView recyclerView1, recyclerView2;
    RecyclerView.LayoutManager layoutManager1, layoutManager2;
    ArrayList<ListViewItem> pickworkInfoArrayList, workInfoArrayList;
    int jp_job_cost[];
    int jp_job_tot_people[], jp_job_picked_people[];
    boolean is_urgency[], is_picked[];
    String jp_num[], field_address[], field_name[], job_name[], business_reg_num[], local_sido1[],local_sigugun1[], jp_title[], jp_contents[],
            jp_job_date[], jp_job_start_time[], jp_job_finish_time[], jp_is_urgency[], jp_datetime[], apply_is_picked[], manager_office_name[];


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

        recyclerView2 = viewGroup.findViewById(R.id.recyclerview2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(context);
        recyclerView2.setLayoutManager(layoutManager2);

        final int[] numofpost = new int[1];
        worker_email = Sharedpreference.get_email(context, "worker_email","memberinfo");

        // 선발 완료 리싸이클러뷰
        Response.Listener rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    numofpost[0] = array.length();

                    business_reg_num = new String[numofpost[0]];
                    jp_title = new String[numofpost[0]];
                    jp_job_cost = new int[numofpost[0]];
                    jp_job_date = new String[numofpost[0]];
                    jp_job_start_time = new String[numofpost[0]];
                    jp_job_finish_time = new String[numofpost[0]];
                    jp_is_urgency = new String[numofpost[0]];
                    jp_num = new String[numofpost[0]];
                    apply_is_picked = new String[numofpost[0]];
                    jp_job_tot_people = new int[numofpost[0]];
                    jp_contents = new String[numofpost[0]];
                    jp_datetime = new String[numofpost[0]];
                    job_name = new String[numofpost[0]];
                    field_address = new String[numofpost[0]];
                    field_name = new String[numofpost[0]];
                    jp_job_picked_people = new int[numofpost[0]];
                    manager_office_name = new String[numofpost[0]];

                    is_urgency = new boolean[numofpost[0]];
                    is_picked = new boolean[numofpost[0]];

                    pickworkInfoArrayList = new ArrayList<>();
                    workInfoArrayList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject JPInfo = array.getJSONObject(i);
                        business_reg_num[i] = JPInfo.getString("business_reg_num");
                        jp_title[i] = JPInfo.getString("jp_title");
                        jp_job_cost[i] = JPInfo.getInt("jp_job_cost");
                        jp_job_date[i] = JPInfo.getString("jp_job_date");
                        jp_job_start_time[i] = JPInfo.getString("jp_job_start_time");
                        jp_job_finish_time[i] = JPInfo.getString("jp_job_finish_time");
                        jp_is_urgency[i] = JPInfo.getString("jp_is_urgency");
                        if(jp_is_urgency[i].equals("0")) is_urgency[i]=false;
                        else is_urgency[i] = true;
                        jp_num[i] = JPInfo.getString("jp_num");
                        apply_is_picked[i] = JPInfo.getString("apply_is_picked");
                        if(apply_is_picked[i].equals("0")) is_picked[i]=false;
                        else is_picked[i] = true;
                        jp_job_tot_people[i] = JPInfo.getInt("jp_job_tot_people");
                        jp_contents[i] = JPInfo.getString("jp_contents");
                        jp_datetime[i] = JPInfo.getString("jp_datetime");
                        job_name[i] = JPInfo.getString("job_name");
                        field_address[i] = JPInfo.getString("field_address");
                        field_name[i] = JPInfo.getString("field_name");
                        jp_job_picked_people[i]= JPInfo.getInt("jp_job_current_people");
                        manager_office_name[i] = JPInfo.getString("manager_office_name");

//                        jp_job_tot_people[i] = JPInfo.getInt("jp_job_tot_people");
//                        jp_job_current_people[i]= JPInfo.getInt("current_people");
//                        jp_contents[i] = JPInfo.getString("jp_contents");
//                        jp_datetime[i] = JPInfo.getString("jp_datetime");
                        //Log.d("=====================", jp_title[i] + " | " + jp_num[i] + " | " + jp_job_cost[i] + " | " + jp_job_tot_people[i] + " | " + jp_job_picked_people[i]);

                        if (is_picked[i])
                            pickworkInfoArrayList.add(new ListViewItem(jp_num[i], jp_title[i], jp_job_date[i], jp_job_cost[i], job_name[i], field_address[i], manager_office_name[i],
                                    jp_job_picked_people[i], jp_job_tot_people[i], is_urgency[i], jp_job_start_time[i], jp_job_finish_time[i], jp_contents[i]));
                        else
                            workInfoArrayList.add(new ListViewItem(business_reg_num[i], jp_num[i], jp_title[i], jp_job_date[i], jp_job_cost[i], job_name[i], field_address[i],
                                    manager_office_name[i], jp_job_picked_people[i], jp_job_tot_people[i], is_urgency[i], jp_job_start_time[i], jp_job_finish_time[i], jp_contents[i], field_name[i]));
                    }
                    ListWorkPickOutAdapter myworkAdapter1 = new ListWorkPickOutAdapter(context.getApplicationContext(), pickworkInfoArrayList);
                    recyclerView1.setAdapter(myworkAdapter1);

                    ListAdapter myworkAdapter2 = new ListAdapter(context.getApplicationContext(), workInfoArrayList);
                    recyclerView2.setAdapter(myworkAdapter2);

                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };
        ApplyStateRequest applyStateRequest = new ApplyStateRequest(worker_email, rListener);  // Request 처리 클래스

        RequestQueue queue = Volley.newRequestQueue(context);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
        queue.add(applyStateRequest);  // Volley로 구현된 큐에 ValidateRequest 객체를 넣어둠으로써 실제로 서버 연동 발생


//        final ArrayList<ListViewItem> pickworkInfoArrayList = new ArrayList<>();
//        pickworkInfoArrayList.add(new ListViewItem("레미안 건축", "2020-06-14", 150000, "건축", "상수 레미안 아파트", "개미인력소", true));
//        pickworkInfoArrayList.add(new ListViewItem("해모로 아파트 건축", "2020-06-17", 130000, "건축", "광흥창 해모로 아파트", "베짱이인력소", false));
//        pickworkInfoArrayList.add(new ListViewItem("자이아파트 신축", "2020-06-20", 160000, "건축", "광흥창 자이 아파트", "사람인력소", false));
//
//
//        ListWorkPickOutAdapter myworkAdapter1 = new ListWorkPickOutAdapter(context.getApplicationContext(), pickworkInfoArrayList);
//        recyclerView1.setAdapter(myworkAdapter1);

//선발대기 리스트뷰 생성
//        final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();
//        recyclerView2 = viewGroup.findViewById(R.id.recyclerview2);
//        recyclerView2.setHasFixedSize(true);
//        layoutManager2 = new LinearLayoutManager(context);
//        recyclerView2.setLayoutManager(layoutManager2);
//
//        workInfoArrayList.add(new ListViewItem("마포 체육관 보수공사", "2020-07-03", 110000, "보수", "마포구민체육관", "당근인력소", 1, 3, false,"222", "222", "3"));
//        workInfoArrayList.add(new ListViewItem("명지전문대학 운동장 공사", "2020-07-04", 120000, "보통인부", "명지전문대학", "당근인력소", 2, 3));
//        workInfoArrayList.add(new ListViewItem("명지대학교 기숙사 철거", "2020-07-05", 130000, "보통인부", "명지대학교", "사람인력소", 2, 3));
//
//        ListAdapter myworkAdapter2 = new ListAdapter(context.getApplicationContext(), workInfoArrayList);
//        recyclerView2.setAdapter(myworkAdapter2);

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