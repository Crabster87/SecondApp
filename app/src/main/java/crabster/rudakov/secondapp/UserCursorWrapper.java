package crabster.rudakov.secondapp;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import crabster.rudakov.secondapp.database.UserDbSchema;

//  Осуществляет построчное чтение данных тз БД
public class UserCursorWrapper extends CursorWrapper {

    public UserCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public User getUser() {
        //  по номерам колонок получаем строковые представления данных БД
        String uuidString = getString(getColumnIndex(UserDbSchema.Cols.UUID));
        String userName = getString(getColumnIndex(UserDbSchema.Cols.USERNAME));
        String userLastName = getString(getColumnIndex(UserDbSchema.Cols.USERLASTNAME));
        String phone = getString(getColumnIndex(UserDbSchema.Cols.PHONE));
        //  из строки создаем UUID и на его основании создаем объект
        User user = new User(UUID.fromString(uuidString));
        user.setUserName(userName);
        user.setUserLastName(userLastName);
        user.setPhone(phone);
        return user;
    }

}
