package com.sovarb.cibus.db;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import android.widget.Toast;

import com.sovarb.cibus.S;
import com.sovarb.cibus.adapter.Regla;
import com.sovarb.cibus.adapter.Titulos;
import com.sovarb.cibus.json.JSONAditivo;
import com.sovarb.cibus.json.JSONAditivosXAlimento;
import com.sovarb.cibus.json.JSONAlimento;
import com.sovarb.cibus.json.JSONIngrediente;
import com.sovarb.cibus.json.JSONIngredientesXAlimento;
import com.sovarb.cibus.json.JSONNutriente;
import com.sovarb.cibus.json.JSONNutrientesXAlimento;
import com.sovarb.cibus.json.JSONTipoAditivo;

public final class GestorDB {

	// #####################
	// DEFINICION DE TABLAS
	// #####################
	public final static class TAlimentos implements BaseColumns {

		private TAlimentos(){}
		
		public final static String NOMBRE_TABLA = "alimentos";
		public static final String NOMBRE_COLUMNA_ID = "_id";
		public static final String NOMBRE_COLUMNA_NOMBRE = "nombre";
		public static final String NOMBRE_COLUMNA_TIPO = "tipo";
		public static final String NOMBRE_COLUMNA_DESCRIPCION = "descripcion";
		public static final String NOMBRE_COLUMNA_FORMATO = "formato";
		public static final String NOMBRE_COLUMNA_MARCA = "marca";
		public static final String NOMBRE_COLUMNA_SUBMARCA = "submarca";
		public static final String NOMBRE_COLUMNA_CODIGO1D = "codigo1D";
		public static final String NOMBRE_COLUMNA_CODIGO2D = "codigo2D";
	}

	public final static class TAditivos implements BaseColumns {
		
		private TAditivos(){}
		
		public static final String NOMBRE_TABLA = "aditivos";
		public static final String NOMBRE_COLUMNA_ID = "_id";
		public static final String NOMBRE_COLUMNA_NUMERO = "numero";
		public static final String NOMBRE_COLUMNA_NOMBRE = "nombre";
		public static final String NOMBRE_COLUMNA_DESCRIPCION = "descripcion";
		public static final String NOMBRE_COLUMNA_CARACTER = "caracter";
		public static final String NOMBRE_COLUMNA_ID_TIPO = "idTipoForeign";
	}

	public final static class TAditivosXAlimento implements BaseColumns {
		
		private TAditivosXAlimento(){}
		
		public static final String NOMBRE_TABLA = "aditivosXalimento";
		public static final String NOMBRE_COLUMNA_ID_ALIMENTO = "idAlimentoForeign";
		public static final String NOMBRE_COLUMNA_ID_ADITIVO = "idAditivoForeign";
	}
	
	public final static class TTiposAditivos implements BaseColumns {
		
		private TTiposAditivos(){}
		
		
		public static final String NOMBRE_TABLA = "tiposAditivos";
		public static final String NOMBRE_COLUMNA_ID = "_id";
		public static final String NOMBRE_COLUMNA_NOMBRE = "nombre";
		public static final String NOMBRE_COLUMNA_RANGO = "rango";
	}
	
	public final static class TIngredientes implements BaseColumns {
		
		private TIngredientes(){}
		
		public static final String NOMBRE_TABLA = "ingredientes";
		public static final String NOMBRE_COLUMNA_ID = "_id";
		public static final String NOMBRE_COLUMNA_NOMBRE = "nombre";
		public static final String NOMBRE_COLUMNA_DESCRIPCION = "descripcion";
	}
	
	public final static class TIngredientesXAlimento implements BaseColumns {
		
		private TIngredientesXAlimento(){}
		
		public static final String NOMBRE_TABLA = "ingredientesXalimento";
		public static final String NOMBRE_COLUMNA_ID_ALIMENTO = "idAlimentoForeign";
		public static final String NOMBRE_COLUMNA_ID_INGREDIENTE = "idIngredienteForeign";
		public static final String NOMBRE_COLUMNA_CANTIDAD = "cantidad";
	}
	
	public final static class TNutrientes implements BaseColumns {
	
		private TNutrientes(){}
		
		public static final String NOMBRE_TABLA = "nutrientes";
		public static final String NOMBRE_COLUMNA_ID = "_id";
		public static final String NOMBRE_COLUMNA_NOMBRE = "nombre";
		public static final String NOMBRE_COLUMNA_DESCRIPCION = "descripcion";
	}
	
	public final static class TNutrientesXAlimento implements BaseColumns{
		
		private TNutrientesXAlimento(){}
		
		public static final String NOMBRE_TABLA = "nutrientesXalimento";
		public static final String NOMBRE_COLUMNA_ID_ALIMENTO = "idAlimentoForeign";
		public static final String NOMBRE_COLUMNA_ID_NUTRIENTE = "idNutrienteForeign";
		public static final String NOMBRE_COLUMNA_CANTIDAD = "cantidad";
	}

	public final static class TReglas implements BaseColumns{
		
		private TReglas(){}
		
		public static final String NOMBRE_TABLA = "reglas";
		public static final String NOMBRE_COLUMNA_ID = "_id";
		public static final String NOMBRE_COLUMNA_REGLA = "regla";
	}
		
	public final static class TFiltros implements BaseColumns{
		
		private TFiltros(){}
		
		public static final String NOMBRE_TABLA = "filtros";
		public static final String NOMBRE_COLUMNA_ID = "_id";
		public static final String NOMBRE_COLUMNA_NOMBRE = "nombre";
		public static final String NOMBRE_COLUMNA_DESCRIPCION = "descripcion";
	}
	
	public final static class ReglasXFiltro	implements BaseColumns {
		
		private ReglasXFiltro(){}
		
		public static final String NOMBRE_TABLA = "reglasXfiltro";
		public static final String NOMBRE_COLUMNA_ID_REGLA = "idReglaForeign";
		public static final String NOMBRE_COLUMNA_ID_FILTRO = "idFiltroForeign";
	}
	

	// #############################
	// SENTENCIAS DE CREACION TABLAS
	// #############################
	public static final String TEXT_TYPE = " TEXT ";
	public static final String INTEGER_TYPE = " INTEGER ";
	public static final String COMMA_SEP = ", ";
	public static final String NULL = " NULL ";
	public static final String NOT_NULL = " NOT NULL ";
	public static final String UNIQUE = " UNIQUE ";
	
	private static final String SQL_CREATE_ADITIVOS = 
			"CREATE TABLE " + TAditivos.NOMBRE_TABLA + " (" +
					TAditivos.NOMBRE_COLUMNA_ID + INTEGER_TYPE + NOT_NULL + " PRIMARY KEY AUTOINCREMENT, " +
					TAditivos.NOMBRE_COLUMNA_NUMERO + TEXT_TYPE + UNIQUE + NOT_NULL + COMMA_SEP +
					TAditivos.NOMBRE_COLUMNA_NOMBRE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
					TAditivos.NOMBRE_COLUMNA_DESCRIPCION + TEXT_TYPE + NULL + COMMA_SEP + 
					TAditivos.NOMBRE_COLUMNA_CARACTER + TEXT_TYPE + NULL + COMMA_SEP +
					TAditivos.NOMBRE_COLUMNA_ID_TIPO + INTEGER_TYPE + COMMA_SEP +
					" FOREIGN KEY (" + TAditivos.NOMBRE_COLUMNA_ID_TIPO + ") " +
					" REFERENCES " + TTiposAditivos.NOMBRE_TABLA + "(" + TTiposAditivos.NOMBRE_COLUMNA_ID + ")); ";
	
	private static final String SQL_CREATE_ADITIVOSXALIMENTOS =
			" CREATE TABLE " + TAditivosXAlimento.NOMBRE_TABLA + " (" +
					TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + INTEGER_TYPE + COMMA_SEP +
					TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO + INTEGER_TYPE + COMMA_SEP +
					" FOREIGN KEY (" + TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + ") " +
					" REFERENCES " + TAlimentos.NOMBRE_TABLA + "(" + TAlimentos.NOMBRE_COLUMNA_ID + "), " +
					" FOREIGN KEY (" + TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO + ") " + 
					" REFERENCES " + TAditivos.NOMBRE_TABLA + "(" + TAditivos.NOMBRE_COLUMNA_ID + ")); ";
	
	private static final String SQL_CREATE_ALIMENTOS =
			" CREATE TABLE " + TAlimentos.NOMBRE_TABLA + " (" +
					TAlimentos.NOMBRE_COLUMNA_ID + INTEGER_TYPE + NOT_NULL + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP + 
					TAlimentos.NOMBRE_COLUMNA_NOMBRE + TEXT_TYPE + NOT_NULL + COMMA_SEP +
					TAlimentos.NOMBRE_COLUMNA_TIPO + TEXT_TYPE + NULL + COMMA_SEP + 
					TAlimentos.NOMBRE_COLUMNA_DESCRIPCION + TEXT_TYPE + NULL + COMMA_SEP +
					TAlimentos.NOMBRE_COLUMNA_FORMATO + TEXT_TYPE + NULL + COMMA_SEP + 
					TAlimentos.NOMBRE_COLUMNA_MARCA + TEXT_TYPE + NULL + COMMA_SEP + 
					TAlimentos.NOMBRE_COLUMNA_SUBMARCA + TEXT_TYPE + NULL + COMMA_SEP +
					TAlimentos.NOMBRE_COLUMNA_CODIGO1D + TEXT_TYPE + UNIQUE + NULL + COMMA_SEP +
					TAlimentos.NOMBRE_COLUMNA_CODIGO2D + TEXT_TYPE + NULL + "); ";
	
