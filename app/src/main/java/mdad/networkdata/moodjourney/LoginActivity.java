package mdad.networkdata.moodjourney;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private ImageButton loginButton; //Action button
    private TextView registerLink; //Link & guide text to register page
    private EditText password,username; //Input fields
    private String u_name,u_pw; //To store user's input
    private static final String url_login_user = MainActivity.ipBaseAddress+"/loginVolley.php"; //Url for dbs comms
    public static String userID; //Store user's id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        //Get resource id of the login button
        loginButton = (ImageButton) findViewById(R.id.login2Button);
        //Get resource id of the register page link
        registerLink = (TextView) findViewById(R.id.registerLink);
        //Get resource id of username & password input fields
        username = (EditText) findViewById(R.id.usernameInput);
        password = (EditText) findViewById(R.id.passwordInput);

        //Login button click event
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Retrieve the values in EditText entered by user
                u_name = username.getText().toString();
                u_pw = password.getText().toString();

                //Check empty values in EditText
                if (u_name.isEmpty() || u_pw.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Ensure all the fields have been filled.",
                            Toast.LENGTH_LONG).show(); //Inform user to fill them if true
                    return;
                }

                // Log user input for debugging, output to LogCat
                Log.d("RegisterActivity", "Username: " + u_name);
                Log.d("RegisterActivity", "Password: " + u_pw);
                Log.d("LoginActivity", "URL: " + url_login_user);

                //Put the user info as key-values pair in HashMap
                Map<String,String> params_create = new HashMap<String, String>();
                params_create.put("username",u_name);
                params_create.put("password",u_pw);

                //postData method to use Volley to verify user's login details against database
                postData(url_login_user,params_create);
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent here to load the Register activity
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void postData(String url, Map params){
        //Create a RequestQueue for Volley
        RequestQueue requestQueue_login = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to send user info to database
        StringRequest stringRequest_login = new StringRequest(Request.Method.POST, url,
                //response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("No result")) //If request fails
                        {
                            Toast.makeText(getApplicationContext(),"Invalid username/password.Please try again",Toast.LENGTH_LONG).show();
                            //Inform user
                        }
                        else if(response.contains("Success")) //If request succeeds
                        {
                            Toast.makeText(getApplicationContext(),"Successfully logged in!",
                                    Toast.LENGTH_LONG).show(); //Inform user

                            String[] user = response.split(":"); //Split response & retrieve required data
                            userID = user[1];

                            //load the AllProductActivity with updated ListView
                            Intent i = new Intent (getApplicationContext(), ListViewActivity.class);
                            i.putExtra("userid",userID); //Send user's id to next activity
                            startActivity(i);
                            finish();

                        } else if(response.equals("Error")) { //If request fails

                            Toast.makeText(getApplicationContext(),"Error. Please try again.",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                },
                //Error in Volley
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle error
                        Toast.makeText(getApplicationContext(),"Error.Please try again",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Nullable
            @Override
//To send user login info stored in HashMap params_create to server via HTTP Post
            protected Map<String, String> getParams() {
                return params;
            }
        };

//Add StringRequest to Volley Queue
        requestQueue_login.add(stringRequest_login);
    }

}

