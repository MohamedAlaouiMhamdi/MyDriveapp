package com.example.myapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class Regestration extends AppCompatActivity {


    private TextInputEditText name,phone,email,passWord;
    private Button next;

     FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
     DatabaseReference userDatabaseRef = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestration);


        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        passWord=findViewById(R.id.passWord);
        next = findViewById(R.id.next);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  nam = name.getText().toString();
                String  num = phone.getText().toString();
                String  mail = email.getText().toString();
                String  pass = passWord.getText().toString();

                Info_helper info_helper = new Info_helper(nam,num,mail,pass);
                ListView listView = findViewById(R.id.listview);

                List<String> list = new ArrayList<>();
                list.add("Driver");
                list.add("User");



                ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1,list);
                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if(position==0){
                            //clicked apple

                            startActivity(new Intent(Regestration.this,page1.class));

                        }else if(position==1){
                            //clicked orange
                            startActivity(new Intent(Regestration.this,page1.class));
                        }else{

                        }
                    }
                });


                System.out.println("hi");




                mAuth.createUserWithEmailAndPassword(mail, pass)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    if (user != null) {
                                        String userId = user.getUid();
                                        //----------------------------  firebase firestore -------------------
                                        db.collection("users").document(userId).set(info_helper);
                                        //--------------------- firebase realtime -------------------------
                                        userDatabaseRef.child("users").child(userId).setValue(info_helper);
                                    }
                                    Toast.makeText(Regestration.this,"successful",Toast.LENGTH_SHORT).show();
                                    Intent nextIntent = new Intent(Regestration.this, firstpage.class);
                                    startActivity(nextIntent);
                                    System.out.println("v");

                                } else {
                                    Toast.makeText(Regestration.this,"echec",Toast.LENGTH_SHORT).show();
                                    System.out.println("faux");
                                }
                            }
                        });
            }

        });





    }
}