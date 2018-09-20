<?php

define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DB','dbregulations');
 
$con = mysqli_connect(HOST,USER,PASS,DB);

$sql = "select link, konten from tbl_lim_airline";
 
$res = mysqli_query($con,$sql);
 
$result = array();
 
while($row = mysqli_fetch_array($res)){
array_push($result,
array('link'=>$row[0],
'konten'=>$row[1]
));
}
 
echo json_encode(array("result"=>$result));
 
mysqli_close($con);
 
?>
