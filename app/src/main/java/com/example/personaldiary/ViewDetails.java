package com.example.personaldiary;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ViewDetails extends AppCompatActivity {

    String titleView;
    String dateView;
    String timeView;
    String descriptionView;
    byte[] imageData;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_details);

        TextView title = findViewById(R.id.viewTitle);
        TextView date = findViewById(R.id.viewDate);
        TextView time = findViewById(R.id.viewTime);
        TextView description = findViewById(R.id.viewDescription);
        ImageView image = findViewById(R.id.seeImage);

        Intent intent = getIntent();
        titleView =intent.getStringExtra("Title");
        dateView=intent.getStringExtra("Date");
        timeView =intent.getStringExtra("Time");
        descriptionView =intent.getStringExtra("Description");
        imageData = intent.getByteArrayExtra("ImageData");
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
        Log.d("DetailActivity", "Received Title: " + titleView);
        Log.d("DetailActivity", "Received Date: " + dateView);
        Log.d("DetailActivity", "Received Time: " + timeView);
        Log.d("DetailActivity", "Received Content: " + descriptionView);
        Log.d("DetailActivity", "Received Image: "+ imageData);
        title.setText( titleView);
        date .setText(  dateView);
        time.setText( timeView);
        description.setText( descriptionView);
        image.setImageBitmap(bitmap);
    }
}