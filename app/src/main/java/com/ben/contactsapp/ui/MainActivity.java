package com.ben.contactsapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ben.contactsapp.R;
import com.ben.contactsapp.data.DataManager;
import com.ben.contactsapp.data.DataManagerImpl;
import com.ben.contactsapp.data.Person;
import com.ben.contactsapp.listeners.ItemClick;
import com.ben.contactsapp.ui.components.PersonAdapter;
import com.ben.contactsapp.utils.Constants;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DataManager.DownloadListener, ItemClick {

    private static final String MAIN_URL = "http://test.php-cd.attractgroup.com/test.json";

    private DrawerLayout drawer;
    private RecyclerView personList;
    private PersonAdapter adapter;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        personList = (RecyclerView) findViewById(R.id.personList);
        adapter = new PersonAdapter();
        adapter.setListener(this);
        dataManager = DataManagerImpl.getInstance();

        personList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        personList.setLayoutManager(layoutManager);
        personList.setAdapter(adapter);

        dataManager.loadData(MAIN_URL, this);
    }

    @Override
    public void dataUpdate(List<Person> personList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.setData(personList);
            }
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.ID, position);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            showMessage("Camera");
        } else if (id == R.id.nav_gallery) {
            showMessage("Gallery");
        } else if (id == R.id.nav_slideshow) {
            showMessage("Slideshow");
        } else if (id == R.id.nav_manage) {
            showMessage("Manage");
        } else if (id == R.id.nav_share) {
            showMessage("Share");
        } else if (id == R.id.nav_send) {
            showMessage("Send");
        }

        if (drawer != null)
            drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
