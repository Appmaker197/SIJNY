package com.example.user.sijny;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;




public class Prayers extends Fragment {
    View myView;
    String reportDate;
    static final String url = "https://www.sijny.org/";
    ArrayList<String> parsedTime = new ArrayList<>();
    ArrayList<String> parsedPrayerType = new ArrayList<>();
    ProgressDialog mProgressDialog;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.second_layout, container, false);
        getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if(isOnline()) {
            new Title().execute();
        }
        else{openDialog();}
            return myView;
    }

    private class Title extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                    // Connect to the web site
                    Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                            .timeout(30000)
                            .get();
                    // Get the html document title
                    Elements prayerTable = document.select("table[class=dptTimetable  customStyles dptUserStyles]");
                    Elements prayerTimes = prayerTable.select("td");
                    Elements prayerName = prayerTable.select("th");
                for (int i = 0; i < prayerTimes.size(); i++) {
                    parsedTime.add(prayerTimes.get(i).text());
                    for (int j = 3; j < prayerName.size(); j++) {
                        parsedPrayerType.add(prayerName.get(j).text());
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set title into TextView
            SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d, yyyy", Locale.ENGLISH);
            Date today = Calendar.getInstance().getTime();
            reportDate = df.format(today);

            TextView theTime = (TextView) getActivity().findViewById(R.id.theT);
            theTime.setText(reportDate);

            TextView fjrTime = (TextView) getActivity().findViewById(R.id.fajTime);
            fjrTime.setText(parsedTime.get(0));

            TextView fajr_Time = (TextView) getActivity().findViewById(R.id.fajr1);
            fajr_Time.setText(parsedPrayerType.get(0));

            TextView suntime = (TextView) getActivity().findViewById(R.id.sunTime);
            suntime.setText(parsedTime.get(1));
            TextView sunRiseTime = (TextView) getActivity().findViewById(R.id.sunrise);
            sunRiseTime.setText(parsedPrayerType.get(1));

            TextView zhurtime = (TextView) getActivity().findViewById(R.id.zurTime);
            zhurtime.setText(parsedTime.get(2));
            TextView zhur_Time = (TextView) getActivity().findViewById(R.id.zuhr1);
            zhur_Time.setText(parsedPrayerType.get(2));

            TextView magtime = (TextView) getActivity().findViewById(R.id.magTime);
            magtime.setText(parsedTime.get(3));
            TextView mag_Time = (TextView) getActivity().findViewById(R.id.mag1);
            mag_Time.setText(parsedPrayerType.get(3));

            mProgressDialog.dismiss();
        }


    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void openDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("No Internet Connection!");
        alertDialog.setMessage("This app needs an internet connection to function!");
        alertDialog.setButton(Dialog.BUTTON_NEGATIVE,"OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                System.exit(0);
            }
        });
        alertDialog.show();
    }
}


