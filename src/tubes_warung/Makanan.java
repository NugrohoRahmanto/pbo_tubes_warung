/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubes_warung;

/**
 *
 * @author anand
 */
public class Makanan extends Kategori{
    private String namaMakanan;
    private int stokMakanan;
    private int hargaMakanan;
    
    public Makanan(String kategori, String makanan, int stok, int harga){
        super(kategori);
        this.namaMakanan = makanan;
        this.stokMakanan = stok;
        this.hargaMakanan = harga;
    }

    public String getNama() {
        return namaMakanan;
    }

    public void setNama(String nama) {
        this.namaMakanan = nama;
    }

    public int getStok() {
        return stokMakanan;
    }

    public void setStok(int stok) {
        this.stokMakanan = stok;
    }

    public int getHarga() {
        return hargaMakanan;
    }

    public void setHarga(int harga) {
        this.hargaMakanan = harga;
    }
    
    
}
