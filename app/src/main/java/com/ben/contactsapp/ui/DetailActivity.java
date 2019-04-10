package com.ben.contactsapp.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.ben.contactsapp.R;
import com.ben.contactsapp.data.DataManager;
import com.ben.contactsapp.data.DataManagerImpl;
import com.ben.contactsapp.data.Person;
import com.ben.contactsapp.ui.components.MainPagerAdapter;
import com.ben.contactsapp.utils.Constants;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private ViewPager mainViewPager;
    private MainPagerAdapter adapter;
    private List<Person> personList;
    private DataManager dataManager;
    private int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getIntent() != null && getIntent().hasExtra(Constants.ID)) {
            currentId = getIntent().getIntExtra(Constants.ID, 0);
        }

        mainViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        adapter = new MainPagerAdapter(getSupportFragmentManager());
        dataManager = DataManagerImpl.getInstance();
        personList = dataManager.getData();
        if (personList != null && personList.size() > 0) {
            for (Person elem : personList) {
                adapter.addFragment(DetailFragment.newInstance(elem), elem.getName());
            }
        }
        mainViewPager.setAdapter(adapter);
        mainViewPager.setCurrentItem(currentId);
    }
}
