package mdad.networkdata.moodjourney;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    String userid,username,email,birthdate; //To store user's input of their details
    String new_pw,curr_pw,cnfm_curr_pw; //To store user's input of passwords
    EditText name_disp,email_disp; //References to text input fields
    EditText new_pw_input,curr_pw_input,cnfm_curr_pw_input; //References to text input fields
    TextView bdate_disp; //Display selected date from datepicker
    ImageButton cancelBtn,saveBtn; //Action buttons
    private ImageButton datePicker; //Button to launce datepicker
    String verification_result; //To store status of password verification

    //Urls for dbs comms
    private static final String url_user_profile = MainActivity.ipBaseAddress+"/getUserProfileVolley.php";
    private static final String url_save_user_profile_nopw = MainActivity.ipBaseAddress+"/saveUserProfileVolley_NoPw.php";
    private static final String url_save_user_profile_pw = MainActivity.ipBaseAddress+"/saveUserProfileVolley_Pw.php";
    private static final String url_verify_password = MainActivity.ipBaseAddress+"/verifyUserPasswordVolley.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);

        //Get ids for input fields
        name_disp = (EditText) findViewById(R.id.name_input);
        email_disp = (EditText) findViewById(R.id.email_input);
        new_pw_input = (EditText) findViewById(R.id.newPWInput);
        curr_pw_input = (EditText) findViewById(R.id.oldPWInput);
        cnfm_curr_pw_input = (EditText) findViewById(R.id.oldPWCnfmInput);
        //Get id for text display
        bdate_disp = (TextView) findViewById(R.id.bdate_input);
        //Get ids for action buttons
        cancelBtn = (ImageButton) findViewById(R.id.cancelBtnProfile);
        saveBtn = (ImageButton) findViewById(R.id.saveBtnProfile);
        datePicker = (ImageButton) findViewById(R.id.datePickerBtn2);

        //Receiving user id from previous activity
        Intent intent_ListView = getIntent();
        Bundle extras = intent_ListView.getExtras();
        userid = extras.getString("userid");

        //Put user's id as key-value pair in HashMap
        Map<String, String> params = new HashMap<String, String>();
        params.put("userid", userid);
        //Method to use Volley to retrieve user's details from database
        getData(url_user_profile, params);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //End this activity and return to user's profile page
                Intent i = new Intent (EditProfileActivity.this, ProfileActivity.class);
                Bundle extras = new Bundle();
                extras.putString("userid",userid);
                i.putExtras(extras);
                startActivity(i);
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Retrieve user's input from various fields and store in relevant variables
                new_pw = String.valueOf(new_pw_input.getText());
                curr_pw = String.valueOf(curr_pw_input.getText());
                cnfm_curr_pw = String.valueOf(cnfm_curr_pw_input.getText());
                username = String.valueOf(name_disp.getText());
                email = String.valueOf(email_disp.getText());
                birthdate = String.valueOf(bdate_disp.getText());

                //Check for empty variables == missing input
                if( username.isEmpty() || email.isEmpty() || birthdate.isEmpty() || curr_pw.isEmpty() || cnfm_curr_pw.isEmpty() ) {

                    //Inform user of missing info
                    Toast.makeText(getApplicationContext(), "Please make sure all fields apart from the new password, are filled.",
                            Toast.LENGTH_LONG).show();

                } else {

                    //Check if entered current and confirm passwords are the same
                    if(Objects.equals(curr_pw, cnfm_curr_pw)) {


                        //Set userid into hashmap
                        Map<String, String> params_save = new HashMap<String, String>();
                        params_save.put("userid", userid);

                        //Run method to verify user's password against the one saved in the dbs
                        verifyPassword(url_verify_password, params_save);

                    }  else {

                        //Inform user if passwords do not match
                        Toast.makeText(getApplicationContext(), "Your passwords do not match. Please try again.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }});

                //Adding click listener for date picker button
                datePicker.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Get calendar instance
                        final Calendar c = Calendar.getInstance();

                        //Get day, month, year
                        int year = c.get(Calendar.YEAR);
                        int month = c.get(Calendar.MONTH);
                        int day = c.get(Calendar.DAY_OF_MONTH);

                        //Create variable for date picker dialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(
                                // on below line we are passing context.
                                EditProfileActivity.this,
                                R.style.CustomDatePickerDialog,  // Apply custom style
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {
                                        //Set selected date into textview.
                                        bdate_disp.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                    }
                                },
                                //Pass day, month, year of selected date.
                                year, month, day);

                        // Add customization to the DatePickerDialog
                        datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialog) {

                                // Load the custom font
                                Typeface customFont = ResourcesCompat.getFont(EditProfileActivity.this, R.font.josefin_sans_bold);
                                int buttonColor = Color.parseColor("#999999");

                                // Access the DatePicker inside the DatePickerDialog
                                DatePicker datePicker = datePickerDialog.getDatePicker();

                                // Access the header view and set a gradient background
                                int headerId = getResources().getIdentifier("android:id/date_picker_header", null, null);
                                View headerView = datePickerDialog.findViewById(headerId);
                                if(headerView != null) {

                                    // Set a gradient background to the header view
                                    headerView.setBackgroundResource(R.drawable.gradient_drawable);
                                }

                                // Change color of OK and Cancel buttons
                                Button positiveButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE);
                                Button negativeButton = datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE);

                                if (positiveButton != null) {
                                    positiveButton.setTextColor(buttonColor); // Change OK button color
                                }
                                if (negativeButton != null) {
                                    negativeButton.setTextColor(buttonColor); // Change Cancel button color
                                }
                            }
                        });
                        //Call show to display date picker dialog.
                        datePickerDialog.show();
                    }
                });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void getData(String url, Map params){ //Function to retrieve user's details from dbs
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to retrieve info
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Error")) //If request fails
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Error in retrieving your profile.Please try again",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else if(response.contains("Success")) //If request succeeds
                        {
                            //Display retrieved data in the relevant input fields
                            Log.d("Response",response);

                            String[] userprofile = response.split("_");
                            userprofile = userprofile[1].split(";");

                            username = userprofile[0];
                            email = userprofile[1];
                            birthdate = userprofile[2];

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

    private void updateData(String url, Map params){ //Function to update existing user's details
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to update existing user's details
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Update-Response",response);
                        if(response.equals("Error")) //If request fails
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Error in updating your details.Please try again",Toast.LENGTH_LONG).show();
                        }
                        else if(response.equals("Success")) //If request succeeds
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Your profile has been successfully updated!",
                                    Toast.LENGTH_LONG).show();

                            //Pass user's id and start profile page activity, end current activity.
                            Intent i = new Intent (EditProfileActivity.this, ProfileActivity.class);
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
                        Toast.makeText(getApplicationContext(),"Error in updating your details.Please try again",Toast.LENGTH_LONG).show();
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

    private void verifyPassword(String url, Map params){ //Function to verify user's password against the dbs
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Error")) //If request fails
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Error in retrieving your details.Please try again",Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else if(response.contains("Success")) //If request succeeds
                        {

                            Log.d("VerifyPW-Response",response);

                            String[] userprofile = response.split("_");
                            String dbs_pw = userprofile[1];
                            Log.d("userprofile[1]", userprofile[1]);
                            Log.d("userprofile", Arrays.toString(userprofile));
                            Log.d("Do they match", String.valueOf(Objects.equals(dbs_pw, curr_pw)));

                            if(Objects.equals(dbs_pw, curr_pw)) { //Compare passwords and update status
                                verification_result = "true";

                            } else {
                                verification_result = "false";
                            }

                            if(Objects.equals(verification_result, "true")) { //If true, retrieve user's details from the form
                                Log.d("Verification_result",verification_result);
                                Log.d("new_pw there?", String.valueOf(new_pw.isEmpty()));

                                if(new_pw.isEmpty()) { //Check if user has entered a new password

                                    //Set user's details into hashmap to send via HTTP request, including value from new password field
                                    Map<String, String> params_no_new_pw = new HashMap<String, String>();
                                    params_no_new_pw.put("userid", userid);
                                    params_no_new_pw.put("username", username);
                                    params_no_new_pw.put("email", email);
                                    params_no_new_pw.put("birthdate", birthdate);

                                    updateData(url_save_user_profile_nopw,params_no_new_pw); //Use function to save their updated details into dbs

                                } else { //If falase,

                                    //Set user's details into hashmap to send via HTTP request without new password as the field is blank
                                    Map<String, String> params_new_pw = new HashMap<String, String>();
                                    params_new_pw.put("userid", userid);
                                    params_new_pw.put("username", username);
                                    params_new_pw.put("email", email);
                                    params_new_pw.put("birthdate", birthdate);
                                    params_new_pw.put("newpassword", new_pw);

                                    updateData(url_save_user_profile_pw,params_new_pw); //Use function to save their updated details into dbs

                                }
                            } else {

                                //Inform user if password does not match that from the dbs
                                Toast.makeText(getApplicationContext(), "Your password is incorrect. Please try again.",
                                        Toast.LENGTH_LONG).show();
                            }

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