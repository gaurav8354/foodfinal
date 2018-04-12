package com.singhster.gaurav.eatit;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.singhster.gaurav.eatit.Common.Common;
import com.singhster.gaurav.eatit.Database.Database;
import com.singhster.gaurav.eatit.ViewHolder.CartAdapter;
import com.singhster.gaurav.eatit.model.Order;
import com.singhster.gaurav.eatit.model.Request;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import info.hoang8f.widget.FButton;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;
    TextView txtTotalPrice;
    FButton btnPlace;

    List<Order> cart=new ArrayList<>();
  CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //firebase
        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");

        //Init

        recyclerView= (RecyclerView) findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice= (TextView) findViewById(R.id.total);
        btnPlace= (FButton) findViewById(R.id.btn_place_order);


btnPlace.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //request

showAlertDialog();
    }
});

        loadListFood();

    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("One more step");
        alertDialog.setMessage("enter your address");

        final EditText edttext = new EditText(Cart.this);

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(  LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT

        );

        edttext.setLayoutParams(lp);

        alertDialog.setView(edttext);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Request request=new Request(


                        Common.currentuser.getPhone(),
                        Common.currentuser.getName(),
                        edttext.getText().toString(),
                        txtTotalPrice.getText().toString(),cart
                );

                //submit to firebase
                requests.child(String.valueOf(System.currentTimeMillis())).setValue(request);

                //DeleteCart
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Thanks for placing order", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFood() {
cart=new Database(this).getCarts();
        adapter=new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);


        //Calculate total price
        int total=0;
        for(Order order:cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

        Locale locale=new Locale("en","US");
        NumberFormat fmt=NumberFormat.getCurrencyInstance(locale);

        txtTotalPrice.setText(fmt.format(total));


    }
}
