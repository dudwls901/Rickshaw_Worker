package kr.co.ilg.activity.findwork;

//import android.app.AlertDialog;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;

import java.util.ArrayList;

import kr.co.ilg.activity.login.FindPasswordInfoActivity;
import kr.co.ilg.activity.login.FindPwRequest;

public class ListWorkPickOutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LinearLayout gotoworkCheck, gotohomeCheck;
    TextView checkStart, checkFinish;
    Intent intent;
    View dialogView;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, pay, job, place, office, current_people, total_people, paid;
        LinearLayout linear1;

        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            pay = view.findViewById(R.id.pay);
            job = view.findViewById(R.id.job);
            place = view.findViewById(R.id.place);
            office = view.findViewById(R.id.office);
            current_people = view.findViewById(R.id.current_people);
            total_people = view.findViewById(R.id.total_people);
            linear1 = view.findViewById(R.id.linear1);

        }
    }


    private Context context;
    private ArrayList<ListViewItem> workInfo;

    public ListWorkPickOutAdapter(Context context, ArrayList<ListViewItem> workInfo) {
        this.context = context;
        this.workInfo = workInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywork_pickoutlist_custom, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;

//Log.d("booltest",workInfo.get(position).urgency);
//        Log.d("booltest",workInfo.get(position).title);
        if (workInfo.get(position).urgency == false) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(String.valueOf(workInfo.get(position).pay));
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);

        } else if (workInfo.get(position).urgency == true) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(String.valueOf(workInfo.get(position).pay));
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);
            myViewHolder.linear1.setBackgroundColor(context.getResources().getColor(R.color.UrgencyColor));
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                context = view.getContext();
                final AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dialogView = View.inflate(context, R.layout.work_check, null);
                dlg.setView(dialogView);
                gotoworkCheck = dialogView.findViewById(R.id.gotoworkCheck);
                gotohomeCheck = dialogView.findViewById(R.id.gotohomeCheck);
                checkStart = dialogView.findViewById(R.id.checkStart);
                checkFinish = dialogView.findViewById(R.id.checkFinish);

                checkStart.setText("출근 인증\n" + workInfo.get(position).jp_job_start_time);
                checkFinish.setText("퇴근 인증\n" + workInfo.get(position).jp_job_finish_time);

                dlg.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean select_CT = jResponse.getBoolean("select_CT");
                            if(select_CT) {
                                String mf_is_choolgeun =  jResponse.getString("mf_is_choolgeun");
                                String mf_is_toigeun =  jResponse.getString("mf_is_toigeun");

                                if(mf_is_choolgeun.equals("1"))
                                    gotoworkCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                                else ;
                                if(mf_is_toigeun.equals("1"))
                                    gotohomeCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                                else ;

                            } else {
                                Toast.makeText(context, "출퇴근 여부 로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                MyFieldSelectRequest mfsRequest = new MyFieldSelectRequest(Sharedpreference.get_email(context, "worker_email"), workInfo.get(position).jp_num, rListener);

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(mfsRequest);

                gotoworkCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotoworkCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                    }
                });
                gotohomeCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gotohomeCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                    }
                });
                dlg.show();
            }

        });


    }

    @Override
    public int getItemCount() {
        return workInfo.size();
    }
}
