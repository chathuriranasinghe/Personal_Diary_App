package com.example.personaldiary;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AddNewEntry extends AppCompatActivity {

    int year;
    int month;
    int date;
    String finalDate;
    String finalTime;
    EditText defaultdate;
    EditText time;
    //private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;
    private Uri imageUri;
    byte[] imageData;
    private ImageView seeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_new_entry);

        defaultdate = findViewById(R.id.editTextDate);
        time = findViewById(R.id.editTextTime);
        // diaryContainer = findViewById(R.id.diaryContainer);
        EditText titleInput = findViewById(R.id.titleinput);
        ImageButton imageButton3 = findViewById(R.id.imageButton3);
        EditText descriptionInput = findViewById(R.id.descriptioninput);
        Button saveButton = findViewById(R.id.savebutton);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());

        defaultdate.setText(currentDate);
        time.setText(currentTime);

        Realm.init(getApplicationContext());
        //Realm realm = Realm.getDefaultInstance();
        RealmConfiguration newConfig2 = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()  // Delete existing Realm if migration is needed
                .build();
        Realm realm = Realm.getInstance(newConfig2);

        imageButton3.setOnClickListener(view -> selectImage());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleInput.getText().toString();
                String description = descriptionInput.getText().toString();
                //long createdTime = System.currentTimeMillis();
                finalDate = defaultdate.getText().toString();
                finalTime = time.getText().toString();

                realm.beginTransaction();
                previousEntries entry = realm.createObject(previousEntries.class);
                entry.setTitle(title);
                entry.setImageData(imageData);
                entry.setDescription(description);
                //entry.setCreatedTime(createdTime);
                entry.setDate(finalDate);
                entry.setTime(finalTime);
                realm.commitTransaction();
                Toast.makeText(getApplicationContext(), "Saved Successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddNewEntry.this, ViewDiary.class);
                startActivity(intent);
            }
        });

    }

    ;

    public void setDateManual(View view) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                defaultdate.setText(String.valueOf(day) + "-" + String.valueOf(month) + "-" + String.valueOf(year));
                finalDate = defaultdate.getText().toString();
            }
        }, year, month, date);

        // Show the DatePickerDialog
        dialog.show();
        //copy from youtube

    }

    public void setTimeManual(View view) {
        final Calendar c = Calendar.getInstance();

        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewEntry.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time.setText(hourOfDay + ":" + minute);
                    }
                }, hour, minute, false);

        timePickerDialog.show();
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK && data != null) {
            handleImage(data);
        }
    }

    private void handleImage(Intent data) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
            imageData = convertBitmapToByteArray(bitmap);
            //Bitmap bitmapveiw = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            ImageView imageView = findViewById(R.id.seeImage);
            imageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private byte[] convertBitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        // Determine the format based on the bitmap's config
        Bitmap.CompressFormat format;
        if (bitmap.hasAlpha()) {
            format = Bitmap.CompressFormat.PNG;
        } else {
            format = Bitmap.CompressFormat.JPEG;
        }

        bitmap.compress(format, 100, stream);
        return stream.toByteArray();
    }

}