<?php
include_once('aditivosXalimento.php');

$aditivosXalimento = new aditivosXalimento();

if(isset($_POST['idAlimentoForeign']) && isset($_POST['idAditivoForeign'])){
	echo $aditivosXalimento->getJSONInsertAditivosXAlimento($_POST['idAlimentoForeign'], $_POST['idAditivoForeign']);	
}else{
	echo $aditivosXalimento->getJSONError('insert','Error en el envio de datos');
}

?>