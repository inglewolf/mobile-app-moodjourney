<?php
/*Following code will save changes to an existing user's profile without a new password*/

//Check that the user's details have been posted and set
if (isset($_POST['userid']) && isset($_POST['username']) && isset($_POST['email']) && isset($_POST['birthdate']) ) {

    //Retrieve the values from http post
    $userid = $_POST['userid'];
    $username = $_POST['username'];
    $email = $_POST['email'];
    $birthdate = $_POST['birthdate'];

    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Sql query to update changes into selected row
    $sqlCommand = "UPDATE users SET Username = '$username',Email = '$email', Birthdate = '$birthdate' WHERE UserID = '$userid'";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    if($result) { //If successful, echo status

        echo("Success");

    } else { //Echo error status

        echo("Error");

    } 
} else {
    echo("Error");
}
?>