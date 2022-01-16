/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CD_InventoryImages;

/**
 *
 * @author jnoj
 */
public class stringTest {
    
    public static void main(String arg[]){
     String myST="﻿DORIVAL GEL;BAYER;CAPSULA;DOLOR MENSTRUAL;;11418399549;3;PRINCIPIO ACTIVO.Ibuprofeno        200.00MG                                                                                                                                                                                                 PRESENTACION. Caja de 36 capsula                                                                                                                                                                                                    INDICACIONES .Alteraciones musculoesqueléticas y traumáticas con dolor e inflamación. Tto. sintomático del dolor leve o moderado (dolor de origen dental, dolor posquirúrgico, dolor de cabeza, migraña). Dismenorrea primaria.                                                                                                          DOSIS .Adultos y niños mayores de 12 años, 1 ó 2 cápsulas cada 4 horas, hasta un máximo de 6 cápsulas en 24 horas;***";
     
     String []array=myST.split(";");
         for (int i = 0; i <array.length; i++) {
 
            // Print current character
            System.out.print(array[i] + " ");
        }
     System.out.println(array.toString());
    }
    
}
