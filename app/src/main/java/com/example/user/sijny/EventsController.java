package com.example.user.sijny;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/31/15.
 */
public class EventsController extends Fragment {

    View myView;

    private ListView lvProduct;
    private EventsListAdapter adapter;
    private List<Events> mProductList;
    ProgressDialog mProgressDialog;
    String linkHref;

    TextView example;

    public static String link;

    public ArrayList<String> beerList = new ArrayList<String>();
    public ArrayList<String> beerList2 = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.events, container, false);
        new NewThread().execute();
        return myView;
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
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected String doInBackground(String... arg) {
            mProductList = new ArrayList<>();
            mProductList.clear();
            Document doc;
            try {
                doc = Jsoup.connect("http://www.sijny.org/").get();
                linkHref = new String("");
                link = new String("");
                Elements beer = doc.select("div[class=av-upcoming-events  avia-builder-el-22  el_after_av_hr  avia-builder-el-last]");
                Elements theH4 = beer.select("h4");
                Elements links = beer.select("a"); //Sections

                for (int j = 0; j < theH4.size(); j++) {
                    beerList.add(theH4.get(j).text());
                }


                for (org.jsoup.nodes.Element link : links) {
                    linkHref = link.attr("href");
                    beerList2.add(linkHref);

                }


                for (int f = 0; f < theH4.size(); f++) {
                    mProductList.add(new Events(beerList.get(f), beerList2.get(f)));
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
            //Init adapter
            lvProduct = (ListView) getActivity().findViewById(R.id.events1);

            adapter = new EventsListAdapter(getActivity(), mProductList);
            mProgressDialog.dismiss();
            lvProduct.setAdapter(adapter);

            lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Do something
                    //Ex: display msg with product id get from view.getTag
                    //oast.makeText(getActivity(), beerList2.get(position), Toast.LENGTH_SHORT).show();

                    link = beerList2.get(position);

                    Intent myIntent = new Intent(getActivity(), Info.class);
                    getActivity().startActivity(myIntent);

                }
            });


        }

        //public boolean isOnline() {
        //ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        //NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //if (netInfo != null && netInfo.isConnectedOrConnecting()) {
        //return true;
        //}
        //return false;
        // }


    }

    public String getLink() {

        return link;
    }
}

