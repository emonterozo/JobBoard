package com.eqmonterozo.jobboard;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recViewJob;
    private FloatingActionButton fabAdd;

    ArrayList<String> job_id;
    ArrayList<String> job_title;
    ArrayList<String> company_name;
    ArrayList<String> company_address;
    ArrayList<String> salary_range;
    ArrayList<String> employment_type;
    ArrayList<String> phone_number;
    ArrayList<String> email_address;
    ArrayList<String> job_photo;
    ArrayList<String> job_description;
    ArrayList<String> elapsed_time;
    DatabaseHelper databaseHelper;
    JobsAdapter jobsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recViewJob = findViewById(R.id.recViewJob);

        databaseHelper = new DatabaseHelper(this);
        job_id = new ArrayList<>();
        job_title = new ArrayList<>();
        company_name = new ArrayList<>();
        company_address = new ArrayList<>();
        salary_range = new ArrayList<>();
        employment_type = new ArrayList<>();
        phone_number = new ArrayList<>();
        email_address = new ArrayList<>();
        job_photo = new ArrayList<String>();
        job_description = new ArrayList<>();
        elapsed_time = new ArrayList<>();

        displayJobs();

        jobsAdapter = new JobsAdapter(this, databaseHelper, job_id, job_title, company_name, company_address, salary_range, employment_type, phone_number, email_address, job_photo, job_description, elapsed_time);
        recViewJob.setAdapter(jobsAdapter);
        recViewJob.setLayoutManager(new LinearLayoutManager(this));

        fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), JobActivity.class);
                intent.putExtra("action", "ADD");
                startActivity(intent);
            }
        });


    }


    public void displayJobs() {
        Cursor cursor = databaseHelper.getJobs();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No available data.", Toast.LENGTH_LONG).show();
        } else {
            while (cursor.moveToNext()) {
                job_id.add(cursor.getString(0));
                job_title.add(cursor.getString(1));
                company_name.add(cursor.getString(2));
                company_address.add(cursor.getString(3));
                salary_range.add(cursor.getString(4));
                employment_type.add(cursor.getString(5));
                phone_number.add(cursor.getString(6));
                email_address.add(cursor.getString(7));
                job_photo.add(cursor.getString(8));
                job_description.add(cursor.getString(9));
                elapsed_time.add(cursor.getString(10));
            }
        }
    }
}