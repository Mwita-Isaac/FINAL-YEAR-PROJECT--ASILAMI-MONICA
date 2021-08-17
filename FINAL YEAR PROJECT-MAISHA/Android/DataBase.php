<?php
require "DataBaseConfig.php";

class DataBase
{
    public $connect;
    public $data;
    private $sql;
    protected $servername;
    protected $username;
    protected $password;
    protected $databasename;

    public function __construct()
    {
        $this->connect = null;
        $this->data = null;
        $this->sql = null;
        $dbc = new DataBaseConfig();
        $this->servername = $dbc->servername;
        $this->username = $dbc->username;
        $this->password = $dbc->password;
        $this->databasename = $dbc->databasename;
    }

    function dbConnect()
    {
        $this->connect = mysqli_connect($this->servername, $this->username, $this->password, $this->databasename);
        return $this->connect;
    }

    function prepareData($data)
    {
        return mysqli_real_escape_string($this->connect, stripslashes(htmlspecialchars($data)));
    }

    function logIn($table, $username, $password)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $this->sql = "SELECT * FROM $table WHERE user_name = '$username'";
        $result = mysqli_query($this->connect, $this->sql);

        if ($result) {
            if (mysqli_num_rows($result) > 0) {
                $hello = mysqli_fetch_array($result);
                $hash_pass = $hello['user_pass'];
                if (password_verify($password, $hash_pass)) {
                    $login = true;
                } else $login = false;
            } else $login = false;
            return $login;
        }
    }

    function signUp($table, $location, $username, $password,$fullname)
    {
        $username = $this->prepareData($username);
        $password = $this->prepareData($password);
        $location = $this->prepareData($location);
        $password = password_hash($password, PASSWORD_DEFAULT);
        $this->sql = "INSERT INTO  $table (user_name, user_pass, user_location_id, user_fullname,level) VALUES ('$username','$password','$location','$fullname','ward')";
        if (mysqli_query($this->connect, $this->sql)) {
            return true;
        } else return false;
    }

    function getLocationId($location){
        $this->sql = "SELECT * FROM location WHERE ward_name = '$location'";
        $take = mysqli_query($this->connect,$this->sql);
        if(mysqli_num_rows($take)  > 0){
            while ($takerow = mysqli_fetch_array($take)) { 
                return $takerow['location_id'];
            } 
        }else{
            return "location not found";
        }

    }
}
