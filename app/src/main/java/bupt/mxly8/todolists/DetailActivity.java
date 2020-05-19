package bupt.mxly8.todolists;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener, AddTimeDialog.Message {
    private Button back;
    private Button save;
    private EditText title;
    private EditText context;
    private LinearLayout ddl;
    private TextView year, month, day, time;

    private int note_id = 0;
    private String get_title;
    private String get_context;
    private String get_year;
    private String get_month;
    private String get_day;
    private String get_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        title = (EditText) findViewById(R.id.title_detail);
        context = (EditText) findViewById(R.id.context_detail);
        back = (Button) findViewById(R.id.back_detail);
        save = (Button) findViewById(R.id.save_detail);

        ddl = (LinearLayout) findViewById(R.id.edit_ddl);
        year = (TextView) findViewById(R.id.edit_ddl_year);
        month = (TextView) findViewById(R.id.edit_ddl_month);
        day = (TextView) findViewById(R.id.edit_ddl_day);
        time = (TextView) findViewById(R.id.edit_ddl_time);

        back.setOnClickListener(this);
        save.setOnClickListener(this);
        ddl.setOnClickListener(this);

        //接收listView中点击item传来的note_id,
        Intent intent = getIntent();
        note_id = intent.getIntExtra("note_id", 0);
        NoteOperator noteOperator = new NoteOperator(this);
        Note note = noteOperator.getNoteById(note_id);
        title.setText(String.valueOf(note.title));
        context.setText(String.valueOf(note.context));
        year.setText(String.valueOf(note.year));
        month.setText(String.valueOf(note.month));
        day.setText(String.valueOf(note.day));
        time.setText(String.valueOf(note.time));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_detail:
                finish();
                break;
            case R.id.save_detail:
                get_title = title.getText().toString().trim();
                get_context = context.getText().toString().trim();
                get_year = year.getText().toString().trim();
                get_month = month.getText().toString().trim();
                get_day = day.getText().toString().trim();
                get_time = time.getText().toString().trim();

                if (TextUtils.isEmpty(get_title) || TextUtils.isEmpty(get_context)
                        || TextUtils.isEmpty(get_year) || TextUtils.isEmpty(get_month)
                        || TextUtils.isEmpty(get_day) || TextUtils.isEmpty(get_time)) {
                    Toast.makeText(this, "修改内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Note note = new Note();
                    note.note_id = note_id;
                    note.title = get_title;
                    note.context = get_context;
                    note.year = get_year;
                    note.month = get_month;
                    note.day = get_day;
                    note.time = get_time;
                    NoteOperator noteOperator = new NoteOperator(DetailActivity.this);
                    noteOperator.update(note);
                    Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            case R.id.edit_ddl:
                AddTimeDialog dialog = new AddTimeDialog(this);
                dialog.ShowDia();
                dialog.setMessage(this);
                break;
        }
    }

    @Override
    public void changer(Map<String, String> map) {
        year.setText(map.get("year"));
        month.setText(map.get("month"));
        day.setText(map.get("day"));
        time.setText(map.get("time"));
    }
}
