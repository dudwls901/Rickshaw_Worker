package kr.co.ilg.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import kr.co.ilg.activity.findwork.GoneFieldRequest;
import kr.co.ilg.activity.findwork.ListAdapter1;
import kr.co.ilg.activity.findwork.ListViewItem;
import kr.co.ilg.activity.findwork.Sharedpreference;


public class Fragment3 extends Fragment{
    ViewGroup viewGroup;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView Calendar1, Calendar2;
    final Calendar cal = Calendar.getInstance();
    final Calendar cal1 = Calendar.getInstance();
    final Calendar cal2 = Calendar.getInstance();
    final Calendar cal3 = Calendar.getInstance();
    final Calendar cal4 = Calendar.getInstance();
    int m=0, p=0, u=0;

    Dialog dialog;
    RadioGroup rdgroup;
    RadioButton btn1,btn2, btn3;
    Response.Listener rListener;
    String worker_email;
    View dialog1;
    int numofarray;
    String jp_is_urgency[];
    int jp_job_cost[];
    boolean k[];
    String date, phpdate, phpdate1;
    Button search;
    EditText edit1, edit2;
    String field_address[],business_reg_num[],jp_num[],jp_contents[],paid[], job_name[], jp_title[], fieldname[], jp_job_date[], officename[],mf_is_paid[],jp_job_start_time[],jp_job_finish_time[];


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);


        context = container.getContext();
        recyclerView = viewGroup.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        Calendar1 = viewGroup.findViewById(R.id.cal1);
        Calendar2 = viewGroup.findViewById(R.id.cal2);
        rdgroup = viewGroup.findViewById(R.id.radiogroup);
        btn1 = viewGroup.findViewById(R.id.btn1);
        btn1.setChecked(true);// 클릭되어있음.
        btn2 = viewGroup.findViewById(R.id.btn2);
        btn3 = viewGroup.findViewById(R.id.btn3);
        edit1 = viewGroup.findViewById(R.id.edt1);
        edit2 = viewGroup.findViewById(R.id.edt2);
        worker_email = Sharedpreference.get_email(context,"worker_email");
        rdgroup.setOnCheckedChangeListener(radioGroupButtonChangeListener);
        int m = cal.get(Calendar.MONTH);
        int y = cal.get(Calendar.YEAR);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        search = viewGroup.findViewById(R.id.search);


        edit1.setText(y + "년 "+m + "월 "+ d + "일");
        phpdate = y+"-"+m+"-"+d;
        edit2.setText(y + "년 "+(m+1) + "월 "+ d + "일");
        phpdate1 = y+"-"+(m+1)+"-"+d;

        dialog1 = (View) View.inflate(context, R.layout.calendar, null);

        Calendar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
                        phpdate = year+"-"+month+1+"-"+date;
                        edit1.setText(msg);
                        btnfalse();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();


            }
        });

        Calendar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d 년 %d 월 %d 일", year, month+1, date);
                        edit2.setText(msg);
                        phpdate1 = year+"-"+month+1+"-"+date;
                        btnfalse();
                    }
                }, cal1.get(Calendar.YEAR), cal1.get(Calendar.MONTH), cal1.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();


            }
        });







        /*workInfoArrayList.add(new ListViewItem("빌라 건축", "2020-06-14", 150000, "전기", "서울 영등포구 여의나루로57", "개미인력소", "지급완료", true,"2222222","22222222","3"));
        workInfoArrayList.add(new ListViewItem("제일아파트 건축", "2020-06-17", 130000, "페인트", "서울 영등포구 당산로 219", "베짱이인력소", "지급예정", false,"2222222","22222222","3"));
        workInfoArrayList.add(new ListViewItem("자이아파트 신축", "2020-06-20", 160000, "건축", "서울 영등포구 양평로24길 9", "사람인력소", "지급예정", false,"2222222","22222222","3"));
        workInfoArrayList.add(new ListViewItem("신송센터빌딩 철거", "2020-06-23", 160000, "철거", "서울 영등포구 양평로24길 10", "돈돈인력소", "지급예정", false,"2222222","22222222","3"));
        workInfoArrayList.add(new ListViewItem("신송센터빌딩 철거", "2020-06-23", 160000, "철거", "서울 영등포구 양평로24길 10", "돈돈인력소", "지급예정", false,"2222222","22222222","3"));*/





        rListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    JSONArray array = jResponse.getJSONArray("response");
                    numofarray = array.length();
                    job_name = new String[ numofarray];////
                    jp_job_cost = new int[ numofarray];////
                    field_address = new String[ numofarray];////
                    jp_title = new String[ numofarray];////
                    jp_is_urgency = new String[ numofarray];//
                    jp_job_start_time = new String[ numofarray];//
                    jp_job_finish_time = new String[ numofarray];//
                    jp_job_date = new String[ numofarray];////
                    officename = new String[ numofarray];////
                    fieldname= new String[ numofarray];////
                    mf_is_paid= new String[ numofarray];////
                    paid= new String[ numofarray];
                    jp_contents= new String[ numofarray];
                    k = new boolean[numofarray];
                    jp_num = new String[numofarray];
                    business_reg_num = new String[numofarray];

                    final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject MainRequest = array.getJSONObject(i);
                        job_name[i] = MainRequest.getString("job_name");
                        jp_job_cost[i] = MainRequest.getInt("jp_job_cost");//
                        field_address[i] = MainRequest.getString("field_address");
                        jp_title[i] = MainRequest.getString("jp_title");//
                        jp_job_date[i] = MainRequest.getString("jp_job_date");//
                        officename[i] = MainRequest.getString("office_name");
                        fieldname[i] = MainRequest.getString("field_name");
                        mf_is_paid[i] = MainRequest.getString("mf_is_paid");
                        jp_is_urgency[i] = MainRequest.getString("jp_is_urgency");
                        jp_job_start_time[i] = MainRequest.getString("jp_job_start_time");
                        jp_job_finish_time[i] = MainRequest.getString("jp_job_finish_time");
                        jp_contents[i] = MainRequest.getString("jp_contents");
                        jp_num[i]=MainRequest.getString("jp_num");
                        business_reg_num[i]=MainRequest.getString("business_reg_num");
                        if(mf_is_paid[i]=="0"){
                            paid[i] = "지급안됨";
                        }
                        else  paid[i] = "지급완료";
                        if(jp_is_urgency[i] == "0"){
                            k[i] = false;
                        }
                        else k[i]=true;



                        workInfoArrayList.add(new ListViewItem(business_reg_num[i],jp_num[i],fieldname[i],jp_title[i],jp_job_date[i],jp_job_cost[i],job_name[i],field_address[i],officename[i],paid[i],k[i],jp_job_start_time[i],jp_job_finish_time[i],jp_contents[i]));
                    } // 값넣기*/
                    ListAdapter1 myworkAdapter = new ListAdapter1(context.getApplicationContext(), workInfoArrayList);
                    recyclerView.setAdapter(myworkAdapter);


                } catch (Exception e) {
                    Log.d("mytest", e.toString());
                }
            }
        };

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoneFieldRequest mainRequest = new GoneFieldRequest(worker_email,phpdate, phpdate1, rListener);  // Request 처리 클래스

                RequestQueue queue = Volley.newRequestQueue(context);  // 데이터 전송에 사용할 Volley의 큐 객체 생성
                queue.add(mainRequest);
                Log.d("qqqqqqqqqqqqqqqq",phpdate+" "+phpdate1);
            }
        });



        return viewGroup;
    }
    RadioGroup.OnCheckedChangeListener radioGroupButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
        @Override public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
            if(i == R.id.btn1 && m==0) {
                m++;
                if (p!=0) {
                    p--;
                    cal3.add(Calendar.MONTH,+2);
                }
                if (u!=0) {
                    u--;
                    cal4.add(Calendar.MONTH,+5);
                }
                date = cal2.get(Calendar.YEAR) + "년 "+cal2.get(Calendar.MONTH) + "월 "+ cal2.get(Calendar.DAY_OF_MONTH) + "일";
                phpdate = cal2.get(Calendar.YEAR)+"-"+cal2.get(Calendar.MONTH)+"-"+cal2.get(Calendar.DAY_OF_MONTH);
                edit1.setText(date);

            }
            else if(i == R.id.btn2 && p==0){
                cal3.add(Calendar.MONTH,-2);
                p++;
                if (m!=0) {
                    m--;
                }
                if (u!=0) {
                    u--;
                    cal4.add(Calendar.MONTH,+5);
                }
                date = cal3.get(Calendar.YEAR) + "년 "+cal3.get(Calendar.MONTH) + "월 "+ cal3.get(Calendar.DAY_OF_MONTH) + "일";
                phpdate = cal3.get(Calendar.YEAR)+"-"+cal3.get(Calendar.MONTH)+"-"+cal3.get(Calendar.DAY_OF_MONTH);
                edit1.setText(date);
            }
            else if(i == R.id.btn3 && u==0){
                cal4.add(Calendar.MONTH,-5);
                u++;
                if (m!=0) {
                    m--;
                }
                if (p!=0) {
                    p--;
                    cal3.add(Calendar.MONTH,+2);
                }
                date = cal4.get(Calendar.YEAR) + "년 "+cal4.get(Calendar.MONTH) + "월 "+ cal4.get(Calendar.DAY_OF_MONTH) + "일";
                phpdate = cal4.get(Calendar.YEAR)+"-"+cal4.get(Calendar.MONTH)+"-"+cal4.get(Calendar.DAY_OF_MONTH);
                edit1.setText(date);
            }

        }
    };
    void btnfalse(){
        btn1.setChecked(false);
        btn2.setChecked(false);
        btn3.setChecked(false);

    }


}