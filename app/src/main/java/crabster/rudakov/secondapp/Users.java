package crabster.rudakov.secondapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.UUID;

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
        this.dataBase = new UserBaseHelper(context).getWritableDatabase();
    }

    //  Осуществляет добавление пользователя в БД
    public void addUser(User user) {
        ContentValues values = getContentValues(user);
        dataBase.insert(UserDbSchema.UserTable.NAME, null, values);
    }

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

    //  Осуществляет получение пользователя из БД
    public User getUserFromDB(UUID uuid) {
        Cursor cursor = dataBase.query(UserDbSchema.UserTable.NAME, null,
                UserDbSchema.Cols.UUID + "=?",
                new String[]{uuid.toString()}, null,
                null,
                null);
        UserCursorWrapper cursorWrapper = new UserCursorWrapper(cursor);
        cursorWrapper.moveToFirst();
        return cursorWrapper.getUser();
    }

    //  Осуществляет удаление пользователя из БД
    public void removeUser(UUID uuid) {
        String stringUuid = uuid.toString();
        dataBase.delete(UserDbSchema.UserTable.NAME,
                UserDbSchema.Cols.UUID + "=?", new String[]{stringUuid});
    }

    //  Осуществляет обновление данных пользователя в БД
    public void updateUser(User user) {
        ContentValues values = getContentValues(user);
        String stringUuid = user.getUuid().toString();
        dataBase.update(UserDbSchema.UserTable.NAME, values,
                UserDbSchema.Cols.UUID + "=?", new String[]{stringUuid});
    }

}
