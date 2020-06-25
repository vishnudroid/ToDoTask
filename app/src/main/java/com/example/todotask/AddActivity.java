package com.example.todotask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {
   private TextView save;
  private   TextView cancel;
  private EditText title_edit_text;
  private EditText description_edit_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        title_edit_text=findViewById(R.id.title_edit_text);
        description_edit_text=findViewById(R.id.description_edit_text);
        save=findViewById(R.id.save);
        cancel=findViewById(R.id.cancel);
        save.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                saveTask();
                break;
            case R.id.cancel:
                break;
            default:
        }
    }

    private void saveTask() {
        final String sTask = title_edit_text.getText().toString().trim();
        final String sDesc = description_edit_text.getText().toString().trim();


        if (sTask.isEmpty()) {
            title_edit_text.setError("Task required");
            title_edit_text.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            description_edit_text.setError("Desc required");
            description_edit_text.requestFocus();
            return;
        }



        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Task task = new Task();
                task.setTask(sTask);
                task.setDesc(sDesc);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

}
