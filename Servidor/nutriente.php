<?php
/* Clase Nutriente
 * int - idNutriente
 * text - nombre
 * text - descripcion
*/
include_once('globals.php');
include_once('dbmanager.php');

class nutriente{

	private function getNutrientes(){
		$sql = "SELECT l.* ";
		$sql .= " FROM nutrientes AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONNutrientes(){
		$json = "";
		$i = 0; 
		$result = $this->getNutrientes();
		$json .= " { \"nutrientes\" : [ ";

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

	private function insertNutriente($nombre, $descrip){
		$sql = sprintf("INSERT INTO nutrientes (nombre, descripcion) 
		VALUES ('%s', '%s')", mysql_real_escape_string($nombre), mysql_real_escape_string($descrip));

		$db = new dbmanager();
		$result = $db->executeInsertQuery($sql);
		return $result;
	}

	public function getJSONInsertNutriente($nombre, $descrip){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$idNutriente = $this->insertNutriente($nombre, $descrip);

		if($idNutriente > 0){
			$response["success"] = 1;
			$response["nutriente"]["_id"] = $idNutriente;
		}else{
			$response["error"] = 1;
			if($idNutriente != 0 && $idNutriente == false){
				$response["error_msg"] = "Fallo al establecer conexion MySQL";			
			}else{
				$response["error_msg"] = $idNutriente;
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