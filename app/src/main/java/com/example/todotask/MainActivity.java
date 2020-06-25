package com.example.todotask;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView add_text;
    private RecyclerView recyclerview;
    private EditText search_edit_text;
    private TasksAdapter tasksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_text = findViewById(R.id.add_text);
        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        search_edit_text = findViewById(R.id.search_edit_text);
        add_text.setOnClickListener(this);
        getTasks();
        search_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tasksAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable editable) {
               tasksAdapter.getFilter().filter(editable);
            }
        });


    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_text:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
            default:
        }
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Task>> {

            @Override
            protected List<Task> doInBackground(Void... voids) {
                List<Task> taskList = DatabaseClient
                        .getInstance(getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Task> tasks) {
                super.onPostExecute(tasks);
                tasksAdapter = new TasksAdapter(MainActivity.this, tasks);
                recyclerview.setAdapter(tasksAdapter);


            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


}
