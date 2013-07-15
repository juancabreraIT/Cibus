<?php
include_once('nutriente.php');

$nutriente = new nutriente();
echo $nutriente->getJSONNutrientes();
?>