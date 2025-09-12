package mdad.networkdata.moodjourney;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Edit_Activity extends AppCompatActivity {

    String entry_id,moodid,entry_title,entrybody;
    TextView monthTitle,dayTitle; //To display month name & day/date values
    ImageButton backBtn,saveBtn,delBtn; //Action buttons
    RadioGroup moodRadio;
    EditText entryText, entryTitle; //Input fields for journal title and entry
    RadioButton radioHappy,radioNeutral,radioSad; //Radio buttons for emoticons
    String[] monthArray = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"}; //Array to store month names

    private static final String url_save_curr_entry = MainActivity.ipBaseAddress+"/saveExistingEntryVolley.php"; //Urls for dbs comms
    private static final String url_delete_entry = MainActivity.ipBaseAddress+"/deleteEntryVolley.php";
    private static final String url_retrieve_entry = MainActivity.ipBaseAddress+"/getSingleEntryVolley.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);

        //Get id of month name
        monthTitle = (TextView) findViewById(R.id.monthTitle);
        //Get id of day label
        dayTitle = (TextView) findViewById(R.id.dayTitle);
        //Get id of entry title input field
        entryTitle = (EditText) findViewById(R.id.entryTitleInput);
        //Get id of entry text input field
        entryText = (EditText) findViewById(R.id.journalEntryInput);
        //Get id of back button
        backBtn = (ImageButton) findViewById(R.id.backBtn);
        //Get id of back button
        saveBtn = (ImageButton) findViewById(R.id.saveBtn);
        //Get id of delete button
        delBtn = (ImageButton) findViewById(R.id.deleteBtn);

        //Get id of radio group
        moodRadio = (RadioGroup) findViewById(R.id.radioGroup);
        //Get id of radio buttons
        radioHappy = (RadioButton) findViewById(R.id.radioHappy);
        radioNeutral = (RadioButton) findViewById(R.id.radioNeutral);
        radioSad = (RadioButton) findViewById(R.id.radioSad);

        //Receiving entry id from previous activity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        entry_id = extras.getString("entryid");

        //put the entry id as key-values pair in HashMap
        Map<String,String> params_create = new HashMap<String, String>();
        params_create.put("entryid",entry_id);

        //postData method to use Volley to update new product details in database
        getData(url_retrieve_entry,params_create);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //End this activity and return to journal entry
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Retrieve id of checked emotion
                int checkedBtnId = moodRadio.getCheckedRadioButtonId();
                RadioButton checkedBtn = findViewById(checkedBtnId);
                moodid = checkedBtn.getText().toString();

                //Retrieve entry title
                entry_title = entryTitle.getText().toString();
                entry_title = entry_title.replaceAll("'","''");

                //Retrieve entry body
                entrybody = entryText.getText().toString();
                entrybody = entrybody.replaceAll("'","''");

                //Check for empty values
                if (moodid.isEmpty() || entry_title.isEmpty() || entrybody.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ensure all the fields have been filled.", Toast.LENGTH_LONG).show();
                    return;
                }

                //Put the entry info as key-values pair in HashMap
                Map<String,String> params_save = new HashMap<String, String>();
                params_save.put("entryid",entry_id);
                params_save.put("moodid",moodid);
                params_save.put("entrytitle",entry_title);
                params_save.put("entrybody",entrybody);

                //updateData method to use Volley to update existing entry info in database
                updateData(url_save_curr_entry, params_save);
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View customView = getLayoutInflater().inflate(R.layout.delete_dialog, null);

                //Create a builder variable for our alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(Edit_Activity.this);
                builder.setView(customView);

                // Get references to buttons and set onClickListeners
                Button positiveButton = customView.findViewById(R.id.positiveButton);
                Button negativeButton = customView.findViewById(R.id.negativeButton);

                // Create the AlertDialog
                AlertDialog dialog = builder.create();

                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Set the entry id as key-value pair in HashMap
                        Map<String,String> params_del = new HashMap<String, String>();
                        params_del.put("entryid",entry_id);

                        deleteData(url_delete_entry, params_del);
                        dialog.dismiss();
                    }
                });

                negativeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismissing our dialog box if negative button clicked
                        dialog.dismiss();
                    }
                });
                //Show display
                dialog.show();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void deleteData(String url, Map params){
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to delete current entry from dbs
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Edit_Activity","Response: " + response);
                        if(response.equals("Error")) //If request fails
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Error in deleting journal entry.Please try again",Toast.LENGTH_LONG).show();
                        }
                        else if(response.contains("Success")) //If request succeeds
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Your journal entry has been deleted.",
                                    Toast.LENGTH_LONG).show();

                            String[] value = response.split(":");
                            String userid = value[1];

                            //Load JournalEntryActivity
                            Intent i = new Intent (getApplicationContext(), ListViewActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("userid",userid);
                            i.putExtras(extras);
                            startActivity(i);
                            finish();
                        }
                    }
                },
                //Error in Volley
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle error
                        Toast.makeText(getApplicationContext(),"Error in deleting journal entry.Please try again",Toast.LENGTH_LONG).show();
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

    private void updateData(String url, Map params){
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to save updated entry to dbs
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Edit_Activity","Response: " + response);
                        if(response.equals("Error"))
                        {
                            Toast.makeText(getApplicationContext(),"Error in updating journal entry.Please try again",Toast.LENGTH_LONG).show();
                        }
                        else if(response.equals("Success"))
                        {
                            Toast.makeText(getApplicationContext(),"Your journal entry has been successfully updated!",
                                    Toast.LENGTH_LONG).show();

                            //Pass entry id & load JournalEntryActivity
                            Intent i = new Intent (getApplicationContext(), JournalEntryActivity.class);
                            Bundle extras = new Bundle();
                            extras.putString("entryid",entry_id);
                            i.putExtras(extras);
                            startActivity(i);
                            finish();
                        }
                    }
                },
                //Error in Volley
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle error
                        Toast.makeText(getApplicationContext(),"Error in saving updating entry.Please try again",Toast.LENGTH_LONG).show();
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

    private void getData(String url, Map params){ //Retrieve entry to edit
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to retrieve entry details from dbs
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Error")) //If request fails
                        {   //Inform user and end activity
                            Toast.makeText(getApplicationContext(),"Error in retrieving journal entry.Please try again",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else if(response.contains("Success")) //If request succeeds
                        {
                            //Load selected entry's data into relevant fields
                            Log.d("Response",response);

                            String[] entry = response.split(":");
                            entry = entry[1].split(";");

                            String entry_id = entry[0];
                            Log.d("entry_id",entry_id);
                            String userid = entry[1];
                            Log.d("userid",userid);

                            //Retrieve month from entry record & display
                            int monthNum = Integer.parseInt(entry[2]);
                            String sel_month = monthArray[monthNum - 1];
                            monthTitle.setText(sel_month);

                            //Retrieve day from entry & display
                            String day = "Day-" + entry[3];
                            dayTitle.setText(day);

                            //Retrieve mood id and display emoticon accordingly
                            int moodid = Integer.parseInt(entry[4]);

                            //Check appropriate radio button according to the value of moodid
                            if(moodid == 1) {
                                radioHappy.setChecked(true);
                            } else if (moodid == 2) {
                                radioNeutral.setChecked(true);
                            } else if (moodid == 3) {
                                radioSad.setChecked(true);
                            }

                            //Retrieve entry title & display
                            String entry_title = entry[5];
                            entryTitle.setText(entry_title);

                            //Retrieve entry body text & display
                            String entryBody = entry[6];
                            entryText.setText(entryBody);
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