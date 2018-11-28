<?php
/**
 * Created by PhpStorm.
 * User: Kimmo
 * Date: 28.11.2018
 * Time: 13:24
 */

class phpclass
{
    private $conn;
    public function __construct()
    {
        $servername = "localhost";
        $username = "dbuser02";
        $password = "1234";

// Create connection
        $this->conn =  new mysqli($servername, $username, $password, "pelisivusto");

// Check connection
        if ($this->conn->connect_error) {
            die("Connection failed: " . $this->conn->connect_error);

        }
}
public function getTable(){
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

}
public function updateTable($nimi, $pisteet, $aika){
    $sql2 = "select sija from pisteet order by sija desc ";
    $result = mysqli_query($this->conn, $sql2);
    $fieldinfo = mysqli_fetch_assoc($result);
    $sija =  $fieldinfo["sija"] +1;
    $sql= "insert INTO pisteet (Id, pisteet, sija, nimi, aika) VALUES (1, ".$pisteet.", ".$sija.", '".$nimi."', ".$aika.")";
    mysqli_query($this->conn, $sql);
}

}