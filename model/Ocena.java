package model;

public class Ocena {
	private int ido;
	private String wartoscOpisowa;
	private float wartoscNumeryczna;

	public Ocena(int ido, String wartoscOpisowa, float wartoscNumeryczna) {
		this.ido = ido;
		this.wartoscOpisowa = wartoscOpisowa;
		this.wartoscNumeryczna = wartoscNumeryczna;
	}

	public int getIdo() {
		return ido;
	}

	public void setIdo(int ido) {
		this.ido = ido;
	}

	public String getWartoscOpisowa() {
		return wartoscOpisowa;
	}

	public void setWartoscOpisowa(String wartoscOpisowa) {
		this.wartoscOpisowa = wartoscOpisowa;
	}

	public float getWartoscNumeryczna() {
		return wartoscNumeryczna;
	}

	public void setWartoscNumeryczna(float wartoscNumeryczna) {
		this.wartoscNumeryczna = wartoscNumeryczna;
	}

	@Override
	public String toString() {
		return "Ocena [ido=" + ido + ", wartoscOpisowa=" + wartoscOpisowa + ", wartoscNumeryczna=" + wartoscNumeryczna
				+ "]";
	}
}
