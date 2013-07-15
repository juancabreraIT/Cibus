<?php
/* Clase TipoAditivo
 * int - idTipoAditivo
 * text - nombre
 * text - rango
*/
include_once('globals.php');
include_once('dbmanager.php');

class tipoaditivo{

	private function getTiposAditivos(){
		$sql = "SELECT l.* ";
		$sql .= " FROM tiposAditivos AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONTiposAditivos(){
		$json = "";
		$i = 0; 
		$result = $this->getTiposAditivos();
		$json .= " { \"tiposAditivos\" : [ ";

		while($row = mysql_fetch_array($result)){
			if($i > 0)
				$json .= ",";

			$json .= " { \"_id\" : ".$row['_id'].",
						\"nombre\": \"".$row['nombre']."\",
						\"rango\": \"".$row['rango']."\" ";
			$json .= "} ";   
			$i++;
		}

		$json .= " ] ";
		$json .= " } ";  
		return $json; 
	}
}