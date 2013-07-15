<?php
include_once('ingredientesXalimento.php');

$ingredientesXalimento = new ingredientesXalimento();

if(isset($_POST['idAlimentoForeign']) && isset($_POST['idIngredienteForeign']) && isset($_POST['cantidad'])){	
	echo $ingredientesXalimento->getJSONInsertIngredientesXAlimento($_POST['idAlimentoForeign'], $_POST['idIngredienteForeign'], $_POST['cantidad']);
}else{
	echo $ingredientesXalimento->getJSONError('insert','Error en el envio de datos IngredientesXAlimento');
}

?>