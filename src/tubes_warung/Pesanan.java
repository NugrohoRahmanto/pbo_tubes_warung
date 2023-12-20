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
    private int id;
    private String nama;
    private int hargaTotal;

    public Pesanan(int id, String nama, int hargaTotal) {
        this.id = id;
        this.nama = nama;
        this.hargaTotal = hargaTotal;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public int getHargaTotal() {
        return hargaTotal;
    }
    
}
