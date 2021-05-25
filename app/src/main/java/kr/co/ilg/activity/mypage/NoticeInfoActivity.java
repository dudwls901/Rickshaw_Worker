package kr.co.ilg.activity.mypage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.capstone.R;

public class NoticeInfoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_1);

        TextView date = findViewById(R.id.date);
        TextView content = findViewById(R.id.content);
        TextView title = findViewById(R.id.title);

        Intent receiver = getIntent();
        String date1 = receiver.getExtras().getString("noticedate");
        String content1 = receiver.getExtras().getString("content");
        String title1 = receiver.getExtras().getString("noticetitle");
        String user1 = receiver.getExtras().getString("user");
        date.setText(date1);
        content.setText(content1);
        title.setText(title1);

    }
}
