/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubes_warung;

/**
 *
 * @author Nugee
 */
public abstract class Produk {
    private int id;
    private String nama;
    private int harga;
    int stok;
    
    public abstract void kurangiStok(int jum);

    public Produk(int id, String nama, int harga, int stok) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }

    public int getStok() {
        return stok;
    }

    
    
    
}
