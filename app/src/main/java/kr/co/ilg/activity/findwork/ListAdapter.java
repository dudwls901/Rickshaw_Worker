package kr.co.ilg.activity.findwork;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,pay,job,place,office,current_people,total_people;

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

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    private ArrayList<ListViewItem> workInfo;
    public ListAdapter(ArrayList<ListViewItem> workInfo){
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
        myViewHolder.title.setText(workInfo.get(position).title);
        myViewHolder.date.setText(workInfo.get(position).date);
        myViewHolder.pay.setText(workInfo.get(position).pay);
        myViewHolder.job.setText(workInfo.get(position).job);
        myViewHolder.place.setText(workInfo.get(position).place);
        myViewHolder.office.setText(workInfo.get(position).office);
        myViewHolder.current_people.setText(workInfo.get(position).current_people);
        myViewHolder.total_people.setText(workInfo.get(position).total_people);


    }
    @Override
    public int getItemCount() {
        return  workInfo.size();
    }
}
