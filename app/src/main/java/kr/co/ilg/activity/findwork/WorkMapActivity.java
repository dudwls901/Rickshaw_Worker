package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

//import com.example.capstone.Manifest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapReverseGeoCoder;
import net.daum.mf.map.api.MapView;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkMapActivity extends AppCompatActivity implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener, MapView.POIItemEventListener {
    ImageButton back;
    private static final String LOG_TAG = "WorkMapActivity";
    MapView mapView;
    ViewGroup mapViewContainer;
    LinearLayout checkbox_layout;
    CheckBox field_checkbox, office_checkbox;
    //private net.daum.mf.map.api.MapView mMapView;

    String[] manager_office_address, field_address, field_name, manager_office_name;
    int[] field_code;
    // String school = "태평로1가35";
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    //String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION};
    String[] REQUIRED_PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION}; //android
    Boolean fieldCheck, managerCheck;
    String firstScreen =null;
    private GpsTracker gpsTracker;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//지도 생성
        Intent receiver = getIntent();
         firstScreen = receiver.getExtras().getString("mapAddress");
        mapView = new MapView(this);
        setContentView(R.layout.work_map);
        back = findViewById(R.id.back);
        field_checkbox = findViewById(R.id.field_checkbox);
        office_checkbox = findViewById(R.id.office_checkbox);
        checkbox_layout = findViewById(R.id.checkbox_layout);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Log.d("bbbbbb", "back");

            }
        });
        field_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("bbbbb", "field");
            }
        });
        gpsTracker = new GpsTracker(WorkMapActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

     //    firstScreen = getCurrentAddress(latitude, longitude);




        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
//        mapViewContainer.addView(checkbox_layout);
//        mapViewContainer.setPadding(0,200,0,0);
        mapView.setCurrentLocationEventListener(this);
          mapView.setPOIItemEventListener(this);
        mapView.setMapViewEventListener(this);

//        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter(WorkMapActivity.this));
   //            mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(100.53737528, 127.00557633), true);
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(MapView.CurrentLocationTrackingMode.),true);
//mapView.setCurrentLocationMarker();
        //setMapCenterPoint(MapPoint,true) 맵뷰 위치 설정해줌
        Log.d("ffffffffff",firstScreen);
       if(firstScreen.equals("0"))
       {
           if (!checkLocationServicesStatus()) {

               showDialogForLocationServiceSetting();
           } else {
               mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
               checkRunTimePermission();
           }
//            mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
       }
       // mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

       else {
           Geocoder geocoder = new Geocoder(this);

           List<Address> list = null;
           try {
               list = geocoder.getFromLocationName(
                       firstScreen, // 지역 이름
                       10); // 읽을 개수
           } catch (IOException e) {
               e.printStackTrace();
               Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
               Log.d("test", e.toString());
           }

           if (list != null) {
               if (list.size() == 0) {
                   Log.d("test", "해당되는 주소 정보는 없습니다");
               } else {

                   mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(list.get(0).getLatitude(), list.get(0).getLongitude()),1 ,true);
               }
           }
       }
