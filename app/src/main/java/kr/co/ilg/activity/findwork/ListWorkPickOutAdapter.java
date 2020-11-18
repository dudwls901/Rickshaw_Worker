package kr.co.ilg.activity.findwork;

//import android.app.AlertDialog;

import androidx.appcompat.app.AlertDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.R;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.co.ilg.activity.login.FindPasswordInfoActivity;
import kr.co.ilg.activity.login.FindPwRequest;

public class ListWorkPickOutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    LinearLayout gotoworkCheck, gotohomeCheck;
    TextView checkStart, checkFinish;
    TextView startTime, finishTime, work_date;
    Intent intent;
    View dialogView;
    long startDiff = 1111111111, finishDiff = 1111111111;
    long startcheckterm1, startcheckterm2, finishCheckterm1, finishCheckterm2;
    String key, mf_is_choolgeun, mf_is_toigeun;
    private GpsTracker gpsTracker;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, date, pay, job, place, office, current_people, total_people, paid;
        LinearLayout linear1;

        MyViewHolder(View view) {
            super(view);

            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            pay = view.findViewById(R.id.pay);
            job = view.findViewById(R.id.job);
            place = view.findViewById(R.id.place);
            office = view.findViewById(R.id.office);
            current_people = view.findViewById(R.id.current_people);
            total_people = view.findViewById(R.id.total_people);
            linear1 = view.findViewById(R.id.linear1);

        }
    }

    private Context context;
    private ArrayList<ListViewItem> workInfo;

    public ListWorkPickOutAdapter(Context context, ArrayList<ListViewItem> workInfo) {
        this.context = context;
        this.workInfo = workInfo;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mywork_pickoutlist_custom, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        final MyViewHolder myViewHolder = (MyViewHolder) holder;
        if (workInfo.get(position).urgency == false) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(String.valueOf(workInfo.get(position).pay));
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);

        } else if (workInfo.get(position).urgency == true) {
            myViewHolder.title.setText(workInfo.get(position).title);
            myViewHolder.date.setText(workInfo.get(position).date);
            myViewHolder.pay.setText(String.valueOf(workInfo.get(position).pay));
            myViewHolder.job.setText(workInfo.get(position).job);
            myViewHolder.place.setText(workInfo.get(position).place);
            myViewHolder.office.setText(workInfo.get(position).office);
            myViewHolder.linear1.setBackgroundColor(context.getResources().getColor(R.color.UrgencyColor));
        }

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long mNow = System.currentTimeMillis();
                Date mReDate = new Date(mNow);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String sDate = dateFormat.format(mReDate);
                String sTime = timeFormat.format(mReDate);
                String startcheck1 = "", startcheck2 = "", finishcheck1 = "", finishcheck2 = "";

                try {
                    Date nowTime = timeFormat.parse(sTime);
                    Date startTime = timeFormat.parse(workInfo.get(position).jp_job_start_time);
                    Date finishTime = timeFormat.parse(workInfo.get(position).jp_job_finish_time);
                    startDiff = (nowTime.getTime() - startTime.getTime()) / 1000;
                    finishDiff = (nowTime.getTime() - finishTime.getTime()) / 1000;

                    startcheckterm1 = startTime.getTime() - 1800000;
                    startcheckterm2 = startTime.getTime() + 1800000;
                    finishCheckterm1 = finishTime.getTime() - 1800000;
                    finishCheckterm2 = finishTime.getTime() + 1800000;

                    Date start1 = new Date(startcheckterm1);
                    Date start2 = new Date(startcheckterm2);
                    Date finish1 = new Date(finishCheckterm1);
                    Date finish2 = new Date(finishCheckterm2);
                    startcheck1 = timeFormat.format(start1);
                    startcheck2 = timeFormat.format(start2);
                    finishcheck1 = timeFormat.format(finish1);
                    finishcheck2 = timeFormat.format(finish2);
                } catch (java.text.ParseException pe) {
                    Log.d("mytest", pe.toString());
                }

                String[] splitNowDate = sDate.split("-");
                String[] splitNowTime = sTime.split(":");

                Toast.makeText(context, "현재시각 : " + splitNowDate[0] + "년 " + splitNowDate[1] + "월 " + splitNowDate[2] + "일 "
                        + splitNowTime[0] + "시 " + splitNowTime[1] + "분 " + splitNowTime[2] + "초", Toast.LENGTH_SHORT).show();
                context = view.getContext();
                final AlertDialog.Builder dlg = new AlertDialog.Builder(context);
                dialogView = View.inflate(context, R.layout.work_check, null);
                dlg.setView(dialogView);
                gotoworkCheck = dialogView.findViewById(R.id.gotoworkCheck);
                gotohomeCheck = dialogView.findViewById(R.id.gotohomeCheck);
                work_date = dialogView.findViewById(R.id.work_date);
                checkStart = dialogView.findViewById(R.id.checkStart);
                checkFinish = dialogView.findViewById(R.id.checkFinish);
                startTime = dialogView.findViewById(R.id.startTime);
                finishTime = dialogView.findViewById(R.id.finishTime);

                work_date.setText("출퇴근 일자 : " + workInfo.get(position).date);
                startTime.setText("출근 시간\n" + workInfo.get(position).jp_job_start_time);
                finishTime.setText("퇴근 시간\n" + workInfo.get(position).jp_job_finish_time);
                checkStart.setText(startcheck1 + " ~ " + startcheck2);
                checkFinish.setText(finishcheck1 + " ~ " + finishcheck2);

                dlg.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                Response.Listener rListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                            boolean select_CT = jResponse.getBoolean("select_CT");
                            if (select_CT) {
                                mf_is_choolgeun = jResponse.getString("mf_is_choolgeun");
                                mf_is_toigeun = jResponse.getString("mf_is_toigeun");

                                if (mf_is_choolgeun.equals("1"))
                                    gotoworkCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                                else ;
                                if (mf_is_toigeun.equals("1"))
                                    gotohomeCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                                else ;

                                Log.d("======출퇴근=======", mf_is_choolgeun + mf_is_toigeun);
                            } else {
                                Toast.makeText(context, "출퇴근 여부 로드 실패", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                TimeCheckRequest tcRequest = new TimeCheckRequest("LoadChoolToi", Sharedpreference.get_email(context, "worker_email","memberinfo"), workInfo.get(position).jp_num, rListener);

                RequestQueue queue = Volley.newRequestQueue(context);
                queue.add(tcRequest);
                final Geocoder geocoder = new Geocoder(context);
                Log.d("pppppppppppp",workInfo.get(position).place);
                gpsTracker = new GpsTracker(context);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();
                Log.d("pppppgpss",latitude+"위도/경도"+longitude);
                //현재위치 gps

                List<Address> list = null;
                try {
                    list = geocoder.getFromLocationName(
                            workInfo.get(position).place, // 지역 이름
                            10); // 읽을 개수
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e("test","입출력 오류 - 서버에서 주소변환시 에러발생");
                }

                if (list != null) {
                    if (list.size() == 0) {
                        Log.d("test","해당되는 주소 정보는 없습니다");
                    } else {
                        Log.d("test",list.get(0).toString());
                        //          list.get(0).getLatitude();        // 위도
                        //          list.get(0).getLongitude();    // 경도
                    }
                }
Log.d("ppppfieldgps",list.get(0).getLatitude()+"필드위도/필드경도"+list.get(0).getLongitude());
                double distanceMeter =
                        distance(latitude, longitude, list.get(0).getLatitude(), list.get(0).getLongitude(), "meter");
Log.d("pppppdistance",String.valueOf(distanceMeter));



                gotoworkCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimeCheck();
                        //if(gotoworkCheck.getBackgroundColor)
                        if (mf_is_choolgeun.equals("0")) {  // 출근 인증 안 돼있을 때
                            if (sDate.equals(workInfo.get(position).date)) {  // 날짜 비교
                                if(distanceMeter<100.0) {
                                    if (startDiff > -1800 && startDiff < 1800) {  // 30분 전후 이내인가
                                        key = "checkStart";
                                        Response.Listener rListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {

                                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                                    boolean checkStartSuccess = jResponse.getBoolean("checkStartSuccess");
                                                    if (checkStartSuccess) {
                                                        gotoworkCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                                                        Toast.makeText(context, "출근 인증 완료", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, "출근 인증 실패 : DB Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (Exception e) {
                                                    Log.d("mytest", e.toString());
                                                }
                                            }
                                        };
                                        TimeCheckRequest tcRequest = new TimeCheckRequest(key, Sharedpreference.get_email(context, "worker_email","memberinfo"), workInfo.get(position).jp_num, rListener);

                                        RequestQueue queue = Volley.newRequestQueue(context);
                                        queue.add(tcRequest);
                                    } else if (startDiff == 1111111111) {  // 시간 비교 위한 클래스 오류
                                        Toast.makeText(context, "서비스 오류 : 현장 관리자에게 문의 바람", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "출근인증 시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(context,"현장주변 100m이내에서 인증해주세요.",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "출근 날짜가 아닙니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "출근 인증이 이미 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                gotohomeCheck.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TimeCheck();
                        if (mf_is_toigeun.equals("0")) {  // 퇴근 인증 안 돼있을 때
                            if (sDate.equals(workInfo.get(position).date)) {  // 날짜 비교
                                if(distanceMeter<100.0) {
                                    if (finishDiff > -1800 && finishDiff < 1800) {  // 30분 전후 이내인가
                                        key = "checkFinish";
                                        Response.Listener rListener = new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                try {
                                                    JSONObject jResponse = new JSONObject(response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1));
                                                    boolean checkFinishSuccess = jResponse.getBoolean("checkFinishSuccess");
                                                    if (checkFinishSuccess) {
                                                        gotohomeCheck.setBackgroundColor(context.getResources().getColor(R.color.checkColor));
                                                        Toast.makeText(context, "퇴근 인증 완료", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(context, "퇴근 인증 실패 : DB Error", Toast.LENGTH_SHORT).show();
                                                    }
                                                } catch (Exception e) {
                                                    Log.d("mytest", e.toString());
                                                }
                                            }
                                        };
                                        TimeCheckRequest tcRequest = new TimeCheckRequest(key, Sharedpreference.get_email(context, "worker_email","memberinfo"), workInfo.get(position).jp_num, rListener);

                                        RequestQueue queue = Volley.newRequestQueue(context);
                                        queue.add(tcRequest);
                                    } else if (finishDiff == 1111111111) {  // 시간 비교 위한 클래스 오류
                                        Toast.makeText(context, "서비스 오류 : 현장 관리자에게 문의 바람", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(context, "퇴근 인증 시간이 아닙니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }else
                                {
                                    Toast.makeText(context,"현장주변 100m이내에서 인증해주세요.",Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(context, "퇴근 날짜가 아닙니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "퇴근 인증이 이미 완료되었습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.show();
            }

            public void TimeCheck() {
                long mNow = System.currentTimeMillis();
                Date mReDate = new Date(mNow);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
                String sTime = timeFormat.format(mReDate);

                try {
                    Date nowTime = timeFormat.parse(sTime);
                    Date startTime = timeFormat.parse(workInfo.get(position).jp_job_start_time);
                    Date finishTime = timeFormat.parse(workInfo.get(position).jp_job_finish_time);
                    startDiff = (nowTime.getTime() - startTime.getTime()) / 1000;
                    finishDiff = (nowTime.getTime() - finishTime.getTime()) / 1000;

                    Log.d("=====현재시간 ", String.valueOf(nowTime.getTime()));
                    Log.d("=====출근시간 ", String.valueOf(startTime.getTime()));
                    Log.d("=====퇴근시간 ", String.valueOf(finishTime.getTime()));


                } catch (java.text.ParseException pe) {
                    Log.d("mytest", pe.toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return workInfo.size();
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }


}
