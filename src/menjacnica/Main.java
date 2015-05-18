package menjacnica;

import java.util.GregorianCalendar;
import java.util.LinkedList;

public class Main {

	public static void main(String[] args) {
		AzuriranjeKursneListe a = new AzuriranjeKursneListe();
		
		LinkedList<Valuta> val = new LinkedList<Valuta>();
		Valuta v = new Valuta();
		v.setNaziv("EUR");
		val.add(v);
		Valuta v2 = new Valuta();
		v2.setNaziv("USD");
		val.add(v2);
		
		a.upisiValute(val, new GregorianCalendar());
		
		a.azurirajValute();

	}

}
