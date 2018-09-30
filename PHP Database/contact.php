<?php

	include_once "koneksi.php";

	class usr{}
	
	$nama = array_key_exists('nama', $_POST) ? $_POST['nama'] : "";
	$email = array_key_exists('email', $_POST) ? $_POST['email'] : "";
	$telephone = array_key_exists('telephone', $_POST) ? $_POST['telephone'] : "";
	$pesan = array_key_exists('pesan', $_POST) ? $_POST['pesan'] : "";


		$query = $con, "INSERT INTO tbl_contact (nama, email, telephone, pesan) VALUES('".$nama."','".$email."','".$telephone."','".$pesan."')";
		$num_rows = mysqli_query($query);


				if ($num_rows){
					$response = new usr();
					$response->success = 1;
					$response->message = "Kirim Pesan Berhasil";
					die(json_encode($response));

				} else {
					$response = new usr();
					$response->success = 0;
					$response->message = "Kirim Pesan Gagal";
					die(json_encode($response));
				}
			

	mysqli_close($con);

?>	