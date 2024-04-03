package main.model;

public class Menu {
	private String code;
	private String nama;
	private int harga;
	private int stok;
	public Menu(String code, String nama, int harga, int stok) {
		this.code = code;
		this.nama = nama;
		this.harga = harga;
		this.stok = stok;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public int getHarga() {
		return harga;
	}
	public void setHarga(int harga) {
		this.harga = harga;
	}
	public int getStok() {
		return stok;
	}
	public void setStok(int stok) {
		this.stok = stok;
	}
	
	
}
