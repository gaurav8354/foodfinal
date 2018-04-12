package com.singhster.gaurav.eatit;

import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.singhster.gaurav.eatit.Common.Common;
import com.singhster.gaurav.eatit.ViewHolder.OrderViewHolder;
import com.singhster.gaurav.eatit.model.Request;

public class OrderStatus extends AppCompatActivity {


    public RecyclerView recyclerView;
    public  RecyclerView.LayoutManager layoutManager;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;


    FirebaseDatabase database;
    DatabaseReference request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);


        database=FirebaseDatabase.getInstance();
        request=database.getReference("Requests");
        recyclerView= (RecyclerView) findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrders(Common.currentuser.getPhone());

    }

    private void loadOrders(String phone) {

    adapter= new FirebaseRecyclerAdapter<Request, OrderViewHolder>(

            Request.class,
            R.layout.orderlayout,
            OrderViewHolder.class,
            request.orderByChild("phone")
            .equalTo(phone)) {


        @Override
        protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
            viewHolder.txtOrderId.setText(adapter.getRef(position).getKey());

            viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
            viewHolder.txtOrderAddress.setText(model.getAddress());
            viewHolder.txtOrderPhone.setText(model.getPhone());
        }
    };
recyclerView.setAdapter(adapter);
    }
    private String convertCodeToStatus(String status) {
        if(status.equals("0"))
            return  "Placed";
        else if(status.equals("1"))
            return "On the way";
        else
            return "Shipped";


    }
}
