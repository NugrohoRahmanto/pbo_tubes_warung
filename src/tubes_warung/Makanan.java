/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubes_warung;

/**
 *
 * @author anand
 */
public class Makanan extends Produk implements Kategori{
    private String Kategori;

    public Makanan(int id, String Kategori, String nama, int harga, int stok) {
        super(id, nama, harga, stok);
        this.Kategori = Kategori;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String Kategori) {
        this.Kategori = Kategori;
    }
    
    @Override
    public void kurangiStok(int jum) {
        this.stok = this.stok-jum;
    }

    @Override
    public void tambahKategori(String kat) {
        
        this.Kategori =kat;
    }
    
    
}
