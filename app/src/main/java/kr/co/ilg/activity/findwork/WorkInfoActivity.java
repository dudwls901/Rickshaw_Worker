package kr.co.ilg.activity.findwork;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

public class WorkInfoActivity extends AppCompatActivity {
    TextView field_address,office_name;
    Button map_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.work_info);
        //ActionBar ad=getActionBar();
        //ad.setDisplayHomeAsUpEnabled(true);

        field_address=findViewById(R.id.place);
        office_name=findViewById(R.id.office_info);
        map_btn=findViewById(R.id.map_btn);
        field_address.setPaintFlags(field_address.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        office_name.setPaintFlags(field_address.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);

        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WorkInfoActivity.this,WorkMapActivity.class);
                startActivity(intent);
            }
        });

        field_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WorkInfoActivity.this,FieldInfoActivity.class);
                startActivity(intent);
            }
        });

        office_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WorkInfoActivity.this,OfficeInfoActivity.class);
                startActivity(intent);
            }
        });



    }
}
