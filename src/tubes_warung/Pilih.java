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
public class Pilih {
    private int id_book;
    private int id_makanan;
    private int id_minuman;
    private int jumlah;

    public Pilih(int id_book, int id_makanan, int id_minuman, int jumlah) {
        this.id_book = id_book;
        this.id_makanan = id_makanan;
        this.id_minuman = id_minuman;
        this.jumlah = jumlah;
    }

    public int getId_book() {
        return id_book;
    }

    public int getId_makanan() {
        return id_makanan;
    }

    public int getId_minuman() {
        return id_minuman;
    }

    public int getJumlah() {
        return jumlah;
    }
    
    
}
