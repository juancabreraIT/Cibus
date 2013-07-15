<?php
include_once('aditivo.php');

$aditivo = new aditivo();

if(isset($_POST['numero']) && isset($_POST['nombre']) && isset($_POST['descripcion']) && isset($_POST['caracter']) && isset($_POST['idTipoForeign'])){
	echo $aditivo->getJSONInsertAditivo($_POST['numero'], $_POST['nombre'],	$_POST['descripcion'], $_POST['caracter'], $_POST['idTipoForeign']);
}else{
	echo $aditivo->getJSONError('insert','Error en el envio de datos ADITIVOS');
}

?>