package com.eqmonterozo.jobboard;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class JobDetailsActivity extends AppCompatActivity {

    private TextView txtDetailJobName, txtDetailElapsedTime, txtDetailCompany, txtDetailJobLocation, txtDetailJobSalary, txtDetailEmploymentType, txtDetailJobDescription;
    private ImageView imgDetailJobPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        txtDetailJobName = findViewById(R.id.txtDetailJobName);
        txtDetailElapsedTime = findViewById(R.id.txtDetailElapsedTime);
        txtDetailCompany = findViewById(R.id.txtDetailCompany);
        txtDetailJobLocation = findViewById(R.id.txtDetailJobLocation);
        txtDetailJobSalary = findViewById(R.id.txtDetailJobSalary);
        txtDetailEmploymentType = findViewById(R.id.txtDetailEmploymentType);
        txtDetailJobDescription = findViewById(R.id.txtDetailJobDescription);
        imgDetailJobPhoto = findViewById(R.id.imgDetailJobPhoto);

        txtDetailJobName.setText(getIntent().getStringExtra("jobTitle"));
        txtDetailElapsedTime.setText(getIntent().getStringExtra("elapsedTime"));
        txtDetailCompany.setText(getIntent().getStringExtra("company"));
        txtDetailJobLocation.setText(getIntent().getStringExtra("companyAddress"));
        txtDetailJobSalary.setText("Salary: " + getIntent().getStringExtra("salary"));
        txtDetailEmploymentType.setText("Employment: " + getIntent().getStringExtra("employmentType"));
        txtDetailJobDescription.setText(getIntent().getStringExtra("jobDescription"));
        Glide.with(this).load(getIntent().getStringExtra("jobPhoto")).into(imgDetailJobPhoto);

    }
}