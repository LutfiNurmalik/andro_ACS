<?php
	
	include_once "koneksi.php";
	class usr{}
	
		
	$username = array_key_exists('username', $_POST) ? $_POST['username'] : "";
	$password = array_key_exists('password', $_POST) ? $_POST['password'] : "";
	if ((empty($username)) || (empty($password))) { 
		$response = new usr();
		$response->success = 0;
		$response->message = "Kolom tidak boleh kosong"; 
		die(json_encode($response));
	}
	
	$query = mysqli_query($con, "SELECT * FROM tbl_register WHERE username='$username'");
	$row = mysqli_fetch_array($query);
	$password_hash = $row['password'];
	if($row>0){
		if (password_verify($password,$password_hash)){
			$response = new usr();
			$response->success = 1;
			$response->message = "Selamat datang ".$row['username'];
			$response->username = $row['username'];
			echo json_encode($response);
			
		} else { 
			$response = new usr();
			$response->success = 0;
			$response->message = "Username atau password salah betul";
			die(json_encode($response));
		}
	}
	mysqli_close($con);
?>