	private static final String SQL_CREATE_NUTRIENTES = 
			" CREATE TABLE " + TNutrientes.NOMBRE_TABLA + " (" + 
					TNutrientes.NOMBRE_COLUMNA_ID + INTEGER_TYPE + NOT_NULL + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
					TNutrientes.NOMBRE_COLUMNA_NOMBRE + TEXT_TYPE + UNIQUE + NOT_NULL + COMMA_SEP +
					TNutrientes.NOMBRE_COLUMNA_DESCRIPCION + TEXT_TYPE + NULL + "); ";
	
	private static final String SQL_CREATE_NUTRIENTESXALIMENTO =					
			" CREATE TABLE " + TNutrientesXAlimento.NOMBRE_TABLA + " (" +
					TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + INTEGER_TYPE + COMMA_SEP +
					TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE + INTEGER_TYPE + COMMA_SEP + 
					TNutrientesXAlimento.NOMBRE_COLUMNA_CANTIDAD + TEXT_TYPE + " DEFAULT 'No facilitado' " + NULL + COMMA_SEP +
					" FOREIGN KEY(" + TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + ")" +
					" REFERENCES " + TAlimentos.NOMBRE_TABLA + "(" + TAlimentos.NOMBRE_COLUMNA_ID + ")" + COMMA_SEP +
					" FOREIGN KEY(" + TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE + ")" +
					" REFERENCES " + TNutrientes.NOMBRE_TABLA + "(" + TNutrientes.NOMBRE_COLUMNA_ID + ")); ";
	
	private static final String SQL_CREATE_TIPOSADITIVOS = 					
			" CREATE TABLE " + TTiposAditivos.NOMBRE_TABLA + " (" + 
					TTiposAditivos.NOMBRE_COLUMNA_ID + INTEGER_TYPE + NOT_NULL + " PRIMARY KEY AUTOINCREMENT" + COMMA_SEP +
					TTiposAditivos.NOMBRE_COLUMNA_NOMBRE + TEXT_TYPE + UNIQUE + NOT_NULL + COMMA_SEP +
					TTiposAditivos.NOMBRE_COLUMNA_RANGO + TEXT_TYPE + NULL + "); ";
	
	private static final String SQL_CREATE_INGREDIENTES = 					
			" CREATE TABLE " + TIngredientes.NOMBRE_TABLA + "(" +
					TIngredientes.NOMBRE_COLUMNA_ID + INTEGER_TYPE + NOT_NULL + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
					TIngredientes.NOMBRE_COLUMNA_NOMBRE + TEXT_TYPE + UNIQUE + NOT_NULL + COMMA_SEP +
					TIngredientes.NOMBRE_COLUMNA_DESCRIPCION + TEXT_TYPE + NULL + "); ";
	
	private static final String SQL_CREATE_INGREDIENTESXALIMENTO = 					
			" CREATE TABLE " + 	TIngredientesXAlimento.NOMBRE_TABLA + "(" +
					TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + INTEGER_TYPE + COMMA_SEP +
					TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE + INTEGER_TYPE + COMMA_SEP +
					TIngredientesXAlimento.NOMBRE_COLUMNA_CANTIDAD + TEXT_TYPE + " DEFAULT 'No facilitado' " + NULL + COMMA_SEP +
					" FOREIGN KEY(" + TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + ") " +
					" REFERENCES " + TAlimentos.NOMBRE_TABLA + "(" + TAlimentos.NOMBRE_COLUMNA_ID + ")" + COMMA_SEP +
					" FOREIGN KEY(" + TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE + ") " +
					" REFERENCES " + TIngredientes.NOMBRE_TABLA + "(" + TIngredientes.NOMBRE_COLUMNA_ID + ")); ";
	
	private static final String SQL_CREATE_REGLAS = 					
			" CREATE TABLE " + TReglas.NOMBRE_TABLA + "(" +
					TReglas.NOMBRE_COLUMNA_ID + INTEGER_TYPE + NOT_NULL + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP + 
					TReglas.NOMBRE_COLUMNA_REGLA + TEXT_TYPE + NOT_NULL + "); ";
	
	private static final String SQL_CREATE_FILTROS = 					
			" CREATE TABLE " + TFiltros.NOMBRE_TABLA + "(" +
					TFiltros.NOMBRE_COLUMNA_ID + INTEGER_TYPE + NOT_NULL + " PRIMARY KEY AUTOINCREMENT " + COMMA_SEP +
					TFiltros.NOMBRE_COLUMNA_NOMBRE + TEXT_TYPE + UNIQUE + NOT_NULL + COMMA_SEP +
					TFiltros.NOMBRE_COLUMNA_DESCRIPCION + TEXT_TYPE + NULL + "); ";
	
	private static final String SQL_CREATE_REGLASXFILTRO = 					
			" CREATE TABLE " + ReglasXFiltro.NOMBRE_TABLA + "(" +
					ReglasXFiltro.NOMBRE_COLUMNA_ID_REGLA + INTEGER_TYPE + COMMA_SEP +
					ReglasXFiltro.NOMBRE_COLUMNA_ID_FILTRO + INTEGER_TYPE + COMMA_SEP +
					" FOREIGN KEY(" + ReglasXFiltro.NOMBRE_COLUMNA_ID_REGLA + ")" + 
					" REFERENCES " + TReglas.NOMBRE_TABLA + "(" + TReglas.NOMBRE_COLUMNA_ID + ")," + 
					" FOREIGN KEY(" + ReglasXFiltro.NOMBRE_COLUMNA_ID_FILTRO + ")" + 
					" REFERENCES " + TFiltros.NOMBRE_TABLA + "(" + TFiltros.NOMBRE_COLUMNA_ID + "));" ;

	private static final String SQL_DELETE_ADITIVOS = " DROP TABLE IF EXISTS " + TAditivos.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_ALIMENTOS = " DROP TABLE IF EXISTS " + TAlimentos.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_ADITIVOSXALIMENTOS = " DROP TABLE IF EXISTS " +  TAditivosXAlimento.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_TIPOSADITIVOS = " DROP TABLE IF EXISTS " + TTiposAditivos.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_INGREDIENTES = " DROP TABLE IF EXISTS " + TIngredientes.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_INGREDIENTESXALIMENTO = " DROP TABLE IF EXISTS " + TIngredientesXAlimento.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_NUTRIENTES = " DROP TABLE IF EXISTS " + TNutrientes.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_NUTRIENTESXALIMENTO = " DROP TABLE IF EXISTS " + TNutrientesXAlimento.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_REGLAS = " DROP TABLE IF EXISTS " + TReglas.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_FILTROS = " DROP TABLE IF EXISTS " + TFiltros.NOMBRE_TABLA + ";";
	private static final String SQL_DELETE_REGLASXFILTRO = " DROP TABLE IF EXISTS " + ReglasXFiltro.NOMBRE_TABLA + ";";

	// ##########
	// ATRIBUTOS
	// ##########
	private static int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "db_cibus.s3db";
	private int numConex;
	private Context context;
	private SQLiteDatabase db;
	private DBHandler dbHelper;
	
	// Constructor
	public GestorDB(Context pContext){
		
		this.context = pContext;
		this.dbHelper = new DBHandler(this.context);
		numConex = 0;
	}

	public synchronized GestorDB openR(){
		this.numConex++;
		this.db = dbHelper.getReadableDatabase();
		return this;
	}

	public synchronized GestorDB openW(){
		this.numConex++;
		this.db = dbHelper.getWritableDatabase();
		return this;
	}

	public synchronized void close(){
		this.numConex--;
		if(this.numConex == 0)
			db.close();
	}

	public synchronized boolean isOpen(){
		return this.db.isOpen();
	}

	public synchronized int getNumConex(){
		return this.numConex;
	}


	// ###############
	// CLASE DBHandler
	// ###############
	public static class DBHandler extends SQLiteOpenHelper {

		Context myContext;
		Activity activity;
		private ProgressDialog progressDialog = null;
		private Runnable syncroDB;

		DBHandler(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			myContext = context;
			activity = (Activity) context;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(SQL_DELETE_ADITIVOS);
			db.execSQL(SQL_DELETE_ALIMENTOS);
			db.execSQL(SQL_DELETE_ADITIVOSXALIMENTOS);
			db.execSQL(SQL_DELETE_TIPOSADITIVOS);
			db.execSQL(SQL_DELETE_INGREDIENTES);
			db.execSQL(SQL_DELETE_INGREDIENTESXALIMENTO);
			db.execSQL(SQL_DELETE_NUTRIENTES);
			db.execSQL(SQL_DELETE_NUTRIENTESXALIMENTO);
			db.execSQL(SQL_DELETE_REGLAS);
			db.execSQL(SQL_DELETE_FILTROS);
			db.execSQL(SQL_DELETE_REGLASXFILTRO);

			db.execSQL(SQL_CREATE_ADITIVOS);
			db.execSQL(SQL_CREATE_ADITIVOSXALIMENTOS);
			db.execSQL(SQL_CREATE_ALIMENTOS);
			db.execSQL(SQL_CREATE_NUTRIENTES);
			db.execSQL(SQL_CREATE_NUTRIENTESXALIMENTO);			
			db.execSQL(SQL_CREATE_TIPOSADITIVOS); 					
			db.execSQL(SQL_CREATE_INGREDIENTES); 					
			db.execSQL(SQL_CREATE_INGREDIENTESXALIMENTO); 					
			db.execSQL(SQL_CREATE_REGLAS); 
			db.execSQL(SQL_CREATE_FILTROS); 					
			db.execSQL(SQL_CREATE_REGLASXFILTRO);

			initDB();
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			try{
				db.execSQL(SQL_DELETE_ADITIVOS);
				db.execSQL(SQL_DELETE_ALIMENTOS);
				db.execSQL(SQL_DELETE_ADITIVOSXALIMENTOS);
				db.execSQL(SQL_DELETE_TIPOSADITIVOS);
				db.execSQL(SQL_DELETE_INGREDIENTES);
				db.execSQL(SQL_DELETE_INGREDIENTESXALIMENTO);
				db.execSQL(SQL_DELETE_NUTRIENTES);
				db.execSQL(SQL_DELETE_NUTRIENTESXALIMENTO);
				db.execSQL(SQL_DELETE_REGLAS);
				db.execSQL(SQL_DELETE_FILTROS);
				db.execSQL(SQL_DELETE_REGLASXFILTRO);
				
				onCreate(db);
			}catch(SQLException e){		}
		}

