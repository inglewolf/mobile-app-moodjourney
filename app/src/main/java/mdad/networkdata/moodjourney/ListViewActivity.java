package mdad.networkdata.moodjourney;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ListViewActivity extends AppCompatActivity {

    ImageButton arrowRight,arrowLeft,userProfile; //Action buttons
    String[] monthArray = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"}; //Array to store month names
    Integer[] monthDays = {31,28,31,30,31,30,31,31,30,31,30,31}; //Array to store total number of days for each month
    Integer numOfDays,monthValue,yearValue; //To store values relating to the date
    TextView monthTitle,yearTitle; //To display month & day/date
    String str_monthValue,selMonth,userid; //To store relevant variables
    ListView list; //Variable to store ListView
    ArrayList<HashMap<String,String>> entryList; //Array to store entry details.
    private static final String url_listEntries = MainActivity.ipBaseAddress+"/getEntriesListVolley.php"; //Urls for dbs comms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_view);

        //Retrieve month value of current page & user id from previous activity
        Intent intent_ListView = getIntent();
        Bundle extras = intent_ListView.getExtras();
        Integer new_monthValue = extras.getInt("new_monthValue");
        userid = extras.getString("userid");

        //Retrieve just user id from LoginActivity
        Intent intent_Login = getIntent();
        userid = intent_Login.getStringExtra("userid");

        //Get resource ids of buttons to change months
        arrowLeft = (ImageButton) findViewById(R.id.arrowLeft);
        arrowRight = (ImageButton) findViewById(R.id.arrowRight);
        //Get id of user profile button
        userProfile = (ImageButton) findViewById(R.id.profileBtn);
        //Get resource id of month title
        monthTitle = (TextView) findViewById(R.id.monthTitle);
        //Get resource id of year title
        yearTitle = (TextView) findViewById(R.id.dayTitle);
        //Get resource id of ListView
        list = (ListView) findViewById(R.id.list);

        // ArrayList to store product info in Hashmap for ListView
        entryList = new ArrayList<HashMap<String, String>>();

        //Get current date
        LocalDate today = LocalDate.now();

        //Get current year & display it
        yearValue = today.getYear();

        //If value goes over 12, reset to 1(Jan)
        if(new_monthValue == 13) {
            new_monthValue = 1;
            yearValue += 1;
        } else if(new_monthValue == -1){ //If value goes below 1, reset to 12(Dec)
            new_monthValue = 12;
            yearValue -= 1;
        }

        String str_yearTitle = String.valueOf(yearValue);
        yearTitle.setText(str_yearTitle); //Display the year

        //Get current month
        monthValue = today.getMonthValue(); // Numeric value of the month (1-12)

        //Compare month value from previous activity and current real time month value
        if(new_monthValue == 0 || new_monthValue.equals(monthValue)) {
            selMonth = monthArray[monthValue - 1];
            str_monthValue = String.valueOf(monthValue); //Use the real time month value for display
        } else {
            selMonth = monthArray[new_monthValue - 1];
            str_monthValue = String.valueOf(new_monthValue); //Use month value sent from previous activity to display
        }

        monthTitle.setText(selMonth); //Display name of month selected from array
        
        //put the product info as key-values pair in HashMap
        Map<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);
        params.put("month", str_monthValue);
        //Re-usable method to use Volley to retrieve entries list from database
        postData(url_listEntries, params);

        userProfile.setOnClickListener(new View.OnClickListener() { //Re-direct user to their profile page upon click
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ListViewActivity.this, ProfileActivity.class);
                //Pass the message using the name-value pair
                Bundle extras = new Bundle();
                extras.putString("userid",userid); //Send user id to next activity
                i.putExtras(extras);
                startActivity(i);
            }
        });

        arrowLeft.setOnClickListener(new View.OnClickListener() { //This button takes user to the previous month on the calender
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);
                //Reset month value to the previous month before sending to next instance of ListViewActivity
                int new_monthValue;
                if(Integer.parseInt(str_monthValue) == 1) {
                    new_monthValue = -1;
                } else {
                    new_monthValue = Integer.parseInt(str_monthValue) - 1;
                }

                //Pass the message using the name-value pair
                Bundle extras = new Bundle();
                extras.putInt("new_monthValue",new_monthValue); //Send month value & user id to next instance of ListViewActivity
                extras.putString("userid",userid);
                i.putExtras(extras);
                startActivity(i);
                finish(); //End current activity
            }
        });

        arrowRight.setOnClickListener(new View.OnClickListener() { //This button takes user to the next month on the calender
                    @Override
                    public void onClick(View view) {

                        Intent i = new Intent(ListViewActivity.this, ListViewActivity.class);

                        //Reset month value to the next month
                        int new_monthValue = Integer.parseInt(str_monthValue) + 1;
                        Bundle extras = new Bundle();
                        extras.putInt("new_monthValue",new_monthValue);
                        extras.putString("userid",userid); //Send month value & user id to next instance of ListViewActivity
                        i.putExtras(extras);
                        startActivity(i);
                        finish();
                    }
                });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() { //This allows user to click into each list item.
            public void onItemClick(AdapterView parent, View v, int position, long id) {

                // Retrieve entryid selected item from ArrayList based on the hashmap key
                String entry_id = entryList.get(position).get("entryid");

                if(entry_id == null) { //If selected list item has no stored entry id, take user to add new entry page
                    Intent i = new Intent(ListViewActivity.this, Add_Activity.class);

                    //Retrieve date/day value
                    String date = entryList.get(position).get("date");

                    Bundle extras = new Bundle();
                    extras.putString("day",date); //Set date value to bundle
                    extras.putString("month",selMonth); //Set month value
                    extras.putInt("year",yearValue); //Set year value to bundle
                    extras.putString("userid",userid); //Send details required for Add_Activity including userid
                    //Pass bundle of extras
                    i.putExtras(extras);
                    startActivity(i);

                } else { //If list item consists of stored entry, direct user to JournalEntryActivity with selected entry's details

                    Intent i = new Intent(ListViewActivity.this, JournalEntryActivity.class);
                    //Pass entry id using the name-value pair
                    Bundle extras = new Bundle();
                    extras.putString("entryid",entry_id); //Send entry id to retrieve its data in the next activity
                    i.putExtras(extras);
                    startActivity(i);

                }

            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void postData(String url, Map<String, String> params) { //Function to retrieve data of all the available entries & load them into listview
        // Create a RequestQueue for Volley
        RequestQueue queue = Volley.newRequestQueue(this);

        // Create a StringRequest for Volley for HTTP Post
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Check if error code received from server
                        if (response.equals("Error")) { //If request failed, inform user
                            Toast.makeText(getApplicationContext(), "Error in accessing database.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Log.d("ListViewActivity","Response: " + response); //Debugging, output to LogCat

                        //Handle the response data received from server
                        //Store each product from database records in String array
                        String[] entries = response.split(":");

                        numOfDays = monthDays[monthValue - 1];
                        // For each entry, retrieve its details
                        for (int i = 0; i < numOfDays; i++) { //Loop through all days of the selected month

                            //Initialise variables to store details of each entry with default values
                            String date = String.valueOf(i + 1); //Set date value to loop number + 1
                            String moodId = "4";
                            String title = "-";
                            String entryid = null;

                            Log.d("ListViewActivity","---------------------"); //Debugging, output to LogCat
                            Log.d("ListViewActivity","Loop Number " + i);
                            Log.d("ListViewActivity","Date " + date);

                            for (int x = 0; x < entries.length; x++) {

                                String[] details = entries[x].split(";"); //Split string of data into individual items stored in array

                                if(Objects.equals(details[0], date)) { //If entry's date from dbs matches current value of date variable
                                    //Store the entry's details into relevant variables for display as list item
                                    moodId = details[1];
                                    title = details[2];
                                    entryid = details[3];
                                    break;
                                }}

                        // Creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
                        //Add each entry info to HashMap as key-value pairs
                        map.put("date","Day-" + date);
                        map.put("moodid", moodId);
                        map.put("title", title);
                        map.put("entryid", entryid);

                        // Adding map HashList to ArrayList
                        entryList.add(map);
                        }

                        // Populate the ListView with entry information from HashMap
                        ListAdapter adapter = new SimpleAdapter(
                                ListViewActivity.this, entryList, R.layout.listview_item, new String[]{"title","date","moodid"},
                                new int[]{R.id.entryTitle,R.id.date,R.id.emote});

                        ((SimpleAdapter) adapter).setViewBinder(new SimpleAdapter.ViewBinder() {
                            @Override
                            public boolean setViewValue(View view, Object data, String textRepresentation) {
                                //Function to change imageview of emoticon based on value of moodid
                                if (view.getId() == R.id.emote) {
                                    ImageView imageView = (ImageView) view;
                                    int moodId = Integer.parseInt((String) data);
                                    switch (moodId) {
                                        case 1:
                                            imageView.setImageResource(R.drawable.happy); //If == 1, set happy emoticon
                                            break;
                                        case 2:
                                            imageView.setImageResource(R.drawable.neutral); //If == 2, set neutral emoticon
                                            break;
                                        case 3:
                                            imageView.setImageResource(R.drawable.sad); //If == 3, set sad emoticon
                                            break;
                                        default:
                                            imageView.setImageResource(R.drawable.empty); //Default == 4, set question mark
                                            break;
                                    }
                                    return true;
                                }
                                return false;
                            }
                        });

                        // Updating ListView
                        list.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(getApplicationContext(), "Error in retrieving journal entries.", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                return params;
            }
        };

        // Add StringRequest to RequestQueue in Volley
        queue.add(stringRequest);
    }
}