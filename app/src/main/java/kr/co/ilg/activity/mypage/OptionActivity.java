package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.capstone.R;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class OptionActivity extends Activity implements View.OnClickListener {
    Button servicepromise, changepassword, deletemb;
    Button[] buttons = {servicepromise, changepassword, deletemb};
    int[] buttonid = {R.id.servicepromise, R.id.changepassword, R.id.deletemb};
    Switch switch1, switch2, switch3;
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);
        mContext=this;
        switch1 = findViewById(R.id.switch1);
        switch2 = findViewById(R.id.switch2);
        switch3 = findViewById(R.id.switch3);

        boolean k =Sharedpreference.get_state(mContext,"switch1","state");
        switch1.setChecked(k);

        String worker_email = Sharedpreference.get_email(mContext,"worker_email","memberinfo");
        String worker_pw = Sharedpreference.get_Password(mContext,"worker_pw","memberinfo");

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Sharedpreference.set_state(mContext,"switch1",isChecked,"state");
                if(isChecked){
                        Sharedpreference.set_id(mContext, "worker_email", worker_email, "autologin");
                        Sharedpreference.set_pw(mContext, "worker_pw", worker_pw, "autologin");
                }
                else Sharedpreference.clear(mContext, "autologin");
            }
        });



        for(int i=0; i<3; i++){
            buttons[i] = (Button)findViewById(buttonid[i]);
            buttons[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.servicepromise : intent = new Intent(getApplicationContext(),ServiceInfoActivity.class); startActivity(intent);
                                        break;
            case R.id.changepassword : intent = new Intent(getApplicationContext(),PasswordChangeActivity.class); startActivity(intent);
                                        break;
            case R.id.deletemb : intent = new Intent(getApplicationContext(),MemberRemoveActivity.class); startActivity(intent);
                                        break;
        }
    }
}
