package jsonrates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import menjacnica.Valuta;
public class JsonRatesAPIKomunikacija {
	
	private static final String appKey = "jr-ba8999934fc5a7ab64a4872fb4ed9af7";
	private static final String jsonRatesURL = "http://jsonrates.com/get/";
	
	public LinkedList<Valuta> vratiIznosKurseva(String[] nazivi) {
		
		LinkedList<Valuta> valute = new LinkedList<Valuta>();
		
		for (int i = 0; i < nazivi.length; i++) {
			String from = nazivi[i];
			String to = "RSD";			
			String url = jsonRatesURL + "?" +
					"from=" + from +
					"&to=" + to +
					"&apiKey=" + appKey;
			
			Gson gson = new GsonBuilder().create();
			try {
				JsonObject result = gson.fromJson(sendGet(url), JsonObject.class);
				
				Valuta v = new Valuta();
				v.setNaziv(result.get("from").getAsString());
				v.setKurs(result.get("rate").getAsDouble());
				
				valute.add(v);
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return valute;
		
	}
	
	private String sendGet(String url) throws IOException {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		
		boolean endReading = false;
		String response = "";
		
		while (!endReading) {
			String s = in.readLine();
			
			if (s != null) {
				response += s;
			} else {
				endReading = true;
			}
		}
		in.close();
 
		return response.toString();
	}

}
