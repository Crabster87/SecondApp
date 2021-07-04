package crabster.rudakov.secondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import crabster.rudakov.secondapp.database.UserDbSchema;

public class PersonalUserFormActivity extends AppCompatActivity {

    Button deleteUserButton;
    TextView watchName;
    TextView watchLastName;
    TextView watchPhone;

    private SQLiteDatabase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_user_form);

        watchName = findViewById(R.id.watchName);
        watchLastName = findViewById(R.id.watchLastName);
        watchPhone = findViewById(R.id.watchPhone);

        //  разделяем строку с данными пользователя по принципу "пробел или начало строки"
        String[] arrayUserData = getIntent().getStringExtra("data").split("(^|\\s)");
        watchName.setText(arrayUserData[0]);
        watchLastName.setText(arrayUserData[1]);
        watchPhone.setText(arrayUserData[2]);

//        deleteUserButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//             ///////////////////TODO
//                dataBase.delete(UserDbSchema.UserTable.NAME, "_id = ?", new String[]{String.valueOf(watchName),
//                                                                               String.valueOf(watchLastName),
//                                                                               String.valueOf(watchPhone)
//                });
//            }
//        });
    }

}