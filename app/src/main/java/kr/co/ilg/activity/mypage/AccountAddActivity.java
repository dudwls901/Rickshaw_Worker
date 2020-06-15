package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.capstone.R;

import java.util.ArrayList;

import kr.co.ilg.activity.login.LoginActivity;

public class AccountAddActivity extends Activity {

    Button addBtn;
    Spinner bankSelectSpn;
    ArrayList<String> bSList;
    ArrayAdapter<String> bSAdapter;
    TextView nextTimeTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_add);

        addBtn = findViewById(R.id.addBtn);
        nextTimeTV = findViewById(R.id.nextTimeTV);
        bankSelectSpn = findViewById(R.id.bankSelectSpn);

        bSList = new ArrayList<>();
        bSList.add("은행");
        bSList.add("KB 국민");
        bSList.add("신한");
        bSList.add("농협");
        bSList.add("하나");
        bSList.add("우리");
        bSList.add("IBK 기업");
        bSList.add("시티");
        bSList.add("KDB 산업");

        bSAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, bSList);
        bankSelectSpn.setAdapter(bSAdapter);
        bankSelectSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // bSList.get(position)
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountAddActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        nextTimeTV.setPaintFlags(nextTimeTV.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        nextTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AccountAddActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
