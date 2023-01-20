package com.example.myapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class getDataFromFirestore extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    String userId = user.getUid();
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data_from_firestore);


        next = findViewById(R.id.next);

        // In your activity's onCreate() method
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String string1 = document.getString("name");
                        String string2 = document.getString("email");
                        String imageUrl = document.getString("imageURL");

                        TextView nameTextView = findViewById(R.id.name);
                        TextView emailTextView = findViewById(R.id.email);

                        nameTextView.setText(string1);
                        emailTextView.setText(string2);

                        ImageView imageView = findViewById(R.id.firebaseimage);

                        Glide.with(getDataFromFirestore.this)
                                .load(imageUrl)
                                .into(imageView);


                    } else {
                        System.out.println("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                    }
                } else {
                    System.out.println("cccccccccccccccccccccccccccccccccccccccccccccc");                }
            }
        });




        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent nextIntent = new Intent(getDataFromFirestore.this, getData_from_realtime.class);
                startActivity(nextIntent);


            }

        });


    }
}