package com.example.lionsclubdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class YearlyReportActivity extends AppCompatActivity {
    RecyclerView recview;
   clubAdapter adapter;
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yearly_report);
        recview=findViewById(R.id.y_rec);
        try {
        recview.setLayoutManager(new LinearLayoutManager(this));
            recview.setItemAnimator(null);



            FirebaseRecyclerOptions<clubmodel> options =
                    new FirebaseRecyclerOptions.Builder<clubmodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List"), clubmodel.class)
                            .build();

            adapter=new clubAdapter(options);
            recview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            FirebaseRecyclerOptions<clubmodel> options =
                    new FirebaseRecyclerOptions.Builder<clubmodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List"), clubmodel.class)
                            .build();

            adapter=new clubAdapter(options);
            recview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    class clubAdapter extends FirebaseRecyclerAdapter<clubmodel, clubAdapter.myviewholder> {
        public clubAdapter(@NonNull FirebaseRecyclerOptions<clubmodel> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull clubAdapter.myviewholder holder, int position, @NonNull clubmodel model)
        {
            holder.name.setText(model.getClub());

            holder.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cc= model.getCode();
                    Intent intent = new Intent(YearlyReportActivity.this,MonthlyTransActivity.class);
                    intent.putExtra("cid",cc);


                    startActivity(intent);
                }
            });

        }

        @NonNull
        @Override
        public clubAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.clubitem,parent,false);
            return new clubAdapter.myviewholder(view);
        }

        class myviewholder extends RecyclerView.ViewHolder
        {

            TextView name;
            public myviewholder(@NonNull View itemView)
            {
                super(itemView);

                name=itemView.findViewById(R.id.textView10);

            }
        }
    }
}