<?php
/* Clase AditivosXAlimento 
 * int - idAlimentoForeign
 * int - idAditivoForeign
*/
include_once('globals.php');
include_once('dbmanager.php');

class aditivosXalimento{

	private function getAditivosXAlimento(){
		$sql = "SELECT l.* ";
		$sql .= " FROM aditivosXalimento AS l ";
		$db = new dbmanager();
		return $db->executeSelectQuery($sql);
	}

	public function getJSONAditivosXAlimento(){
		$json = "";
		$i = 0; 
		$result = $this->getAditivosXAlimento();
		$json .= " { \"aditivosXalimento\" : [ ";

		while($row = mysql_fetch_array($result)){
			if($i > 0)
				$json .= ",";

			$json .= " { \"idAlimentoForeign\" : ".$row['idAlimentoForeign'].",
						\"idAditivoForeign\": \"".$row['idAditivoForeign']."\" ";
			$json .= "} ";   
			$i++;
		}

		$json .= " ] ";
		$json .= " } ";  
		return $json; 
	}

	private function insertAditivosXAlimento($idAlimento, $idAditivo){
		$sql = sprintf("INSERT INTO aditivosXalimento (idAlimentoForeign, idAditivoForeign) 
		VALUES ('%d', '%d')", $idAlimento, $idAditivo);	
		
		$db = new dbmanager();
		$result = $db->executeInsertQuery2($sql);
		return $result;
	}

	public function getJSONInsertAditivosXAlimento($idAlimento, $idAditivo){
		$response = array("tag" => "insert", "success" => 0, "error" => 0);
		$idAditivosXAlimento = $this->insertAditivosXAlimento($idAlimento, $idAditivo);

		if($idAditivosXAlimento){
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