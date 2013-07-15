<?php
/* Clase Alimento 
 * int - idAlimento
 * text - nombre
 * text - tipo
 * text - descripcion
 * text - formato
 * text - marca
 * text - submarca
 * text - codigo1D
 * text - codigo2D
*/
include_once('globals.php');
include_once('dbmanager.php');

class alimento{

	private function getAlimentos(){
		$sql = "SELECT l.* ";
		$sql .= " FROM alimentos AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONAlimentos(){
		$json = "";
		$i = 0; 
		$result = $this->getAlimentos();
		$json .= " { \"alimentos\" : [ ";

		while($row = mysql_fetch_array($result)){
			if($i > 0)
				$json .= ",";

			$json .= " { \"_id\" : ".$row['_id'].",
						\"nombre\": \"".$row['nombre']."\",
						\"tipo\": \"".$row['tipo']."\",
						\"descripcion\": \"".$row['descripcion']."\",
						\"formato\": \"".$row['formato']."\",
						\"marca\": \"".$row['marca']."\",
						\"submarca\": \"".$row['submarca']."\",
						\"codigo1D\": \"".$row['codigo1D']."\",
						\"codigo2D\": \"".$row['codigo2D']."\" ";    
			$json .= "} ";   
			$i++;
		}

		$json .= " ] ";
		$json .= " } ";  
		return $json; 
	}

	private function insertAlimento($nombre, $tipo, $descrip, $formato,
									$marca, $submarca, $codigo1d, $codigo2d){
		$sql = sprintf(
				"INSERT INTO alimentos 
				(nombre, tipo, descripcion, formato, marca, submarca, codigo1D, codigo2D) 
				VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
				mysql_real_escape_string($nombre), mysql_real_escape_string($tipo),
				mysql_real_escape_string($descrip), mysql_real_escape_string($formato),
				mysql_real_escape_string($marca), mysql_real_escape_string($submarca),
				mysql_real_escape_string($codigo1d),mysql_real_escape_string($codigo2d)
				);	
		
		$db = new dbmanager();
		$result = $db->executeInsertQuery($sql);
		return $result;
	}

	public function getJSONInsertAlimento($nombre, $tipo, $descrip, $formato,
										$marca, $submarca, $codigo1d, $codigo2d){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$idAlimento = $this->insertAlimento($nombre, $tipo, $descrip, $formato,
										$marca, $submarca, $codigo1d, $codigo2d);

		if($idAlimento > 0){
			$response["success"] = 1;
			$response["alimento"]["_id"] = $idAlimento;
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