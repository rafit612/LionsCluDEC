package com.example.lionsclubdec;

import static androidx.core.content.ContextCompat.startActivity;

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

public class ClubActivity extends AppCompatActivity {
    RecyclerView recview;
    clubAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club);

        recview=findViewById(R.id.clubrec);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setItemAnimator(null);
       try {
           FirebaseRecyclerOptions<clubmodel> options =
                   new FirebaseRecyclerOptions.Builder<clubmodel>()
                           .setQuery(FirebaseDatabase.getInstance("https://lions-club-dec-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Club_List"), clubmodel.class)
                           .build();

           adapter=new clubAdapter(options);
           recview.setAdapter(adapter);
       } catch (Exception e) {
           e.printStackTrace();
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
                    Intent intent = new Intent(ClubActivity.this,ClubDetailActivity.class);
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