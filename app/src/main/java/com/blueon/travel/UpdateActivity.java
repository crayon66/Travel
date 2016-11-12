package com.blueon.travel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blueon.library.UserFunctions;

import org.json.JSONObject;

//import com.example.androidhive.library.DatabaseHandler;
//import com.example.androidhive.library.UserFunctions;

public class UpdateActivity extends Activity {
    
    Button btnUpdate;
    Button btnLinkToLogin;
    EditText OldPass;
    EditText NewPass1;
    EditText NewPass2;
    UserFunctions userFunctions;
 
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update);
        
        // Importing all assets like buttons, text fields
        OldPass = (EditText) findViewById(R.id.updateOldPassword);
        NewPass1 = (EditText) findViewById(R.id.updateNewPassword);
        NewPass2 = (EditText) findViewById(R.id.updateNewPassword2);
        btnUpdate = (Button) findViewById(R.id.updatebtn);
        btnLinkToLogin = (Button) findViewById(R.id.updatetoLogin);
        // Login button Click Event
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())){
        
        btnUpdate.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View arg0) {
            	 new Update().execute();  
            }
        });
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
        	 
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        LoginActivity.class);
                startActivity(i);
                // Close Registration View
                finish();
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
    class Update extends AsyncTask<String, Integer, String> {
    	
    	
    	  @Override
    	  protected void onPreExecute() {  
    	   // TODO Auto-generated method stub  
    	   super.onPreExecute();  
    	   progress = new ProgressDialog(UpdateActivity.this);
    	   progress.setMax(100);  
    	   progress.setIndeterminate(false);  
    	   progress.setMessage("Please Wait");
    	   progress.show();  
    	  }  
    	  
    	  @Override
    	  protected String doInBackground(String... arg0) {
    		  
    		  String oldpass = OldPass.getText().toString();
              String newpass1 = NewPass1.getText().toString();
              String newpass2 = NewPass2.getText().toString();
              if(!newpass1.equals(newpass2)){
            	  showToast("Update Password Failed");
            	  return "Failed";
              }
              
              UserFunctions userFunction = new UserFunctions();
              JSONObject json = userFunction.updateUser(oldpass ,newpass1);
              
              // check for login response
              try {
            	 
                  if (json.getString(KEY_SUCCESS) != null) {
                	  
                	  String errr=json.getString(KEY_ERROR_MSG);
                	  System.out.println(errr);
                      //loginErrorMsg.setText("");
                      String res = json.getString(KEY_SUCCESS);
                      System.out.println(res);
                      if(Integer.parseInt(res) == 1){
                    	  showToast("Update Password Success" +res+" - "+ errr);
                    	  Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
                          startActivity(i);
                          
                      }
                      else{
                          // Error in Update
                    	  showToast("Update Password Failed" +res+" - "+ errr);
                      }
                  }
                  
              } catch (Exception e) {
                  e.printStackTrace();
                  System.out.println(e);
              } 
    		
    	   return "done";  
    	  }  
    	    
    	  @Override
    	  protected void onPostExecute(String result) {
    	   // TODO Auto-generated method stub  
    	   super.onPostExecute(result);  
    	   progress.dismiss();  
    	  }  
    	 }  
    
    public void showToast(final String toast)    {
        runOnUiThread(new Runnable() {
            public void run(){
                Toast.makeText(UpdateActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
}