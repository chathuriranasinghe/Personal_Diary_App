package com.example.personaldiary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaldiary.R;
import com.google.android.material.button.MaterialButton;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class ViewDiary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_diary);

        Button addNewButton = findViewById(R.id.addNewEntry);

        addNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewDiary.this, AddNewEntry.class));
            }
        });

        Realm.init(getApplicationContext());
        //Realm realm = Realm.getDefaultInstance();
        RealmConfiguration newConfig2 = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        // Obtain a Realm instance with the custom configuration
        Realm realm = Realm.getInstance(newConfig2);


        RealmResults<previousEntries> entryList = realm.where(previousEntries.class).findAll().sort("date", Sort.DESCENDING, "time", Sort.DESCENDING);

          RecyclerView recyclerView = findViewById(R.id.recyclerview);
          recyclerView.setLayoutManager(new LinearLayoutManager(this));
          MyAdapter myAdapter = new MyAdapter(getApplicationContext(),entryList);
          recyclerView.setAdapter(myAdapter);

          entryList.addChangeListener(new RealmChangeListener<RealmResults<previousEntries>>() {
              @Override
              public void onChange(RealmResults<previousEntries> previousEntries) {
                 myAdapter.notifyDataSetChanged();
              }
          });
    }
}