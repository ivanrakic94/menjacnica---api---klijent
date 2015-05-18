package menjacnica;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.GregorianCalendar;
import java.util.LinkedList;

import jsonrates.JsonRatesAPIKomunikacija;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class AzuriranjeKursneListe {
	
	private final static String putanjaDoFajlaKursnaLista = "data/kursnaLista.json";
	
	public LinkedList<Valuta> ucitajValute() {
		
		LinkedList<Valuta> valute = new LinkedList<Valuta>();
		
			try {
				FileReader in = new FileReader(putanjaDoFajlaKursnaLista);
				
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				
				JsonObject valuta = gson.fromJson(in, JsonObject.class);
				JsonArray valuteJson = valuta.get("valute").getAsJsonArray();
				
				for (int i = 0; i < valuteJson.size(); i++) {
					JsonObject o = (JsonObject) valuteJson.get(i);
					
					Valuta v = new Valuta();
					v.setKurs(o.get("kurs").getAsDouble());
					v.setNaziv(o.get("naziv").getAsString());
					
					valute.add(v);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return valute;
	}
	
	public void upisiValute(LinkedList<Valuta> valute, GregorianCalendar datum) {
		
		JsonObject val = new JsonObject();
		val.addProperty("datum", datum.getTime().toString());
		
		JsonArray valuteJson = new JsonArray();
 		
		for (int i = 0; i < valute.size(); i++) {
			JsonObject o = new JsonObject();
			
			o.addProperty("naziv", valute.get(i).getNaziv());
			o.addProperty("kurs", valute.get(i).getKurs());
			
			valuteJson.add(o);
		}
		
		val.add("valute", valuteJson);
		
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(putanjaDoFajlaKursnaLista)));
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			
			String valutaString = gson.toJson(val);
			
			out.println(valutaString);
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void azurirajValute() {
		LinkedList<Valuta> valute = ucitajValute();
		
		String[] nazivi = new String[valute.size()];
		
		for (int i = 0; i < nazivi.length; i++) {
			nazivi[i] = valute.get(i).getNaziv();
		}
		
		LinkedList<Valuta> valuteAzurirano = new JsonRatesAPIKomunikacija().vratiIznosKurseva(nazivi);
		
		GregorianCalendar datum = new GregorianCalendar();
		
		upisiValute(valuteAzurirano, datum);
	}

}
