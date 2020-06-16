package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.capstone.R;

public class OptionActivity extends Activity implements View.OnClickListener {
    Button servicepromise, changepassword, deletemb;
    Button[] buttons = {servicepromise, changepassword, deletemb};
    int[] buttonid = {R.id.servicepromise, R.id.changepassword, R.id.deletemb};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option);

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
