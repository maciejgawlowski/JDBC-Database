package model;

public class Uczen {
	private int idu;
	private String nazwisko;
	private String imie;

	public Uczen(int idu, String nazwisko, String imie) {
		this.idu = idu;
		this.nazwisko = nazwisko;
		this.imie = imie;
	}

	public int getIdu() {
		return idu;
	}

	public void setIdu(int idu) {
		this.idu = idu;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	@Override
	public String toString() {
		return "Uczen [idu=" + idu + ", nazwisko=" + nazwisko + ", imie=" + imie + "]";
	}
}
