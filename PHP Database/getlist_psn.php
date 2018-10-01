<?php
include_once "koneksi.php";

$psn = $_GET['psn'];

$sql = "select * from tbl_psn where psn = '$psn' ";
 
$result = mysqli_query($con, $sql);
 
while(($row = mysqli_fetch_assoc($result)) == true){
 
 $data[]=$row;
}
echo json_encode($data);
 
?>