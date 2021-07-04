package crabster.rudakov.secondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class UserFormActivity extends AppCompatActivity {

    Button insertUserBtn;
    EditText editTextName;
    EditText editTextLastName;
    EditText editTextPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);

        insertUserBtn = findViewById(R.id.insertUserButton);
        insertUserBtn.setOnClickListener(view -> {
            //  нажатием кнопки записываем в объект все данные
            User user = new User();
            user.setUserName(editTextName.getText().toString());
            user.setUserLastName(editTextLastName.getText().toString());
            user.setPhone(editTextPhone.getText().toString());
            Users users = new Users(UserFormActivity.this);
            //  добавляем его в список
            users.addUser(user);
            //  метод закрывает текущую и возвращает предыдущую активность
            onBackPressed();
        });
    }

}