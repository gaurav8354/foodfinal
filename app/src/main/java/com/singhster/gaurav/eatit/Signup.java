package com.singhster.gaurav.eatit;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.singhster.gaurav.eatit.model.User;

public class Signup extends AppCompatActivity {
    Button signup;
    EditText number,name,password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    idsetter();
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");
signup.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        final ProgressDialog pd=new ProgressDialog(Signup.this);
        pd.setMessage("please wait");
        pd.show();
        table_user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //check if already phone number
                if(dataSnapshot.child(number.getText().toString()).exists()){
                 pd.dismiss();
                    Toast.makeText(Signup.this, "Phone number already exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    pd.dismiss();
                    User user=new User(name.getText().toString(),password.getText().toString());
                    table_user.child(number.getText().toString()).setValue(user);

                    Toast.makeText(Signup.this, "Signin success", Toast.LENGTH_SHORT).show();
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

    private void idsetter() {
        signup= (Button) findViewById(R.id.button_singhup_main);
        number= (EditText) findViewById(R.id.edittext_signup_phone);
        name= (EditText) findViewById(R.id.edittext_signup_name);
        password= (EditText) findViewById(R.id.edittext_signup_pass);
    }
}
