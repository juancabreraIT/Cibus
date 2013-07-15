<?php
/* Contiene los parámetros de configuración para acceder a la base de datos */
class config{
	public static function getBBDDServer() {
		return 'localhost';
	}

	public static function getBBDDName(){
		return  'db_cibus'; 
	}
 
	public static function getBBDDUser(){
		return 'android'; 
	} 
 
	public static function getBBDDPwd(){
		return ''; 
	}
}
?>