package com.example.user.sijny;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.content.res.ResourcesCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.app.Fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class Announcements extends android.app.Fragment {

    int current = 0;
    int count;
    private ListView lvProduct;
    private SectionListAdapter adapter;
    private List<Section> mProductList;
    ProgressDialog mProgressDialog;
    String theDesc;
    String linkHref;
    Elements noMoreTitles;
    Elements onlyP;
    public ArrayList<String> beerList = new ArrayList<String>();
    public ArrayList<String> beerList2 = new ArrayList<String>();

    public ArrayList<String> linksT = new ArrayList<String>();
    View myView;

    SpannableStringBuilder builder = new SpannableStringBuilder();
    SpannableString str2;
    ClickableSpan clickableSpan;
    private static final String TAG = "Testing: ";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.announcements, container, false);
        new NewThread().execute();
        return myView;
        //if (isOnline()) {
            //new NewThread().execute();

       // }



    }

    public class NewThread extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(getActivity());
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();


            // Show progressdialog

        }
        @Override
        protected String doInBackground(String... arg) {
            mProductList = new ArrayList<>();
            theDesc = new String("");
            linkHref = new String("");

            mProductList.clear();
            Document doc;
            try {
                doc = Jsoup.connect("http://www.sijny.org/").get();

                Elements beer = doc.select("div[class=avia_textblock]");
                Elements theSections = beer.select("section");
                Elements theH4 = beer.select("h4");
                Elements links = beer.select("a");


                for (int j = 0; j < theH4.size(); j++) {
                    beerList.add(theH4.get(j).text());
                }
                for (Element element : theSections.select("h4")) {
                    element.remove();
                }

                noMoreTitles = theSections.select("section");
                onlyP = noMoreTitles.select("p");


                for (org.jsoup.nodes.Element link : links) {
                    linkHref = link.attr("href");
                    linksT.add(linkHref);

                }

                Elements onlySpan = noMoreTitles.select("span");


                for (int j = 1; j < noMoreTitles.size(); j++) {
                    builder.clear();

                    onlyP = noMoreTitles.get(j).select("p");


                    for (int i = 0; i < onlyP.size(); i++) {
                        if(onlyP.get(i).select("a[href]").size() > 0){//
                            if( i > 0) { // skips indenting first
                                theDesc = "\n" + "\n" +theDesc + onlyP.get(i).text();
                            }else{theDesc =  theDesc + onlyP.get(i).text();}

                            str2 = new SpannableString(theDesc);
                            //str2.setSpan(
                                    //new ForegroundColorSpan(Color.RED),
                                    //0,
                                    //str2.length(),
                                   // Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                            str2.setSpan(new myClickableSpan(count),0, str2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            count++;


                            //str2.setSpan(new ForegroundColorSpan(ResourcesCompat.getColor(getResources(), R.color.linkText, null)), 0, str2.length(), 0);

                            builder.append(str2);

                            theDesc = "";


                        }else{
                            theDesc =  theDesc + onlyP.get(i).text() ; //
                            str2 = new SpannableString(theDesc);
                            str2.setSpan(new ForegroundColorSpan(Color.BLACK), 0, str2.length(), 0);
                            builder.append(str2);
                            theDesc = "";
                        }

                    }

                    SpannableString result = SpannableString.valueOf(builder);





                    beerList2.add(theDesc);

                    mProductList.add(new Section(beerList.get(current), result));
                    current++;

                    theDesc = " ";


                    //beerList2.add(onlyP.get(1).text() + "\n" + "\n");
                }




                //for (int f = 0; f < theH4.size(); f++) {

                //mProductList.add(new Section(beerList.get(1), beerList2.get(2)));
                // }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            //Add sample data for list
            //We can get data from DB, webservice here
            //Init adapter

            lvProduct = (ListView) getActivity().findViewById(R.id.listView1);
            adapter = new SectionListAdapter(getActivity().getApplicationContext(), mProductList);
            mProgressDialog.dismiss();
            lvProduct.setAdapter(adapter);



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

    public class myClickableSpan extends ClickableSpan {

        int pos;
        public myClickableSpan(int position){
            this.pos=position;
        }

        @Override
        public void onClick(View widget) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse( linksT.get(pos)));
            startActivity(i);
        }


    }




}