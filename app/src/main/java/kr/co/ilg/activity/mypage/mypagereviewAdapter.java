package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class mypagereviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public static class MyViewHolder extends RecyclerView.ViewHolder{


        TextView mypagereviewname, mypagereview, mypagereviewdate;

        MyViewHolder(View view){
            super(view);

            mypagereviewname = view.findViewById(R.id.reviewfield);
            mypagereview = view.findViewById(R.id.mypagereview);
            mypagereviewdate = view.findViewById(R.id.mypagereviewdate);
        }
    }

    public ArrayList<mypagereviewitem> List;
    public mypagereviewAdapter(ArrayList<mypagereviewitem> List){
        this.List = List;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mypagereviewitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.mypagereviewname.setText(List.get(position).mypagereviewname);
        myViewHolder.mypagereview.setText(List.get(position).mypagereview);
        myViewHolder.mypagereviewdate.setText(List.get(position).mypagereviewdate);
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}