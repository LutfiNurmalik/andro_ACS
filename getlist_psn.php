<?php

include_once "koneksi.php";

$response = array();
// // include db connect class
// mysqli_connect("localhost","root","");
// mysqli_select_db("dbregulations");
 
//  get by classification
//hai
class usr {}
$result = mysqli_query($con, "SELECT * FROM tbl_psn WHERE 1") or die(mysqli_error());
 
// cek
if (mysqli_num_rows($result) > 0) {
    // looping hasil
    // classification node
    $response["data"] = array();
     
    while ($row = mysqli_fetch_array($result)) {
        $psn = array();
        $psn["un_id"] = $row[0];
        $psn["psn"] = $row[1];
        $psn["class"] = $row[2];
        $psn["hazard"] = $row[3];
        $psn["pg"] = $row[4];
        $psn["pa_pi"] = $row[5];
        $psn["pa_net_qty"] = $row[6];
        $psn["cao_pi"] = $row[7];
        $psn["cao_net_qty"] = $row[8];
        $psn["sp"] = $row[9];
        $psn["erg"] = $row[10];
        // masukan classification pada $response
        array_push($response["data"], $psn);
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