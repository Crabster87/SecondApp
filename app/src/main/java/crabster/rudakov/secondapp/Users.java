package crabster.rudakov.secondapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import crabster.rudakov.secondapp.database.UserBaseHelper;
import crabster.rudakov.secondapp.database.UserDbSchema;

//  Класс содержит логику работы с БД
public class Users {

    private ArrayList<User> userList;
    private SQLiteDatabase dataBase;
    private Context context;

    public Users(Context context) {
        //  Получаем контекст всего приложения для последующей возможности обращения к БД
        this.context = context.getApplicationContext();
        //  Создаем объект для взаимодействия с БД
        dataBase = new UserBaseHelper(context).getWritableDatabase();
    }

    //  Осуществляет добавление пользователя в БД
    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        dataBase.insert(UserDbSchema.UserTable.NAME, null, values);
    }

//    //  Осуществляет удаление пользователя из БД
//    public void deleteUser(long id) {
//        dataBase.delete(UserDbSchema.UserTable.NAME, "_id = id", new String[]{String.valueOf(id)});
//    }

    //  Осуществляет сопоставление данных(свойства объекта User относительно колонок БД)
    private static ContentValues getContentValues(User user) {
        ContentValues values = new ContentValues();
        values.put(UserDbSchema.Cols.UUID, user.getUuid().toString());
        values.put(UserDbSchema.Cols.USERNAME, user.getUserName());
        values.put(UserDbSchema.Cols.USERLASTNAME, user.getUserLastName());
        values.put(UserDbSchema.Cols.PHONE, user.getPhone());
        return values;
    }

    // Осуществялет запрос к БД
    private UserCursorWrapper queryUsers() {
        Cursor cursor = dataBase.query(UserDbSchema.UserTable.NAME
                , null
                , null
                , null
                , null
                , null
                , null);
        return new UserCursorWrapper(cursor);
    }

    public ArrayList<User> getUserList() {
        this.userList = new ArrayList<User>();
        UserCursorWrapper cursorWrapper = queryUsers();
        try {
            //  перемещаем курсор на первую строку
            cursorWrapper.moveToFirst();
            //  проверяем не является ли запись финальной
            while (!cursorWrapper.isAfterLast()) {
                User user = cursorWrapper.getUser();
                userList.add(user);
                cursorWrapper.moveToNext();
            }
        } finally {
            cursorWrapper.close();
        }
        return userList;
    }

}
