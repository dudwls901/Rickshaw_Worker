package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.content.Intent;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class ListExpandableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int position;

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,date,pay,job,place,office,current_people,total_people,gotoworkText,getoffworkText;
ImageView gotoworkImage, getoffworkImage;
LinearLayout workcheckLayout;

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
            gotoworkText=view.findViewById(R.id.gotoworkText);
            getoffworkText=view.findViewById(R.id.getoffworkText);
            gotoworkImage=view.findViewById(R.id.gotoworkImage);
            getoffworkImage=view.findViewById(R.id.getoffworkImage);
            workcheckLayout=view.findViewById(R.id.workcheckLayout);
        }
    }


    private  Context context;
    private ArrayList<ListExpandableViewItem> workInfo;
    public ListExpandableAdapter(Context context, ArrayList<ListExpandableViewItem> workInfo){
        this.context=context;
        this.workInfo=workInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.mywork_header,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myViewHolder=(MyViewHolder) holder;
      this.position = position;
        myViewHolder.title.setText(workInfo.get(position).title);
        myViewHolder.date.setText(workInfo.get(position).date);
        myViewHolder.pay.setText(workInfo.get(position).pay);
        myViewHolder.job.setText(workInfo.get(position).job);
        myViewHolder.place.setText(workInfo.get(position).place);
        myViewHolder.office.setText(workInfo.get(position).office);

//        int mExpandedPosition=-1;
//        final boolean isExpanded = position==mExpandedPosition;
//        myViewHolder.workcheckLayout.setVisibility(View.GONE);
//        myViewHolder.workcheckLayout.setActivated(false);
  //      notifyItemChanged(position);
 //      myViewHolder.current_people.setText(workInfo.get(position).current_people);
 //       myViewHolder.total_people.setText(workInfo.get(position).total_people);
//       myViewHolder.gotoworkImage.setImageResource(R.drawable.building);
//        myViewHolder.getoffworkImage.setImageResource(R.drawable.building);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener(){
            private SparseBooleanArray selectedItems = new SparseBooleanArray();
            private int prePosition = -1;

            @Override
            public void onClick(View view) {
//                Context context=view.getContext();
//                Intent intent=new Intent(context,WorkInfoActivity.class);
//                context.startActivity(intent);
                if(myViewHolder.workcheckLayout.getVisibility()==View.GONE) {
                    myViewHolder.workcheckLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    myViewHolder.workcheckLayout.setVisibility(View.GONE);

                }
     //myViewHolder.workcheckLayout.removeViewInLayout(view);
//     selectedItems.delete(position);

            }

        });


    }
    @Override
    public int getItemCount() {
        return  workInfo.size();
    }
}
