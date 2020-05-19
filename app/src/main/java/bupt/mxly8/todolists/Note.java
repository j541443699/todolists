package bupt.mxly8.todolists;

import java.sql.Timestamp;

public class Note {
    //表名
    public static final String TABLE = "todo";
    //列名
    public static final String KEY_id="id";
    public static final String KEY_title="title";
    public static final String KEY_context="context";
    public static final String KEY_ddl_year="year";
    public static final String KEY_ddl_month="month";
    public static final String KEY_ddl_day="day";
    public static final String KEY_ddl_time="time";
    public static final String KEY_ddl = "ddl";

    public int note_id;
    public String title;
    public String context;
    public String year;
    public String month;
    public String day;
    public String time;
    public Timestamp ddl;
}
