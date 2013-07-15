<?php
/* Clase Ingredientes 
 * int - idIngrediente
 * text - nombre
 * text - descripcion
*/
include_once('globals.php');
include_once('dbmanager.php');

class ingrediente{

	private function getIngredientes(){
		$sql = "SELECT l.* ";
		$sql .= " FROM ingredientes AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONIngredientes(){
		$json = "";
		$i = 0; 
		$result = $this->getIngredientes();
		$json .= " { \"ingredientes\" : [ ";

		while($row = mysql_fetch_array($result)){
			if($i > 0)
				$json .= ",";
 
			$json .= " { \"_id\" : ".$row['_id'].", \"nombre\": \"".$row['nombre']."\", \"descripcion\": \"".$row['descripcion']."\" ";    
			$json .= "} ";   
			$i++;
		}  
 
		$json .= " ] ";
		$json .= " } ";  
		return $json; 
	}

	private function insertIngrediente($nombre, $descrip){
		$sql = sprintf("INSERT INTO ingredientes (nombre, descripcion) 
		VALUES ('%s', '%s')", mysql_real_escape_string($nombre), mysql_real_escape_string($descrip));	
		
		$db = new dbmanager();
		$result = $db->executeInsertQuery($sql);
		return $result;
	}

	public function getJSONInsertIngrediente($nombre, $descrip){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$idIngrediente = $this->insertIngrediente($nombre, $descrip);

		if($idIngrediente > 0){
			$response["success"] = 1;
			$response["ingrediente"]["_id"] = $idIngrediente;
		}else{
			$response["error"] = 1;
			if($idIngrediente == false){
				$response["error_msg"] = "Fallo al establecer conexion MySQL";			
			}else{
				$response["error_msg"] = "Fallo al insertar el ingrediente";
			}
		}
		return json_encode($response);
	}

	public function getJSONError($query, $message){
		$response = array("tag" => $query, "success" => 0, "error" => 1);
		$response["error_msg"] = $message;
		return json_encode($response);
	}

}