/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tubes_warung;

/**
 *
 * @author anand
 */
public class Minuman extends Produk{
    public Minuman( String nama, int harga, int stok) {
        super(nama, harga, stok);
       
    }
    
    @Override
    public void kurangiStok(int jum) {
        this.stok = this.stok-jum;
    }
    
    
    
}
