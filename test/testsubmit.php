<?php

$response=array();
$response["mode"]=$_POST["mode"];
$response["water"]=$_POST["water"];
$response["proj"]=$_POST["proj"];
$response["file1"]=true;
$response["file2"]=true;
$response["file3"]=true;

if(isset($_FILES['file1']))
	move_uploaded_file($_FILES['file1']['tmp_name'],$_FILES['file1']['name']);
else
	$response["file1"]=false;

if(isset($_FILES['file2']))
	move_uploaded_file($_FILES['file2']['tmp_name'],$_FILES['file2']['name']);
else
	$response["file2"]=false;

if(isset($_FILES['file3']))
	move_uploaded_file($_FILES['file3']['tmp_name'],$_FILES['file3']['name']);
else
	$response["file3"]=false;

header("Content-Type:application/json;charset=utf-8");
echo json_encode($response);