//        mapView.setShowCurrentLocationMarker(true);
        Log.d("ccccc", mapView.getCurrentLocationTrackingMode().toString());


        Response.Listener responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    //              JSONObject jsonResponse = new JSONObject(response);
                    //         JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                    //  Log.d("mytesstt", response);

                    Log.d("mytesstt", response);
                    Log.d("mytesttt", String.valueOf(response.length()));
                    int index_search_start;
                    int index_search_end;
                    JSONArray jsonArray_Manager = new JSONArray(response.substring(response.indexOf("["), response.indexOf("]") + 1));
                    index_search_start = response.indexOf("[") + 1;
                    index_search_end = response.indexOf("]") + 1;
                    JSONArray jsonArray_Field = new JSONArray(response.substring(response.indexOf("[", index_search_start), response.indexOf("]", index_search_end) + 1));
                    Log.d("address?????", jsonArray_Field.toString());

                    manager_office_address = new String[jsonArray_Manager.length()];
                    manager_office_name = new String[jsonArray_Manager.length()];
                    field_address = new String[jsonArray_Field.length()];
                    field_name = new String[jsonArray_Field.length()];
                    field_code = new int[jsonArray_Field.length()];
                    for (int i = 0; i < jsonArray_Manager.length(); i++) {
                        manager_office_address[i] = jsonArray_Manager.getJSONObject(i).getString("manager_office_address");
                        manager_office_name[i] = jsonArray_Manager.getJSONObject(i).getString("manager_office_name");
                    }
                    for (int i = 0; i < jsonArray_Field.length(); i++) {
                        field_address[i] = jsonArray_Field.getJSONObject(i).getString("field_address");
                        field_name[i] = jsonArray_Field.getJSONObject(i).getString("field_name");
                        field_code[i] = jsonArray_Field.getJSONObject(i).getInt("field_code");
                    }

                    final MapPOIItem[] marker = new MapPOIItem[field_address.length];
                    for (int i = 0; i < field_address.length; i++)
                        marker[i] = new MapPOIItem();
                    MapPOIItem[] marker1 = new MapPOIItem[manager_office_address.length];
                    for (int i = 0; i < manager_office_address.length; i++)
                        marker1[i] = new MapPOIItem();

                    //초기값 설정
                    field_checkbox.setChecked(true);
                    office_checkbox.setChecked(true);
                    fieldCheck = true;
                    managerCheck = true;
                    markerChange(marker, marker1,field_address,manager_office_address, fieldCheck, managerCheck);


                    field_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            fieldCheck = isChecked;
                            markerChange(marker, marker1,field_address,manager_office_address, fieldCheck, managerCheck);
                        }
                    });


                    office_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            managerCheck = isChecked;
                            markerChange(marker, marker1,field_address,manager_office_address, fieldCheck, managerCheck);

                        }
                    });


                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("mytest4", e.toString());
                }
            }
        };

        SelectAddress selectAddress = new SelectAddress(responseListener);
        RequestQueue queue1 = Volley.newRequestQueue(WorkMapActivity.this);
        queue1.add(selectAddress);


    }

    public void markerChange(MapPOIItem[] marker, MapPOIItem[] marker1,String[] field_address, String[] manager_office_address, boolean fieldCheck, boolean managerCheck) {

        final Geocoder geocoder = new Geocoder(this);

        if (fieldCheck) {
            if(mapView.findPOIItemByName(field_name[0])==null) {
                for (int i = 0; i < field_address.length; i++) {
                    List<Address> list_field = null;

                    try {
                        list_field = geocoder.getFromLocationName(
                                field_address[i], // 지역 이름
                                10); // 읽을 개수
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                        Log.d("test", e.toString());
                    }

                    if (list_field != null) {
                        if (list_field.size() == 0) {
                            Log.d("test", "해당되는 주소 정보는 없습니다");
                        } else {
                            Log.d("testschool", list_field.get(0).toString());
                            Log.d("testschool", list_field.get(0).getCountryName());
                            Log.d("testschool", String.valueOf(list_field.get(0).getLatitude()));
                            Log.d("testschool", String.valueOf(list_field.get(0).getLongitude()));

                            //          list.get(0).getCountryName();  // 국가명
                            //          list.get(0).getLatitude();        // 위도
                            //          list.get(0).getLongitude();    // 경도

                            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(list_field.get(0).getLatitude(), list_field.get(0).getLongitude());
                            //  MapPOIItem mapPOIItem = new MapPOIItem();
                            //  MapPOIItem[] marker = new MapPOIItem[field_address.length];

                            marker[i].setItemName(field_name[i]);
                            marker[i].setTag(field_code[i]);
                            marker[i].setMapPoint(mapPoint);
                            marker[i].setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
                            marker[i].setCustomImageResourceId(R.drawable.building_mint1);
                            marker[i].setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                            marker[i].setCustomSelectedImageResourceId(R.drawable.building_mint2);
                            //onMapViewInitialized(mapView);
//                            marker[i].setCustomCalloutBalloon(mapView);
                            mapView.addPOIItem(marker[i]);



                            Log.d("bbbbbcreate", marker[i].getItemName() + marker[i].getTag());
                        }
                    }


                }
            }

        } else {
//            for (int i = 0; i < marker.length; i++)
//                Log.d("bbbbmarker", marker[i].getItemName() + marker[i].getTag());

                mapView.removePOIItems(marker);

        }

        if (managerCheck) {
            if(mapView.findPOIItemByName(manager_office_name[0])==null) {
                for (int i = 0; i < manager_office_address.length; i++) {

                    List<Address> list_manager = null;
                    try {
                        list_manager = geocoder.getFromLocationName(
                                manager_office_address[i], // 지역 이름
                                10); // 읽을 개수
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("test", "입출력 오류 - 서버에서 주소변환시 에러발생");
                        Log.d("test", e.toString());
                    }

                    if (list_manager != null) {
                        if (list_manager.size() == 0) {
                            Log.d("test", "해당되는 주소 정보는 없습니다");
                        } else {
                            Log.d("testschool", list_manager.get(0).toString());
                            Log.d("testschool", list_manager.get(0).getCountryName());
                            Log.d("testschool", String.valueOf(list_manager.get(0).getLatitude()));
                            Log.d("testschool", String.valueOf(list_manager.get(0).getLongitude()));

                            //          list.get(0).getCountryName();  // 국가명
                            //          list.get(0).getLatitude();        // 위도
                            //          list.get(0).getLongitude();    // 경도

                            MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(list_manager.get(0).getLatitude(), list_manager.get(0).getLongitude());


                            marker1[i].setItemName(manager_office_name[i]);
                            marker1[i].setTag(0);
                            marker1[i].setMapPoint(mapPoint);
                            marker1[i].setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
                            marker1[i].setCustomImageResourceId(R.drawable.supervisor2);
                            marker1[i].setSelectedMarkerType(MapPOIItem.MarkerType.CustomImage); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                            marker1[i].setCustomSelectedImageResourceId(R.drawable.supervisor1);
               //             marker1[i].setCustomCalloutBalloon(mapView);
                            mapView.addPOIItem(marker1[i]);
                            Log.d("bbbbbcreate", marker1[i].getItemName() + marker1[i].getTag());
                        }
                    }


                }
            }
        } else {
            for (int i = 0; i < marker1.length; i++)
                Log.d("bbbbmarker", marker1[i].getItemName() + marker1[i].getTag());

                mapView.removePOIItems(marker1);

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapViewContainer.removeAllViews();
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint currentLocation, float accuracyInMeters) {
        MapPoint.GeoCoordinate mapPointGeo = currentLocation.getMapPointGeoCoord();
        Log.i(LOG_TAG, String.format("MapView onCurrentLocationUpdate (%f,%f) accuracy (%f)", mapPointGeo.latitude, mapPointGeo.longitude, accuracyInMeters));
    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {
    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {
    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {
    }


    private void onFinishReverseGeoCoding(String result) {
//        Toast.makeText(LocationDemoActivity.this, "Reverse Geo-coding : " + result, Toast.LENGTH_SHORT).show();
    }

    // ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면
            boolean check_result = true;

            // 모든 퍼미션을 허용했는지 체크합니다.
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {
                Log.d("@@@", "start");
                //위치 값을 가져올 수 있음

            } else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있다
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                    Toast.makeText(WorkMapActivity.this, "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(WorkMapActivity.this, "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(WorkMapActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED) {
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)
            // 3.  위치 값을 가져올 수 있음

        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.
            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(WorkMapActivity.this, REQUIRED_PERMISSIONS[0])) {
                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(WorkMapActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(WorkMapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(WorkMapActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(WorkMapActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {
                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {


    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {
   //     Log.d("ccccc",mapPOIItem.getItemName());
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

            //매니저인포액티비티
            if(mapPOIItem.getTag()==0) {
                Log.d("cccccccccc", mapPOIItem.getItemName());

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            Log.d("mytesstt", response);

                            JSONArray jsonArray_Manager = new JSONArray(response.substring(response.indexOf("["),response.indexOf("]")+1));


                            String[] business_reg_num = new String[jsonArray_Manager.length()];

                            for(int i =0; i<jsonArray_Manager.length();i++) {
                                business_reg_num[i] = jsonArray_Manager.getJSONObject(i).getString("business_reg_num");
                            }


                            Intent intent = new Intent(WorkMapActivity.this, OfficeInfoActivity.class);
                            intent.putExtra("business_reg_num", business_reg_num[0]);
                            startActivity(intent);
                            mapView.refreshMapTiles();
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest4", e.toString());

                        }
                    }
                };

                MapForOfficeRequest mapForOffice = new MapForOfficeRequest(mapPOIItem.getItemName(), responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(WorkMapActivity.this);
                queue1.add(mapForOffice);
            }
            //현장인포액티비팇
            else
            {
                Log.d("ccccctag",""+mapPOIItem.getTag());

                Response.Listener responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            Log.d("mytesstt", response);
                            int index_search_start;
                            int index_search_end;
                            JSONArray jsonArray_Field = new JSONArray(response.substring(response.indexOf("["),response.indexOf("]")+1));
                            index_search_start = response.indexOf("[")+1;
                            index_search_end = response.indexOf("]")+1;
                            JSONArray jsonArray_jp = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));
                            index_search_start = response.indexOf("[",index_search_start)+1;
                            index_search_end = response.indexOf("]",index_search_end)+1;
                            JSONArray jsonArray_job = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));
                            index_search_start = response.indexOf("[",index_search_start)+1;
                            index_search_end = response.indexOf("]",index_search_end)+1;
                            JSONArray jsonArray_Manager = new JSONArray(response.substring(response.indexOf("[",index_search_start),response.indexOf("]",index_search_end)+1));

                            String[]  jp_num = new String[jsonArray_Field.length()];
                            String[] field_name_MY = new String[jsonArray_Field.length()];
                            String[] field_address_MY = new String[jsonArray_Field.length()];
                            String[] jp_title = new String[jsonArray_jp.length()];
                            String[] jp_job_date = new String[jsonArray_jp.length()];
                            String[] jp_job_cost = new String[jsonArray_jp.length()];
                            String[] job_name = new String[jsonArray_job.length()];
                            String[] manager_office_name_MY = new String[jsonArray_Manager.length()];
                            String[] jp_job_tot_people = new String[jsonArray_jp.length()];
                            String[] jp_job_start_time = new String[jsonArray_jp.length()];
                            String[] jp_job_finish_time = new String[jsonArray_jp.length()];
                            String[] jp_contents = new String[jsonArray_jp.length()];
                            String[] business_reg_num = new String[jsonArray_Manager.length()];
                            Log.d("mytessss",jsonArray_job.toString());
                            for(int i =0; i<jsonArray_jp.length();i++) {
                                jp_num[i] = jsonArray_jp.getJSONObject(i).getString("jp_num");
                                jp_title[i] = jsonArray_jp.getJSONObject(i).getString("jp_title");
                                jp_job_date[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_date");
                                jp_job_cost[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_cost");
                                jp_job_tot_people[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_tot_people");
                                jp_job_start_time[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_start_time");
                                jp_job_finish_time[i] = jsonArray_jp.getJSONObject(i).getString("jp_job_finish_time");
                                jp_contents[i] = jsonArray_jp.getJSONObject(i).getString("jp_contents");

                            }
                            for(int i =0; i<jsonArray_job.length();i++) {
                            job_name[i] = jsonArray_job.getJSONObject(i).getString("job_name");
                            }
                            for(int i =0; i<jsonArray_Manager.length();i++) {
                                manager_office_name_MY[i] = jsonArray_Manager.getJSONObject(i).getString("manager_office_name");
                                business_reg_num[i] = jsonArray_Manager.getJSONObject(i).getString("business_reg_num");
                            }
                            for(int i =0; i<jsonArray_Field.length();i++) {
                                field_name_MY[i] = jsonArray_Field.getJSONObject(i).getString("field_name");
                                field_address_MY[i] = jsonArray_Field.getJSONObject(i).getString("field_address");

                            }

                            Intent intent = new Intent(WorkMapActivity.this, FieldInfoActivity.class);
                            intent.putExtra("jp_num", jp_num[0]);
                            intent.putExtra("field_name", field_name_MY[0]);
                            intent.putExtra("field_address", field_address_MY[0]);
                            intent.putExtra("jp_title", jp_title[0]);
                            intent.putExtra("jp_job_date", jp_job_date[0]);
                            intent.putExtra("jp_job_cost", jp_job_cost[0]);
                            intent.putExtra("job_name", job_name[0]);
                            intent.putExtra("manager_office_name", manager_office_name_MY[0]);
                            intent.putExtra("jp_job_tot_people", jp_job_tot_people[0]);
                            intent.putExtra("jp_job_start_time",jp_job_start_time[0]);
                            intent.putExtra("jp_job_finish_time",jp_job_finish_time[0]);
                            intent.putExtra("jp_contents",jp_contents[0]);
                            intent.putExtra("business_reg_num",business_reg_num[0]);

                            startActivity(intent);
                            finish();
                            mapView.refreshMapTiles();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d("mytest4", e.toString());

                        }
                    }
                };

                MapForFieldRequest mapForFieldRequest = new MapForFieldRequest(String.valueOf(mapPOIItem.getTag()), responseListener);
                RequestQueue queue1 = Volley.newRequestQueue(WorkMapActivity.this);
                queue1.add(mapForFieldRequest);


            }

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
   //     Log.d("cccccccccccccc",mapPOIItem.getItemName());
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }

}