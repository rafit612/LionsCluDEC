package com.example.lionsclubdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DailytransActivity extends AppCompatActivity {
    RecyclerView recview;
    clubAdapter adapter;
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dailytrans);
        SimpleDateFormat sdf4 = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDateandTime4 = sdf4.format(new Date());
        pid=getIntent().getStringExtra("cid");
        recview=findViewById(R.id.d_rec);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setItemAnimator(null);
        try {
            FirebaseRecyclerOptions<dailyamodel> options =
                    new FirebaseRecyclerOptions.Builder<dailyamodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_daily_report").child(pid).child(currentDateandTime4), dailyamodel.class)
                            .build();

            adapter=new clubAdapter(options);
            recview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            FirebaseRecyclerOptions<dailyamodel> options =
                    new FirebaseRecyclerOptions.Builder<dailyamodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_daily_report").child(pid).child(currentDateandTime4), dailyamodel.class)
                            .build();

            adapter=new clubAdapter(options);
            recview.setAdapter(adapter);
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
    class clubAdapter extends FirebaseRecyclerAdapter<dailyamodel, clubAdapter.myviewholder> {
        public clubAdapter(@NonNull FirebaseRecyclerOptions<dailyamodel> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull clubAdapter.myviewholder holder, int position, @NonNull dailyamodel model)
        {
            holder.id.setText(model.getID());
            holder.amount.setText(model.getTotal_D());
            holder.time.setText(model.getTime());



        }

        @NonNull
        @Override
        public clubAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_d_y_m,parent,false);
            return new clubAdapter.myviewholder(view);
        }

        class myviewholder extends RecyclerView.ViewHolder
        {

            TextView time,id,amount;
            public myviewholder(@NonNull View itemView)
            {
                super(itemView);


                id=itemView.findViewById(R.id.textView32);
                amount=itemView.findViewById(R.id.textView36);
                time=itemView.findViewById(R.id.textView34);

            }
        }
    }
}