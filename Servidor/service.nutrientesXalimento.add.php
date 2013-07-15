<?php
include_once('nutrientesXalimento.php');

$nutrientesXalimento = new nutrientesXalimento();

if(isset($_POST['idAlimentoForeign']) && isset($_POST['idNutrienteForeign']) && isset($_POST['cantidad'])){
	echo $nutrientesXalimento->getJSONInsertNutrientesXAlimento($_POST['idAlimentoForeign'], $_POST['idNutrienteForeign'], $_POST['cantidad']);
}else{
	echo $nutrientesXalimento->getJSONError('insert','Error en el envio de datos');
}

?>