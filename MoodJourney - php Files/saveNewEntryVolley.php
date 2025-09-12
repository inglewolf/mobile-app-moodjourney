<?php
/*Following code will add new entry to the database*/

//Check that the entry's details have been posted and set
if (isset($_POST['day']) && isset($_POST['month']) && isset($_POST['year']) && isset($_POST['entrytitle']) && isset($_POST['entrybody']) && isset($_POST['moodid']) && isset($_POST['userid']) ) {
 
    //Retrieve the values from http post
    $day = $_POST['day'];
    $month = $_POST['month'];
    $year = $_POST['year'];
    $moodid = $_POST['moodid'];
    $entrytitle = $_POST['entrytitle'];
    $entrybody = $_POST['entrybody'];
    $userid = $_POST['userid']; 
 
    //Include db connect class
    require_once __DIR__ . '/db_connect.php';

    //Connecting to db
    $myConnection= new DB_CONNECT();
    $myConnection->connect();

    //Sql query to insert row into the journalentry table
    $sqlCommand = "INSERT INTO journalentry (UserID, Month, Year, Day, MoodID, EntryTitle, JournalEntry) VALUES ('$userid','$month','$year','$day','$moodid','$entrytitle','$entrybody')";

    //Connect to db and run sql query
    $result = mysqli_query($myConnection->myconn, "$sqlCommand");

    //Initialise empty string
    $entryid ="";

    if($result) { //If successful, retrieve the new entry's id

        $sqlCommand = "SELECT EntryID FROM journalentry WHERE UserID = '$userid' AND Month = '$month' AND Year = '$year' AND Day = '$day'";

        $result = mysqli_query($myConnection->myconn, "$sqlCommand");

        $row = mysqli_fetch_array($result);
        
        $entryid = $entryid.$row["EntryID"]; //Save entry id into variable

        echo("Pass:"); //Echo status and entry id
        echo($entryid);

    }

} else {

    echo("Fail"); //Echo status

}    
?>