package com.example.dell.otp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class SentFragment extends Fragment {

    private String TAG = "Sent Fragment";
     ListView lv;
  static ArrayList<HashMap<String, String>> contactList;
    ProgressDialog progressDialog;
     HashMap<String, String> contact;


    ListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sent, container, false);
        lv = (ListView) rootView.findViewById(R.id.list1);

         contact=new HashMap<>();
        contactList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(Environment.getExternalStorageDirectory()+"/hello.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            contactList = (ArrayList<HashMap<String, String>>) ois.readObject();
            Collections.reverse(contactList);
            ois.close();
        } catch(Exception e) {
            e.printStackTrace();
        }



        adapter = new SimpleAdapter(getActivity(), contactList,
                R.layout.listitemsent, new String[]{ "name","mobile","time"},
                new int[]{R.id.textView4, R.id.textView6,R.id.textView5});

      if (adapter!=null)
        lv.setAdapter(adapter);
        return rootView;
    }
    @Override
    public void onResume() {
        Log.e("DEBUG", "onResume of HomeFragment");



        super.onResume();



    }


}