		private void initDB(){
			syncroDB = new Runnable() {
				@Override
			    public void run() {
					try{
						JSONTipoAditivo jsonTA = new JSONTipoAditivo();
						jsonTA.readJSONTiposAditivos();

						JSONIngrediente jsonI = new JSONIngrediente();
						jsonI.readJSONIngredientes();

						JSONNutriente jsonN = new JSONNutriente();
						jsonN.readJSONNutrientes();

						JSONAlimento jsonAl = new JSONAlimento();
						jsonAl.readJSONAlimentos();

						JSONAditivo jsonAd = new JSONAditivo();
						jsonAd.readJSONAditivos();

						JSONAditivosXAlimento jsonAxA = new JSONAditivosXAlimento();
						jsonAxA.readJSONAditivosXAlimento();

						JSONNutrientesXAlimento  jsonNxA = new JSONNutrientesXAlimento();
						jsonNxA.readJSONNutrientesXAlimento();

						JSONIngredientesXAlimento jsonIxA = new JSONIngredientesXAlimento();
						jsonIxA.readJSONIngredientesXAlimento();

						activity.runOnUiThread(returnRes);
					} catch(Exception e){	}
				}
			};
			Thread thread = new Thread(null, syncroDB, "bgReadJSONCibus");
			thread.start();			
			progressDialog = ProgressDialog.show(activity, S.TITLE_JSON_DIALOG, S.MENSAJE_JSON_DIALOG, false, true);
		}

		private Runnable returnRes = new Runnable(){
			@Override 
			public void run() {
				progressDialog.dismiss();
			}
		};

	}

	// ######################################
	// ######## BASE DE DATOS REMOTA ########
	// ######################################

