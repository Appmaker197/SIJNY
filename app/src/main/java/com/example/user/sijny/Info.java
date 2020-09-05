package com.example.user.sijny;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Info extends Activity {

    TextView thePtag;
    TextView times;
    TextView tit;
    TextView dayT;
    Bitmap bitmap;
    TextView night;
    TextView theSpeaker;
    Element theImage;
    Element theparagraph;

    Elements theP;


    ProgressDialog mProgressDialog;
    ImageView logoimg;
    String imgSrc;
    String pSrc;
    String linkHref;
    String url;
    String pTxt;
    String theTitle;
    String theTime;
    String theStrong;
    String theTable;
    String theSpk;
    String modString;
    String theSpkFinal;
    int posOfH;
    EventsController theThing;

    String paraMain;

    public ArrayList<String> links = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        theThing = new EventsController();
        url = theThing.getLink();
        new NewThread().execute();

    }

    public class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Info.this);
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }
        @Override
        protected String doInBackground(String... arg) {
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                        .timeout(30000)
                        .get();
                // Get the html document title
                Elements centerAll = document.select("div[class=av-single-event-content]");
                Elements theSections = centerAll.select("span"); //Sections
                Elements bigTexts = centerAll.select("h2"); //Sections
                Elements theSpeakers = centerAll.select("li"); //Sections
                Elements bigBodys = centerAll.select("strong"); //Sections
                Elements bigBodys2 = centerAll.select("tbody"); //Sections
                Elements theEvents = bigBodys2.select("td"); //Sections
                theP = centerAll.select("p"); //Sections

                if(centerAll.select("img").size() > 0)
                {
                    // then do this
                    theImage = centerAll.select("img").first(); //Sections
                    imgSrc = theImage.attr("src");
                    // Download image from URL
                    InputStream input = new java.net.URL(imgSrc).openStream();
                    // Decode Bitmap
                    bitmap = BitmapFactory.decodeStream(input);
                }

                if(centerAll.select("p").size() > 0)
                {
                    theparagraph = centerAll.select("p").first(); //Sections
                    pSrc = theparagraph.attr("src");


                    paraMain = new  String("");
                    // then do this

                    Elements  theLinks = theP.select("a");

                    for (Element para: theP) {
                        pTxt = para.text();
                        paraMain = paraMain + pTxt + "\n" + "\n";
                    }

                    for (org.jsoup.nodes.Element theLink : theLinks) {
                        linkHref = theLink.attr("href");
                        links.add(linkHref);

                    }

                }


                theTitle = new String("");
                theTime = new String("");
                theTable = new String("");
                theStrong = new String("");
                theSpk = new String("");
                theSpkFinal = new String("");




                for (Element bigText : bigTexts) {
                    theTitle = bigText.text();

                }

                if (theSections.size() == 2) {
                    for (int i = 0; i < theSections.size(); i++) {
                        theTime = theTime + " - " + theSections.get(i).text();
                    }
                } else {
                    for (int i = 0; i < theSections.size() - 1; i++) {
                        theTime = theTime + " - " + theSections.get(i).text();
                    }
                    posOfH = theTime.indexOf('-');
                    theSpkFinal = theTime.substring(posOfH + 2);
                    theTime = theSpkFinal;
                }

                for (Element bigBody : bigBodys) {
                    theStrong = bigBody.text();

                }

                for (Element theEvent : theEvents.select("strong")) {
                    theEvent.remove();
                }

                for (Element theEvent : theEvents) {
                    theTable = theTable + theEvent.text() + "\n" + "\n";
                }


                for (Element theSpeaker : theSpeakers) {
                    theSpk = theSpeaker.text();
                }

                if (theSpk.contains("/li>")) {
                    modString = new String("");
                    modString = theSpk.substring(0, theSpk.length() - 4);
                    theSpk = modString;
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //Add sample data for list
            //We can get data from DB, webservice here
            //Init adapte

            times = (TextView) findViewById(R.id.sch);
            tit = (TextView) findViewById(R.id.title);
            dayT = (TextView) findViewById(R.id.time);
            night = (TextView) findViewById(R.id.date);
            theSpeaker = (TextView) findViewById(R.id.spk);
            thePtag =  (TextView) findViewById(R.id.ptag);

            logoimg = (ImageView) findViewById(R.id.image1);
            logoimg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String url = imgSrc;

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });


            thePtag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String url = links.get(0);

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            logoimg.setImageBitmap(bitmap);
            thePtag.setText(paraMain);




            times.setText(theTable);
            tit.setText(theTitle);
            dayT.setText(theSpkFinal);
            night.setText(theStrong);
            theSpeaker.setText(theSpk);

            mProgressDialog.dismiss();

        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }







}