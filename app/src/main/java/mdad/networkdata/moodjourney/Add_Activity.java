package mdad.networkdata.moodjourney;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
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

public class Add_Activity extends AppCompatActivity {

    EditText entryText, entryTitle; //Input fields for entry title and body text
    TextView monthTitle,dayTitle; //Display month name and day/date
    String[] monthArray = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    ImageButton save,cancelBtn; //Action buttons
    RadioGroup moodRadio;
    RadioButton radioHappy,radioNeutral,radioSad; //Selectors for the 3 moods
    String month,day;
    Integer year;
    String s_day,s_month,s_year,moodid,entrytitle,entrybody,userid;
    private static final String url_save_new_entry = MainActivity.ipBaseAddress+"/saveNewEntryVolley.php"; //Url for dbs comms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        //Get id of journal entry input field
        entryText = (EditText) findViewById(R.id.journalEntryInput);
        //Activate scroll for body text
        entryText.setMovementMethod(new ScrollingMovementMethod());

        //Get id of title input field
        entryTitle = (EditText) findViewById(R.id.entryTitleInput);
        //Get id of month name
        monthTitle = (TextView) findViewById(R.id.monthTitle);
        //Get id of day label
        dayTitle = (TextView) findViewById(R.id.dayTitle);
        //Get id of save button
        save = (ImageButton) findViewById(R.id.saveBtn);
        //Get id of delete button
        cancelBtn = (ImageButton) findViewById(R.id.cancelBtn);
        //Get id of radio group
        moodRadio = (RadioGroup) findViewById(R.id.radioGroup);

        //Get id of radio buttons
        radioHappy = (RadioButton) findViewById(R.id.radioHappy);
        radioNeutral = (RadioButton) findViewById(R.id.radioNeutral);
        radioSad = (RadioButton) findViewById(R.id.radioSad);

        //Get the intent that started this activity
        Intent intent = getIntent();

        //Retrieve the bundle and its data from the intent
        Bundle extras = intent.getExtras();
        day = extras.getString("day");
        month = extras.getString("month");
        year = extras.getInt("year");
        userid = extras.getString("userid");

        //Display month name
        monthTitle.setText(month);

        //Set date
        dayTitle.setText(day);

        //Handle cancel button event
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Handle save button event
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("SaveButton", "Button Pressed"); //Debugging: Output to LogCat

                //Retrieve day value
                s_day = dayTitle.getText().toString();
                s_day = s_day.substring(4);
                Log.d("SaveButton", "Day: " + s_day);

                //Retrieve month numeric & get month name from array
                s_month = monthTitle.getText().toString();
                Log.d("SaveButton", "Month: " + s_month);
                for (int i = 0; i < monthArray.length; i++) {
                    if (monthArray[i].equals(s_month)) {
                        s_month = String.valueOf(i + 1);
                        Log.d("SaveButton", "Month: " + s_month);
                        break;
                    }
                }

                //Retrieve year value
                s_year = String.valueOf(year);
                Log.d("SaveButton", "Year: " + s_year);

                //Retrieve moodid from radio group
                int checkedBtnId = moodRadio.getCheckedRadioButtonId();
                RadioButton checkedBtn = findViewById(checkedBtnId);
                moodid = checkedBtn.getText().toString();
                Log.d("SaveButton", "Mood: " + moodid);

                //Retrieve entry title
                entrytitle = entryTitle.getText().toString();
                entrytitle = entrytitle.replaceAll("'","''");
                Log.d("SaveButton", "EntryTitle: " + entrytitle);
                //Retrieve entry body
                entrybody = entryText.getText().toString();
                entrybody = entrybody.replaceAll("'","''");

                //Check for empty values
                if (moodid.isEmpty() || entrytitle.isEmpty() || entrybody.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ensure all the fields have been filled.", Toast.LENGTH_LONG).show();
                    return;
                }

                //Put the entry info as key-values pair in HashMap
                Map<String, String> params_create = new HashMap<String, String>();
                params_create.put("day", s_day);
                params_create.put("month", s_month);
                params_create.put("year", s_year);
                params_create.put("moodid", moodid);
                params_create.put("entrytitle", entrytitle);
                params_create.put("entrybody", entrybody);
                params_create.put("userid", userid);
                Log.d("SaveButton", "params_create: " + params_create);

                //postData method to use Volley to update new entry details in database
                postData(url_save_new_entry, params_create);

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

        private void postData(String url, Map params){ //postData function def

            //Create a RequestQueue for Volley
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            //Create StringRequest for http post web request to send new user info to database
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                    new Response.Listener<String>() { //Handle response from server
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Fail"))
                            {
                                //Inform user of status
                                Toast.makeText(getApplicationContext(),"Error in saving journal entry.Please try again",Toast.LENGTH_LONG).show();
                            }
                            else if(response.contains("Pass"))
                            {
                                //Inform user of status
                                Toast.makeText(getApplicationContext(),"Your journal entry has been successfully saved!",
                                        Toast.LENGTH_LONG).show();

                                //Log.d("Response",response);

                                //Retrieve userid and entryid from response and pass to next activity
                                String[] entry = response.split(":");
                                String entry_id = entry[1];
                                Log.d("entry_id",entry_id); //Debugging: Output to LogCat

                                //Load JournalEntryActivity
                                Intent i = new Intent (getApplicationContext(), JournalEntryActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString("userid",userid);
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
                            Toast.makeText(getApplicationContext(),"Error in saving journal entry.Please try again",Toast.LENGTH_LONG).show();
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
