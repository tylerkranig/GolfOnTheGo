<?php
include('dbConnection.php');

error_reporting(-1);
ini_set('display_errors','On');

$guildID = $_GET["guildID"];
$userID = $_GET["userID"];

addNewGuild($guildID, $userID);

function addNewGuild($guildID, $userID){
    global $con;
    $query = "Select * from db309amc1.guild where guildID = $guildID";
    
    $result = mysqli_query($con, $query);

    if($result->num_rows == 0){
        $data = ['result' => 0];
        echo json_encode($data);
    }
    else{
        $query = "insert into guildMember (guildID, userID) values ($guildID, $userID)";
        $con->query($query);

        if($con->affected_rows == 1){
            $data = ['result' => 1];
            echo json_encode($data);
        }
    }

}
?>