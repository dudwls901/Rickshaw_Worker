package kr.co.ilg.activity.findwork;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView user,date,contents;

            MyViewHolder(View view){
                super(view);
                user=view.findViewById(R.id.user);
                date=view.findViewById(R.id.date);
                contents=view.findViewById(R.id.contents);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }

        private ArrayList<ReviewItem> reviewList;
        public ReviewAdapter(ArrayList<ReviewItem> reviewList){
            this.reviewList=reviewList;
        }


        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_custom,parent,false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            MyViewHolder myViewHolder=(MyViewHolder) holder;
            myViewHolder.user.setText(reviewList.get(position).user);
            myViewHolder.date.setText(reviewList.get(position).date);
            myViewHolder.contents.setText(reviewList.get(position).contents);
        }
        @Override
        public int getItemCount() {
            return reviewList.size();
        }
    }


