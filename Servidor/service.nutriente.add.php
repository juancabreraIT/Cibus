<?php
include_once('nutriente.php');

$nutriente = new nutriente();

if(isset($_POST['nombre']) && isset($_POST['descripcion'])){
	echo $nutriente->getJSONInsertNutriente($_POST['nombre'], $_POST['descripcion']);	
}else{
	echo $nutriente->getJSONError('insert','Error en el envio de datos');
}

?>