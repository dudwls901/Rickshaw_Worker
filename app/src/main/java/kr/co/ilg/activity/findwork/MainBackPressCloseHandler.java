package kr.co.ilg.activity.findwork;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.capstone.MyFirebaseInstanceservice;
import com.example.capstone.TokenRequest;
import com.google.firebase.iid.FirebaseInstanceId;

public class MainBackPressCloseHandler {
    private long backKeyPressedTime = 0;
    private Toast toast;

    private Activity activity;

    public MainBackPressCloseHandler(Activity context) {
        this.activity = context;
    }

    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity.finish();
            ActivityCompat.finishAffinity(activity);
            toast.cancel();

            if(Sharedpreference.get_id(activity,"worker_email","autologin") == null) {
                Response.Listener rListener = new Response.Listener<String>() {  // Generics를 String타입으로 한정
                    @Override
                    public void onResponse(String response) {
                        try {

                        } catch (Exception e) {
                            Log.d("mytest", e.toString());
                        }
                    }
                };
                String token = FirebaseInstanceId.getInstance().getToken();
                TokenRequest tokenRequest = new TokenRequest("0",token, rListener);
                RequestQueue queue = Volley.newRequestQueue(activity);
                queue.add(tokenRequest);
            }
        }

    }

    public void showGuide() {
        toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.",
                Toast.LENGTH_SHORT);
        toast.show();
    }

}
