package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.capstone.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

public class WorkMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    MapFragment map;
    GoogleMap gMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_map);
        ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},MODE_PRIVATE);

        map=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);
    }

    public void onMapReady(GoogleMap googleMap){
        gMap=googleMap;
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.568256,126.897240),15));
        gMap.getUiSettings().setZoomControlsEnabled(true);

    }


}
