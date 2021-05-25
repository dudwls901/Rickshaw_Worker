package kr.co.ilg.activity.findwork;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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


    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
        if (poiItem.getTag() == 0) {
            ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.supervisor);
        } else
        {
            ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.building);

        }
        ((TextView) mCalloutBalloon.findViewById(R.id.balloon_item_title)).setText(poiItem.getItemName().substring(0,poiItem.getItemName().indexOf("*")));
        ((TextView) mCalloutBalloon.findViewById(R.id.balloon_item_address)).setText(poiItem.getItemName().substring(poiItem.getItemName().indexOf("*")+1));
//        ((ImageView) mCalloutBalloon.findViewById(R.id.balloon_close)).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCalloutBalloon.setVisibility(View.GONE);
//            }
//        });
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return mCalloutBalloon;
    }
}

