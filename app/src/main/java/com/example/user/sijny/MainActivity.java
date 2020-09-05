package com.example.user.sijny;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
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

import org.joda.time.Chronology;
import org.joda.time.LocalDate;
import org.joda.time.chrono.IslamicChronology;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            // on first time to display view for first navigation item based on the number
            onSectionAttached(4); // 2 is your fragment's number for "CollectionFragment"
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView = navigationView.getHeaderView(0);
        TextView theTime = (TextView) navHeaderView.findViewById(R.id.theT);
        TextView thehij = (TextView) navHeaderView.findViewById(R.id.hij);

        SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH);
        Date today = Calendar.getInstance().getTime();
        String reportDate = df.format(today);
        theTime.setText(reportDate);

        // islamic
        Chronology hijri = IslamicChronology.getInstanceUTC();
        LocalDate todayIso = new LocalDate(hijri);

        String wholeIslmaic = todayIso.toString();
        int posOfHyphen = wholeIslmaic.indexOf("-");

        //Year
        String islamicYear = wholeIslmaic.substring(0, posOfHyphen);
        //Month
        String temp = wholeIslmaic.substring(posOfHyphen + 1);
        String islamicMonth = temp.substring(0, posOfHyphen - 2);
        //Day
        int posOfSecHyphen = temp.indexOf("-");
        String islamicDay = temp.substring(posOfSecHyphen + 1);


        //Months Condition
        if (islamicMonth.equals("01")) {
            String charislamicMonth = new String("Muharram");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("02")) {
            String charislamicMonth = new String("Safar");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("03")) {
            String charislamicMonth = new String("Rabi’ I");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("04")) {
            String charislamicMonth = new String("Rabi’ II");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("05")) {
            String charislamicMonth = new String("Jumada");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("06")) {
            String charislamicMonth = new String("Jumada II");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("07")) {
            String charislamicMonth = new String("Rajab");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("08")) {
            String charislamicMonth = new String("Sha'ban");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("09")) {
            String charislamicMonth = new String("Ramadan");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("10")) {
            String charislamicMonth = new String("Shawwal");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("11")) {
            String charislamicMonth = new String("al-Qi'dah");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        } else if (islamicMonth.equals("12")) {
            String charislamicMonth = new String("al-Hijjah");
            thehij.setText(islamicDay + " " + charislamicMonth + ", " + islamicYear + " H");
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
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
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_first_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Announcements())
                    .commit();
        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new Prayers())
                    .commit();
        } else if (id == R.id.nav_third_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame
                            , new EventsController())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onSectionAttached(int number) {
        // update the main content by replacing fragments
        Fragment fragment = null;

        switch (number) {
            case 1:
                mTitle = "Home";
                break;
            case 2:
                mTitle = "Prayer Times";
                fragment = new Prayers();
                break;
            case 3:
                mTitle = "My Display";
                fragment = new EventsController();
                break;
            case 4:
                mTitle = "Push";
                fragment = new PushM();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment).commit();

        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


}
