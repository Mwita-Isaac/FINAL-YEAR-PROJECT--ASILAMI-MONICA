<?php
require "DataBase.php";
$db = new DataBase();
$username = $_POST['username'];
$password = $_POST['password'];

if (isset($_POST['username']) && isset($_POST['password'])) {

    if ($db->dbConnect()) {

        if ($db->logIn("user", $_POST['username'], $_POST['password'])) {

            echo "Login Success";

        } else echo "Username or Password wrong";

    } else echo "check your connection";

} else echo "All fields are required";

?>
