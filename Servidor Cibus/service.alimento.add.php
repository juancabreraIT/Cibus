<?php
include_once('alimento.php');

$alimento = new alimento();

if(isset($_POST['nombre']) && isset($_POST['tipo']) && isset($_POST['descripcion']) && isset($_POST['formato']) && isset($_POST['marca']) && isset($_POST['submarca']) && isset($_POST['codigo1D']) && isset($_POST['codigo2D'])){
	echo $alimento->getJSONInsertAlimento($_POST['nombre'], $_POST['tipo'], $_POST['descripcion'], $_POST['formato'], $_POST['marca'], $_POST['submarca'], $_POST['codigo1D'], $_POST['codigo2D']);
}else{
	echo $alimento->getJSONError('insert','Error en el envio de datos Alimento');
}

?>