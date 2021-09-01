package com.eqmonterozo.jobboard;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class JobActivity extends AppCompatActivity {

    private static final int SELECT_PICTURE = 200;
    private TextInputLayout inputJobName, inputCompany, inputCompanyAddress, inputSalary, inputEmploymentType, inputPhoneNumber, inputEmail, inputJobDescription;
    private AutoCompleteTextView autEmploymentType;
    private TextInputEditText edtJobName, edtCompany, edtCompanyAddress, edtSalary, edtPhoneNumber, edtEmail, edtJobDescription;
    private MaterialButton btnNext, btnSubmit, btnChooseImage;
    private ImageView imgJobPhoto;
    private ViewFlipper flipJob;
    private DatabaseHelper databaseHelper;
    private String action, jobId;

    ArrayAdapter<String> employmentTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job);

        inputJobName = findViewById(R.id.inputJobName);
        edtJobName = findViewById(R.id.edtJobName);

        inputCompany = findViewById(R.id.inputCompany);
        edtCompany = findViewById(R.id.edtCompany);

        inputCompanyAddress = findViewById(R.id.inputCompanyAddress);
        edtCompanyAddress = findViewById(R.id.edtCompanyAddress);

        inputSalary = findViewById(R.id.inputSalary);
        edtSalary = findViewById(R.id.edtSalary);

        inputEmploymentType = findViewById(R.id.inputEmploymentType);
        autEmploymentType = findViewById(R.id.autEmploymentType);

        inputPhoneNumber = findViewById(R.id.inputPhoneNumber);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);

        inputEmail = findViewById(R.id.inputEmail);
        edtEmail = findViewById(R.id.edtEmail);

        inputJobDescription = findViewById(R.id.inputJobDescription);
        edtJobDescription = findViewById(R.id.edtJobDescription);

        btnNext = findViewById(R.id.btnNext);
        flipJob = findViewById(R.id.flipJob);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnChooseImage = findViewById(R.id.btnChooseImage);
        imgJobPhoto = findViewById(R.id.imgJobPhoto);

        databaseHelper = new DatabaseHelper(this);

