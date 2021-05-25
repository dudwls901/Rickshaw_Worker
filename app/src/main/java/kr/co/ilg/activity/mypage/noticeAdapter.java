package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.WorkInfoActivity;

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

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent intent=new Intent(context, NoticeInfoActivity.class);
                intent.putExtra("noticetitle", List.get(position).noticetitle);
                intent.putExtra("noticedate", List.get(position).noticedate);
                intent.putExtra("user",List.get(position).user);
                intent.putExtra("content",List.get(position).content);
                context.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}