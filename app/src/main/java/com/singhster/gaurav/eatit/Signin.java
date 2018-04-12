package com.singhster.gaurav.eatit;
import android.app.ProgressDialog;
import android.content.Intent;
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
import com.singhster.gaurav.eatit.Common.Common;
import com.singhster.gaurav.eatit.model.User;

public class Signin extends AppCompatActivity {
EditText pnum,pname;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
   idgetter();

        FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference table_user=database.getReference("User");

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd=new ProgressDialog(Signin.this);
                pd.setMessage("please wait");
                pd.show();
                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //check if user did not exist
if(dataSnapshot.child(pnum.getText().toString()).exists()) {

//user info
    pd.cancel();
    User user = dataSnapshot.child(pnum.getText().toString()).getValue(User.class);
   user.setPhone(pnum.getText().toString()); //phone

    if (user.getPassword().equals(pname.getText().toString())) {

        Intent i=new Intent(Signin.this,Home.class);
        Common.currentuser=user;
        startActivity(i);
        finish();
    } else {
        Toast.makeText(Signin.this, "signin failed", Toast.LENGTH_SHORT).show();
    }
}
else{
    Toast.makeText(Signin.this, "user not exist in database", Toast.LENGTH_SHORT).show();
    pd.cancel();
}

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    private void idgetter() {
    pnum= (EditText) findViewById(R.id.edittext_phone);
        pname= (EditText) findViewById(R.id.edittext_name);
        signup=(Button)findViewById(R.id.button_singhup_main);

    }


}
