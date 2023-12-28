/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubes_warung;

/**
 *
 * @author anand
 */
public class Diskon {
    private int id;
    private String namaKategori;
    private int diskon;

    public Diskon(int id, String namaKategori, int diskon) {
        this.id = id;
        this.namaKategori = namaKategori;
        this.diskon = diskon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getNamaKategori() {
        return namaKategori;
    }

    public void setNamaKategori(String namaKategori) {
        this.namaKategori = namaKategori;
    }

    public int getDiskon() {
        return diskon;
    }

    public void setDiskon(int diskon) {
        this.diskon = diskon;
    }
    
}
