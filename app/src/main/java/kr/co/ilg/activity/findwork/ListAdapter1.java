package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class ListAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    View dialogView;
    Button btnPay, btnReview;
    Context dcontext;
    ConstraintLayout workinfoCons, fieldReview, supervisorReview;
    Intent intent;

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,pay,job,place,office,current_people,total_people,paid;
        LinearLayout linear1;
        MyViewHolder(View view){
            super(view);
            title=view.findViewById(R.id.title);
            date=view.findViewById(R.id.date);
            pay=view.findViewById(R.id.pay);
            paid=view.findViewById(R.id.paid);
            job=view.findViewById(R.id.job);
            place=view.findViewById(R.id.place);
            office=view.findViewById(R.id.office);
        }
    }

    private Context context;
    private ArrayList<ListViewItem> workInfo;
    public ListAdapter1(Context context, ArrayList<ListViewItem> workInfo){
        this.context=context;
        this.workInfo=workInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.mywork_gonelist_custom,parent,false);

        return new ListAdapter1.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ListAdapter1.MyViewHolder myViewHolder=(ListAdapter1.MyViewHolder) holder;

        myViewHolder.title.setText(workInfo.get(position).title);
        myViewHolder.date.setText(workInfo.get(position).date);
        myViewHolder.pay.setText(String.valueOf(workInfo.get(position).pay));
        myViewHolder.job.setText(workInfo.get(position).job);
        myViewHolder.place.setText(workInfo.get(position).place);
        myViewHolder.office.setText(workInfo.get(position).office);
        myViewHolder.paid.setText(workInfo.get(position).paid);


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                context =view.getContext();
                final AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dialogView = View.inflate(context, R.layout.workpickoutdialog, null);
                dlg.setView(dialogView);
                workinfoCons = dialogView.findViewById(R.id.workinfo);
                fieldReview = dialogView.findViewById(R.id.fieldReview);
                supervisorReview = dialogView.findViewById(R.id.supervisorReview);

                dlg.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                workinfoCons.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context =v.getContext();
                        intent = new Intent(context, WorkInfoActivity.class);
                        intent.putExtra("jp_title",workInfo.get(position).title);
                        intent.putExtra("field_address",workInfo.get(position).place);
                        intent.putExtra("manager_office_name",workInfo.get(position).office);
                        intent.putExtra("job_name",workInfo.get(position).job);
                        intent.putExtra("jp_job_cost",String.valueOf(workInfo.get(position).pay));
                        intent.putExtra("jp_job_date",workInfo.get(position).date);
                        intent.putExtra("jp_job_start_time",workInfo.get(position).jp_job_start_time);
                        intent.putExtra("jp_job_finish_time",workInfo.get(position).jp_job_finish_time);
                        intent.putExtra("jp_job_tot_people",String.valueOf(workInfo.get(position).total_people));
                        intent.putExtra("jp_contents",workInfo.get(position).jp_contents);
                        intent.putExtra("field_name",workInfo.get(position).fieldname);
                        intent.putExtra("business_reg_num",workInfo.get(position).business_reg_num);
                        context.startActivity(intent);
                    }
                });

                fieldReview.setOnClickListener(new View.OnClickListener() {//현장후기작성
                    @Override
                    public void onClick(View v) {

                        dcontext =v.getContext();
                        intent=new Intent(context, UserReviewWriteActivity.class);

                        String field_name=workInfo.get(position).fieldname;
                        String jp_num = workInfo.get(position).jp_num;
                        intent.putExtra("key",0);
                        intent.putExtra("jp_num",jp_num);
                        intent.putExtra("현장 후기", field_name);

                        context.startActivity(intent);

                    }
                });
                supervisorReview.setOnClickListener(new View.OnClickListener() {//인력사무소(구인자)후기작성
                    @Override
                    public void onClick(View v) {

                        dcontext =v.getContext();
                        intent=new Intent(context, UserReviewWriteActivity.class);

                        String manager_name=workInfo.get(position).office;
                        String jp_num = workInfo.get(position).business_reg_num;
                        intent.putExtra("key",1);
                        intent.putExtra("jp_num",jp_num);
                        intent.putExtra("인력사무소 후기", manager_name);

                        context.startActivity(intent);


                    }
                });
                dlg.show();
            }

        });


    }
    @Override
    public int getItemCount() {
        return  workInfo.size();
    }
}
