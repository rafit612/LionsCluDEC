package com.example.lionsclubdec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class MonthlyTransActivity extends AppCompatActivity {
    RecyclerView recview;
    clubAdapter adapter;
    String pid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_trans);
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy", Locale.getDefault());
        String currentDateandTime = sdf1.format(new Date());
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM", Locale.getDefault());
        String currentDateandTime2 = sdf2.format(new Date());
        pid=getIntent().getStringExtra("cid");
        recview=findViewById(R.id.m_tran_rec);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setItemAnimator(null);

        try {
            FirebaseRecyclerOptions<monthlymodel> options =
                    new FirebaseRecyclerOptions.Builder<monthlymodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_monthly_report").child(pid).child(currentDateandTime).child(currentDateandTime2), monthlymodel.class)
                            .build();

            adapter=new clubAdapter(options);
            recview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            FirebaseRecyclerOptions<monthlymodel> options =
                    new FirebaseRecyclerOptions.Builder<monthlymodel>()
                            .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_monthly_report").child(pid).child(currentDateandTime).child(currentDateandTime2), monthlymodel.class)
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
    class clubAdapter extends FirebaseRecyclerAdapter<monthlymodel, clubAdapter.myviewholder> {
        public clubAdapter(@NonNull FirebaseRecyclerOptions<monthlymodel> options) {
            super(options);
        }

        @Override
        protected void onBindViewHolder(@NonNull clubAdapter.myviewholder holder, int position, @NonNull monthlymodel model)
        {
            holder.id.setText(model.getID());
            holder.amount.setText(model.getTotal_D());
            holder.time.setText(model.getTime());
            holder.date.setText(model.getDate());



        }

        @NonNull
        @Override
        public clubAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.trans_y_m,parent,false);
            return new clubAdapter.myviewholder(view);
        }

        class myviewholder extends RecyclerView.ViewHolder
        {

            TextView time,id,amount,date;
            public myviewholder(@NonNull View itemView)
            {
                super(itemView);


                id=itemView.findViewById(R.id.textView38);
                amount=itemView.findViewById(R.id.textView44);
                time=itemView.findViewById(R.id.textView42);
                date=itemView.findViewById(R.id.textView40);

            }
        }
    }
}