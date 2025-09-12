<?php
/*Following code will add new user to the database*/

//Check that the required user details have been posted and set
if (isset($_POST['username']) && isset($_POST['email']) && isset($_POST['birthdate']) && isset($_POST['password']) ) {
 
    //Retrieve the values from http post
    $username = $_POST['username'];
    $email = $_POST['email'];
    $birthdate = $_POST['birthdate'];
    $password = $_POST['password']; 
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Query to check if user already exists
    $sqlCommand = "SELECT * FROM users WHERE Username = '$username' OR Email = '$email'";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    if(mysqli_num_rows($result) == 0) {

        //Set sql query to insert new user's details as a row into users table
        $sqlCommand="INSERT INTO users(Username, Email, Birthdate, Password) VALUES ('$username','$email','$birthdate','$password')";
 
        //Connect to db and run sql query
        $result = mysqli_query($myConnection->myconn, "$sqlCommand");

        //Check if row inserted or not
        if ($result) {

            //Successfully inserted into database
            echo("Successfully created account.");
 
        } else {

            //Failed to insert row
            echo("Error.User registration failed.");
        }

    } else if ((mysqli_num_rows($result) > 0)) {

        //User already exists
        echo("User already exists."); 
    }
} else { //Error connecting to db

    echo("Error.User registration failed.");
    
}    
?>