package kz.mobile.databaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomDatabase;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextAge;
    private Button buttonCreate;

    private DatabaseHelper databaseHelper;

    private PersonRoomDatabase roomDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //initDatabase();
        initRoomDatabase();
        initViews();
    }

    private void initDatabase() {
        databaseHelper = new DatabaseHelper(this);
        Log.d("all_persons", databaseHelper.getAllPersons().toString());
    }

    private void initRoomDatabase() {
        roomDatabase = PersonRoomDatabase.getInstance(this);
        new GetAllPersonsAsync().execute();
    }

    private void initViews() {
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextAge = findViewById(R.id.editTextAge);
        buttonCreate = findViewById(R.id.buttonCreate);

        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPerson2(
                        editTextName.getText().toString(),
                        editTextSurname.getText().toString(),
                        Integer.parseInt(editTextAge.getText().toString())
                );
            }
        });
    }

    private void createPerson(String name, String surname, int age) {
        Person person = new Person(name, surname, age);
        //new CreatePersonAsync().execute(person);
    }

    private void createPerson2(String name, String surname, int age) {
        Person2 person = new Person2();
        person.setName(name);
        person.setAge(age);
        person.setSurname(surname);
        new CreatePersonAsync().execute(person);
    }

    class CreatePersonAsync extends AsyncTask<Person2, Void, Void> {

        @Override
        protected Void doInBackground(Person2... people) {
            roomDatabase.daoPerson().insert(people[0]);
            //databaseHelper.insert(people[0]);
            return null;
        }
    }

    class GetAllPersonsAsync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d("all_persons", roomDatabase.daoPerson().getAllPersons().toString());
            return null;
        }
    }
}
