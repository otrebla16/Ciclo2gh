package reto2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import java.nio.charset.StandardCharsets;

import java.util.Iterator;

import javax.swing.JOptionPane;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.opencsv.CSVWriter;

/**
 * Esta clase permite convertir un archivo JSON en un archivo CSV
 * 
 * @author Alberto Pérez Arroyo
 *
 */
public class JsonConverter {

	/**
	 * Método que realiza la conversión del archivo JSON que recibe a un CSV en ela misma ruta del archivo original.
	 * 
	 * @param jsonFile es el archivo que será convertido a CSV
	 */
	public void convertFile(File jsonFile) {
		String rutaArchivo = jsonFile.getParent(); //obtenemos la ruta del archivo para aquí mismo guardar el nuevo archivo CSV.
		
		JSONParser jsonParser = new JSONParser(); //preparando el parser para descomponer la cadena JSON.
		JSONArray resultados = new JSONArray(); //preparando el arreglo donde almacenaremos los resultados de la cadena JSON.
		
		//Es de suma importancia utilizar el ISO_8859_1 para leer adecuadamente los acentos
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(jsonFile),StandardCharsets.ISO_8859_1))) {
			Object obj = jsonParser.parse(reader); //parseando el archivo seleccionado por el usuario.
			
			JSONObject jsonObj = (JSONObject) obj; //convirtiendo el resultado en un objeto JSON para su manipulación.
			
			JSONObject searchResults = (JSONObject) jsonObj.get("search-results"); //obteniendo el JSON anidado bajo la clave "search-results".

			resultados = (JSONArray) searchResults.get("entry"); //almacenando los resultados de la clave "entry" ya en un arreglo JSON para navegar por sus elementos.

			@SuppressWarnings("rawtypes")
			Iterator objIter = resultados.iterator(); //construyendo el iterador que permita analizar cada línea del arreglo.
			
			rutaArchivo = rutaArchivo + "\\conversionJSON.csv"; //estableciendo la ruta donde guardaremos el CSV.
			File archivoCSV = new File(rutaArchivo); //creando el nuevo archivo		
			FileWriter fWriter = new FileWriter(archivoCSV, StandardCharsets.ISO_8859_1); //preparando el componente para escribir en el archivo.
			CSVWriter csvWriter = new CSVWriter(fWriter); //utilizando la librería opencsv para generar el archivo CSV.
			
			//preparando el encabezado del archivo CSV
			String[] encabezado = {"URL", "SCOPUS_ID", "EID", "TITLE", "CREATOR", "PUBLICATIONNAME", "ISSN", "EISSN", "VOLUME", "PAGERANGE", "COVERDATE", "COVERDISPLAYDATE", "DOI", "CITEDBY-COUNT", "PUBMED-ID", "AGGREGATIONTYPE", "SUBTYPE", "SUBTYPEDESCRIPTION", "ARTICLE-NUMBER", "SOURCE-ID", "OPENACCESS", "OPENACCESSFLAG"};
			csvWriter.writeNext(encabezado); //escribiendo el encabezado en el archivo.
			
			/*
			 * Mientras existan líneas en el arreglo que estamos iterando, generaremos una nueva línea de datos para el archivo CSV
			 */
			while(objIter.hasNext()) {
				Object resultado = objIter.next(); //apuntamos al siguiente elemento a leer.
				JSONObject resultadoJSON = (JSONObject) resultado; //almacenamos el resultado para poder obtener los valores
				
				//Preparamos los datos que escribiremos en el CSV, cuidando que coincidan con su encabezado correspondiente.					
				String[] data = {((resultadoJSON.get("prism:url") == null) ? "" : resultadoJSON.get("prism:url").toString()), ((resultadoJSON.get("dc:identifier") == null) ? "" : resultadoJSON.get("dc:identifier").toString()), ((resultadoJSON.get("eid") == null) ? "" : resultadoJSON.get("eid").toString()), ((resultadoJSON.get("dc:title") == null) ? "" : resultadoJSON.get("dc:title").toString()), ((resultadoJSON.get("dc:creator") == null) ? "" : resultadoJSON.get("dc:creator").toString()), ((resultadoJSON.get("prism:publicationName") == null) ? "" : resultadoJSON.get("prism:publicationName").toString()), ((resultadoJSON.get("prism:issn") == null) ? "" : resultadoJSON.get("prism:issn").toString()), ((resultadoJSON.get("prism:elssn") == null) ? "" : resultadoJSON.get("prism:elssn").toString()), ((resultadoJSON.get("prism:volume") == null) ? "" : resultadoJSON.get("prism:volume").toString()), ((resultadoJSON.get("prism:pageRange") == null) ? "" : resultadoJSON.get("prism:pageRange").toString()), ((resultadoJSON.get("prism:coverDate") == null) ? "" : resultadoJSON.get("prism:coverDate").toString()), ((resultadoJSON.get("prism:coverDisplayDate") == null) ? "" : resultadoJSON.get("prism:coverDisplayDate").toString()), ((resultadoJSON.get("prism:doi") == null) ? "" : resultadoJSON.get("prism:doi").toString()), ((resultadoJSON.get("citedby-count") == null) ? "" : resultadoJSON.get("citedby-count").toString()), ((resultadoJSON.get("pubmed-id") == null) ? "" : resultadoJSON.get("pubmed-id").toString()), resultadoJSON.get("prism:aggregationType").toString(), resultadoJSON.get("subtype").toString(), resultadoJSON.get("subtypeDescription").toString(), ((resultadoJSON.get("article-number") == null) ? "" : resultadoJSON.get("article-number").toString()), ((resultadoJSON.get("source-id") == null) ? "" : resultadoJSON.get("source-id").toString()), ((resultadoJSON.get("openaccess") == null) ? "" : resultadoJSON.get("openaccess").toString()), ((resultadoJSON.get("openaccessFlag") == null) ? "" : resultadoJSON.get("openaccessFlag").toString())};
				csvWriter.writeNext(data); //escribimos la línea de datos en el archivo.
			}
			
			csvWriter.close(); //cerramos el archivo que hemos creado.
			
			JOptionPane.showMessageDialog(null, "El archivo se generó correctamente en la ruta: " + rutaArchivo, "Éxito", JOptionPane.INFORMATION_MESSAGE);
		}
		catch(FileNotFoundException ex){ex.printStackTrace();}
		catch(IOException ex){ex.printStackTrace();}
		catch(ParseException ex){ex.printStackTrace();}
		catch(Exception ex){ex.printStackTrace();}
	}
}
