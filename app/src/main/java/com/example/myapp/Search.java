package com.example.myapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Search extends AppCompatActivity {


    private Button search;
    private TextInputEditText email;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        search = findViewById(R.id.search);
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);



        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mail = email.getText().toString();
                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference("users");


                Query searchQuery = rootRef.orderByChild("email").equalTo(mail);


                searchQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // The query returned at least one result
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                // TODO: process the user data
                                User user = userSnapshot.getValue(User.class);
                                String nam = user.getName();
                                name.setText(nam);
                            }
                        } else {
                            // The query returned no results
                            // TODO: do something
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // TODO: handle the error
                    }
                });



            }

        });




    }
}