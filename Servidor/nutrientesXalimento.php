<?php
/* Clase NutrientesXAlimento 
 * int - idAlimentoForeign
 * int - idNutrienteForeign
 * text - cantidad
*/
include_once('globals.php');
include_once('dbmanager.php');

class nutrientesXalimento {

	private function getNutrientesXAlimento(){
		$sql = "SELECT l.* ";
		$sql .= " FROM nutrientesXalimento AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONNutrientesXAlimento(){
		$json = "";
		$i = 0; 
		$result = $this->getNutrientesXAlimento();
		$json .= " { \"nutrientesXalimento\" : [ ";

		while($row = mysql_fetch_array($result)){
			if($i > 0)
				$json .= ",";

			$json .= " { \"idAlimentoForeign\" : ".$row['idAlimentoForeign'].",
						\"idNutrienteForeign\": \"".$row['idNutrienteForeign']."\",
						\"cantidad\": \"".$row['cantidad']."\" ";
			$json .= "} ";   
			$i++;
		}

		$json .= " ] ";
		$json .= " } ";  
		return $json; 
	}

	private function insertNutrientesXAlimento($idAlimento, $idNutriente, $cantidad){
		$sql = sprintf("INSERT INTO nutrientesXalimento (idAlimentoForeign, idNutrienteForeign, cantidad) 
		VALUES ('%d', '%d', '%s')", $idAlimento, $idNutriente, mysql_real_escape_string($cantidad));	
		
		$db = new dbmanager();
		$result = $db->executeInsertQuery2($sql);
		return $result;
	}

	public function getJSONInsertNutrientesXAlimento($idAlimento, $idNutriente, $cantidad){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$idNutrientesXAlimento = $this->insertNutrientesXAlimento($idAlimento, $idNutriente, $cantidad);

		if($idNutrientesXAlimento){
			$response["success"] = 1;
		}else{
			$response["error"] = 1;
			$response["error_msg"] = "Fallo al establecer conexion MySQL o al insertar.";
		}
		return json_encode($response);
	}

	public function getJSONError($query, $message){
		$response = array("tag" => $query, "success" => 0, "error" => 1);
		$response["error_msg"] = $message;
		return json_encode($response);
	}

}