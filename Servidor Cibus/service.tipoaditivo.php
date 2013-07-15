<?php
include_once('tipoaditivo.php');

$tipoaditivo = new tipoaditivo();
echo $tipoaditivo->getJSONTiposAditivos();
?>