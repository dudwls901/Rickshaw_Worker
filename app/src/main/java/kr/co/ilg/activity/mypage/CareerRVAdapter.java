package kr.co.ilg.activity.mypage;

import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class CareerRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public ArrayList<CareerRVItem> cList =null;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView job;


        MyViewHolder(View view) {
            super(view);
            job = view.findViewById(R.id.job);

        }
    }

    CareerRVAdapter(ArrayList<CareerRVItem> cList) {
        this.cList = cList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.career_item, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        Log.d("qq onBindViewHolder", String.valueOf(position));
        myViewHolder.job.setText(cList.get(position).c_job);

    }

    @Override
    public int getItemCount() {
        return cList.size();
    }


}
