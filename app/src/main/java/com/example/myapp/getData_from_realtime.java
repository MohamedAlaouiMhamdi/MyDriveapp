package com.example.myapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class getData_from_realtime extends AppCompatActivity {



    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userId = user.getUid();
    Button next;
    DatabaseReference userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_from_realtime);





        next = findViewById(R.id.next);


        userDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);
                String string1 = user.getEmail();
                String  string2 = user.getName();
                String imageUrl = user.getImageURL();
                TextView nameTextView = findViewById(R.id.name);
                TextView emailTextView = findViewById(R.id.email);


                nameTextView.setText(string1);
                emailTextView.setText(string2);

                ImageView imageView = findViewById(R.id.firebaseimage);


                Glide.with(getData_from_realtime.this)
                        .load(imageUrl)
                        .into(imageView);














            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextIntent = new Intent(getData_from_realtime.this, Search.class);
                startActivity(nextIntent);


            }

        });

    }
}