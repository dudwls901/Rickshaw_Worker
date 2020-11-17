package kr.co.ilg.activity.findwork;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstone.R;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
    private final View mCalloutBalloon;
        private Context context;

    public CustomCalloutBalloonAdapter(Context context) {
        this.context = context;
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mCalloutBalloon = inflater.inflate(R.layout.custom_callout_balloon, null);
//        mCalloutBalloon =LayoutInflater.from()
//                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.work_list_custom,parent,false);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
        ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.building_mint2);
        ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
        ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText("Custom CalloutBalloon");
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return null;
    }
}

