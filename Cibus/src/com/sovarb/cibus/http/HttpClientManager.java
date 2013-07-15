package com.sovarb.cibus.http;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpClientManager {

    private String responseBody = "";
	private Activity activity;
    private ArrayList<NameValuePair> nameValuePairs;

    public HttpClientManager(Activity activity){
    	this.activity = activity;
    	nameValuePairs = new ArrayList<NameValuePair>();
    }

    /** GETTERS SETTERS **/
    public String getResponseBody(){
    	return responseBody;
    } 
    private void setResponseBody(String ResponseBody){
    	responseBody = ResponseBody;
    }

    private boolean isInternetAllowed(Activity activity){
    	ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isAvailable() && ni.isConnected()) {
        	return true;
        }else{
        	return false; 
        }
    }  

    public void addNameValue(String name, String value){
    	nameValuePairs.add(new BasicNameValuePair(name,value));
    }


    /** MAIN METHOD **/
    public String executeHttpPostAsync(String UrlService){
    	if(isInternetAllowed(this.activity)){
    		try{
    			HttpClient httpclient = new DefaultHttpClient(); // Realiza conexion con SERVIDOR
    			HttpPost httppost = new HttpPost(UrlService);	// Envia por POST los parametros
    			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

    			ResponseHandler<String> responseHandler = new BasicResponseHandler();	// Recoge resultado y parsea a String
    			setResponseBody(httpclient.execute(httppost, responseHandler));    			
    		}catch(HttpResponseException hre){    			
    			setResponseBody(hre.getMessage());
    		}catch(Exception e){
    			setResponseBody(e.getMessage());
    		} 
    	} else{
    		setResponseBody("No es posible realizar la conexión. Comprueba tu conexión de datos.");
    	}
    	return getResponseBody();
    }

}
