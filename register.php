<?php

	include_once "koneksi.php";

	class usr{}
	
	$username = array_key_exists('username', $_POST) ? $_POST['username'] : "";
	$password = array_key_exists('password', $_POST) ? $_POST['password'] : "";
	$confirm_password = array_key_exists('confirm_password', $_POST) ? $_POST['confirm_password'] : "";
	$first_name = array_key_exists('first_name', $_POST) ? $_POST['first_name'] : "";
	$last_name = array_key_exists('last_name', $_POST) ? $_POST['last_name'] : "";
	$email = array_key_exists('email', $_POST) ? $_POST['email'] : "";
	$phone = array_key_exists('phone', $_POST) ? $_POST['phone'] : "";
	$gender = array_key_exists('gender', $_POST) ? $_POST['gender'] : "";
	$birth = array_key_exists('birth', $_POST) ? $_POST['birth'] : "";
	$address = array_key_exists('address', $_POST) ? $_POST['address'] : "";
	$institution = array_key_exists('institution', $_POST) ? $_POST['institution'] : "";

	if (!empty($username) && $password == $confirm_password){
			$num_rows = mysqli_num_rows(mysqli_query($con, "SELECT * FROM tbl_register WHERE username='".$username."'"));

			if ($num_rows == 0){
				$passwordHash = password_hash ($password, PASSWORD_DEFAULT);
				$query = mysqli_query($con, "INSERT INTO tbl_register (username, password, id_user, first_name, last_name, email, phone, gender, birth, address, institution) VALUES('".$username."','".$passwordHash."','".$id."','".$first_name."','".$last_name."','".$email."','".$phone."','".$gender."','".$birth."','".$address."','".$institution."')");

				if ($query){
					$response = new usr();
					$response->success = 1;
					$response->message = "Register berhasil, silahkan login.";
					die(json_encode($response));

				} else {
					$response = new usr();
					$response->success = 0;
					$response->message = "Username sudah ada";
					die(json_encode($response));
				}
			} else {
				$response = new usr();
				$response->success = 0;
				$response->message = "Username sudah ada";
				die(json_encode($response));
			}
		
	}
	mysqli_close($con);

?>	