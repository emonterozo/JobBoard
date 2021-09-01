package com.eqmonterozo.jobboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private  static final String TABLE_NAME = "jobs";
    private static  final String id = "id";
    private static  final String job_title = "job_title";
    private static  final String company_name = "company_name";
    private static  final String company_address = "company_address";
    private static  final String salary_range = "salary_range";
    private static  final String employment_type = "employment_type";
    private static  final String phone_number = "phone_number";
    private static  final String email_address = "email_address";
    private static  final String job_photo = "job_photo";
    private static  final String job_description = "job_description";
    private static  final String elapsed_time = "elapsed_time";

    public  DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" + id + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                job_title + " TEXT," +
                company_name + " TEXT," +
                company_address + " TEXT," +
                salary_range + " TEXT," +
                employment_type + " TEXT," +
                phone_number + " TEXT," +
                email_address + " TEXT," +
                job_photo + " TEXT," +
                job_description + " TEXT," +
                elapsed_time + " DEFAULT CURRENT_TIMESTAMP)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addJob(String jobTitle, String companyName, String companyAddress,
                          String salaryRange, String employmentType, String phoneNumber,
                          String emailAddress, String jobPhoto, String jobDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(job_title, jobTitle);
        contentValues.put(company_name, companyName);
        contentValues.put(company_address, companyAddress);
        contentValues.put(salary_range, salaryRange);
        contentValues.put(employment_type, employmentType);
        contentValues.put(phone_number, phoneNumber);
        contentValues.put(email_address, emailAddress);
        contentValues.put(job_photo, jobPhoto);
        contentValues.put(job_description, jobDescription);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getJobs() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public boolean deleteJob(String jobId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, id + "=?", new String[]{jobId});
        return result != -1;
    }

    public boolean updateJob(String jobId, String jobTitle, String companyName, String companyAddress, String salaryRange, String employmentType,
                          String phoneNumber, String emailAddress, String jobPhoto, String jobDescription) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(job_title, jobTitle);
        contentValues.put(company_name, companyName);
        contentValues.put(company_address, companyAddress);
        contentValues.put(salary_range, salaryRange);
        contentValues.put(employment_type, employmentType);
        contentValues.put(phone_number, phoneNumber);
        contentValues.put(email_address, emailAddress);
        contentValues.put(job_photo, jobPhoto);
        contentValues.put(job_description, jobDescription);
        long result = db.update(TABLE_NAME, contentValues, "id = ?", new String[]{jobId});
        return result != -1;
    }
}
