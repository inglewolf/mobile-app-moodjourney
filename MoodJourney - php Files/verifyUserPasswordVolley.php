<?php
/*Following code will retrieve user's password for verification*/

//Check that the user id has been posted and set
if (isset($_POST['userid'])) {
 
    //Retrieve the values from http post
    $userid = $_POST['userid'];
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Sql query to select current user's saved password
    $sqlCommand = "SELECT Password FROM users WHERE UserID = '$userid'";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    //Initialise empty string
    $user ="";

    if (($result)) { //If successful

        $row = mysqli_fetch_array($result);
        
        $user = $user.$row["Password"]; //Save password

        //Echo password and status
        echo("Success_"); 
        echo($user); 
    }
} else {

    echo("Error"); //Echo status

}    
?>