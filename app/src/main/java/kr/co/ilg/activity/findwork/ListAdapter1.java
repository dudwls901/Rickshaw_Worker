package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class ListAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

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
                Context context=view.getContext();
                Intent intent=new Intent(context,WorkInfoActivity.class);
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
                context.startActivity(intent);
            }

        });


    }
    @Override
    public int getItemCount() {
        return  workInfo.size();
    }
}
