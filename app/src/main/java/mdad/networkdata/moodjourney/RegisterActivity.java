package mdad.networkdata.moodjourney;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private ImageButton signupButton,datePicker; //Action buttons
    private TextView loginLink,dateView; //To display login text and selected date from datepicker
    private EditText password,confirmPassword,email,username; //Input fields
    private String u_name,e_mail,b_date,u_pw,c_pw; //To store text from input fields
    private static final String url_register_user = MainActivity.ipBaseAddress+"/registerVolley.php"; //Url for dbs comms


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        //Retrieve resource for buttons
        signupButton = (ImageButton) findViewById(R.id.signup2Button);
        datePicker = (ImageButton) findViewById(R.id.datePickerBtn);

        //Retrieve resource for TextViews
        loginLink = (TextView) findViewById(R.id.loginLink);
        dateView = (TextView) findViewById(R.id.birthdateView);

        //Retrieve resource for input fields
        password = (EditText) findViewById(R.id.PasswordInput);
        confirmPassword = (EditText) findViewById(R.id.regConfirmPassword);
        email = (EditText) findViewById(R.id.regEmailInput);
        username = (EditText) findViewById(R.id.regUsernameInput);

        //Sign up button click event
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Retrieve text entered by user and save into variables
                u_name = username.getText().toString();
                e_mail = email.getText().toString();
                b_date = dateView.getText().toString();
                u_pw = password.getText().toString();
                c_pw = confirmPassword.getText().toString();

                //Check for empty variables / input fields
                if (u_name.isEmpty() || e_mail.isEmpty() || b_date.isEmpty() || b_date.equals("Birthdate") || u_pw.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Ensure all the fields have been filled.",
                            Toast.LENGTH_LONG).show(); //Inform user of empty fields
                    return;
                }
                else if(!u_pw.equals(c_pw)) { //Check if both entered passwords are different
                    Toast.makeText(getApplicationContext(), "Your passwords do not match. Please try again.",
                            Toast.LENGTH_LONG).show(); //Inform user of difference
                    return;
                }

                // Log user input for debugging, output to LogCat
                Log.d("RegisterActivity", "Username: " + u_name);
                Log.d("RegisterActivity", "Email: " + e_mail);
                Log.d("RegisterActivity", "Birthdate: " + b_date);
                Log.d("RegisterActivity", "Password: " + u_pw);

                //Put the user's info as key-values pair in HashMap
                Map<String,String> params_create = new HashMap<String, String>();
                params_create.put("username",u_name);
                params_create.put("email",e_mail);
                params_create.put("birthdate",b_date);
                params_create.put("password",u_pw);

                //postData method to use Volley to register new user in the database
                postData(url_register_user,params_create);
            }
        });

        //Add click listener for the login link
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create an Intent here to load the Login activity when button clicked
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        //Adding click listener for date picker button
        datePicker.setOnClickListener(new View.OnClickListener() { //Launch datepicker when button clicked
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
                        RegisterActivity.this,
                        R.style.CustomDatePickerDialog,  // Apply custom style
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                //Set selected date into textview.
                                dateView.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            }
                        },
                        //Pass day, month, year of selected date.
                        year, month, day);

                // Add customization to the DatePickerDialog
                datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {

                        // Load the custom font
                        Typeface customFont = ResourcesCompat.getFont(RegisterActivity.this, R.font.josefin_sans_bold);
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

     private void postData(String url, Map params){ //Function to register/save new user
        //Create a RequestQueue for Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Create StringRequest for http post web request to send new user info to database
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Response from server
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("Error.User registration failed.")) //If request fails
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Error in registering user.Please try again",Toast.LENGTH_LONG).show();
                        }
                        else if(response.equals("Successfully created account.")) //If request succeeds
                        {
                            //Inform user
                            Toast.makeText(getApplicationContext(),"Your account has been successfully created! Log in now.",
                                    Toast.LENGTH_LONG).show();

                            //Load LoginActivity
                            Intent i = new Intent (getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                            finish();

                        } else if(response.equals("User already exists.")) { //If user already exists in database
                            Toast.makeText(getApplicationContext(),"User already exists.",
                                    Toast.LENGTH_LONG).show();  //Inform user
                        }
                    }
                },
                //Error in Volley
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Handle error
                        Toast.makeText(getApplicationContext(),"Error in registering user.Please try again",Toast.LENGTH_LONG).show();
                    }
                }
        ) {
            @Nullable
            @Override
            //To send product info stored in HashMap params_create to server via HTTP Post
            protected Map<String, String> getParams() {
                return params;
            }
        };

        //Add StringRequest to Volley Queue
        requestQueue.add(stringRequest);
    }}