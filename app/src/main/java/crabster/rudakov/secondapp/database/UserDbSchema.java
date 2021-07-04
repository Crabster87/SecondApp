package crabster.rudakov.secondapp.database;

//  Структура БД
public class UserDbSchema {

    public static final class UserTable {
        public static final String NAME = "username"; // название схемы БД
    }

    public static final class Cols {
        public static final String UUID = "uuid";
        public static final String USERNAME = "user_name";
        public static final String USERLASTNAME = "user_last_name";
        public static final String PHONE = "phone";
    }

}
