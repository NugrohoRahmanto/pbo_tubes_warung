/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubes_warung;

/**
 *
 * @author anand
 */
public class Minuman {
    private String namaMinuman;
    private int stokMinuman;
    private int hargaMinuman;

    public Minuman(String namaMinuman, int stokMinuman, int hargaMinuman) {
        this.namaMinuman = namaMinuman;
        this.stokMinuman = stokMinuman;
        this.hargaMinuman = hargaMinuman;
    }

    public String getNamaMinuman() {
        return namaMinuman;
    }

    public void setNamaMinuman(String namaMinuman) {
        this.namaMinuman = namaMinuman;
    }

    public int getStokMinuman() {
        return stokMinuman;
    }

    public void setStokMinuman(int stokMinuman) {
        this.stokMinuman = stokMinuman;
    }

    public int getHargaMinuman() {
        return hargaMinuman;
    }

    public void setHargaMinuman(int hargaMinuman) {
        this.hargaMinuman = hargaMinuman;
    }
    
    
}
