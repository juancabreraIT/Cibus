<?php
include_once('alimento.php');

$alimento = new alimento();
echo $alimento->getJSONAlimentos();
?>