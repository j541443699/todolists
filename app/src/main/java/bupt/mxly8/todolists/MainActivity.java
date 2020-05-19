package bupt.mxly8.todolists;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    //private static String TAG = "LIFECYCLE";

    private ListView listView;
    private Button addBt;
    TextView note_id;//向其他界面传值
    ArrayList<HashMap<String,String>> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Log.i(TAG,"(1)onCreate()");

        listView = (ListView)findViewById(R.id.listView);
        addBt = (Button)findViewById(R.id.addButton);
        addBt.setOnClickListener(this);

        //通过list获取数据库表中的所有id和title，通过ListAdapter给listView赋值
        final NoteOperator noteOperator = new NoteOperator(MainActivity.this);
        list = noteOperator.getNoteList();
        final ListAdapter listAdapter = new SimpleAdapter(MainActivity.this, list,
                R.layout.item, new String[]{"id", "title","year","month","day","time"},
                new int[]{R.id.note_id, R.id.note_title, R.id.note_ddl_year, R.id.note_ddl_month,
                        R.id.note_ddl_day, R.id.note_ddl_time});
        listView.setAdapter(listAdapter);

        if (list.size() != 0) {
            //点击listView的任何一项跳到详情页面
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long i) {
                    String id = list.get(position).get("id");
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, DetailActivity.class);
                    intent.putExtra("note_id", Integer.parseInt(id));
                    startActivity(intent);
                }
            });

            //长按实现对列表的删除
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(final AdapterView<?> adapterView, View view, final int position, long l) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("确定删除？");
                    builder.setTitle("提示");

                    //添加AlterDialog.Builder对象的setPositiveButton()方法
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            String id = list.get(position).get("id");
                            noteOperator.delete(Integer.parseInt(id));
                            list.remove(position);
                            //listAdapter.notify();
                            listView.setAdapter(listAdapter);
                        }
                    });

                    //添加AlterDialog.Builder对象的setNegativeButton()方法
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.create().show();
                    return true;
                }
            });
        } else {
            Toast.makeText(this, "暂无待办事项，请添加", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onStart(){
        super.onStart();
        //从其他Activity回到MainActivity时，调用onStart()来刷新列表
        final NoteOperator noteOperator_2 = new NoteOperator(MainActivity.this);
        list = noteOperator_2.getNoteList();
        final ListAdapter listAdapter_2 = new SimpleAdapter(MainActivity.this, list,
                R.layout.item, new String[]{"id", "title","year","month","day","time"},
                new int[]{R.id.note_id, R.id.note_title, R.id.note_ddl_year, R.id.note_ddl_month,
                        R.id.note_ddl_day, R.id.note_ddl_time});
        list = noteOperator_2.getNoteList();
        listView.setAdapter(listAdapter_2);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addButton:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
        }
    }



}
