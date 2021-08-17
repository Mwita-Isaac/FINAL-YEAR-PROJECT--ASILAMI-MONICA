<?php
require "DataBase.php";
$db = new DataBase();
$username = $_POST['username'];
$password = $_POST['password'];

if (isset($username) && isset($password)) {

    if ($db->dbConnect()) {
        $result=$db->logIn("user", $username, $password);

        if ($result=="admin" || $result=="ward") {

            echo $result;

        } else echo "Username or Password wrong";
        

    } else echo "check your connection";

} else echo "All fields are required";

?>
