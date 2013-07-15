<?php
include_once('ingrediente.php');

$ingrediente = new ingrediente();
echo $ingrediente->getJSONIngredientes();
?>