package mdad.networkdata.moodjourney;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

public class JournalEntryActivity extends AppCompatActivity {

    TextView entryText,day_title,entry_title,month; //TextViews to display entry's details
    ImageView mood_icon; //ImageView to display selected mood of entry
    ImageButton backBtn,editBtn; //Action buttons
    String entry_id; //String to store entry id
    String[] monthArray = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"}; //Array to store month names
    private static final String url_get_entry = MainActivity.ipBaseAddress+"/getSingleEntryVolley.php"; //Urls for dbs comms


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_journal_entry);

        //Get id of entry's body text
        entryText = (TextView) findViewById(R.id.entryText);
        //Activate scroll for body text
        entryText.setMovementMethod(new ScrollingMovementMethod());

        //Get id of date text
        day_title = (TextView) findViewById(R.id.dayTitle);
        //Get id of mood icon
        mood_icon = (ImageView) findViewById(R.id.emoticon);
        //Get id of entry title
        entry_title = (TextView) findViewById(R.id.postTitle);
        //Get id of month title
        month = (TextView) findViewById(R.id.monthTitle);
        //Get id of back button
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        //Get id of edit button
        editBtn = (ImageButton) findViewById(R.id.editBtn);

        //Receiving entryid
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        entry_id = extras.getString("entryid");

        //put the product info as key-values pair in HashMap
        Map<String,String> params_create = new HashMap<String, String>();
        params_create.put("entryid",entry_id);

        //postData method to use Volley to update new product details in database
        postData(url_get_entry,params_create);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //End this activity and return to listview
                finish();
            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pass entry id and go to Edit_Activity
                Intent i = new Intent(JournalEntryActivity.this, Edit_Activity.class);
                Bundle extras = new Bundle();
                extras.putString("entryid",entry_id);
                i.putExtras(extras);
                startActivity(i);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void postData(String url, Map params){ //Function to retrieve entry data
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to send user info to database
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("Error")) //If request fails
                        {
                            Toast.makeText(getApplicationContext(),"Error retrieving journal entry. Please try again.",Toast.LENGTH_LONG).show();
                            finish(); //Inform user and end this activity
                        }
                        else if(response.contains("Success"))
                        {
                            String[] entry = response.split(":");
                            entry = entry[1].split(";");

                            //Retrieve month from entry record & display
                            int monthNum = Integer.parseInt(entry[2]);
                            String sel_month = monthArray[monthNum - 1];
                            month.setText(sel_month);

                            //Retrieve day from entry & display
                            String day = "Day-" + entry[3];
                            day_title.setText(day);

                            //Retrieve mood id and display emoticon accordingly
                            int moodid = Integer.parseInt(entry[4]);

                            if(moodid == 1) {
                                mood_icon.setImageResource(R.drawable.happy);
                            } else if (moodid == 2) {
                                mood_icon.setImageResource(R.drawable.neutral);
                            } else if (moodid == 3) {
                                mood_icon.setImageResource(R.drawable.sad);
                            }

                            //Retrieve entry title & display
                            String entryTitle = entry[5];
                            entry_title.setText(entryTitle);

                            //Retrieve entry body text & display
                            String entryBody = entry[6];
                            entryText.setText(entryBody);

                        } else if(response.equals("Error")) {

                            Toast.makeText(getApplicationContext(),"Error accessing database.",
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
        requestQueue.add(stringRequest);
    }}
