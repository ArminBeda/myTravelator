/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package RestClient;

import java.net.URL;
import java.io.*;
import java.net.MalformedURLException;
import java.util.Base64;
import javax.net.ssl.HttpsURLConnection;

/**
 *
 * @author yusefoenkol
 */
public class RestClient {

    protected static String name;
    protected static String password;
    public static void main(String[] args) throws Exception {
       //System.out.println(holeDatenVonWebAPI());
       try {
	        String webPage = "https://localhost:8443/jTodo/webresources/api/user/";
	        
                name = "AbuHacker";
                password = "Hallo123";
	        String authString = name + ":" + password;
	        System.out.println("auth string: " + authString);
	        byte[] authEncBytes = Base64.getUrlEncoder().encode(authString.getBytes());
	        String authStringEnc = new String(authEncBytes);
	        System.out.println("Base64 encoded auth string: " + authStringEnc);

	        URL url = new URL(webPage);
	        HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
	        urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
	        InputStream is = urlConnection.getInputStream();
	        InputStreamReader isr = new InputStreamReader(is);

	        int numCharsRead;
	        char[] charArray = new char[1024];
	        StringBuffer sb = new StringBuffer();
	        while ((numCharsRead = isr.read(charArray)) > 0) {
	            sb.append(charArray, 0, numCharsRead);
	        }
	        String result = sb.toString();

	        System.out.println("*** BEGIN ***");
	        System.out.println(result);
	        System.out.println("*** END ***");
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

    public static String holeDatenVonWebAPI() throws Exception {

        URL url = null;
        HttpsURLConnection conn = null;
        String httpsErgebnisDokument = "";

        url = new URL("https://localhost:8443/jTodo/webresources/api/user/");
        conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET"); // Eigentlich nicht nötig, weil "GET" Default-Wert ist.
        
        if (conn.getResponseCode() != HttpsURLConnection.HTTP_OK) {
            String errorMessage = "HTTP-Fehler: " + conn.getResponseMessage();
            System.out.println(errorMessage);
            throw new Exception(errorMessage);
        } else {
            InputStream is = conn.getInputStream();
            InputStreamReader ris = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(ris);

            // JSON-Dokument zeilenweise einlesen
            String zeile = "";
            while ((zeile = reader.readLine()) != null) {
                httpsErgebnisDokument += zeile;
            }
        }

        return httpsErgebnisDokument;
    }
}
