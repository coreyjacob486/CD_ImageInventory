/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CD_InventoryImages;

import java.util.LinkedList;

/**
 *
 * @author jnoj
 */
public class ProductList {
    
    LinkedList <CDProduct> milist=null;
    String BRAND="";
    public ProductList(String brand){
        this.BRAND=brand;
        milist=new LinkedList<CDProduct>();
       
        
    }
    /**
     * adding a CDproduct to the list
     * @param e 
     */
    public void AddProductToList(CDProduct e){
        this.milist.add(e);
    }
    
   
}
