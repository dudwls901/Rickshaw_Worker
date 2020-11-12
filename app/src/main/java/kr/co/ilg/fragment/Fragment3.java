package kr.co.ilg.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstone.R;

import java.util.ArrayList;

import kr.co.ilg.activity.findwork.ListWorkGoneAdapter;
import kr.co.ilg.activity.findwork.ListWorkPickOutAdapter;
import kr.co.ilg.activity.findwork.ListViewItem;


public class Fragment3 extends Fragment {
    ViewGroup viewGroup;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment3, container, false);

        context = container.getContext();
        recyclerView = viewGroup.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        final ArrayList<ListViewItem> workInfoArrayList = new ArrayList<>();
        workInfoArrayList.add(new ListViewItem("빌라 건축", "2020-06-14", 150000, "전기", "서울 영등포구 여의나루로57", "개미인력소", "지급완료", true));
        workInfoArrayList.add(new ListViewItem("제일아파트 건축", "2020-06-17", 130000, "페인트", "서울 영등포구 당산로 219", "베짱이인력소", "지급예정", false));
        workInfoArrayList.add(new ListViewItem("자이아파트 신축", "2020-06-20", 160000, "건축", "서울 영등포구 양평로24길 9", "사람인력소", "지급예정", false));
        workInfoArrayList.add(new ListViewItem("신송센터빌딩 철거", "2020-06-23", 160000, "철거", "서울 영등포구 양평로24길 10", "돈돈인력소", "지급예정", false));
        workInfoArrayList.add(new ListViewItem("신송센터빌딩 철거", "2020-06-23", 160000, "철거", "서울 영등포구 양평로24길 10", "돈돈인력소", "지급예정", false));

        ListWorkGoneAdapter myworkAdapter = new ListWorkGoneAdapter(context.getApplicationContext(), workInfoArrayList);
        recyclerView.setAdapter(myworkAdapter);


        return viewGroup;
    }
}