	public final void insertarAlimento(final Alimento pAlimento, final ArrayList<Ingrediente> arrayIngredientes, final ArrayList<String> cantIngr,
								  final ArrayList<Nutriente> arrayNutrientes, final ArrayList<String> cantNut, 
								  final ArrayList<Integer> arrayAditivos, final Activity activity){

		Runnable able = new Runnable(){
			@Override
			public void run(){				
				try{
					openW();
					Alimento aux1 = pAlimento.saveAlimentoCloud(activity);
					if(aux1.getId() != -1){
						insertarAlimentoLocal(aux1);

						int idIngrediente;
						Ingrediente aux2;
						IngredientesXAlimento IXA;
						for(int i = 0; i < arrayIngredientes.size(); i++){
							idIngrediente = consultaIdIngredienteXNombre(arrayIngredientes.get(i).getNombre());
							if(idIngrediente == -1){
								aux2 = arrayIngredientes.get(i).saveIngredienteCloud(activity);
								idIngrediente = aux2.getId();
								insertarIngredienteLocal(aux2);
							}

							IXA = new IngredientesXAlimento(aux1.getId(), idIngrediente, cantIngr.get(i));
							IXA.saveIngredientesXAlimentoCloud(activity);
							insertarIngredientesXAlimentoLocal(IXA);
						}

						int idNutriente;
						Nutriente aux3;
						NutrientesXAlimento NXA;
						for(int i = 0; i < arrayNutrientes.size(); i++){
							idNutriente = consultaIdNutrienteXNombre(arrayNutrientes.get(i).getNombre());
							if(idNutriente == -1){
								aux3 = arrayNutrientes.get(i).saveNutrienteCloud(activity);
								idNutriente = aux3.getId();
								insertarNutrienteLocal(aux3);
							}

							NXA = new NutrientesXAlimento(aux1.getId(), idNutriente, cantNut.get(i));
							NXA.saveNutrientesXAlimentoCloud(activity);
							insertarNutrientesXAlimentoLocal(NXA);
						}
						
						AditivosXAlimento AXA;
						for(int i = 0; i < arrayAditivos.size(); i++){							
							AXA = new AditivosXAlimento(aux1.getId(), arrayAditivos.get(i));
							AXA.saveAditivosXAlimentoCloud(activity);
							insertarAditivosXAlimentoLocal(AXA);
						}

					}else{
						Toast.makeText(activity, S.FAIL_INSERT_ALIMENTO, Toast.LENGTH_LONG).show();
					}
					close();

				}catch(Exception e){	}
			}
		};

		Thread thread = new Thread(null, able, "insertarAlimento");		
		thread.start();
		try {
			thread.join(2000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

	public final void insertarIngrediente(final Ingrediente pIngrediente, final Activity pActivity){			

		Runnable able = new Runnable(){
			@Override
			public void run(){				
				try{
					openW();
					Ingrediente aux1 = pIngrediente.saveIngredienteCloud(pActivity);
					if(aux1.getId() != -1){
						insertarIngredienteLocal(aux1);
					}else{
						Toast.makeText(pActivity, S.FAIL_INSERT_INGREDIENTE, Toast.LENGTH_LONG).show();
					}					
					close();

				}catch(Exception e){	}
			}
		};

		Thread thread = new Thread(null, able, "insertarIngrediente");		
		thread.start();
		try {
			thread.join(1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}

	}

	public final void insertarNutriente(final Nutriente pNutriente, final Activity pActivity){

		Runnable able = new Runnable(){
			@Override
			public void run(){				
				try{
					openW();
					Nutriente aux1 = pNutriente.saveNutrienteCloud(pActivity);
					if(aux1.getId() != -1){
						insertarNutrienteLocal(aux1);
					}else{
						Toast.makeText(pActivity, S.FAIL_INSERT_NUTRIENTE, Toast.LENGTH_LONG).show();
					}
					close();

				}catch(Exception e){	}
			}
		};

		Thread thread = new Thread(null, able, "insertarNutriente");		
		thread.start();
		try {
			thread.join(1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

	public final void insertarAditivo(final Aditivo pAditivo, final Activity pActivity){		

		Runnable able = new Runnable(){
			@Override
			public void run(){				
				try{
					openW();
					Aditivo aux1 = pAditivo.saveAditivoCloud(pActivity);
					if(aux1.getId() != -1){
						insertarAditivoLocal(aux1);
					}else{
						Toast.makeText(pActivity, S.FAIL_INSERT_ADITIVO, Toast.LENGTH_LONG).show();
					}
					close();

				}catch(Exception e){	}
			}
		};

		Thread thread = new Thread(null, able, "insertarAditivo");
		thread.start();
		try {
			thread.join(1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}

	public final void actualizarAditivo(final Aditivo pAditivo, final Activity pActivity){

		Runnable able = new Runnable(){
			@Override
			public void run(){				
				try{
					openW();
					int result = pAditivo.updateAditivoCloud(pActivity);					
					if(result > 0){
						actualizarAditivoLocal(pAditivo);
					}else{
						Toast.makeText(pActivity, S.FAIL_UPDATE_ADITIVO, Toast.LENGTH_LONG).show();
					}
					close();

				}catch(Exception e){	}
			}
		};

		Thread thread = new Thread(null, able, "actualizarAditivo");
		thread.start();
		try {
			thread.join(1000);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}


	// ######################################
	// ######## BASE DE DATOS LOCAL ########
	// ######################################

	/******** ADITIVOS *********/
	/******** CONSULTAS ********/

	/**
	 * 
	 * @return Nombres de todos los Aditivos.
	 */
	public final String[] consultaNombreAditivos(){
		if(db != null){
			String[] columnas = { TAditivos.NOMBRE_COLUMNA_NOMBRE };
			Cursor result = db.query(
					TAditivos.NOMBRE_TABLA,
					columnas,
					null, null, null, null, null);
			
			List<String> list = new ArrayList<String>();
			if(result.moveToFirst()){			
				do{
					list.add(result.getString(0));
				}while(result.moveToNext());
				
				String[] aditivos = new String[list.size()];
				list.toArray(aditivos);
				
				return aditivos;
			}
		}
		return null;
	}
	
	/**
	 * @return Numeros de todos los Aditivos.
	 */
	public final String[] consultaNumeroAditivos(){
		if(db != null){
			String[] columnas = { TAditivos.NOMBRE_COLUMNA_NUMERO };
			Cursor result = db.query(
					TAditivos.NOMBRE_TABLA,
					columnas,
					null, null, null, null, null);
			
			List<String> list = new ArrayList<String>();
			if(result.moveToFirst()){			
				do{
					list.add(result.getString(0));
				}while(result.moveToNext());
				
				String[] aditivos = new String[list.size()];
				list.toArray(aditivos);
				
				return aditivos;
			}
		}
		return null;
	}
	
	/** 
	 * @return Parejas de [numero, nombre] de los Aditivos.
	 */
	public final String[] consultaNombreNummeroAditivos(){
		
		if(db != null){
			String[] columnas = { TAditivos.NOMBRE_COLUMNA_NUMERO, TAditivos.NOMBRE_COLUMNA_NOMBRE };
			Cursor result = db.query(
					TAditivos.NOMBRE_TABLA,
					columnas,
					null, null, null, null, null);
			
			List<String> list = new ArrayList<String>();
			if(result.moveToFirst()){			
				do{
					list.add(result.getString(0));
					list.add(result.getString(1));
				}while(result.moveToNext());
				
				String[] aditivos = new String[list.size()];
				list.toArray(aditivos);
				
				return aditivos;
			}
		}
		return null;
	}
	
	/**
	 * @return Nombre de todos los TiposAditivos
	 */
	public final String[] consultaTiposAditivos(){
		
		if(db != null){
			String[] campos = {TTiposAditivos.NOMBRE_COLUMNA_NOMBRE};
			List<String> listaTipos = new ArrayList<String>();
			
			Cursor result = db.query(
					TTiposAditivos.NOMBRE_TABLA,	// Tabla
					campos,							// Columnas a devolver
					null,							// Columnas cláusula WHERE
					null,							// Valores cláusula WHERE
					null,							// Agrupacion filas
					null,							// Filtrar por grupos de filas
					null);							// Orden a utilizar
			
			if(result.moveToFirst()){
				do{
					listaTipos.add(result.getString(0));
				}while(result.moveToNext());
			}
			
			String[] Tipos = new String[listaTipos.size()];
			listaTipos.toArray(Tipos);
			
			return Tipos;
		}
		
		return new String[0];
	}
		
	/**
	 * @brief Inserta al lado del nombre de cada TipoAditivo el número de aditivos pertenecientes.
	 * @return [id, nombre, rango] de todos los aditivos.
	 */
	public final String[] consultaTiposAditivosC(){
		
		if(db != null){
			String[] campos = { TTiposAditivos.NOMBRE_COLUMNA_ID, 
								TTiposAditivos.NOMBRE_COLUMNA_NOMBRE, 
								TTiposAditivos.NOMBRE_COLUMNA_RANGO};
			String aux;
			int idTipo;
			List<String> listaTipos = new ArrayList<String>();
			Cursor result = db.query(
					TTiposAditivos.NOMBRE_TABLA,
					campos,
					null, null, null, null, null);
			Cursor result2;
			
			if(result.moveToFirst()){
				do{
					idTipo = result.getInt(0);
					result2 = db.rawQuery("SELECT COUNT(*) FROM " + TAditivos.NOMBRE_TABLA + 
										  " WHERE " + TAditivos.NOMBRE_COLUMNA_ID_TIPO + " = " + idTipo, null);
					result2.moveToFirst();
					aux = result.getString(1) + " (" + result2.getInt(0) + ")";
					listaTipos.add(aux);
					listaTipos.add(result.getString(2));
				}while(result.moveToNext());
			}
			
			if(!listaTipos.isEmpty()){
				String[] Tipos = new String[listaTipos.size()];
				listaTipos.toArray(Tipos);
				return Tipos;
			}
		}
		
		return new String[0];
	}

	/**
	 * 
	 * @param pNombreTipo
	 * @return Parejas [numero, nombre] de todos los Aditivos de un determinado tipoAditivo.
	 */
	public final String[] consultaAditivosXTipo(String pNombreTipo){
		
		if(db != null){
			Cursor result = db.rawQuery(
					" SELECT " + TAditivos.NOMBRE_COLUMNA_NUMERO + ", " + TAditivos.NOMBRE_COLUMNA_NOMBRE +
					" FROM " + TAditivos.NOMBRE_TABLA +
					" WHERE " + TAditivos.NOMBRE_COLUMNA_ID_TIPO + " = " +
							"(SELECT " + TTiposAditivos.NOMBRE_COLUMNA_ID +
							" FROM " + TTiposAditivos.NOMBRE_TABLA +
							" WHERE " + TTiposAditivos.NOMBRE_COLUMNA_NOMBRE + " = '" + pNombreTipo + "')", null);
			
			List<String> listaTipos = new ArrayList<String>();


			if(result.moveToFirst()){
				do{
					listaTipos.add(result.getString(0));
					listaTipos.add(result.getString(1));
				}while(result.moveToNext());
			}

			if(!listaTipos.isEmpty()){
				String[] Tipos = new String[listaTipos.size()];
				listaTipos.toArray(Tipos);
				return Tipos;
			}
		}
		
		return null;
	}	

	/**
	 * @return [numero, nombre, tipo, descripcion, caracter] de un Aditivo.
	 */
	public final Aditivo consultaAditivoXNumero(String pNumero){

		if(db != null){
			Cursor result = db.rawQuery(
					" SELECT A." + TAditivos.NOMBRE_COLUMNA_ID + 
						", A." + TAditivos.NOMBRE_COLUMNA_NUMERO + 
						", A." + TAditivos.NOMBRE_COLUMNA_NOMBRE +  
						", A." + TAditivos.NOMBRE_COLUMNA_DESCRIPCION + 
						", A." + TAditivos.NOMBRE_COLUMNA_CARACTER +
						", A." + TAditivos.NOMBRE_COLUMNA_ID_TIPO + 
					" FROM " + TAditivos.NOMBRE_TABLA + " as A " +
					" WHERE " + TAditivos.NOMBRE_COLUMNA_NUMERO + " = '" + pNumero + "' ", null);
			
			if(result.moveToFirst()){
				Aditivo aditivo = new Aditivo(result.getInt(0), result.getString(1),
											result.getString(2), result.getString(3), 
											result.getString(4), result.getInt(5));
				return aditivo;
			}
		}

		return null;
	}

	/**
	 * @return [numero, nombre, tipo, descripcion, caracter] de un Aditivo.
	 */
	public final Aditivo consultaAditivoXNombre(String pNombreAditivo){
		
		if(db != null){
			Cursor result = db.rawQuery(
					" SELECT A." + TAditivos.NOMBRE_COLUMNA_ID +  
						", A." + TAditivos.NOMBRE_COLUMNA_NUMERO + 
						", A." + TAditivos.NOMBRE_COLUMNA_NOMBRE +  
						", A." + TAditivos.NOMBRE_COLUMNA_DESCRIPCION +
						", A." + TAditivos.NOMBRE_COLUMNA_CARACTER +
						", A." + TAditivos.NOMBRE_COLUMNA_ID_TIPO + 
					" FROM " + TAditivos.NOMBRE_TABLA + " as A " + 
					" WHERE A." + TAditivos.NOMBRE_COLUMNA_NOMBRE + " = '" + pNombreAditivo + "'", null);
			
			if(result.moveToFirst()){
				Aditivo aditivo = new Aditivo(result.getInt(0), result.getString(1),
											result.getString(2), result.getString(3), 
											result.getString(4), result.getInt(5));
				return aditivo;
			}
		}
		return null;
	}

	/******** ADITIVOS *********/
	/******** INSERT UPDATE ********/

	/**
	 * 
	 * @param oldAditivo
	 * @param newAditivo
	 * @return Número de filas afectadas por la actualización.
	 */
	public final int actualizarAditivoLocal(Aditivo newAditivo){
		if(db != null){

			ContentValues nuevoRegistro = new ContentValues();			
			String[] args = new String[]{newAditivo.getNumero()};

			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_NUMERO, newAditivo.getNumero());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_NOMBRE, newAditivo.getNombre());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_DESCRIPCION, newAditivo.getDescripcion());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_CARACTER, newAditivo.getCaracter());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_ID_TIPO, newAditivo.getTipoAditivo());

			try{
				return db.update(TAditivos.NOMBRE_TABLA, nuevoRegistro, TAditivos.NOMBRE_COLUMNA_NUMERO + "=?", args);				
			}catch(Exception e){
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param pA
	 * @return Id de la fila insertada o -1 si falló.
	 */
	public final long insertarAditivoLocal(Aditivo pA){
		if(db != null){

			ContentValues nuevoRegistro = new ContentValues();

			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_ID, pA.getId());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_NUMERO, pA.getNumero());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_NOMBRE, pA.getNombre());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_DESCRIPCION, pA.getDescripcion());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_CARACTER, pA.getCaracter());
			nuevoRegistro.put(TAditivos.NOMBRE_COLUMNA_ID_TIPO, pA.getTipoAditivo());
			return db.insert(TAditivos.NOMBRE_TABLA, null, nuevoRegistro);			
		}
		return -1;
	}

	/**
	 * 
	 * @param pAxA
	 * @return Id fila insertada, -1 si falló.
	 */
	public final long insertarAditivosXAlimentoLocal(AditivosXAlimento pAxA){
		if(db != null){

			ContentValues nuevoRegistro = new ContentValues();

			nuevoRegistro.put(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, pAxA.getIdAlimento());
			nuevoRegistro.put(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO, pAxA.getIdAditivo());
			return db.insert(TAditivosXAlimento.NOMBRE_TABLA, null, nuevoRegistro);			
		}
		return -1;
	}

	/****** TIPOS ADITIVOS *****/
	/******** CONSULTAS ********/

	/**
	 * 
	 * @param nombre
	 * @return Id del tipoAditivo, -1 si falló.
	 */
	public final int consultaIdTipoAditivoXNombre(String nombre){

		if(db != null){
			String[] campos = {TTiposAditivos.NOMBRE_COLUMNA_ID};
			String categoria = TTiposAditivos.NOMBRE_COLUMNA_NOMBRE + "=?";
			String[] args = new String[] { nombre };

			Cursor result = db.query(
					TTiposAditivos.NOMBRE_TABLA, 
					campos, 
					categoria,
					args, 
					null, null, null);

			if(result.moveToFirst()){
				return result.getInt(0);
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param id
	 * @return Nombre del tipoAditivo, String vacío si falló.
	 */
	public final String consultaNombreTipoAditivoXId(int id){
		if(db != null){
			String[] campos = {TTiposAditivos.NOMBRE_COLUMNA_NOMBRE};
			String categoria = TTiposAditivos.NOMBRE_COLUMNA_ID + "=?";
			String[] args = new String[] { String.valueOf(id) };
			
			Cursor result = db.query(
					TTiposAditivos.NOMBRE_TABLA, 
					campos, 
					categoria,
					args, 
					null, null, null);

			if(result.moveToFirst()){
				return result.getString(0);
			}
		}
		return "";
	}

	/******** TIPOS ADITIVOS *******/
	/******** INSERT UPDATE ********/
	/**
	 * 
	 * @param tipo
	 * @return Id del tipoAditivo insertado, -1 si falló.
	 */
	public final long insertarTipoAditivo(TipoAditivo tipo){
		if(db != null){
			
			ContentValues nuevoRegistro = new ContentValues();

			nuevoRegistro.put(TTiposAditivos.NOMBRE_COLUMNA_ID, tipo.getId());
			nuevoRegistro.put(TTiposAditivos.NOMBRE_COLUMNA_NOMBRE, tipo.getNombre());
			nuevoRegistro.put(TTiposAditivos.NOMBRE_COLUMNA_RANGO, tipo.getRango());
			return db.insert(TTiposAditivos.NOMBRE_TABLA, null, nuevoRegistro);			
		}
		return -1;
	}

	/******* ALIMENTOS **********/
	/******** CONSULTAS ********/
	/**
	  * SELECT COUNT(*) FROM alimentos
	  * WHERE tipo = 'pTipoA'
	  */
	public final String[] consultaNumAlimentosXTipo(){

		if(db != null){
			String[] numAlimentos = new String[S.CATEGORIAS.length];
			for(int i = 0; i < S.CATEGORIAS.length; i++){				
				Cursor result = db.rawQuery(
						" SELECT COUNT(*) " +
						" FROM " + TAlimentos.NOMBRE_TABLA + 
						" WHERE " + TAlimentos.NOMBRE_COLUMNA_TIPO + " = '" + S.CATEGORIAS[i] + "'", null);
				
				result.moveToFirst();
				numAlimentos[i] = String.valueOf(result.getInt(0));				
			}			
			return numAlimentos;
		}

		return new String[0];
	}

	/*
	 * Realiza la consulta: 
	 * SELECT nombre, marca FROM alimentos
	 * WHERE tipo = 'pTipo' 
	 * @return String[] con los nombres de los TIPOS de ADITIVOS
	 */
	public final String[] consultaAlimentosXCategoria(String pCategoria){

		if(db != null){
			String[] campos = {TAlimentos.NOMBRE_COLUMNA_ID, TAlimentos.NOMBRE_COLUMNA_NOMBRE, TAlimentos.NOMBRE_COLUMNA_MARCA};
			String[] args = new String[] { pCategoria };
			String categoria = TAlimentos.NOMBRE_COLUMNA_TIPO + "=?";
			List<String> listaAlimentos = new ArrayList<String>();

			Cursor result = db.query(
					TAlimentos.NOMBRE_TABLA,
					campos, 
					categoria, 
					args, 
					null, null, null);

			if(result.moveToFirst()){
				do{
					listaAlimentos.add(result.getString(0));
					listaAlimentos.add(result.getString(1));
					listaAlimentos.add(result.getString(2));
				}while(result.moveToNext());

				String[] Alimentos = new String[listaAlimentos.size()];
				listaAlimentos.toArray(Alimentos);

				return Alimentos;
			}
		}
		return null;
	}

	/*
	 * SELECT nombre, tipo, descripcion, formato, marca, submarca FROM alimentos
	 * WHERE idAlimento = pId
	 */
	public final Alimento consultaAlimentoXId(String pId){

		if(db != null){
			String[] campos = {
					TAlimentos.NOMBRE_COLUMNA_NOMBRE, TAlimentos.NOMBRE_COLUMNA_TIPO,
					TAlimentos.NOMBRE_COLUMNA_DESCRIPCION, TAlimentos.NOMBRE_COLUMNA_FORMATO,
					TAlimentos.NOMBRE_COLUMNA_MARCA, TAlimentos.NOMBRE_COLUMNA_SUBMARCA,pId,
					TAlimentos.NOMBRE_COLUMNA_CODIGO1D, TAlimentos.NOMBRE_COLUMNA_CODIGO2D};
			String[] args = new String[] { pId };
			String categoria = TAlimentos.NOMBRE_COLUMNA_ID + "=?";

			Cursor result = db.query(
					TAlimentos.NOMBRE_TABLA,	// Tabla 
					campos, 					// Columnas
					categoria, 					// Columnas Where
					args, 						// Valores Where
					null, null, null);			

			if(result.moveToFirst()){
				Alimento alimento = new Alimento(result.getString(0), result.getString(1), result.getString(2), 
						result.getString(3), result.getString(4), result.getString(5),result.getString(6),result.getString(7));
				return alimento;
			}
		}
		return null;
	}
	
	/*
	 * SELECT nombre, cantidad FROM nutrientesXalimento, nutrientes
	 * WHERE idNutrienteForeign = idNutriente AND idAlimentoForeign = pID
	 */
	public final String[] consultaNutrientesAlimentoXId(String pId){
		
		if(db != null){
			List<String> list = new ArrayList<String>();
			Cursor result = db.rawQuery(
					" SELECT " + TNutrientes.NOMBRE_COLUMNA_NOMBRE + COMMA_SEP + TNutrientesXAlimento.NOMBRE_COLUMNA_CANTIDAD +
					" FROM " + TNutrientes.NOMBRE_TABLA + COMMA_SEP + TNutrientesXAlimento.NOMBRE_TABLA + 
					" WHERE " + TNutrientes.NOMBRE_COLUMNA_ID + " = " + TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE +
					" AND " + TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + " = '" + pId + "'" , null);

			if(result.moveToFirst()){
				do{
					list.add(result.getString(0));
					list.add(result.getString(1));
				}while(result.moveToNext());
				String[] nutrientes = new String[list.size()];
				list.toArray(nutrientes);
				
				return nutrientes;
			}
		}
		return new String[0];
	}

	/*
	 * SELECT numero, nombre FROM aditivos,  aditivosXalimento
	 * WHERE idAlimentoForeign = pId AND idAditivo = idAditivoForeign
	 */
	public final String[] consultaAditivosAlimentoXId(String pId){
		
		if(db != null){
			List<String> list = new ArrayList<String>();
			Cursor result = db.rawQuery(
					" SELECT " + TAditivos.NOMBRE_COLUMNA_NUMERO + COMMA_SEP + TAditivos.NOMBRE_COLUMNA_NOMBRE +					
					" FROM " + TAditivos.NOMBRE_TABLA + COMMA_SEP + TAditivosXAlimento.NOMBRE_TABLA +
					" WHERE " + TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO + " = " + TAditivos.NOMBRE_COLUMNA_ID +
						" AND " +  TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + " = '" + pId + "'" , null);

			if(result.moveToFirst()){
				do{
					list.add(result.getString(0));
					list.add(result.getString(1));
				}while(result.moveToNext());
				String[] aditivos = new String[list.size()];
				list.toArray(aditivos);
				
				return aditivos;
			}
		}
		return new String[0];
	}

	/*  
	 * SELECT nombre FROM nutrientes, nutrientesXalimento
	 * WHERE idNutriente = idNutrienteForeign AND idAlimentoForeign = ( SELECT idAlimento FROM alimentos
	 * 														   			WHERE nombre LIKE '%pNombre%') 
	 */
	public final String[] consultaAlimentoXNombre(String pNombre){
		
		if(db != null){
			Cursor result1 = db.rawQuery(
					" SELECT " + TAlimentos.NOMBRE_COLUMNA_ID + COMMA_SEP + 
								 TAlimentos.NOMBRE_COLUMNA_NOMBRE + COMMA_SEP + 
								 TAlimentos.NOMBRE_COLUMNA_MARCA +
					" FROM " +   TAlimentos.NOMBRE_TABLA + 
					" WHERE " +  TAlimentos.NOMBRE_COLUMNA_NOMBRE + " LIKE ?", new String[] {"%" + pNombre + "%" } );
			
			List<String> lista = new ArrayList<String>();
			
			
			if(result1.moveToFirst()){
				do{
					lista.add(result1.getString(0));
					lista.add(result1.getString(1));
					lista.add(result1.getString(2));
				}while(result1.moveToNext());

				String[] Alimentos = new String[lista.size()];
				lista.toArray(Alimentos);
				return Alimentos;
			}
		}
		
		return new String[0];
	}

	/*
	 * SELECT 
	 */
	public final Alimento consultaAlimentoXCodigo(String pContenido, String pFormato){
		
		if(db != null){						
			Alimento alimento;
			String[] campos = { TAlimentos.NOMBRE_COLUMNA_ID, TAlimentos.NOMBRE_COLUMNA_NOMBRE, 
								TAlimentos.NOMBRE_COLUMNA_TIPO, TAlimentos.NOMBRE_COLUMNA_DESCRIPCION, 
								TAlimentos.NOMBRE_COLUMNA_FORMATO, TAlimentos.NOMBRE_COLUMNA_MARCA, 
								TAlimentos.NOMBRE_COLUMNA_SUBMARCA, TAlimentos.NOMBRE_COLUMNA_CODIGO1D, 
								TAlimentos.NOMBRE_COLUMNA_CODIGO2D};

			String[] args = new String[] { pContenido };
			String categoria;
			if(pFormato.equals(S.QR_FORMAT)){	// QR CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO2D + "=?";
			}else{								// 1D CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO1D + "=?";
			}
			
			Cursor result = db.query(TAlimentos.NOMBRE_TABLA,
					campos,
					categoria,
					args,
					null, null, null);
			
			
			if(result.moveToFirst()){
				alimento = new Alimento(result.getInt(0), result.getString(1), result.getString(2), 
						result.getString(3), result.getString(4), result.getString(5), result.getString(6),
						result.getString(7), result.getString(8));
				return alimento;
			}
		}
		
		return null;
	}
	
	public final String[] consultaNombreIngredientesAlimentoXCodigo(String pContenido, String pFormato){
		
		if(db != null){	
			List<String> list = new ArrayList<String>();
			String categoria;
			if(pFormato.equals(S.QR_FORMAT)){	// QR CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO2D;
			}else{								// 1D CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO1D;
			}
			
			Cursor result = db.rawQuery(
					" SELECT I." + TIngredientes.NOMBRE_COLUMNA_NOMBRE + 
					" FROM " + TAlimentos.NOMBRE_TABLA + " AS A," + 
							TIngredientes.NOMBRE_TABLA + " AS I, " + TIngredientesXAlimento.NOMBRE_TABLA + " AS IA " +
					" WHERE " + "A." + TAlimentos.NOMBRE_COLUMNA_ID + " = " + "IA." + TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + 
						" AND " + "I." + TIngredientes.NOMBRE_COLUMNA_ID + " = " + "IA." + TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE + 
						" AND " + categoria + " = '" + pContenido + "'", null);

			if(result.moveToFirst()){
				do{
					list.add(result.getString(0));
				}while(result.moveToNext());
				
				String[] nutrientes = new String[list.size()];
				list.toArray(nutrientes);
				
				return nutrientes;
			}
		}
		return new String[0];
	}
	
	/*
	 * SELECT N.nombre, cantidad FROM alimentos, nutrientes AS N, nutrientesXalimento
	 * WHERE idAlimento = idAlimentoForeign AND idNutriente = idNutrienteForeign AND codigoXD = pContenido
	 */
	public final String[] consultaNutrientesAlimentoXCodigo(String pContenido, String pFormato){
		
		if(db != null){	
			List<String> list = new ArrayList<String>();
			String categoria;
			if(pFormato.equals(S.QR_FORMAT)){	// QR CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO2D;
			}else{								// 1D CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO1D;
			}
			
			Cursor result = db.rawQuery(
					" SELECT N." + TNutrientes.NOMBRE_COLUMNA_NOMBRE + COMMA_SEP + TNutrientesXAlimento.NOMBRE_COLUMNA_CANTIDAD +
					" FROM " + TAlimentos.NOMBRE_TABLA + " AS A,"+
							TNutrientes.NOMBRE_TABLA + " AS N, " + TNutrientesXAlimento.NOMBRE_TABLA + " AS NA" +
					" WHERE " + "A." + TAlimentos.NOMBRE_COLUMNA_ID + " = " + "NA." + TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + 
						" AND " + "N." + TNutrientes.NOMBRE_COLUMNA_ID + " = " + "NA." + TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE + 
						" AND " + categoria + " = '" + pContenido + "'", null);

			if(result.moveToFirst()){
				do{
					list.add(result.getString(0));
					list.add(result.getString(1));
				}while(result.moveToNext());
				
				String[] nutrientes = new String[list.size()];
				list.toArray(nutrientes);
				
				return nutrientes;
			}
		}
		
		return new String[0];
	}
	
	/*
	 * SELECT A.numero, A.nombre FROM alimentos, aditivos A N, aditivosXalimento
	 * WHERE idAlimento = idAlimentoForeign AND idAditivo = idAditivoForeign AND codigoXD = pContenido
	 */
	public final String[] consultaAditivosAlimentoXCodigo(String pContenido, String pFormato){
		
		if(db != null){
			List<String> list = new ArrayList<String>();
			String categoria;
			if(pFormato.equals(S.QR_FORMAT)){	// QR CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO2D;
			}else{								// 1D CODE
				categoria = TAlimentos.NOMBRE_COLUMNA_CODIGO1D;
			}

			Cursor result = db.rawQuery(
					" SELECT A." + TAditivos.NOMBRE_COLUMNA_NUMERO + ", A." + TAditivos.NOMBRE_COLUMNA_NOMBRE +
					" FROM " +  TAlimentos.NOMBRE_TABLA + " AS AL," + 
								TAditivos.NOMBRE_TABLA + " AS A," + 
								TAditivosXAlimento.NOMBRE_TABLA + " AS AA" +
					" WHERE " + "AL." + TAlimentos.NOMBRE_COLUMNA_ID + " = " + "AA." + TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO +" AND " +
								"A." + TAditivos.NOMBRE_COLUMNA_ID + " = " + "AA." + TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO + " AND " + 
								categoria + " = '" + pContenido + "'", null);
			
			if(result.moveToFirst()){
				do{
					list.add(result.getString(0));
					list.add(result.getString(1));
				}while(result.moveToNext());
				String[] aditivos = new String[list.size()];
				list.toArray(aditivos);
				
				return aditivos;
			}
		}
		
		return new String[0];
	}

	/** Obtiene los nombres de todos los Alimentos disponibles.	
	 */
	public final String[] consultaNombreAlimentos(){
		if(db != null){
			String[] columnas = { TAlimentos.NOMBRE_COLUMNA_NOMBRE };
			Cursor result = db.query(
					TAlimentos.NOMBRE_TABLA,
					columnas,
					null, null, null, null, null);
			
			List<String> list = new ArrayList<String>();
			if(result.moveToFirst()){			
				do{
					list.add(result.getString(0));
				}while(result.moveToNext());

				String[] alimentos = new String[list.size()];
				list.toArray(alimentos);
				
				return alimentos;
			}
		}
		return null;
	}
	
	public final void insertAlimento(Alimento pAlimento, ArrayList<String> datosIngredientes, 
									ArrayList<String> datosNutrientes, ArrayList<Titulos> datosAditivos){
		if(db != null){
			ContentValues nuevoRegistro;

			// INSERTANDO ALIMENTO
			nuevoRegistro = new ContentValues();
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_NOMBRE, pAlimento.getNombre());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_TIPO, pAlimento.getTipo());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_DESCRIPCION, pAlimento.getDescripcion());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_FORMATO, pAlimento.getFormato());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_MARCA, pAlimento.getMarca());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_SUBMARCA, pAlimento.getSubmarca());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_CODIGO1D, pAlimento.getCodigo1D());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_CODIGO2D, pAlimento.getCodigo2D());

