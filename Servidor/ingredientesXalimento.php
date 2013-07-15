<?php
/* Clase IngredientesXAlimento 
 * int - idAlimentoForeign
 * int - idIngredienteForeign
 * text - cantidad
*/
include_once('globals.php');
include_once('dbmanager.php');

class ingredientesXalimento{

	private function getIngredientesXAlimento(){
		$sql = "SELECT l.* ";
		$sql .= " FROM ingredientesXalimento AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONIngredientesXAlimento(){
		$json = "";
		$i = 0; 
		$result = $this->getIngredientesXAlimento();
		$json .= " { \"ingredientesXalimento\" : [ ";

		while($row = mysql_fetch_array($result)){
			if($i > 0)
				$json .= ",";
 
			$json .= " { \"idAlimentoForeign\" : ".$row['idAlimentoForeign'].",
						\"idIngredienteForeign\": \"".$row['idIngredienteForeign']."\",
						\"cantidad\": \"".$row['cantidad']."\" ";
			$json .= "} ";   
			$i++;
		}  
 
		$json .= " ] ";
		$json .= " } ";  
		return $json; 
	}

	private function insertIngredientesXAlimento($idAlimento, $idIngrediente, $cantidad){
		$sql = sprintf("INSERT INTO ingredientesXalimento (idAlimentoForeign, idIngredienteForeign, cantidad) 
		VALUES ('%d', '%d', '%s')", $idAlimento, $idIngrediente, mysql_real_escape_string($cantidad));
		
		$db = new dbmanager();
		$result = $db->executeInsertQuery2($sql);
		return $result;
	}

	public function getJSONInsertIngredientesXAlimento($idAlimento, $idIngrediente, $cantidad){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$idIngredientesXAlimento = $this->insertIngredientesXAlimento($idAlimento, $idIngrediente, $cantidad);

		if($idIngredientesXAlimento){
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