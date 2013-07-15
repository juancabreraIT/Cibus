<?php
include_once('ingredientesXalimento.php');

$ingredientesXalimento = new ingredientesXalimento();
echo $ingredientesXalimento->getJSONIngredientesXAlimento();
?>