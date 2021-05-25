package kr.co.ilg.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupPasswordActivity extends AppCompatActivity {

    Button nextBtn;
    EditText passwdET, checkPwET;
    private Context mContext;
    String worker_email;
    public static final String pattern1 = "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[$@$!%*#?&])[A-Za-z[0-9]$@$!%*#?&]{8,20}$"; // 영문, 숫자, 특수문자
    Matcher match;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_password);

        Intent receiver = getIntent();
        worker_email = receiver.getExtras().getString("worker_email");

        mContext = getApplicationContext();
        getHashKey(mContext);

        nextBtn = findViewById(R.id.nextBtn);
        passwdET = findViewById(R.id.passwdET);
        checkPwET = findViewById(R.id.checkPwET);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (((checkPwET.getText().toString()).trim()).equals("") || ((passwdET.getText().toString()).trim()).equals("")) {
                    Toast.makeText(SignupPasswordActivity.this, "값을 모두 입력해주세요.", Toast.LENGTH_SHORT).show();
                } else {
                    if (passwdET.getText().toString().equals(checkPwET.getText().toString())) {
                        match = Pattern.compile(pattern1).matcher(passwdET.getText().toString());
                        if (match.find()) {  // 조건 만족
                            Toast.makeText(SignupPasswordActivity.this, "설정 완료", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupPasswordActivity.this, SignupUserInfoActivity.class);
                            intent.putExtra("worker_email", worker_email);
                            intent.putExtra("worker_pw", passwdET.getText().toString());
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupPasswordActivity.this, "비밀번호가 조건에 맞지 않습니다", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupPasswordActivity.this, "비밀번호가 다릅니다", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    @Nullable

    public static String getHashKey(Context context) {

        final String TAG = "KeyHash";

        String keyHash = null;

        try {

            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                keyHash = new String(Base64.encode(md.digest(), 0));
                Log.d(TAG, keyHash);
            }
        } catch (Exception e) {
            Log.e("name not found", e.toString());
        }

        if (keyHash != null) {
            return keyHash;
        } else {
            return null;
        }
    }
}
