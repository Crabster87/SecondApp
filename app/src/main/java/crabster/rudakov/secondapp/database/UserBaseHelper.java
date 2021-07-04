package crabster.rudakov.secondapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

//  Класс для работы с БД
public class UserBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userBase.db";
    private static final int VERSION = 1;

    public UserBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + UserDbSchema.UserTable.NAME
                               + "(" + "_id integer primary key autoincrement, "
                               + UserDbSchema.Cols.UUID + ", "
                               + UserDbSchema.Cols.USERNAME + ", "
                               + UserDbSchema.Cols.USERLASTNAME + ","
                               + UserDbSchema.Cols.PHONE + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }

}