			int idAlimento = (int) db.insert(TAlimentos.NOMBRE_TABLA, null, nuevoRegistro);
			int idAditivo, idIngrediente, idNutriente;

			// INSERTANDO ADITIVOS X ALIMENTO
			for(int i = 0; i < datosAditivos.size(); i++){
				idAditivo = consultaAditivoXNumero(datosAditivos.get(i).getTitulo()).getId();

				nuevoRegistro = new ContentValues();
				nuevoRegistro.put(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, idAlimento);
				nuevoRegistro.put(TAditivosXAlimento.NOMBRE_COLUMNA_ID_ADITIVO, idAditivo);
				db.insert(TAditivosXAlimento.NOMBRE_TABLA, null, nuevoRegistro);
			}

			// INSERTANDO INGREDIENTES
			for(int i = 0; i < datosIngredientes.size(); i = i + 2){
				idIngrediente = this.consultaIdIngredienteXNombre(datosIngredientes.get(i));
				
				if(idIngrediente == -1){
					nuevoRegistro = new ContentValues();
					nuevoRegistro.put(TIngredientes.NOMBRE_COLUMNA_NOMBRE, datosIngredientes.get(i));
					idIngrediente = (int) db.insert(TIngredientes.NOMBRE_TABLA, null, nuevoRegistro);
				}
				
				nuevoRegistro = new ContentValues();
				nuevoRegistro.put(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, idAlimento);
				nuevoRegistro.put(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE, idIngrediente);
				nuevoRegistro.put(TIngredientesXAlimento.NOMBRE_COLUMNA_CANTIDAD, datosIngredientes.get(i+1));
				db.insert(TIngredientesXAlimento.NOMBRE_TABLA, null, nuevoRegistro);				
			}

