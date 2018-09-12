<?php

include_once "koneksi.php";

$response = array();
// // include db connect class
// mysqli_connect("localhost","root","");
// mysqli_select_db("dbregulations");
 
//  get by classification

class usr {}
$result = mysqli_query($con, "SELECT judul FROM tbl_lim_airline WHERE 1") or die(mysqli_error());
 
// cek
if (mysqli_num_rows($result) > 0) {
    // looping hasil
    // classification node
    $response["data"] = array();
     
    while ($row = mysqli_fetch_array($result)) {
        $airline = array();
        $airline["judul"] = $row[0];
        // masukan classification pada $response
        array_push($response["data"], $airline);
    }
    // sukses
    $response["success"] = 1;
 
    // echo JSON response
    echo json_encode($response);
} else {
    $response["success"] = 0;
    $response["message"] = "Tidak ada data yang ditemukan";
 
    echo json_encode($response);
}
?>