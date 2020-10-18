package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.capstone.R;

import org.w3c.dom.Text;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class MyInfomanageActivity extends Activity {

    private Context mContext;
    boolean modify = true;
    View dialogview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myinfomanage);

        mContext = this;


        TextView myname = findViewById(R.id.myname);
        TextView age = findViewById(R.id.age);
        TextView phonenum = findViewById(R.id.phonenum);
        TextView email = findViewById(R.id.email);
        TextView introduce = findViewById(R.id.introduce);

        LinearLayout modifyprofile = findViewById(R.id.modifyprofile);
        LinearLayout modifywanna = findViewById(R.id.modifywanna);

        modifyprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MyInfomanageActivity.this);
                dlg.setTitle("프로필 수정");
                dialogview = (View)View.inflate(MyInfomanageActivity.this,R.layout.updateprofile,null);
                dlg.setView(dialogview);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //서버DB UPDATE
                    }
                });
                dlg.setNegativeButton("취소",null);
                dlg.show();

            }
        });


        modifywanna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modify = false;
                Intent intent = new Intent(getApplicationContext(),kr.co.ilg.activity.mypage.LocalSelectActivity.class);
                intent.putExtra("update",modify);
                startActivity(intent); // 수정 필요할시 false 값과 함께 intent
                                        // 희망 직종까지 선택 후 원래 activity(MyinformanageActivity)로 돌아와야함.

            }
        });

        myname.setText(Sharedpreference.get_Nickname(mContext, "nickname"));
        age.setText(Sharedpreference.get_Birth(mContext, "worker_birth"));
        phonenum.setText(Sharedpreference.get_Phonenum(mContext, "worker_phonenum"));
        email.setText(Sharedpreference.get_email(mContext, "email"));
        introduce.setText(Sharedpreference.get_introduce(mContext,"worker_introduce"));

    }
}
