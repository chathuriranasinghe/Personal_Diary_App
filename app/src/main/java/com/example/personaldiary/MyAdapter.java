package com.example.personaldiary;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    RealmResults<previousEntries> entryList;

    public MyAdapter(Context context, RealmResults<previousEntries> entryList) {
        this.context = context;
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        previousEntries entry = entryList.get(position);
        holder.titleOutput.setText(entry.getTitle());
       // holder.descriptionOutput.setText(entry.getDescription());

        //String formatedTime = DateFormat.getDateTimeInstance().format(entry.date);
        holder.timeOutput.setText(entry.getDate());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                PopupMenu menu = new PopupMenu(context,v);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("DELETE")){
                            //delete the entry
                            RealmConfiguration newConfig2 = new RealmConfiguration.Builder()
                                    .schemaVersion(1)
                                    .deleteRealmIfMigrationNeeded()
                                    .build();
                            // Obtain a Realm instance with the custom configuration
                            Realm realm = Realm.getInstance(newConfig2);
                            realm.beginTransaction();
                            entry.deleteFromRealm();
                            realm.commitTransaction();
                            Toast.makeText(context, "Deleted Successfully!",Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                });
                menu.show();
                return true;
            }
        });

        holder.itemView.setOnClickListener( new View.OnClickListener(){
            public  void  onClick(View view){

                Intent intent =new Intent(context,ViewDetails.class);
                Log.d("MyAdapter", "Received Title: " + entryList.get(holder.getBindingAdapterPosition()).getTitle());
                intent.putExtra("Title",entryList.get(holder.getBindingAdapterPosition()).getTitle());
                intent.putExtra("Date",entryList.get(holder.getBindingAdapterPosition()).getDate());
                intent.putExtra("Description",entryList.get(holder.getBindingAdapterPosition()).getDescription());
                intent.putExtra("Time",entryList.get(holder.getBindingAdapterPosition()).getTime());
                intent.putExtra("ImageData", entryList.get(holder.getBindingAdapterPosition()).getImageData());

                view.getContext().startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                Toast.makeText(context ,"" ,Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titleOutput;
        //TextView descriptionOutput;
        TextView timeOutput;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            //descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}
