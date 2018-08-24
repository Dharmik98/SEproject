package com.example.dharmik.seproject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class FacultyActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    FirebaseAuth mAuth3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_faculty);
        setSupportActionBar(toolbar);

        Bundle extras = getIntent().getExtras();
        String uName = extras.getString("UserName");
        String uEmail = extras.getString("UserEmail");
        String uri = extras.getString("UserPhoto");
        Uri uPhoto = Uri.parse(uri);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_faculty);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_faculty);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_faculty);
        View headerView  = navigationView.getHeaderView(0);
        //set USerPhoto
        ImageView UserPhoto = headerView.findViewById(R.id.header_pic_faculty);
        UserPhoto.setImageURI(uPhoto);

        //set UserName
        TextView userName = headerView.findViewById(R.id.header_text_faculty_name);
        userName.setText(uName);

        //set UserEmail
        TextView userEmail = headerView.findViewById(R.id.header_email_faculty);
        userEmail.setText(uEmail);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_faculty);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.faculty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings_faculty) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_faculty_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_faculty_gallery) {

        } else if (id == R.id.nav_faculty_slideshow) {

        } else if (id == R.id.nav_faculty_manage) {

        } else if (id == R.id.nav_faculty_share) {

        } else if (id == R.id.nav_faculty_logout) {

            mAuth3 = FirebaseAuth.getInstance();
            mAuth3.signOut();
            Intent i  =new Intent(this,SignInActivity.class);
            startActivity(i);
            finish();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_faculty);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
