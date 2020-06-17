package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class ListWorkPickOutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


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
            myViewHolder.pay.setText(workInfo.get(position).pay);
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);

        }
        else if (workInfo.get(position).urgency == true) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(workInfo.get(position).pay);
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);
            myViewHolder.linear1.setBackgroundColor(context.getResources().getColor(R.color.UrgencyColor));
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {




            }

        });


    }

    @Override
    public int getItemCount() {
        return workInfo.size();
    }
}
