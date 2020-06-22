package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.capstone.R;

public class ServiceInfoActivity extends Activity implements View.OnClickListener {

    TextView text1;
    Button btn1, btn2, btn3,btn4;
    Button[] btns = {btn1, btn2, btn3,btn4};
    int[] btnsid = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.servicepromise);

        for(int i=0; i<4; i++){
            btns[i] = (Button)findViewById(btnsid[i]);
            btns[i].setOnClickListener(this);
        }

        text1 = (TextView)findViewById(R.id.text1);



    }

    @Override
    public void onClick(View v) {
        for (int i=0; i<4; i++){
            btns[i].setBackground(getDrawable(R.drawable.line));
        }
        switch (v.getId()){
            case R.id.btn1 : text1.setText("이용약관"); btns[0].setBackground(getDrawable(R.drawable.servicestroke)); break;
            case R.id.btn2 : text1.setText("전자금융거래 이용약관");btns[1].setBackground(getDrawable(R.drawable.servicestroke)); break;
            case R.id.btn3 : text1.setText("개인정보 제3자 제공동의"); btns[2].setBackground(getDrawable(R.drawable.servicestroke));break;
            case R.id.btn4 : text1.setText("위치기반 서비스 이용약관"); btns[3].setBackground(getDrawable(R.drawable.servicestroke));break;
        }
    }
}
