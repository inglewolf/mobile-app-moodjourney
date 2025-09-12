<?php
/*Following code will delete selected journal entry from database*/

//Check that the entry's id has been posted and set
if (isset($_POST['entryid'])) {
 
    //Save entryid into variable
    $entryid = $_POST['entryid'];
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Set first sql query to be executed,select user id of selected entry
    $sqlCommand1 = "SELECT UserID FROM journalentry WHERE EntryID = '$entryid'";

    //Initialise empty string to save result
    $userid = "";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand1");

    if ($result) { //If successful

        $row = mysqli_fetch_array($result);
        $userid = $userid.$row["UserID"]; //Save user id

        //Delete entry
        $sqlCommand2 = "DELETE FROM journalentry WHERE EntryID = '$entryid'";

        //Connect to db and run sql query
        $result = mysqli_query($myConnection->myconn, "$sqlCommand2");

        if($result) { //If successful

        echo("Success:"); //Echo status & userid
        echo($userid);

        } else {
            echo("Error"); //Echo status
        }
    
    } else {
        echo("Error");
    }
} else {

    echo("Error");

}    
?>