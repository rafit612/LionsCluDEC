package com.example.lionsclubdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class DailyReportActivity extends AppCompatActivity {
    RecyclerView recview;
    clubAdapter adapter;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_report);
        progressDialog = new ProgressDialog(DailyReportActivity.this);
        progressDialog.setTitle("Authentication");
        progressDialog.setTitle("Checking User");
        progressDialog.setCanceledOnTouchOutside(false);

        recview=findViewById(R.id.clubdaily);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setItemAnimator(null);

        try {
            FirebaseRecyclerOptions<clubmodel> options =
                    new FirebaseRecyclerOptions.Builder<clubmodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List"), clubmodel.class)
                            .build();

            adapter=new clubAdapter(options);
            progressDialog.show();
            recview.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            FirebaseRecyclerOptions<clubmodel> options =
                    new FirebaseRecyclerOptions.Builder<clubmodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List"), clubmodel.class)
                            .build();

            adapter=new clubAdapter(options);
            progressDialog.show();
            recview.setAdapter(adapter);

        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        progressDialog.dismiss();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        progressDialog.dismiss();
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
                    Intent intent = new Intent(DailyReportActivity.this,DailytransActivity.class);
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