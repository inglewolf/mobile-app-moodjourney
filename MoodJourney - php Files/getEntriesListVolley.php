<?php
/*Following code will retrieve all journal entries*/

if (isset($_POST['userid']) && isset($_POST['month'])) { //Check if user id and month are posted

    //Retrieve and save values into variables
    $userid = $_POST['userid'];
    $month = $_POST['month'];

//Include db connect class
require_once __DIR__ . '/db_connect.php';

//Connecting to db
$db= new DB_CONNECT();
$db->connect();

//Get all entries of current user
$sqlCommand="SELECT * FROM journalentry WHERE UserID = '$userid' AND Month = '$month' ";

//Connect to db and run sql query
$result =mysqli_query($db->myconn, "$sqlCommand");

$entryresult =""; //Initialise empty string to save results

//Check for empty result
if (mysqli_num_rows($result) > 0) {

    //Looping through all results
    while ($row = mysqli_fetch_array($result)) {
        
        //Each entry is separated by ":" & each detail is separated by ";"
        $entryresult = $entryresult.$row["Day"].";".$row["MoodID"].";".$row["EntryTitle"].";".$row["EntryID"].":";
    }
    //Return all entries with separators to requesting client
    echo ($entryresult);
} } else {

    //No entries found
    echo("Error"); }
?>
