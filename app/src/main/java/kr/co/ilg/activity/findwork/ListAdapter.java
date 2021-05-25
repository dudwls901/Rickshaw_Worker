package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,pay,job,place,office,current_people,total_people;
        LinearLayout linear1;
        MyViewHolder(View view){
            super(view);
            title=view.findViewById(R.id.title);
            date=view.findViewById(R.id.date);
            pay=view.findViewById(R.id.pay);
            job=view.findViewById(R.id.job);
            place=view.findViewById(R.id.place);
            office=view.findViewById(R.id.office);
            current_people=view.findViewById(R.id.current_people);
            total_people=view.findViewById(R.id.total_people);
            linear1 = view.findViewById(R.id.linear1);
        }
    }

    private  Context context;
    private ArrayList<ListViewItem> workInfo;
    public ListAdapter(Context context, ArrayList<ListViewItem> workInfo){
        this.context=context;
        this.workInfo=workInfo;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.work_list_custom,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder=(MyViewHolder) holder;
        if (workInfo.get(position).urgency == false) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(String.valueOf(workInfo.get(position).pay));
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);
            myViewHolder.current_people.setText(String.valueOf(workInfo.get(position).current_people));
            myViewHolder.total_people.setText(String.valueOf(workInfo.get(position).total_people));
        }
        else if (workInfo.get(position).urgency == true) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(String.valueOf(workInfo.get(position).pay));
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);
            myViewHolder.current_people.setText(String.valueOf(workInfo.get(position).current_people));
            myViewHolder.total_people.setText(String.valueOf(workInfo.get(position).total_people));
            myViewHolder.linear1.setBackgroundColor(context.getResources().getColor(R.color.UrgencyColor));

        }


        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent intent=new Intent(context,WorkInfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("business_reg_num", workInfo.get(position).business_reg_num);
                intent.putExtra("jp_num", workInfo.get(position).jp_num);
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
                intent.putExtra("urgency",workInfo.get(position).urgency);
                context.startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);;
            }

        });


    }
    @Override
    public int getItemCount() {
        return  workInfo.size();
    }
}
