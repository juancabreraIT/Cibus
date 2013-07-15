<?php
include_once('nutrientesXalimento.php');

$nutrientesXalimento = new nutrientesXalimento();
echo $nutrientesXalimento->getJSONNutrientesXAlimento();
?>