//        Setting Employment Types options
        employmentTypes = new ArrayAdapter<>(getApplicationContext(), R.layout.employment_type_item, getResources().getStringArray(R.array.employment_types));
        autEmploymentType.setAdapter(employmentTypes);

        edtJobName.addTextChangedListener(new InputTextWatcher(edtJobName, inputJobName));
        edtCompany.addTextChangedListener(new InputTextWatcher(edtCompany, inputCompany));
        edtCompanyAddress.addTextChangedListener(new InputTextWatcher(edtCompanyAddress, inputCompanyAddress));
        edtSalary.addTextChangedListener(new InputTextWatcher(edtSalary, inputSalary));
        autEmploymentType.addTextChangedListener(new InputTextWatcher(autEmploymentType, inputEmploymentType));
        edtPhoneNumber.addTextChangedListener(new InputTextWatcher(edtPhoneNumber, inputPhoneNumber));
        edtEmail.addTextChangedListener(new InputTextWatcher(edtEmail, inputEmail));
        edtJobDescription.addTextChangedListener(new InputTextWatcher(edtJobDescription, inputJobDescription));

        action = getIntent().getStringExtra("action");
        if (action.equals("EDIT")) {
            jobId = getIntent().getStringExtra("jobId");
            edtJobName.setText(getIntent().getStringExtra("jobTitle"));
            edtCompany.setText(getIntent().getStringExtra("company"));
            edtCompanyAddress.setText(getIntent().getStringExtra("companyAddress"));
            edtSalary.setText(getIntent().getStringExtra("salary"));
            edtPhoneNumber.setText(getIntent().getStringExtra("phoneNumber"));
            edtEmail.setText(getIntent().getStringExtra("email"));
            edtJobDescription.setText(getIntent().getStringExtra("jobDescription"));
            Glide.with(this).load(getIntent().getStringExtra("jobPhoto")).into(imgJobPhoto);
            btnSubmit.setText("Update");
        }

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if (edtJobName.getText().toString().isEmpty()) {
                    inputJobName.setHelperTextEnabled(false);
                    inputJobName.setError("Job Title is mandatory.");
                    isValid = false;
                } else {
                    inputJobName.setErrorEnabled(false);
                }

                if (edtCompany.getText().toString().isEmpty()) {
                    inputCompany.setHelperTextEnabled(false);
                    inputCompany.setError("Company Name is mandatory.");
                    isValid = false;
                } else {
                    inputCompany.setErrorEnabled(false);
                }

                if (edtCompanyAddress.getText().toString().isEmpty()) {
                    inputCompanyAddress.setHelperTextEnabled(false);
                    inputCompanyAddress.setError("Company Address is mandatory.");
                    isValid = false;
                } else {
                    inputCompanyAddress.setErrorEnabled(false);
                }

                if (edtSalary.getText().toString().isEmpty()) {
                    inputSalary.setHelperTextEnabled(false);
                    inputSalary.setError("Salary Range is mandatory.");
                    isValid = false;
                } else {
                    inputSalary.setErrorEnabled(false);
                }

                if (autEmploymentType.getText().toString().isEmpty()) {
                    inputEmploymentType.setHelperTextEnabled(false);
                    inputEmploymentType.setError("Employment Type is mandatory.");
                    isValid = false;
                } else {
                    inputEmploymentType.setErrorEnabled(false);
                }

                if (edtPhoneNumber.getText().toString().isEmpty()) {
                    inputPhoneNumber.setHelperTextEnabled(false);
                    inputPhoneNumber.setError("Phone Number is mandatory.");
                    isValid = false;
                } else {
                    inputPhoneNumber.setErrorEnabled(false);
                }

                if (edtEmail.getText().toString().isEmpty()) {
                    inputEmail.setHelperTextEnabled(false);
                    inputEmail.setError("Email Address is mandatory.");
                    isValid = false;
                } else {
                    inputEmail.setErrorEnabled(false);
                }


                if (isValid) {
                    flipJob.showNext();
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isValid = true;

                if (edtJobDescription.getText().toString().isEmpty()) {
                    inputJobDescription.setHelperTextEnabled(false);
                    inputJobDescription.setError("Job Description is mandatory.");
                    isValid = false;
                } else {
                    inputJobDescription.setErrorEnabled(false);
                }

                if (isValid) {
                    if (action.equals("EDIT")) {
                        boolean isSuccessful = databaseHelper.updateJob(jobId, edtJobName.getText().toString(), edtCompany.getText().toString(), edtCompanyAddress.getText().toString(),
                                edtSalary.getText().toString(), autEmploymentType.getText().toString(), edtPhoneNumber.getText().toString(),
                                edtEmail.getText().toString(), "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png" , edtJobDescription.getText().toString());

                        if (isSuccessful) {
                            Toast.makeText(view.getContext(), "Job Information successfully updated.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(view.getContext(), MainActivity.class));
                        } else {
                            Toast.makeText(view.getContext(), "Failed to updated job.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        boolean isSuccessful = databaseHelper.addJob(edtJobName.getText().toString(), edtCompany.getText().toString(), edtCompanyAddress.getText().toString(),
                                edtSalary.getText().toString(), autEmploymentType.getText().toString(), edtPhoneNumber.getText().toString(),
                                edtEmail.getText().toString(), "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1200px-Image_created_with_a_mobile_phone.png" , edtJobDescription.getText().toString());

                        if (isSuccessful) {
                            Toast.makeText(view.getContext(), "New Job successfully added.", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(view.getContext(), MainActivity.class));
                        } else {
                            Toast.makeText(view.getContext(), "Failed to add job.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });

        btnChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });


    }



    public void chooseImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    imgJobPhoto.setImageURI(selectedImageUri);
                }
            }
        }
    }



}

