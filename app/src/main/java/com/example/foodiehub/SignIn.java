package com.example.foodiehub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import common.Common;
import model.users;

public class SignIn extends AppCompatActivity {

    EditText editPhone;
    EditText editPassword;
    Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editPassword= (MaterialEditText) findViewById(R.id.editPassword);
        editPhone = (MaterialEditText) findViewById(R.id.editPhone);
        signIn = (Button) findViewById(R.id.signInButton);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("Users");

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                mDialog.setMessage("please wait");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(editPhone.getText().toString()).exists()) {

                            mDialog.dismiss();
                            users user = dataSnapshot.child(editPhone.getText().toString()).getValue(users.class);
                            if (user.getPassword().equals(editPassword.getText().toString())) {
                                Intent homeIntent = new Intent(SignIn.this,Home.class);
                                Common.currentUser = user;
                                startActivity(homeIntent);
                                finish();

                            } else {
                                Toast.makeText(SignIn.this, "sign in failed", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignIn.this,"user doesnt exist",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


    }
}