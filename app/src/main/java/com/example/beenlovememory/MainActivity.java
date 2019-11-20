package com.example.beenlovememory;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView tvDays;
    AppDatabase db;
    TextView name1;
    TextView name2;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvDays = findViewById(R.id.tv_day);
        name1 = findViewById(R.id.tv_name1);
        name2 = findViewById(R.id.tv_name2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "database-name").build();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                if (db.userDao().getAll().size() == 0) {
                    String date = convertDate(new Date());
                    Log.e("TAG", "doInBackground: " + date);
                    User user = new User();
                    user.setName1("Nick Name 1");
                    user.setName2("Nick Name 2");
                    user.setDate(date);
                    db.userDao().insertAll(user);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                setInfor();
            }
        }.execute();
        tvDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void setInfor() {
        new AsyncTask<Void, Void, List<User>>() {
            @Override
            protected List<User> doInBackground(Void... voids) {
                final List<User> users = db.userDao().getAll();
                return users;
            }

            @Override
            protected void onPostExecute(final List<User> users) {
                super.onPostExecute(users);
                final User user = users.get(0);
                name1.setText(user.getName1());
                name2.setText(user.getName2());
                name1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showUpdetName1Screen(user);
                    }
                });
                name2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showUpdetName2Screen(user);
                    }
                });
                beenLove(users.get(0).getDate());
            }
        }.execute();
    }

    @Override
    public void onResume() {
        super.onResume();
        setInfor();
    }

    private void beenLove(String StartLove) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date oldDate = dateFormat.parse(StartLove);
            Date currentDate = new Date();
            long MILI_GIAY = 1000;
            long MILI_PHUT = MILI_GIAY * 60;
            long MILI_GIO = MILI_PHUT * 60;
            long MILI_NGAY = MILI_GIO * 24;
            long MILI_THANG = MILI_NGAY * 30;
            long MILI_NAM = MILI_THANG * 12;

            long TIME = currentDate.getTime() - oldDate.getTime();
            long dayLove = TIME / MILI_NGAY;
            long years = TIME / MILI_NAM;
            long morths = (TIME % MILI_NAM) / MILI_THANG;
            long days = (TIME % MILI_THANG) / MILI_NGAY;
            long hours = (TIME % MILI_NGAY) / MILI_GIO;
            long minutes = (TIME % MILI_GIO) / MILI_PHUT;

            TextView DayLove = findViewById(R.id.tv_day);
            DayLove.setText("" + dayLove);
            Button bt_Year = findViewById(R.id.bt_nam);
            bt_Year.setText("" + years);
            Button bt_Morth = findViewById(R.id.bt_thang);
            bt_Morth.setText("" + morths);
            Button bt_day = findViewById(R.id.bt_ngay);
            bt_day.setText("" + days);
            Button bt_hour = findViewById(R.id.bt_gio);
            bt_hour.setText("" + hours);
            Button bt_minuter = findViewById(R.id.bt_phut);
            bt_minuter.setText("" + minutes);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void showDatePicker() {
        DatePickerDialog date = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, final int year, final int month, final int day) {
                new AsyncTask<Void, Void, List<User>>() {
                    @Override
                    protected List<User> doInBackground(Void... voids) {
                        final List<User> users = db.userDao().getAll();
                        return users;
                    }

                    @Override
                    protected void onPostExecute(final List<User> users) {
                        super.onPostExecute(users);
                        User user = users.get(0);
                        user.setDate(year + "-" + month + "-" + day);
                        updateDate(user);
                    }
                }.execute();
            }
        }, 2019, 01, 01);
        date.show();
    }

    @SuppressLint("StaticFieldLeak")
    public void updateDate(final User user) {
        new AsyncTask<Void, Void, User>() {
            @Override
            protected User doInBackground(Void... voids) {
                db.userDao().updateUser(user);
                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                beenLove(user.getDate());
            }
        }.execute();
    }

    void showUpdetName1Screen(User user) {
        Intent intent = new Intent(MainActivity.this, UpadateName1.class);
        intent.putExtra("id", user.getUid());
        intent.putExtra("name1", user.getName1());
        intent.putExtra("name2", user.getName2());
        intent.putExtra("date", user.getDate());
        startActivity(intent);
    }

    void showUpdetName2Screen(User user) {
        Intent intent = new Intent(MainActivity.this, UpadateName2.class);
        intent.putExtra("id", user.getUid());
        intent.putExtra("name1", user.getName1());
        intent.putExtra("name2", user.getName2());
        intent.putExtra("date", user.getDate());
        startActivity(intent);
    }

    public static String convertDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        Date time = calendar.getTime();
        SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateAsString = outputFmt.format(time);
        return dateAsString;
    }
}
