package crabster.rudakov.secondapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    TextView textViewInfoName;
    TextView textViewInfoLastName;
    TextView textViewInfoPhone;
    Button removeUserInfo;
    Button editUserInfo;
    Users users;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        users = new Users(UserInfoActivity.this);
        user = (User) getIntent().getSerializableExtra("user");

        removeUserInfo = findViewById(R.id.removeUserInfo);
        editUserInfo = findViewById(R.id.editUserInfo);

        //  Создаем и запускаем UserFormActivity для редактирования данных пользователя
        editUserInfo.setOnClickListener(v -> {
            Intent intent = new Intent(UserInfoActivity.this, UserFormActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            onBackPressed();
        });

        removeUserInfo.setOnClickListener(v -> {
            users.removeUser(user.getUuid());
            onBackPressed();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        users.getUserFromDB(user.getUuid());
        //  Заново отрисовываем данные пользователя
        textViewInfoName = findViewById(R.id.textViewInfoName);
        textViewInfoLastName = findViewById(R.id.textViewInfoLastName);
        textViewInfoPhone = findViewById(R.id.textViewInfoPhone);
        textViewInfoName.setText(user.getUserName());
        textViewInfoLastName.setText(user.getUserLastName());
        textViewInfoPhone.setText(user.getPhone());
    }

}