package com.sovarb.cibus;

import com.sovarb.cibus.db.GestorDB;

public final class S {

	public static GestorDB data_base;

	// CATEGORIAS ALIMENTOS
	public final static String[] CATEGORIAS = {
				"Lácteos", "Carnes Pescados Huevos", "Legumbres Patatas Frutos-Secos",
				"Verduras Hortalizas", "Frutas", "Pan Pasta Cereales Dulces",
			 	"Grasas Aceite Mantequilla", "Bebidas"};

	public final static String[] CATEGORIAS_SPINNER = {
		"-Tipo Alimento-", "Lácteos", "Carnes Pescados Huevos", "Legumbres Patatas Frutos-Secos",
		"Verduras Hortalizas", "Frutas", "Pan Pasta Cereales Dulces",
	 	"Grasas Aceite Mantequilla", "Bebidas"};

	// FILTROS
	public final static String FILTRO = "filtro";
	public final static String NOMBRE_FILTRO = "nombre_filtro";
	public final static String DESCRIPCION_FILTRO = "descripcion_filtro";
	public final static String[] FILTRO_DEFAULT_1 = {
		"Huevo", 
		"Alerta de alimentos con huevo",
		"Nombre Ingrediente | es distinto a | \"huevo\""};
	public final static String[] FILTRO_DEFAULT_2 = {
		"Lactosa", 
		"Alerta de alimentos lácteos",										
		"Nombre Ingrediente | es distinto a | \"leche\"",
		"Nombre Ingrediente | es distinto a | \"lactosa\""};
	public final static String[] FILTRO_DEFAULT_3 = {
		"Celíaco",
		"Alerta de alimentos con gluten",
		"Nombre Ingrediente | es distinto a | \"gluten\""};

	// REGLAS
	public final static String[] listaReglas = {"Nombre Ingrediente", "Nombre Aditivo", "Número Aditivo", "Carácter Aditivo"};
	public final static String[] listaReglasExist = {"es igual a", "es distinto a"};
	public final static String[] listaReglasCantidad = {"es igual a", "es al menos", "es como mucho"};
	public static enum RESULTADO {VALIDO, INVALIDO, SIN_DATOS};	
	public final static String[] FILTROS_USUARIO = {"SEPARADOR", "Mis filtros"};
	public final static String[] FILTROS_DEFAULT = {"SEPARADOR", "Filtros predefinidos"};

	public static enum CARACTER {inofensivo, sospechoso, peligroso};

	public final static String NOMBRE = "nombre";	
	public final static int MAXLINES = 4, MINCHAR = 130;

	public final static String SEMAFORO_RESULT = "semaforo_result";
	public final static String LISTADO = "listado";
	public final static String INFO = "info";
	public final static String TIPO = "tipo";
	public final static String QR_FORMAT = "QR_CODE";
	public final static String CODIGO = "codigo";

	public final static String PESTAÑA_ALIMENTO = "pestaña_alimento";
	public final static String PESTAÑA_INGREDIENTES = "pestaña_ingredientes";
	public final static String PESTAÑA_NUTRIENTES = "pestaña_nutrientes";
	public final static String PESTAÑA_ADITIVOS = "pestaña_aditivos";
	public final static String INFO_ALIMENTO = "Info Alimento";
	public final static String INGREDIENTES = "Ingredientes";
	public final static String INGREDIENTE = "Ingrediente";
	public final static String NUTRIENTES = "Nutrientes";
	public final static String NUTRIENTE = "Nutriente";
	public final static String ADITIVOS = "Aditivos";
	public final static String ADITIVO = "Aditivo";
	public final static String CANTIDAD = "Cantidad gr/ml (opcional)";

	// ACTIVITY RESULT
	public final static String REGLA = "regla";
	public final static int REQUEST_CODE_0 = 1000;
	public final static int REQUEST_CODE_1 = 1001;
	public final static int REQUEST_CODE_2 = 1002;
	public final static int REQUEST_CODE_3 = 1003;

