package crabster.rudakov.secondapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<User> userList = new ArrayList<>();
    UserAdapter userAdapter;
    Button addUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  запускаем создание активности на основе сохраненных данных или null
        super.onCreate(savedInstanceState);
        //  устанавливаем основное View
        setContentView(R.layout.activity_main);
        //  переменной "recyclerView" устанавливаем View
        recyclerView = findViewById(R.id.recyclerView);
        //  определяем шаблон макета списка на основе текущей активности
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        //  создаем кнопку, запускающую процесс добавления нового пользователя в БД
        addUserButton = findViewById(R.id.addUserButton);
        addUserButton.setOnClickListener(v -> {
            //  создаем Intent с отсылкой на новую UserFormActivity
            Intent intent = new Intent(MainActivity.this, UserFormActivity.class);
            startActivity(intent);
        });
    }

    private void recycleViewInit() {
        Users users = new Users(MainActivity.this);
        userList = users.getUserList();
        //  создаем Adapter и передаем ему список пользователей
        userAdapter = new UserAdapter(userList);
        //  переменной "recyclerView" устанавливаем Adapter и связываем его с Holder
        recyclerView.setAdapter(userAdapter);
        /*//  генерируем список пользователей добавлением объектов User с нужными параметрами
        for (int i = 1; i < 101; i++) {
            User user = new User();
            user.setUserName("User №" + i);
            user.setUserLastName("Lastname №" + i);
            users.add(user);
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        recycleViewInit();
    }

    //  создаем класс и наследуем соответствующую функциональность для генерации элементов списка
    private class UserHolder extends RecyclerView.ViewHolder {

        TextView itemTextView;

        public UserHolder(LayoutInflater inflater, ViewGroup viewGroup) {
            //  раздуваем активность отдельного элемента списка "single_item"
            super(inflater.inflate(R.layout.single_item, viewGroup, false));
            // itemView - текущий layout single_item
            itemTextView = itemView.findViewById(R.id.itemTextView);

            //  создаем активность по просмотру персональных данный пользователя
            itemTextView.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, PersonalUserFormActivity.class);
                intent.putExtra("data", itemTextView.getText().toString());
                startActivity(intent);
            });
        }

        // метод привязывает "userString" к single_item
        public void bind(String userString) {
            itemTextView.setText(userString);
        }

    }

    //  создаем класс и наследуем соответствующую функциональность для передачи каждого элемента на RecyclerView
    public class UserAdapter extends RecyclerView.Adapter<UserHolder> {

        ArrayList<User> users;

        public UserAdapter(ArrayList<User> users) {
            this.users = users;
        }

        @Override
        //  создаем одну новую View (вызывается LayoutManager'ом)
        //  где viewGroup - место размещения элемента и i - индекс элемента, находящегося на экране
        public UserHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            //  с помощью класса Inflater из содержимого layout-файла создаем View-элемент(раздуваем аткивность)
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            //  возвращаем объект UserHolder, создающий элемент списка, по вызову RecyclerView
            return new UserHolder(inflater, viewGroup);
        }

        @Override
        //  для записи элемента в макет привязываем объект UserHolder
        public void onBindViewHolder(UserHolder userHolder, int position) {
            //  выбираем пользователя с индексом position
            User user = users.get(position);
            //  конструируем строку выходных данных
            String userString = user.getUserName() + "\t" + user.getUserLastName()
                                                   + "\n" + user.getPhone();
            //  отправляем строку холдеру для отображения в itemTextView
            userHolder.bind(userString);
        }

        @Override
        //  метод возвращает количество элементов (вызывается LayoutManager'ом)
        public int getItemCount() {
            return users.size();
        }
    }

}