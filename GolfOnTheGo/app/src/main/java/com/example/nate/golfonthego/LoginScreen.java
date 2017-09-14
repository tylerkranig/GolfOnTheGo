package com.example.nate.golfonthego;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LoginScreen extends AppCompatActivity {

    Connection conn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        /*try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch(Exception e){
            System.out.println("The class was not found!");
            e.printStackTrace();
            return;
        }

        try{
            conn = DriverManager.getConnection("jdbc:mysql://mysql.cs.iastate.edu:3306/db309amc1","dbu309amc1","XFsBvb1t");
        }
        catch(SQLException e){
            System.out.println("couldn't get the connection ");
            e.printStackTrace();
            return;
        }*/
        new URLTask().execute();
    }

    class URLTask extends AsyncTask<Void,Void,Void>{

        protected void onPreExecute() {
            //display progress dialog.
        }
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                URL url = new URL("http://proj-309-am-c-1.cs.iastate.edu/database.php");
                HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestProperty("Accept-Charset", "UTF-8");
                urlconnection.setConnectTimeout(15000);
                urlconnection.setDoOutput(true);
                urlconnection.setRequestMethod("POST");
                urlconnection.connect();
                // add more code here to send a run request ?
                urlconnection.disconnect();
            }
            catch (Exception e) {
                System.out.println("Couldn't get connection");
                e.printStackTrace();
            }

            return null;
        }
    }

}
