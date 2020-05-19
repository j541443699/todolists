package bupt.mxly8.todolists;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Map;


public class AddActivity extends AppCompatActivity implements View.OnClickListener, AddTimeDialog.Message {
    private Button back;  //返回按钮
    private Button finish;  //完成按钮

    private EditText title;   //标题
    private EditText context;   //内容

    private EditText doneType; //任务进行情况
    private LinearLayout ddl; //截止日期
    private TextView year, month, day, time;//年月日时

    private String get_title;
    private String get_context;
    private String get_year;
    private String get_month;
    private String get_day;
    private String get_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        back = (Button) findViewById(R.id.back_add);
        finish = (Button) findViewById(R.id.finish);
        title = (EditText) findViewById(R.id.title_add);
        context = (EditText) findViewById(R.id.context_add);
        finish.setOnClickListener(this);
        back.setOnClickListener(this);

        ddl = (LinearLayout) findViewById(R.id.ddl);
        year = (TextView) findViewById(R.id.ddl_year);
        month = (TextView) findViewById(R.id.ddl_month);
        day = (TextView) findViewById(R.id.ddl_day);
        time = (TextView) findViewById(R.id.ddl_time);
        ddl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.finish:
                NoteOperator noteOperator = new NoteOperator(AddActivity.this);
                get_title = title.getText().toString().trim();
                get_context = context.getText().toString().trim();
                get_year = year.getText().toString().trim();
                get_month = month.getText().toString().trim();
                get_day = day.getText().toString().trim();
                get_time = time.getText().toString().trim();

                if (TextUtils.isEmpty(get_title) || TextUtils.isEmpty(get_context)) {
                    Toast.makeText(AddActivity.this, "添加信息不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Note note = new Note();
                    note.title = get_title;
                    note.context = get_context;
                    note.year = get_year;
                    note.month = get_month;
                    note.day = get_day;
                    note.time = get_time;
                    boolean add = noteOperator.insert(note);
                    //如果添加数据成功，跳到待办事项界面，并通过传值，让目标界面进行刷新
                    if (add) {
                        //Toast.makeText(AddActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(AddActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.back_add:
                finish();
                break;
            case R.id.ddl:
                AddTimeDialog dialog = new AddTimeDialog(this);
                dialog.ShowDia();
                dialog.setMessage(this);
                break;
        }
    }

    @Override
    public void changer(Map<String, String> map) {//接口在AddTimeDialog.java
        year.setText(map.get("year"));
        month.setText(map.get("month"));
        day.setText(map.get("day"));
        time.setText(map.get("time"));
    }
}
