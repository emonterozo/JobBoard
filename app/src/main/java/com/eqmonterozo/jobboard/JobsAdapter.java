package com.eqmonterozo.jobboard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.ViewHolder> {

    private Context context;
    private ArrayList job_id, job_title, company_name, company_address, salary_range, employment_type, phone_number, email_address, job_photo, job_description, elapsed_time;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private DatabaseHelper databaseHelper;

    public JobsAdapter(Context context,
                       DatabaseHelper databaseHelper,
                       ArrayList job_id,
                       ArrayList job_title,
                       ArrayList company_name,
                       ArrayList company_address,
                       ArrayList salary_range,
                       ArrayList employment_type,
                       ArrayList phone_number,
                       ArrayList email_address,
                       ArrayList job_photo,
                       ArrayList job_description,
                       ArrayList elapsed_time) {
        this.context = context;
        this.databaseHelper = databaseHelper;
        this.job_id = job_id;
        this.job_title = job_title;
        this.company_name = company_name;
        this.company_address = company_address;
        this.salary_range = salary_range;
        this.employment_type = employment_type;
        this.phone_number = phone_number;
        this.email_address = email_address;
        this.job_photo = job_photo;
        this.job_description = job_description;
        this.elapsed_time = elapsed_time;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtJobName.setText(String.valueOf(job_title.get(position)));
        holder.txtJobLocation.setText(String.valueOf(company_address.get(position)));
        holder.txtJobSalary.setText(String.valueOf(salary_range.get(position)));
        holder.txtEmploymentType.setText(String.valueOf(employment_type.get(position)));
        holder.txtContact.setText(String.valueOf(phone_number.get(position)));
        Glide.with(context).load(String.valueOf(job_photo.get(position))).into(holder.imgViewJob);
        holder.txtElapsedTime.setText(String.valueOf(elapsed_time.get(position)));

        String jobId = String.valueOf(job_id.get(position));
        String jobTitle = String.valueOf(job_title.get(position));
        String company = String.valueOf(company_name.get(position));
        String companyAddress = String.valueOf(company_address.get(position));
        String salary = String.valueOf(salary_range.get(position));
        String employmentType = String.valueOf(employment_type.get(position));
        String phoneNumber = String.valueOf(phone_number.get(position));
        String email = String.valueOf(email_address.get(position));
        String jobPhoto = String.valueOf(job_photo.get(position));
        String jobDescription = String.valueOf(job_description.get(position));
        String elapsedTime = String.valueOf(elapsed_time.get(position));

        viewBinderHelper.bind(holder.swipeJobItemLayout, String.valueOf(job_id.get(position)));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), JobActivity.class);
                intent.putExtra("action", "EDIT");
                intent.putExtra("jobId",jobId);
                intent.putExtra("jobTitle", jobTitle);
                intent.putExtra("company", company);
                intent.putExtra("companyAddress", companyAddress);
                intent.putExtra("salary", salary);
                intent.putExtra("employmentType", employmentType);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("email", email);
                intent.putExtra("jobDescription", jobDescription);
                intent.putExtra("jobPhoto", jobPhoto);
                intent.putExtra("elapsedTime", elapsedTime);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MaterialAlertDialogBuilder(context)
                        .setMessage("Delete this job?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                boolean isSuccess = databaseHelper.deleteJob(jobId);
                                if (isSuccess) {
                                    Toast.makeText(holder.itemView.getContext(), "Job successfully deleted.", Toast.LENGTH_LONG).show();
                                    job_id.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                } else {
                                    Toast.makeText(holder.itemView.getContext(), "Failed to delete job.", Toast.LENGTH_LONG).show();
                                }


                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .show();
            }
        });

        holder.cardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), JobDetailsActivity.class);
                intent.putExtra("jobTitle", jobTitle);
                intent.putExtra("elapsedTime", elapsedTime);
                intent.putExtra("company", company);
                intent.putExtra("companyAddress", companyAddress);
                intent.putExtra("salary", salary);
                intent.putExtra("employmentType", employmentType);
                intent.putExtra("jobDescription", jobDescription);
                intent.putExtra("jobPhoto", jobPhoto);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return job_id.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtJobName, txtJobLocation, txtJobSalary, txtEmploymentType, txtContact, txtElapsedTime;
        private MaterialButton btnEdit, btnDelete;
        private ImageView imgViewJob;
        private MaterialCardView cardItem;
        private SwipeRevealLayout swipeJobItemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtJobName = itemView.findViewById(R.id.txtJobName);
            txtJobLocation = itemView.findViewById(R.id.txtJobLocation);
            txtJobSalary = itemView.findViewById(R.id.txtJobSalary);
            txtEmploymentType = itemView.findViewById(R.id.txtEmploymentType);
            txtContact = itemView.findViewById(R.id.txtContact);
            imgViewJob = itemView.findViewById(R.id.imgViewJob);
            txtElapsedTime = itemView.findViewById(R.id.txtElapsedTime);
            cardItem = itemView.findViewById(R.id.cardItem);
            swipeJobItemLayout = itemView.findViewById(R.id.swipeJobItemLayout);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);



        }
    }


}
