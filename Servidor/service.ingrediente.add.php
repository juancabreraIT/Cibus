<?php
include_once('ingrediente.php');

$ingrediente = new ingrediente();

if(isset($_POST['nombre']) && isset($_POST['descripcion'])){
	echo $ingrediente->getJSONInsertIngrediente($_POST['nombre'], $_POST['descripcion']);	
}else{
	echo $ingrediente->getJSONError('insert','Error en el envio de datos');
}

?>