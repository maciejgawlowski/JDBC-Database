package model;

public class Przedmiot {
	private int idp;
	private String nazwaPrzedmiotu;
	
	public Przedmiot(int idp, String nazwaPrzedmiotu) {
		this.idp = idp;
		this.nazwaPrzedmiotu = nazwaPrzedmiotu;
	}

	public int getIdp() {
		return idp;
	}

	public void setIdp(int idp) {
		this.idp = idp;
	}

	public String getNazwaPrzedmiotu() {
		return nazwaPrzedmiotu;
	}

	public void setNazwaPrzedmiotu(String nazwaPrzedmiotu) {
		this.nazwaPrzedmiotu = nazwaPrzedmiotu;
	}

	@Override
	public String toString() {
		return "Przedmiot [idp=" + idp + ", nazwaPrzedmiotu=" + nazwaPrzedmiotu + "]";
	}
}
