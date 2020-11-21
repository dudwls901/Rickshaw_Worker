package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class mypagereviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String ForOInfo, String dt);
    }

    private OnItemClickListener mListener = null;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    } //리스너객체를 전달하는 메서드와 전달된 객체를 저장할 변수를 추가

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mypagereviewname, mypagereview, mypagereviewdate, dltBtn;

        MyViewHolder(View view){
            super(view);

            mypagereviewname = view.findViewById(R.id.reviewfield);
            mypagereview = view.findViewById(R.id.mypagereview);
            mypagereviewdate = view.findViewById(R.id.mypagereviewdate);
            dltBtn = view.findViewById(R.id.dltBtn);
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
        String ForOInfo = List.get(position).ForOInfo;
        String dt = List.get(position).mypagereviewdate;


        myViewHolder.dltBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("=+=+=++=+=+=+=+=+=+=+=", ForOInfo + dt);
                mListener.onItemClick(v, position, ForOInfo, dt); // 뷰홀더 밖에서 리스너처리를 하게끔 연결
            }
        });
    }

    @Override
    public int getItemCount() {
        return List.size();
    }
}