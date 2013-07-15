<?php
/* Gestión de base de datos y consultas */
include_once('globals.php');

class dbmanager{

	private $con = null;

	public function executeSelectQuery($sql){
		$con = mysql_connect(config::getBBDDServer(), config::getBBDDUser(), config::getBBDDPwd());
		if (!$con){
			die('Could not connect: ' . mysql_error());
		}

		mysql_select_db(config::getBBDDName(), $con);

		$result = mysql_query($sql);

		mysql_close($con);

		return $result;
	}
	
	public function executeInsertQuery($sql){
		$con = mysql_connect(config::getBBDDServer(), config::getBBDDUser(), config::getBBDDPwd());
		if (!$con){
			die('Could not connect: ' . mysql_error());
		}

		mysql_select_db(config::getBBDDName(), $con);

		$result = mysql_query($sql);
		$id = mysql_insert_id();
		
		mysql_close($con);
		return $id;
	}
	
	public function executeInsertQuery2($sql){
		$con = mysql_connect(config::getBBDDServer(), config::getBBDDUser(), config::getBBDDPwd());
		if (!$con){
			die('Could not connect: ' . mysql_error());
		}

		mysql_select_db(config::getBBDDName(), $con);

		$result = mysql_query($sql);
		mysql_close($con);

		return $result;
	}

	public function executeUpdateQuery($sql){
		$con = mysql_connect(config::getBBDDServer(), config::getBBDDUser(), config::getBBDDPwd());
		if (!$con){
			die('Could not connect: ' . mysql_error());
		}
		
		mysql_select_db(config::getBBDDName(), $con);
		
		mysql_query($sql);
		$result = mysql_affected_rows();
		mysql_close($con);
		
		return $result;
	}
}
?>