package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.example.capstone.R;

public class AccountManageActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanage);

        Button accountmodify = (Button) findViewById(R.id.accountmodify);

        accountmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AccountAddActivity.class); startActivity(intent);
            }
        });
    }
}
