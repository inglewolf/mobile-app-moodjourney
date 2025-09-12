<?php
/*Following code will save changes done to an existing journal entry in the database*/

//Check that the entry's details have been posted and set
if (isset($_POST['entryid']) && isset($_POST['moodid']) && isset($_POST['entrytitle']) && isset($_POST['entrybody']) ) {
 
    //Retrieve the values from http post
    $entryid = $_POST['entryid'];
    $moodid = $_POST['moodid'];
    $entrytitle = $_POST['entrytitle'];
    $entrybody = $_POST['entrybody']; 
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Sql query to update that entry's details into its row
    $sqlCommand = "UPDATE journalentry SET MoodID = '$moodid', EntryTitle = '$entrytitle', JournalEntry = '$entrybody' WHERE EntryID = '$entryid'";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    if($result) {

        //Successfully inserted into database
        echo("Success");
        
    }
} else {

    echo("Error");
    
}    
?>