package kr.co.ilg.activity.mypage;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class noticeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView noticedate, noticetitle;

        MyViewHolder(View view){
            super(view);
            noticedate = view.findViewById(R.id.noticedate);
            noticetitle = view.findViewById(R.id.noticetitle);
        }
    }

    public ArrayList<noticeitem> List;
    noticeAdapter(ArrayList<noticeitem> List){
        this.List = List;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notice_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.noticetitle.setText(List.get(position).noticetitle);
        myViewHolder.noticedate.setText(List.get(position).noticedate);
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}