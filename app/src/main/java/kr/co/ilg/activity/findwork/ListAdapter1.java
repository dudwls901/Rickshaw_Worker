package kr.co.ilg.activity.findwork;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

public class ListAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context dcontext;
    Intent intent;
    View clickedView;
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title,date,pay,job,place,office,current_people,total_people,paid;
        LinearLayout btnWorkInfo, btnBuilding, btnPeople, expanded_menu;

        MyViewHolder(View view){
            super(view);
            title=view.findViewById(R.id.title);
            date=view.findViewById(R.id.date);
            pay=view.findViewById(R.id.pay);
            paid=view.findViewById(R.id.paid);
            job=view.findViewById(R.id.job);
            place=view.findViewById(R.id.place);
            office=view.findViewById(R.id.office);
            btnWorkInfo = view.findViewById(R.id.btnWorkInfo);
            btnBuilding = view.findViewById(R.id.btnBuilding);
            btnPeople = view.findViewById(R.id.btnPeople);
            expanded_menu = view.findViewById(R.id.expanded_menu);
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
                context =view.getContext();
                //전에 클릭한 것이 없을 때
                if(clickedView==null)
                {
                    clickedView=myViewHolder.expanded_menu;
                    changeVisibility(true,clickedView);
                    // recyclerView.smoothScrollToPosition();
                    //    Log.d("viewposition",(int)view.getY()-1+"");
                }
                else//전에 클릭한 것이 있을 때
                {
//                        같은 거 클릭했을 때
                    if(clickedView == myViewHolder.expanded_menu) {
                        changeVisibility(false,clickedView);
                        clickedView = null;
                    }
                    //다른 거 클릭했을 때
                    else {
                        changeVisibility(true,myViewHolder.expanded_menu);;
                        changeVisibility(false,clickedView);;
                        clickedView = myViewHolder.expanded_menu;

                    }
                }


                myViewHolder.btnWorkInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context =v.getContext();
                        intent = new Intent(context, WorkInfoActivity.class);
                        intent.putExtra("jp_num",workInfo.get(position).jp_num);
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
                        intent.putExtra("business_reg_num",workInfo.get(position).business_reg_num);
                        context.startActivity(intent);
                    }
                });

                myViewHolder.btnBuilding.setOnClickListener(new View.OnClickListener() {//현장후기작성
                    @Override
                    public void onClick(View v) {

                        dcontext =v.getContext();
                        intent=new Intent(context, UserReviewWriteActivity.class);

                        String field_name=workInfo.get(position).fieldname;
                        String jp_num = workInfo.get(position).jp_num;
                        intent.putExtra("key",0);
                        intent.putExtra("jp_num",jp_num);
                        intent.putExtra("현장 후기", field_name);

                        context.startActivity(intent);

                    }
                });
                myViewHolder.btnPeople.setOnClickListener(new View.OnClickListener() {//인력사무소(구인자)후기작성
                    @Override
                    public void onClick(View v) {

                        dcontext =v.getContext();
                        intent=new Intent(context, UserReviewWriteActivity.class);

                        String manager_name=workInfo.get(position).office;
                        String jp_num = workInfo.get(position).business_reg_num;
                        intent.putExtra("key",1);
                        intent.putExtra("jp_num",jp_num);
                        intent.putExtra("인력사무소 후기", manager_name);

                        context.startActivity(intent);


                    }
                });

            }
//itemview클릭리스너끝
        });


    }
    private void changeVisibility(final boolean isExpanded, View view) {
        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 300) : ValueAnimator.ofInt(600, 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(200);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.getLayoutParams().height = (int) animation.getAnimatedValue();
                view.requestLayout();
                view.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        // Animation start
        va.start();
    }
    @Override
    public int getItemCount() {
        return  workInfo.size();
    }
}
