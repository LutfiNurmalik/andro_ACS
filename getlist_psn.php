<?php

include_once "koneksi.php";



$psn = $_GET['psn'];

// $un_id = array_key_exists('un_id', $_POST) ? $_POST['un_id'] : "";
// $psn = array_key_exists('psn', $_POST) ? $_POST['psn'] : "";

$sql = "select * from tbl_psn where psn = '$psn' ";
 
$result = mysqli_query($con, $sql);
 
while(($row = mysqli_fetch_assoc($result)) == true){
 
 $data[]=$row;
}
echo json_encode($data);

// $res = mysqli_query($con,$sql);
 
// $result = array();
 
// while($row = mysqli_fetch_array($res)){
// array_push($result,
// array('un_id'=>$row[0],
// 'psn'=>$row[1],
// 'class'=>$row[2],
// 'hazard'=>$row[3],
// 'pg'=>$row[4],
// 'pa_pi'=>$row[5],
// 'pa_net_qty'=>$row[6],
// 'cao_pi'=>$row[7],
// 'cao_net_qty'=>$row[8],
// 'sp'=>$row[9],
// 'erg'=>$row[10]
// ));
// }
 
// echo json_encode(array("result"=>$result));
 
// mysqli_close($con);
 
?>