	// DIALOGS & TOASTS
	public final static String MENSAJE_SAVE_CHANGES = "¿Guardar los cambios?";
	public final static String MENSAJE_DELETE_FILTER = "¿Eliminar filtro?";
	public final static String MENSAJE_DELETE_REGLA = "¿Eliminar regla?";
	public final static String MENSAJE_SAVE_CHANGES_ERROR = "La regla definida no es correcta.";
	public final static String MENSAJE_SAVE_FILTRO_ERROR = "El filtro definido no es correcto.";
	public final static String MENSAJE_SAVE_ADITIVO_ERROR = "Es necesario definir un nombre";
	public final static String MENSAJE_SAVE_ALIMENTO_ERROR = "Es necesario definir un 'Nombre Alimento' y un 'Tipo Alimento'";
	public final static String MENSAJE_NO_GUARDAR = "Los cambios no se guardarán. ¿Salir?";
	public final static String TITLE_ENTRADA_MANUAL = "Entrada Manual";
	public final static String MENSAJE_ENTRADA_MANUAL = "Introduzca los dígitos del código de barras.";
	public final static String TITLE_AÑADIR_INGREDIENTE = "Añadir ingrediente";
	public final static String TITLE_AÑADIR_NUTRIENTE = "Añadir nutriente";
	public final static String TITLE_AÑADIR_ADITIVO = "Añadir aditivo";

	// MENSAJES ADVERTENCIA
	public final static String MENSAJE_NOT_FOUND = "mensaje";
	public final static String NOT_FOUND_1 = "No se ha encontrado el aditivo  ";
	public final static String NOT_FOUND_2 = "No se han encontrado aditivos de tipo ";
	public final static String NOT_FOUND_3 = "No se han encontrado alimentos de la categoría ";
	public final static String NOT_FOUND_4 = "No se ha encontrado el alimento ";
	public final static String NOT_FOUND_5 = "No se ha encontrado el alimento con el código introducido.";
	public final static String NOT_FOUND_6 = "No se han encontrado aditivos.";
	public final static String NOT_FOUND_7 = "No se han encontrado ingredientes.";
	public final static String NOT_FOUND_8 = "No se han encontrado nutrientes";
	public final static String NOT_FOUND_9 = "No se ha encontrado información de ";
	public final static String CUMPLE_CRITERIO = " cumple con los criterios establecidos.";
	public final static String NO_CUMPLE_CRITERIO = " NO cumple con los criterios establecidos.";
	public final static String NO_PUEDE_ELIMINAR_REGLA = "Imposible eliminar regla.\nEl filtro debe tener al menos 1 regla.";
	public final static String FAIL_INSERT_ALIMENTO = "Fallo al insertar el alimento";
	public final static String FAIL_INSERT_INGREDIENTE = "Fallo al insertar el ingrediente.";
	public final static String FAIL_INSERT_NUTRIENTE = "Fallo al insertar el nutriente.";
	public final static String FAIL_INSERT_ADITIVO = "Fallo al insertar el aditivo.";
	public final static String FAIL_UPDATE_ADITIVO = "Fallo al actualizar el aditivo.";

	//JSON
	public final static String TITLE_JSON_DIALOG = "Sincronizando base de datos";
	public final static String MENSAJE_JSON_DIALOG = "Por favor espere";
	public final static String HTTP_SERVER = "http://192.168.1.41/";
	public final static String SERVICE_INGREDIENTE = "cibus/service.ingrediente.php";
	public final static String SERVICE_INGREDIENTES_ADD = "cibus/service.ingrediente.add.php";
	public final static String SERVICE_ADITIVO = "cibus/service.aditivo.php";
	public final static String SERVICE_ADITIVO_ADD = "cibus/service.aditivo.add.php";
	public final static String SERVICE_ADITIVO_UPDATE = "cibus/service.aditivo.update.php";
	public final static String SERVICE_ALIMENTO = "cibus/service.alimento.php";
	public final static String SERVICE_ALIMENTO_ADD = "cibus/service.alimento.add.php";
	public final static String SERVICE_NUTRIENTE = "cibus/service.nutriente.php";
	public final static String SERVICE_NUTRIENTE_ADD = "cibus/service.nutriente.add.php";
	public final static String SERVICE_TIPO_ADITIVO = "cibus/service.tipoaditivo.php";
	public final static String SERVICE_ADITIVOSXALIMENTO = "cibus/service.aditivosXalimento.php";
	public final static String SERVICE_ADITIVOSXALIMENTO_ADD = "cibus/service.aditivosXalimento.add.php";
	public final static String SERVICE_NUTRIENTESXALIMENTO = "cibus/service.nutrientesXalimento.php";
	public final static String SERVICE_NUTRIENTESXALIMENTO_ADD = "cibus/service.nutrientesXalimento.add.php";
	public final static String SERVICE_INGREDIENTESXALIMENTO = "cibus/service.ingredientesXalimento.php";
	public final static String SERVICE_INGREDIENTESXALIMENTO_ADD = "cibus/service.ingredientesXalimento.add.php";

	// HTTP
	public final static String KEY_SUCCESS = "success";
}
