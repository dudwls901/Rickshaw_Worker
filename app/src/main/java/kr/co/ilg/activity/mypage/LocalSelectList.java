package kr.co.ilg.activity.mypage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.capstone.R;

import java.util.ArrayList;
import java.util.List;

public class LocalSelectList extends FrameLayout {

    private ListView cityLV, townLV;
    private List<String> cityData = new ArrayList<>();
    private List<String> townData = new ArrayList<>();

    private LocalSelectList.CommonAdapter cityAdapter, townAdapter;

    private String city, town;

    public void setOnResultSelectListener(LocalSelectList.OnResultSelectListener onResultSelectListener) {
        this.onResultSelectListener = onResultSelectListener;
    }

    private LocalSelectList.OnResultSelectListener onResultSelectListener;

    public LocalSelectList(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs);

        initView(context);
    }

    private void initView(Context context) {
        View layout = LayoutInflater.from(context).inflate(R.layout.local_select, this, true);
        cityLV = layout.findViewById(R.id.cityLv);
        townLV = layout.findViewById(R.id.townLv);

        cityAdapter = new LocalSelectList.CommonAdapter(context, cityData);
        townAdapter = new LocalSelectList.CommonAdapter(context, townData);

        cityLV.setAdapter(cityAdapter);
        townLV.setAdapter(townAdapter);

        cityLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = cityData.get(position);
                // 도시 목록 새로고침
                townAdapter.setSelectedPosition(-1);
                cityAdapter.setSelectedPosition(position);
                view.setBackgroundColor(getResources().getColor(R.color.mainColor));
                townData.clear();
                GetTownData(city);
                if (onResultSelectListener != null) {
                    onResultSelectListener.onResultSelected(false, city, null);
                }
                cityAdapter.notifyDataSetInvalidated();
            }
        });
        townLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                town = townData.get(position);
                townAdapter.setSelectedPosition(position);
                view.setBackgroundColor(getResources().getColor(R.color.mainColor));
                // 결과 처리
                if (onResultSelectListener != null) {
                    onResultSelectListener.onResultSelected(true, city, town);
                }
                townAdapter.notifyDataSetInvalidated();
            }
        });

        initCityData();
    }

    private void initCityData(){
        cityData.add("서울");
        cityData.add("경기");
        cityData.add("인천");
        cityData.add("강원");
        cityData.add("대전");
        cityData.add("세종");
        cityData.add("충남");
        cityData.add("충북");
        cityData.add("부산");
        cityData.add("울산");
        cityData.add("경남");
        cityData.add("경북");
        cityData.add("대구");
        cityData.add("광주");
        cityData.add("전남");
        cityData.add("전북");
        cityData.add("제주");
        cityAdapter.notifyDataSetChanged();
    }

    private void GetTownData(String city) {
        if ("서울".equals(city)) {
            townData.add("강남구");
            townData.add("강동구");
            townData.add("강북구");
            townData.add("강서구");
            townData.add("관악구");
            townData.add("광진구");
            townData.add("구로구");
            townData.add("금천구");
            townData.add("노원구");
            townData.add("도봉구");
            townData.add("동대문구");
            townData.add("동작구");
            townData.add("마포구");
            townData.add("서대문구");
            townData.add("서초구");
            townData.add("성동구");
            townData.add("성북구");
            townData.add("송파구");
            townData.add("양천구");
            townData.add("영등포구");
            townData.add("용산구");
            townData.add("은평구");
            townData.add("종로구");
            townData.add("중구");
            townData.add("중랑구");
        }
        else if ("경기".equals(city)) {
            townData.add("가평군");
        }
        else if ("인천".equals(city)) {
            townData.add("강화군");
        }
        else if ("강원".equals(city)) {
            townData.add("강릉시");
        }
        else if ("대전".equals(city)) {
            townData.add("대덕구");
        }
        else {
            townData.add("기타");
        }
        townAdapter.notifyDataSetChanged();
        townLV.setSelection(0);
    }
/*
    private void initCityData() {

        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    provienceData.add(object.getString("name"));
                }
                mProvienceAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void GetTownData(String city) {
        if (json != null) {
            try {
                JSONArray jsonArray = new JSONArray(json);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject provienceObject = jsonArray.getJSONObject(i);

                    if (provienceObject.getString("name").equals(provience)) {
                        //获取对应省信息
                        JSONArray cityListJson = provienceObject.getJSONArray("cityList");
                        for (int j = 0; j < cityListJson.length(); j++) {
                            JSONObject cityObject = cityListJson.getJSONObject(j);
                            cityData.add(cityObject.getString("name"));
                        }
                        break;
                    }

                }
                mCityAdapter.notifyDataSetChanged();
                mCityLv.setSelection(0);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public String getFromAssets(String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
*/


    public interface OnResultSelectListener {

        public void onResultSelected(boolean isFinish, String city, String town);

    }

    public class CommonAdapter extends BaseAdapter {
        private List<String> ListData;
        private Context context;
        private int selectedPosition = -1;

        public void setSelectedPosition(int selectedPosition) {
            this.selectedPosition = selectedPosition;
        }

        public CommonAdapter(Context context, List<String> ListData) {
            this.context = context;
            this.ListData = ListData;
        }

        @Override
        public int getCount() {
            return ListData.size();
        }

        @Override
        public Object getItem(int position) {
            return ListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LocalSelectList.CommonAdapter.Viewholder holder;
            if(convertView==null) {
                holder = new LocalSelectList.CommonAdapter.Viewholder();
                convertView = View.inflate(context, R.layout.local_select_item, null);

                holder.mTextView = (TextView)convertView.findViewById(R.id.item_name);

                convertView.setTag(holder);
            } else {
                holder = (LocalSelectList.CommonAdapter.Viewholder) convertView.getTag();
            }

            Log.v("测试", "selectedPosition:" + selectedPosition + " position=" + position);
            holder.mTextView.setText(ListData.get(position) + "");
            if (selectedPosition == position) {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.mainColor));
            } else {
                convertView.setBackgroundColor(context.getResources().getColor(R.color.WhiteColor));
            }
            return convertView;
        }

        public class Viewholder {
            public TextView mTextView;
        }

    }

}
