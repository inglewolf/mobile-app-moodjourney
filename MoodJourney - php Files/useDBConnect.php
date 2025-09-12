<?php

//Include db_connect.php file
require_once __DIR__ . '/db_connect.php';

//Check if connect button is set and submitted
if(isset($_POST["submit"])) {
    // create a new instance of DB_CONNECT class
    $db= new DB_CONNECT();

    //Call the function connect() in DB_CONNECT class
    $connection = $db->connect();
    if (!$connection)
        echo("Connection failed");
    else
        echo "Connected successfully";
    }
?>