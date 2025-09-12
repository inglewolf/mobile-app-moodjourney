<?php
/*Following code will check user's enter login infromation against the database*/

//Check that the required user details have been posted and set
if (isset($_POST['username']) && isset($_POST['password']) ) {

    //Retrieve the info from http post
    $username =  $_POST['username'];
    $password =  $_POST['password'];
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Select row that contains entered username
    $sqlCommand = "SELECT * FROM users WHERE Username = '$username'";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    //Initialise empty string
    $userresult ="";

    if(mysqli_num_rows($result) == 0) { //If no user found, echo status
        
        echo("No result");

    } else if ((mysqli_num_rows($result) > 0)) { //If user found, save user's password and id

        while ($row = mysqli_fetch_array($result)) {

            $passwordDB = $userresult.$row["Password"];
            $userresult = $userresult.$row["UserID"]; 

            }

        if ($password==$passwordDB) { //Compare if entered password matches dbs password.

        //If yes, echo success and result
        echo("Success:"); 
        echo($userresult); 

        } else {

            echo("Error"); }
    }
} else {

    echo("Error");

}    
?>