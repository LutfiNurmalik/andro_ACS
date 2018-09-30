<?php
include_once "koneksi.php";
 
$sql = "select * from tbl_lim_country";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('judul'=>$row[0],
'link'=>$row[1],
'konten'=>$row[2]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>