package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.capstone.R;

import org.w3c.dom.Text;

import kr.co.ilg.activity.findwork.Sharedpreference;

public class AccountManageActivity extends Activity {

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountmanage);

        mContext = this;

        Button accountmodify = (Button) findViewById(R.id.accountmodify);
        TextView bankaccount = findViewById(R.id.bankaccount);
        TextView bankname = findViewById(R.id.bankname);
        TextView membernickname = findViewById(R.id.membernickname);

        membernickname.setText(Sharedpreference.get_Nickname(mContext, "worker_name"));
        bankaccount.setText(Sharedpreference.get_bankaccount(mContext,"worker_bankaccount"));
        bankname.setText(Sharedpreference.get_bankname(mContext,"worker_bankname"));

        accountmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AccountAddActivity.class);
                intent.putExtra("isUpdate", 1);
                startActivity(intent);
            }
        });
    }
}
