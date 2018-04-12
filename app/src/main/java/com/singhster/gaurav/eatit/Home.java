package com.singhster.gaurav.eatit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.singhster.gaurav.eatit.Common.Common;
import com.singhster.gaurav.eatit.Interface.ItemOnClickListner;
import com.singhster.gaurav.eatit.ViewHolder.MenuViewHolder;
import com.singhster.gaurav.eatit.model.Category;
import com.squareup.picasso.Picasso;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView textname;
    RecyclerView recyclermenu;
    RecyclerView.LayoutManager layoutManager;

    private FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);
        //init firebase
        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartintent=new Intent(Home.this,Cart.class);
                startActivity(cartintent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//setuser name

        View handlerView=navigationView.getHeaderView(0);
        textname= (TextView)handlerView.findViewById(R.id.user_name_display);
        textname.setText(Common.currentuser.getName());
//load menu

        recyclermenu= (RecyclerView) findViewById(R.id.recycle_menu);
        recyclermenu.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclermenu.setLayoutManager(layoutManager);



        loadMenu();


    }

    private void loadMenu() {

         adapter =new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {
viewHolder.texMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.imageView);
                final Category clickitem=model;
                viewHolder.setItemClickListner(new ItemOnClickListner() {


                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                     Intent i=new Intent(Home.this,FoodList.class);
                        i.putExtra("CategoryId",adapter.getRef(position).getKey());
                        Toast.makeText(Home.this, adapter.getRef(position).getKey(), Toast.LENGTH_SHORT).show();
                         startActivity(i);
                       // Toast.makeText(Home.this, ""+clickitem.getName(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        recyclermenu.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.cart) {

            Intent cartIntent=new Intent(Home.this,Cart.class);
            startActivity(cartIntent);

        } else if (id == R.id.menu) {

        } else if (id == R.id.order) {
            Intent orderIntent=new Intent(Home.this,OrderStatus.class);
            startActivity(orderIntent);

        } else if (id == R.id.logout) {

            Intent signin=new Intent(Home.this,Signin.class);
            signin.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(signin);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
