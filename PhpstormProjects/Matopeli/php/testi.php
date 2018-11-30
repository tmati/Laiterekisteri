<?php

        $servername = "localhost";
        $username = "dbuser02";
        $password = "1234";

// Create connection
        $conn =  new mysqli($servername, $username, $password, "pelisivusto");

// Check connection
        if ($conn->connect_error) {
            die("Connection failed: " . $this->conn->connect_error);

        }

    $sql = "select nimi, pisteet.pisteet, aika from pisteet order by pisteet.pisteet desc ";
    $result = mysqli_query($this->conn, $sql);
    $object = mysqli_fetch_object($result);
    $summa = "";
    while($object != null){
        echo json_encode($object);
        $object = mysqli_fetch_object($result);
        $string = json_encode($object);
        $summa = $summa . $string;
    }
    return $summa;


?>