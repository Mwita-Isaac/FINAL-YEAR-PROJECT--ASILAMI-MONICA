<?php
require "DataBase.php";
$db = new DataBase();
$location =  $_POST['location'];
$username = $_POST['username'];
$password = $_POST['password'];
$name =   $_POST['name'];

if (isset($name) && isset($username) && isset($password) && isset($location)) {

    if ($db->dbConnect()) {

        $userlocation = $db->getLocationId($location);
        
        if($userlocation != "location not found"){

            if ($db->signUp("user", $userlocation, $username, $password, $name)) {

                echo "Sign Up Success";

            } else echo "Sign up Failed";

        }else echo $userlocation." please add new information about the location and try again";

    } else echo "Error: Database connection";

} else echo "All fields are required ";
