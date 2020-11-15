package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.capstone.R;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.ListViewItem;

public class HopeJobCareerLVAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    private TextView jobTv;
    private TextView careerTv;
    private ArrayList<HopeJobCareerLVItem> hopeJobCareerLVItems = new ArrayList<HopeJobCareerLVItem>();

    public HopeJobCareerLVAdapter(Context context, ArrayList<HopeJobCareerLVItem> hopeJobCareerLVItems) {
        mContext = context;
        this.hopeJobCareerLVItems = hopeJobCareerLVItems;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mLayoutInflater.inflate(R.layout.hope_job_career_item, null);

        jobTv = view.findViewById(R.id.jobTv);
        careerTv = view.findViewById(R.id.careerTv);

        jobTv.setText(hopeJobCareerLVItems.get(position).getMyJob());
        careerTv.setText(hopeJobCareerLVItems.get(position).getMyCareer());

        return view;
    }

    @Override
    public int getCount() {
        return hopeJobCareerLVItems.size();
    }

    @Override
    public Object getItem(int position) {
        return hopeJobCareerLVItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
