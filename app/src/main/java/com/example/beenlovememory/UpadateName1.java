package com.example.beenlovememory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class UpadateName1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upadate_name1);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        EditText editText = findViewById(R.id.editnam1);
        Intent intent = getIntent();
        String boyName = intent.getStringExtra("name1");
        final String girlName = intent.getStringExtra("name2");
        final String date = intent.getStringExtra("date");
        final int id = intent.getIntExtra("id", 0);
        editText.setText(boyName);
        findViewById(R.id.updateName1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.editnam1);
                updateName1(id, editText.getText().toString(),girlName,date);
            }
        });
    }
    @SuppressLint("StaticFieldLeak")
    public void updateName1(final int id, final String name1, final String name2, final String date){
        final AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                User user =new User();
                user.setUid(id);
                user.setName1(name1);
                user.setName2(name2);
                user.setDate(date);
                db.userDao().updateUser(user);
                return null;
            }
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent = new Intent(UpadateName1.this, MainActivity.class);
                startActivity(intent);
            }
        }.execute();
    }
}
