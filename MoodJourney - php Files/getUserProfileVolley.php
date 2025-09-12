<?php
/*Following code will retrieve user's details from the database*/

//Check that the user id has been posted and set
if (isset($_POST['userid'])) {
 
    //Retrieve the user id from http post
    $userid = $_POST['userid'];
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Select row of specified user id
    $sqlCommand = "SELECT Username, Email, Birthdate FROM users WHERE UserID = '$userid'";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    //Initialise empty string
    $user ="";

    if (($result)) {

        $row = mysqli_fetch_array($result);
        
        //Each row of product is separated by ":" & each product information is separated by ";"
        $user = $user.$row["Username"].";".$user.$row["Email"].";".$user.$row["Birthdate"];

        //Echo user's details and status
        echo("Success_"); 
        echo($user); 
    }
} else {

    echo("Error");

}    
?>