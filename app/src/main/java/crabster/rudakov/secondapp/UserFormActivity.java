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
    User user;
    Boolean isUpdated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        user = (User) getIntent().getSerializableExtra("user");
        editTextName = findViewById(R.id.editTextName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhone = findViewById(R.id.editTextPhone);
        insertUserBtn = findViewById(R.id.insertUserButton);
        //  Если такой пользователь присутсвует, то редактируем его данные
        if (user != null) {
            isUpdated = true;
            editTextName.setText(user.getUserName());
            editTextLastName.setText(user.getUserLastName());
            editTextPhone.setText(user.getPhone());
            //  Если такой пользователь отсутсвует, то добавляем нового
        } else {
            user = new User();
        }
        insertUserBtn.setOnClickListener(view -> {
            //  Нажатием кнопки записываем в объект все данные
            user.setUserName(editTextName.getText().toString());
            user.setUserLastName(editTextLastName.getText().toString());
            user.setPhone(editTextPhone.getText().toString());
            Users users = new Users(UserFormActivity.this);
            //  Если данные пользователя подлежат редактирвоанию, то вызываем метод 'update()'
            //  Иначе создаем нового пользователя
            if (isUpdated) {
                users.updateUser(user);
            } else
                users.addUser(user);
            //  Метод закрывает текущую и возвращает предыдущую активность
            onBackPressed();
        });
    }

}