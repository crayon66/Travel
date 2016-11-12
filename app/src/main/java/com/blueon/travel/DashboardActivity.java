package com.blueon.travel;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.blueon.library.UserFunctions;


public class DashboardActivity extends Activity {
   UserFunctions userFunctions;
   Button btnLogout;
   Button btnUpdate;
   @Override
   public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);

       /**
        * Dashboard Screen for the application
        * */
       // Check login status in database
       userFunctions = new UserFunctions();
       if(userFunctions.isUserLoggedIn(getApplicationContext())){
      // user already logged in show databoard
           setContentView(R.layout.dashboard);
           btnLogout = (Button) findViewById(R.id.btnLogout);
           btnUpdate=  (Button) findViewById(R.id.btnUpdate);
         
         
           
           btnLogout.setOnClickListener(new View.OnClickListener() {

               public void onClick(View arg0) {
            	   new Dashboard().execute(); 
               }
           });
           
           btnUpdate.setOnClickListener(new View.OnClickListener() {

               public void onClick(View view) {
            	   new Update().execute();
               }
           });
   
           
       }else{
           // user is not logged in show login screen
           Intent login = new Intent(getApplicationContext(), LoginActivity.class);
           login.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
           startActivity(login);
           // Closing dashboard screen
           finish();
       }
   }
   
   ProgressDialog progress;
   class Dashboard extends AsyncTask<String, String, String> {
   	  
   	  @Override
   	  protected void onPreExecute() {  
   	   // TODO Auto-generated method stub  
   	   super.onPreExecute();  
   	   progress = new ProgressDialog(DashboardActivity.this);
   	   progress.setMax(100);  
   	   progress.setIndeterminate(false);  
   	   progress.show();  
   	  }  
   	  
   	  @Override
   	  protected String doInBackground(String... arg0) {

          // TODO Auto-generated method stub
          userFunctions.logoutUser(getApplicationContext());
          Intent login = new Intent(getApplicationContext(), LoginActivity.class);
          login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(login);
          // Closing dashboard screen
          finish(); 
   		
   	   return null;  
   	  }  
   	    
   	  @Override
   	  protected void onPostExecute(String result) {
   	   // TODO Auto-generated method stub  
   	   super.onPostExecute(result);  
   	   progress.dismiss();  
   	  }  
   	 }  
   
   
   
   class Update extends AsyncTask<String, Integer, String> {
	   	  
	   	  @Override
	   	  protected void onPreExecute() {  
	   	   // TODO Auto-generated method stub  
	   	   super.onPreExecute();  
	   	   progress = new ProgressDialog(DashboardActivity.this);
	   	   progress.setMax(100);  
	   	   progress.setIndeterminate(false);  
	   	   progress.show();  
	   	  }  
	   	  
	   	  @Override
	   	  protected String doInBackground(String... arg0) {


	          Intent login = new Intent(getApplicationContext(), UpdateActivity.class);
	          login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	          startActivity(login);
	          // Closing dashboard screen
	          finish(); 
	   		
	   	   return null;  
	   	  }  
	   	    
	   	  @Override
	   	  protected void onPostExecute(String result) {
	   	   // TODO Auto-generated method stub  
	   	   super.onPostExecute(result);  
	   	   progress.dismiss();  
	   	  }  
	   	 }  
	   
	   
}