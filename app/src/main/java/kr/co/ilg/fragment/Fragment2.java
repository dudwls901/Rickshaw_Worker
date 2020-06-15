package kr.co.ilg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import kr.co.ilg.activity.findwork.ListAdapter;
import kr.co.ilg.activity.findwork.ListViewItem;
import kr.co.ilg.fragment.Fragment22;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.capstone.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    ViewGroup viewGroup;
private Context context;
    ArrayList spinner1_array, spinner2_array;
    ArrayAdapter spinner1_Adapter, spinner2_Adapter;
    RecyclerView urgency_RecyclerView, usually_RecyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment2, container, false);

        TabLayout tabs = viewGroup.findViewById(R.id.tabs);

        context = container.getContext();
        PagerAdapter pagerAdapter = new PagerAdapter(context,getFragmentManager());
        FragmentPagerAdapter adapterViewpager;

        adapterViewpager = new PagerAdapter(context,getFragmentManager());
        ViewPager viewPager = viewGroup.findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);

     //  tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
       // viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));



        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
  //      if(tab.getPosition()==1)
//            Fragment22 fragment22 = (Fragment22)getSupportFragment
//        ArticleFragment articleFrag = (ArticleFragment)
//                getSupportFragmentManager().findFragmentById(R.id.article_fragment);
       // getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, fragment2).commitAllowingStateLoss();
        //    callFragment(Fragment22);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
});

        urgency_RecyclerView=viewGroup.findViewById(R.id.list_urgency);
        urgency_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(context);
        urgency_RecyclerView.setLayoutManager(layoutManager);

        ArrayList<ListViewItem> workInfoArrayList=new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("레미안 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));

        ListAdapter urgencyAdapter=new ListAdapter(workInfoArrayList);
        urgency_RecyclerView.setAdapter(urgencyAdapter);

        usually_RecyclerView=viewGroup.findViewById(R.id.list_usually);
        usually_RecyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(context);
        usually_RecyclerView.setLayoutManager(layoutManager);

        ArrayList<ListViewItem> workInfoArrayList2=new ArrayList<>();
        workInfoArrayList2.add(new ListViewItem("레미안아파트 건축","2020-06-14","150,000","상수 레미안 아파트","건축","개미인력소","1","3"));
     //   workInfoArrayList2.add(new ListViewItem("자이아파트 건축","2020-06-30","150,000","광흥창 자이 아파트","건축","코끼리인력소","2","4"));

        ListAdapter usuallyAdapter=new ListAdapter(workInfoArrayList2);
        usually_RecyclerView.setAdapter(usuallyAdapter);

        return viewGroup;
    }


}


class PagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_2, R.string.tab_text_22};
    private final Context mContext;

    public PagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
    switch (position)
    {
        case 0:
            return new Fragment2();
        case 1:
            return new Fragment22();
        default:
            return null;
    }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}