package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.capstone.R;

import kr.co.ilg.activity.login.LoginActivity;

public class CertificateConfirmActivity extends Activity {

    TextView nextTimeTV;
    Button uploadBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certificate_confirm);

        uploadBtn = findViewById(R.id.uploadBtn);
        nextTimeTV = findViewById(R.id.nextTimeTV);

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CertificateConfirmActivity.this, LocalSelectActivity.class);
                startActivity(intent);
            }
        });

        nextTimeTV.setPaintFlags(nextTimeTV.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        nextTimeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CertificateConfirmActivity.this, LocalSelectActivity.class);
                startActivity(intent);
            }
        });
    }
}