			// INSERTANDO NUTRIENTES
			for(int i = 0; i < datosNutrientes.size(); i = i + 2){
				idNutriente = this.consultaIdNutrienteXNombre(datosNutrientes.get(i));
				
				if(idNutriente == -1){
					nuevoRegistro = new ContentValues();
					nuevoRegistro.put(TNutrientes.NOMBRE_COLUMNA_NOMBRE, datosNutrientes.get(i));
					idNutriente = (int) db.insert(TNutrientes.NOMBRE_TABLA, null, nuevoRegistro);
				}
				
				nuevoRegistro = new ContentValues();
				nuevoRegistro.put(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, idAlimento);
				nuevoRegistro.put(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE, idNutriente);
				nuevoRegistro.put(TNutrientesXAlimento.NOMBRE_COLUMNA_CANTIDAD, datosNutrientes.get(i+1));
				db.insert(TNutrientesXAlimento.NOMBRE_TABLA, null, nuevoRegistro);				
			}
		}
	}
	
	public final long insertarAlimentoLocal(Alimento pA){
		if(db != null){
			ContentValues nuevoRegistro;

			nuevoRegistro = new ContentValues();
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_ID, pA.getId());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_NOMBRE, pA.getNombre());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_TIPO, pA.getTipo());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_DESCRIPCION, pA.getDescripcion());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_FORMATO, pA.getFormato());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_MARCA, pA.getMarca());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_SUBMARCA, pA.getSubmarca());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_CODIGO1D, pA.getCodigo1D());
			nuevoRegistro.put(TAlimentos.NOMBRE_COLUMNA_CODIGO2D, pA.getCodigo2D());

			try{
				return db.insert(TAlimentos.NOMBRE_TABLA, null, nuevoRegistro);
			}catch(SQLException e){				
			}
		}
		return -1;
	}

	/******* INGREDIENTES *******/
	/******** CONSULTAS ********/

	/**
	 * 
	 * @return Nombres de todos los Ingredientes, String[] vacío si falló.
	 */
	public final String[] consultaNombreIngredientes(){
		
		if(db != null){
			String[] columnas = { TIngredientes.NOMBRE_COLUMNA_NOMBRE };
			Cursor result = db.query(
					TIngredientes.NOMBRE_TABLA,
					columnas,
					null, null, null, null, null);
			List<String> list = new ArrayList<String>();
			if(result.moveToFirst()){			
				do{
					list.add(result.getString(0));
				}while(result.moveToNext());
				
				String[] ingredientes = new String[list.size()];
				list.toArray(ingredientes);
				
				return ingredientes;
			}
		}
		return new String[0];
	}

	/**
	 * 
	 * @param pNombre
	 * @return Id de un Ingrediente, -1 si falló.
	 */
	public final int consultaIdIngredienteXNombre(String pNombre){

		if(db != null){
			String[] columnas = { TIngredientes.NOMBRE_COLUMNA_ID };
			String where = TIngredientes.NOMBRE_COLUMNA_NOMBRE + "=?";
			String[] args = { pNombre };
			Cursor result = db.query(
					TIngredientes.NOMBRE_TABLA,
					columnas,
					where, 
					args,
					null, null, null);
			
			if(result.moveToFirst()){			
				return result.getInt(0);
			}
		}
		return -1;
	}
	
	/********* INGREDIENTES ********/
	/******** INSERT UPDATE ********/
	
	/**
	 * 
	 * @param pIng
	 * @return Id del Ingrediente insertado, -1 si falló.
	 */
	public final long insertarIngredienteLocal(Ingrediente pIng){
		if(db != null){
			ContentValues nuevoRegistro = new ContentValues();
			
			nuevoRegistro.put(TIngredientes.NOMBRE_COLUMNA_ID, pIng.getId());
			nuevoRegistro.put(TIngredientes.NOMBRE_COLUMNA_NOMBRE, pIng.getNombre());
			nuevoRegistro.put(TIngredientes.NOMBRE_COLUMNA_DESCRIPCION, pIng.getDescripcion());
			return db.insert(TIngredientes.NOMBRE_TABLA, null, nuevoRegistro);			
		}
		return -1;
	}
	
	/******* INGREDIENTES X ALIMENTO *******/
	/************** CONSULTAS **************/
	/**
	 * 
	 * @param pId
	 * @return [nombre, cantidad] de todos los Ingredientes de un Alimento, String[] vacío si falló.
	 */
	public final String[] consultaIngredientesAlimentoXId(String pId){
		
		if(db != null){
			List<String> list = new ArrayList<String>();
			Cursor result = db.rawQuery(
					" SELECT " + TIngredientes.NOMBRE_COLUMNA_NOMBRE + COMMA_SEP + 
								TIngredientesXAlimento.NOMBRE_COLUMNA_CANTIDAD +
					" FROM " + TIngredientes.NOMBRE_TABLA + COMMA_SEP + TIngredientesXAlimento.NOMBRE_TABLA + 
					" WHERE " + TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE + " = " + TIngredientes.NOMBRE_COLUMNA_ID +
					" AND " + TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO + " = '" + pId + "'" , null);
			
			if(result.moveToFirst()){
				do{
					list.add(result.getString(0));
					list.add(result.getString(1));
				}while(result.moveToNext());
				String[] ingredientes = new String[list.size()];
				list.toArray(ingredientes);
				
				return ingredientes;
			}
		}
		
		return new String[0];
	}

	/******* INGREDIENTES X ALIMENTO *******/
	/************* INSERT UPDATE ***********/
	/**
	 * 
	 * @param pIxA
	 * @return Id de IngredienteXAlimento, -1 si falló.
	 */
	public final long insertarIngredientesXAlimentoLocal(IngredientesXAlimento pIxA){
		if(db != null){
			
			ContentValues nuevoRegistro = new ContentValues();

			nuevoRegistro.put(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, pIxA.getIdAlimento());
			nuevoRegistro.put(TIngredientesXAlimento.NOMBRE_COLUMNA_ID_INGREDIENTE, pIxA.getIdIngrediente());
			nuevoRegistro.put(TIngredientesXAlimento.NOMBRE_COLUMNA_CANTIDAD, pIxA.getCantidad());
			return db.insert(TIngredientesXAlimento.NOMBRE_TABLA, null, nuevoRegistro);			
		}
		return -1;
	}


	/********* NUTRIENTES ***********/

	public final String[] consultaNombreNutrientes(){
		if(db != null){
			String[] columnas = { TNutrientes.NOMBRE_COLUMNA_NOMBRE };
			Cursor result = db.query(
					TNutrientes.NOMBRE_TABLA,
					columnas,
					null, null, null, null, null);
			
			List<String> list = new ArrayList<String>();
			if(result.moveToFirst()){			
				do{
					list.add(result.getString(0));
				}while(result.moveToNext());
				
				String[] nutrientes = new String[list.size()];
				list.toArray(nutrientes);
				
				return nutrientes;
			}
		}
		return new String[0];
	}

	public final int consultaIdNutrienteXNombre(String pNombre){
		
		if(db != null){
			String[] columnas = { TNutrientes.NOMBRE_COLUMNA_ID };
			String where = TNutrientes.NOMBRE_COLUMNA_NOMBRE + "=?";
			String[] args = { pNombre };
			Cursor result = db.query(
					TNutrientes.NOMBRE_TABLA,
					columnas,
					where, 
					args,
					null, null, null);
			
			if(result.moveToFirst()){			
				return result.getInt(0);
			}
		}
		return -1;
	}

	public final long insertarNutrienteLocal(Nutriente pN){
		if(db != null){
			ContentValues nuevoRegistro = new ContentValues();
			
			nuevoRegistro.put(TNutrientes.NOMBRE_COLUMNA_ID, pN.getId());
			nuevoRegistro.put(TNutrientes.NOMBRE_COLUMNA_NOMBRE, pN.getNombre());
			nuevoRegistro.put(TNutrientes.NOMBRE_COLUMNA_DESCRIPCION, pN.getDescripcion());
			return db.insert(TNutrientes.NOMBRE_TABLA, null, nuevoRegistro);			
		}
		return -1;
		
	}
	
	public final long insertarNutrientesXAlimentoLocal(NutrientesXAlimento pNxA){
		if(db != null){
			
			ContentValues nuevoRegistro = new ContentValues();

			nuevoRegistro.put(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_ALIMENTO, pNxA.getIdAlimento());
			nuevoRegistro.put(TNutrientesXAlimento.NOMBRE_COLUMNA_ID_NUTRIENTE, pNxA.getIdNutriente());
			nuevoRegistro.put(TNutrientesXAlimento.NOMBRE_COLUMNA_CANTIDAD, pNxA.getCantidad());
			return db.insert(TNutrientesXAlimento.NOMBRE_TABLA, null, nuevoRegistro);			
		}
		return -1;
	}
	
	/******** FILTROS ***********/

	/*
	 * Busca una regla según su estructura.
	 * @return El id de la regla o -1 si no se encontró.
	 */
	public final int consultaIdRegla(String regla){
		
		if(db != null){
			String[] campos = {TReglas.NOMBRE_COLUMNA_ID};
			String categoria = TReglas.NOMBRE_COLUMNA_REGLA + "=?";
			String[] args = new String[] { regla };
			Cursor result = db.query(
					TReglas.NOMBRE_TABLA, 
					campos, 
					categoria, 
					args, 
					null, null, null);

			if(result.moveToFirst()){				
				return result.getInt(0);
			}
		}
		
		return -1;
	}
	

	public final boolean existeRegla(int idRegla){
		if(db != null){
			Cursor result = db.rawQuery("SELECT count(*) FROM "  + TReglas.NOMBRE_TABLA + 
										" WHERE " + TReglas.NOMBRE_COLUMNA_ID + " = " + idRegla, null);
			if(result.moveToFirst()){				
				return true;
			}else{
				return false;
			}			
		}
		return false;
	}
	
	
	/*
	 * Devuelve la lista completa de reglas para un filtro determinado.
	 * SELECT regla FROM reglas
	 * WHERE idRegla = (SELECT idReglaForeign FROM reglasXfiltro
	 * 					WHERE idFiltroForeign = (SELECT idFiltro FROM filtros
	 * 											WHERE nombre = pNombre))
	 */
	public final ArrayList<Regla> consultaReglasXFiltro(String pNombre){

		if(db != null){
			ArrayList<Regla> listaReglas = new ArrayList<Regla>();

			Cursor result = db.rawQuery(
					"SELECT " + "R." + TReglas.NOMBRE_COLUMNA_REGLA + " FROM " + TReglas.NOMBRE_TABLA + " AS R " +
					" JOIN " + ReglasXFiltro.NOMBRE_TABLA + " AS RF " +
								" ON " + "R." + TReglas.NOMBRE_COLUMNA_ID + " = " + "RF." + ReglasXFiltro.NOMBRE_COLUMNA_ID_REGLA +
				    " JOIN " + TFiltros.NOMBRE_TABLA + " AS F " + 
				    			" ON " + "F." + TFiltros.NOMBRE_COLUMNA_ID + " = " + "RF." +ReglasXFiltro.NOMBRE_COLUMNA_ID_FILTRO +
				    " WHERE " + "F." + TFiltros.NOMBRE_COLUMNA_NOMBRE + " = '" + pNombre + "'", null);
			
			if(result.moveToFirst()){
				do{
					Regla regla = new Regla(result.getString(0));
					listaReglas.add(regla);
				}while(result.moveToNext());

				return listaReglas;
			}
		}
		return new ArrayList<Regla>();
	}

	
	/*
	 * Devuelve la lista completa de filtros y descripciones.
	 */
	public final String[] consultaFiltros(){
		
		if(db != null){
			List<String> listaFiltros = new ArrayList<String>();
			String[] campos = {TFiltros.NOMBRE_COLUMNA_NOMBRE, TFiltros.NOMBRE_COLUMNA_DESCRIPCION};
			Cursor result = db.query(
					TFiltros.NOMBRE_TABLA, 
					campos, 
					null, null, null, null, null);
			
			
			if(result.moveToFirst()){
				do{
					listaFiltros.add(result.getString(0));
					listaFiltros.add(result.getString(1));
				}while(result.moveToNext());
				String[] filtros = new String[listaFiltros.size()];
				listaFiltros.toArray(filtros);
				
				return filtros;
			}
		}
		
		return new String[0];
	}
	
	
	/*
	 * Busca un filtro según su nombre.
	 * @return Id del filtro o -1 si no existía.
	 */
	public final int consultaIdFiltro(String pNombre){
		
		if(db != null){
			String[] campos = {TFiltros.NOMBRE_COLUMNA_ID};
			String categoria = TFiltros.NOMBRE_COLUMNA_NOMBRE + "=?";
			String[] args = new String[] { pNombre };
			Cursor result = db.query(
					TFiltros.NOMBRE_TABLA, 
					campos, 
					categoria, 
					args, null, null, null);
			
			
			if(result.moveToFirst()){				
				return result.getInt(0);
			}
		}
		return -1;
	}


	public final void insertFiltro(String pNombre, String pDescripcion, String[] pReglas){

		if(db != null){
			int idRegla, idFiltro;
			ContentValues nuevoRegistro;

			// INSERTANDO FILTRO
			nuevoRegistro = new ContentValues();
			nuevoRegistro.put(TFiltros.NOMBRE_COLUMNA_NOMBRE, pNombre);
			nuevoRegistro.put(TFiltros.NOMBRE_COLUMNA_DESCRIPCION, pDescripcion);
			idFiltro = (int) db.insert(TFiltros.NOMBRE_TABLA, null, nuevoRegistro);

			// INSERTANDO REGLAS
			for(int i = 0; i < pReglas.length; i++){
				idRegla = consultaIdRegla(pReglas[i]);

				if(idRegla == -1){	// No existe la regla, hay que crearla
					nuevoRegistro = new ContentValues();
					nuevoRegistro.put("regla", pReglas[i]);
					idRegla = (int) db.insert(TReglas.NOMBRE_TABLA, null, nuevoRegistro);
				}
				
				// INSERTANDO en reglasXfiltro
				if(!consultaExisteReglaXFiltro(idRegla, idFiltro)){
					nuevoRegistro = new ContentValues();
					nuevoRegistro.put("idReglaForeign", idRegla);
					nuevoRegistro.put("idFiltroForeign", idFiltro);
					db.insert(ReglasXFiltro.NOMBRE_TABLA, null, nuevoRegistro);
				}
			}
		}
	}


	public final boolean consultaExisteReglaXFiltro(int idRegla, int idFiltro){
		if(db != null){
			Cursor result = db.rawQuery("SELECT count(*) FROM " + ReglasXFiltro.NOMBRE_TABLA + 
										" WHERE " + ReglasXFiltro.NOMBRE_COLUMNA_ID_FILTRO + " = " + idFiltro + 
										" AND " + ReglasXFiltro.NOMBRE_COLUMNA_ID_REGLA + " = " + idRegla, null);
			
			if(result.moveToFirst()){
				if(result.getInt(0) > 0){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}


	public final void actualizarFiltro(String pOldNombre, String pNombre, String pDescripcion, String[] pReglas){
		
		
		if(db != null){
			int idRegla, idFiltro;
			ContentValues nuevoRegistro;
			
			idFiltro = consultaIdFiltro(pOldNombre);
			if(idFiltro == -1){
				Toast.makeText(this.context, "Error actualizando el filtro", Toast.LENGTH_LONG).show();
				return;
			}

			// ACTUALIZANDO FILTRO
			String[] args = new String[]{pOldNombre};
			nuevoRegistro = new ContentValues();
			nuevoRegistro.put(TFiltros.NOMBRE_COLUMNA_NOMBRE, pNombre);
			nuevoRegistro.put(TFiltros.NOMBRE_COLUMNA_DESCRIPCION, pDescripcion);
			try{
				db.update(TFiltros.NOMBRE_TABLA, nuevoRegistro, TFiltros.NOMBRE_COLUMNA_NOMBRE+ "=?", args);
			}catch(Exception e){
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
			}
			
			// ACTUALIZANDO LAS REGLAS
			for(int i = 0; i < pReglas.length; i++){
				idRegla = consultaIdRegla(pReglas[i]);
								
				if(idRegla == -1){	// No existe la regla, hay que crearla
					nuevoRegistro = new ContentValues();
					nuevoRegistro.put(TReglas.NOMBRE_COLUMNA_REGLA, pReglas[i]);
					idRegla = (int) db.insert(TReglas.NOMBRE_TABLA, null, nuevoRegistro);
				}
				// INSERTANDO en reglasXfiltro
				if(!consultaExisteReglaXFiltro(idRegla, idFiltro)){
					nuevoRegistro = new ContentValues();
					nuevoRegistro.put(ReglasXFiltro.NOMBRE_COLUMNA_ID_REGLA, idRegla);
					nuevoRegistro.put(ReglasXFiltro.NOMBRE_COLUMNA_ID_FILTRO, idFiltro);
					try{					
						db.insert(ReglasXFiltro.NOMBRE_TABLA, null, nuevoRegistro);
					}catch(Exception e){
						Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
					}
				}
			}
		}
	}


	public final void suprimirFiltro(String pNombre){
		
		if(db != null){
			String where = TFiltros.NOMBRE_COLUMNA_NOMBRE + "=?";
			String[] args = new String[]{ pNombre };
			db.delete(TFiltros.NOMBRE_TABLA, where, args);
		}
	}
	

	public final void suprimirRegla(String pRegla, String pFiltro){
		
		if(db != null){
			String where = TReglas.NOMBRE_COLUMNA_REGLA + "=?";
			String[] args = new String[]{ pRegla };
			String[] campos = {TReglas.NOMBRE_COLUMNA_ID};
			
			Cursor result = db.query(
					TReglas.NOMBRE_TABLA,	// Tabla
					campos,							// Columnas a devolver
					where,							// Columnas cláusula WHERE
					args,							// Valores cláusula WHERE
					null,							// Agrupacion filas
					null,							// Filtrar por grupos de filas
					null);							// Orden a utilizar
			
			
			String where2 = TFiltros.NOMBRE_COLUMNA_NOMBRE + "=?";
			String[] args2 = new String[]{pFiltro };
			String[] campos2 = {TFiltros.NOMBRE_COLUMNA_ID};
			
			Cursor result2 = db.query(
					TFiltros.NOMBRE_TABLA,	// Tabla
					campos2,							// Columnas a devolver
					where2,							// Columnas cláusula WHERE
					args2,							// Valores cláusula WHERE
					null,							// Agrupacion filas
					null,							// Filtrar por grupos de filas
					null);							// Orden a utilizar
			int idRegla, idFiltro;
			
			if(result.moveToFirst() && result2.moveToFirst()){
				idRegla = result.getInt(0);
				idFiltro = result2.getInt(0);
				
				db.execSQL(" DELETE FROM "+ ReglasXFiltro.NOMBRE_TABLA + 
						" WHERE " + ReglasXFiltro.NOMBRE_COLUMNA_ID_FILTRO + " = " + idFiltro + 
						" AND " + ReglasXFiltro.NOMBRE_COLUMNA_ID_REGLA + " = " + idRegla);
			}			
		}
	}

}
