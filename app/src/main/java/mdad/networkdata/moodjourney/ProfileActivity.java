package mdad.networkdata.moodjourney;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    String userid,username,email,birthdate; //Variables to store user's details
    TextView name_disp,email_disp,bdate_disp; //To display user's details
    ImageButton backBtn,logoutBtn,editBtn; //Action buttons
    private static final String url_user_profile = MainActivity.ipBaseAddress+"/getUserProfileVolley.php"; //Url for dbs comms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        //Get resource ids of textviews to display user's details
        name_disp = (TextView) findViewById(R.id.userName);
        email_disp = (TextView) findViewById(R.id.user_email);
        bdate_disp = (TextView) findViewById(R.id.user_bdate);
        //Get resource ids of buttons
        backBtn = (ImageButton) findViewById(R.id.backBtn_profile);
        logoutBtn = (ImageButton) findViewById(R.id.logoutBtn);
        editBtn = (ImageButton) findViewById(R.id.editBtn_profile);

        //Retrieve user id from previous activity
        Intent intent_ListView = getIntent();
        Bundle extras = intent_ListView.getExtras();
        userid = extras.getString("userid");

        //Set user id as a key-value pair into hashmap
        Map<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);
        //Re-usable method to use Volley to retrieve user's details from database
        getData(url_user_profile, params);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //End this activity and return to listview activity on button click
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //End this activity and go to EditProfileActivity
                Intent i = new Intent(ProfileActivity.this, EditProfileActivity.class);
                Bundle extras = new Bundle();
                extras.putString("userid",userid); //Send userid as a key-value pair
                i.putExtras(extras);
                startActivity(i);
                finish();
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //End this activity and return to MainActivity upon button click
                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                Toast.makeText(getApplicationContext(), "You have been logged out.", Toast.LENGTH_LONG).show(); //Inform user
                startActivity(i);
                finish();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getData(String url, Map params){ //Retrieve user's profile details from dbs
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to send new user info to database
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Error")) //If request fails
                        {
                            Toast.makeText(getApplicationContext(),"Error in retrieving your profile.Please try again",Toast.LENGTH_LONG).show();
                            finish(); //Inform user and end activity
                        }
                        else if(response.contains("Success")) //If request succeeds
                        {
                            Log.d("Response",response); //Debugging, output to LogCat

                            String[] userprofile = response.split("_"); //Split string into array items
                            userprofile = userprofile[1].split(";");
                            Log.d("UserProfile", Arrays.toString(userprofile));
                            //Set user's details to variables
                            username = userprofile[0];
                            email = userprofile[1];
                            birthdate = userprofile[2];

                            //Display user's details to relevant TextViews
                            name_disp.setText(username);
                            email_disp.setText(email);
                            bdate_disp.setText(birthdate);
                        }
                    }
                },
                //Error in Volley
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle error
                        Toast.makeText(getApplicationContext(),"Error in retrieving journal entry.Please try again",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Nullable
            @Override
            //To send entry stored in HashMap params_create to server via HTTP Post
            protected Map<String, String> getParams() {
                return params;
            }
        };

        //Add StringRequest to Volley Queue
        requestQueue.add(stringRequest);
    }
}