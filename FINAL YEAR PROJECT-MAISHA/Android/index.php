<?php
$db = mysqli_connect("localhost", "root", "");
mysqli_select_db($db,"android");

$act = $_POST['act'];
//$act = "read";
$user = $_POST['username'];
//$user = "user01@gmail.com";
$userlocation = null;
$data = array();
$cow=0;
$pig=0;
$sheep=0;
$goat=0;
$sum =0;

$take = mysqli_query($db,"SELECT * FROM user WHERE user_name = '$user'" );
    if($take){
        while ($takerow = mysqli_fetch_array($take)) { 
            $userlocation = $takerow['user_location_id'];
        } 
    }

if($act == "read"){

    $result00 = mysqli_query($db,"SELECT * FROM user JOIN location ON user.user_location_id=location.location_id WHERE user_name = '$user'" );
    if($result00){
        while ($row00 = mysqli_fetch_array($result00)) { 
            $data= array('uname' => $row00["user_fullname"], 'location' => $row00["ward_name"],'level' => $row00["level"]);
        } 
    }

    $result = mysqli_query($db,"SELECT * FROM zizi WHERE zizi_location_id = '$userlocation'" );
    if($result){
        if (mysqli_num_rows($result) > 0) {
            while ($row = mysqli_fetch_array($result)) { 
                $cow=$cow+$row["cow"];
                $pig=$pig+$row["pig"];
                $sheep=$sheep+$row["sheep"];
                $goat=$goat+$row["goat"];
               }
               $data['animaldata'] = array('cow' => $cow,'pig' => $pig, 'sheep' => $sheep, 'goat' => $goat);
               echo json_encode($data);
        } else {
            $data['animaldata'] = array('cow' => 0,'pig' => 0, 'sheep' => 0, 'goat' => 0);
            echo json_encode($data);
        }
    }

}

elseif($act == "upload"){

    $cow = $_POST["cow"];
    $sheep = $_POST["sheep"];
    $pig = $_POST["pig"];
    $goat = $_POST["goat"];
    $zizi = $_POST["owner"];

    $result = mysqli_query($db,"UPDATE zizi SET pig = '$pig', cow = '$cow', sheep = '$sheep', goat = '$goat' WHERE zizi_name = '$zizi' " );
    if($result){
        echo "process done";
    }else{
        echo "process fail";
    }

}

elseif($act == "register"){

    $name = $_POST["owner"];
    $cow = $_POST["cow"];
    $sheep = $_POST["sheep"];
    $pig = $_POST["pig"];
    $goat = $_POST["goat"];

    $result = mysqli_query($db,"INSERT INTO zizi (pig, cow, sheep, goat, zizi_location_id , zizi_name) VALUES ('$pig','$cow','$sheep','$goat','$userlocation','$name')" );
    if($result){
        echo "process done";
    }else{
        echo "process fail";
    }

}

elseif($act == "all"){
    $result00 = mysqli_query($db,"SELECT * FROM user JOIN location ON user.user_location_id=location.location_id WHERE user_name = '$user'" );
    if($result00){
        while ($row00 = mysqli_fetch_array($result00)) { 
            $data= array('uname' => $row00["user_fullname"], 'location' => $row00["ward_name"],'level' => $row00["level"]);
        } 
    }
    $result = mysqli_query($db,"SELECT * FROM zizi WHERE zizi_location_id = '$userlocation'" );
    if($result){
        if (mysqli_num_rows($result) > 0) {
            while ($row = mysqli_fetch_array($result)) { 
                $sum=$sum+1;

                $data[$sum]=array('name' => $row['zizi_name'], 'cow' => $row['cow'], 'pig' => $row['pig'], 'goat' => $row['goat'], 'sheep' => $row['sheep']);
               }
               $data['sum'] = array('sum' => $sum);
               echo json_encode($data);
               //echo "hello";
        } else {
            echo "No data";
        }
    }
}