package com.example.foodiehub;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import model.users;


public class SignUp extends AppCompatActivity {
    MaterialEditText editPhone,editName,editpassword;
    Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editName =  findViewById(R.id.editName);
        editpassword =  findViewById(R.id.editPassword);
        editPhone =  findViewById(R.id.editPhone);

        signUpButton=  findViewById(R.id.signUpButton);

//        FirebaseDatabase.getInstance().getReference().setValue("susu son");

        final DatabaseReference table_user = FirebaseDatabase.getInstance().getReference().child("Users");

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("please wait");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Log.d("NUMBER",snapshot.getKey());
                        }
                      if(dataSnapshot.child(editPhone.getText().toString()).exists()){
                          mDialog.dismiss();
                          Toast.makeText(SignUp.this,"user exists",Toast.LENGTH_SHORT).show();
                      }else{
                          mDialog.dismiss();
                          users user = new users(editName.getText().toString(),editpassword.getText().toString());

                          table_user.child(editPhone.getText().toString()).setValue(user);
                          Toast.makeText(SignUp.this,"signup success",Toast.LENGTH_SHORT).show();
                          finish();

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