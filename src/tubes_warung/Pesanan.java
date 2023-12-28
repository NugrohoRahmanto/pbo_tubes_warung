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
public class Pesanan {
    private String nama;
    private int hargaTotal;

    public Pesanan(String nama, int hargaTotal) {
        this.nama = nama;
        this.hargaTotal = hargaTotal;
    }

    public String getNama() {
        return nama;
    }

    public int getHargaTotal() {
        return hargaTotal;
    }
    
}
