package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.capstone.R;

import org.w3c.dom.Text;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class MyInfomanageActivity extends Activity {

    private Context mContext;

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

        myname.setText(Sharedpreference.get_Nickname(mContext, "nickname"));
        age.setText(Sharedpreference.get_Birth(mContext, "worker_birth"));
        phonenum.setText(Sharedpreference.get_Phonenum(mContext, "worker_phonenum"));
        email.setText(Sharedpreference.get_email(mContext, "email"));
        introduce.setText(Sharedpreference.get_introduce(mContext,"worker_introduce"));

    }
}
