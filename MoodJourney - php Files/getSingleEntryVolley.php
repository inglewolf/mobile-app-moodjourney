<?php
/*Following code will select a single journal entry from database*/

//Check that the entry id has been posted and set
if (isset($_POST['entryid'])) {
 
    //Retrieve entry id from http post
    $entryid = $_POST['entryid'];
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Select row of specified entry
    $sqlCommand = "SELECT * FROM `journalentry` WHERE EntryID = '$entryid'";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    //Initialise empty string
    $entry ="";

    if (($result)) { //If successful

        $row = mysqli_fetch_array($result);
        
        //Each row of product is separated by ":" & each product information is separated by ";"
        $entry = $entry.$row["EntryID"].";".$entry.$row["UserID"].";".$entry.$row["Month"].";".$entry.$row["Day"].";".$entry.$row["MoodID"].";".$entry.$row["EntryTitle"].";".$entry.$row["JournalEntry"];

        //Entry successfully retrieved
        echo("Success:"); 
        echo($entry); //Echo results
    }
} else {

    echo("Error");

}    
?>