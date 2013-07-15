<?php
/* Clase Aditivo
 * int - idAditivo
 * text - numero
 * text - nombre
 * text - descripcion
 * text - caracter
 * int - idTipoForeign
*/
include_once('globals.php');
include_once('dbmanager.php');

class aditivo{

	private function getAditivos(){
		$sql = "SELECT l.* ";
		$sql .= " FROM aditivos AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONAditivos(){
		$json = "";
		$i = 0; 
		$result = $this->getAditivos();
		$json .= " { \"aditivos\" : [ ";

		while($row = mysql_fetch_array($result)){
			if($i > 0)
				$json .= ",";

			$json .= " { \"_id\" : ".$row['_id'].",
						\"numero\": \"".$row['numero']."\",
						\"nombre\": \"".$row['nombre']."\",
						\"descripcion\": \"".$row['descripcion']."\",
						\"caracter\": \"".$row['caracter']."\",
						\"idTipoForeign\": \"".$row['idTipoForeign']."\" ";
			$json .= "} ";   
			$i++;
		}

		$json .= " ] ";
		$json .= " } ";  
		return $json; 
	}

	private function insertAditivo($numero, $nombre, $descripcion, $caracter, $idTipo){
		$sql = sprintf("INSERT INTO aditivos (numero, nombre, descripcion, caracter, idTipoForeign) 
						VALUES ('%s', '%s', '%s', '%s', '%d')", 
						mysql_real_escape_string($numero),
						mysql_real_escape_string($nombre),
						mysql_real_escape_string($descripcion),
						mysql_real_escape_string($caracter),
						$idTipo);
					
		$db = new dbmanager();
		$result = $db->executeInsertQuery($sql);
		return $result;
	}
	
	public function getJSONInsertAditivo($numero, $nombre, $descripcion, $caracter, $idTipo){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$idAditivo = $this->insertAditivo($numero, $nombre, $descripcion, $caracter, $idTipo);
		
		if($idAditivo > 0){
			$response["success"] = 1;
			$response["aditivo"]["_id"] = $idAditivo;
		}else{
			$response["error"] = 1;
			if($idAditivo == false){
				$response["error_msg"] = "Fallo al establecer conexion MySQL";
			}else{
				$response["error_msg"] = "Fallo al insertar el ingrediente";
			}
		}
		return json_encode($response);
	}
	
	private function updateAditivo($numero, $nombre, $descripcion, $caracter, $idTipo){
		$nombre = mysql_real_escape_string($nombre);
		$descripcion = mysql_real_escape_string($descripcion);
		$caracter = mysql_real_escape_string($caracter);
		$sql = sprintf("UPDATE aditivos SET nombre = '$nombre', descripcion = '$descripcion',
						caracter = '$caracter', idTipoForeign = $idTipo WHERE numero = '$numero'");
		
		$db = new dbmanager();
		$result = $db->executeUpdateQuery($sql);
		return $result;
	}
	
	public function getJSONUpdateAditivo($numero, $nombre, $descripcion, $caracter, $idTipo){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$numRows = $this->updateAditivo($numero, $nombre, $descripcion, $caracter, $idTipo);
		
		if($numRows > 0){
			$response["success"] = 1;
			$response["aditivo"]["numRows"] = $numRows;
		}else{
			$response["error"] = 1;
			$response["error_msg"] = "Falló la consulta update.";
		}
		return json_encode($response);
	}
	
	public function getJSONError($query, $message){
		$response = array("tag" => $query, "success" => 0, "error" => 1);
		$response["error_msg"] = $message;
		return json_encode($response);	
	}
}