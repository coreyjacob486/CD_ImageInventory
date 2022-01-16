/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package CD_InventoryImages;

/**
 *
 * @author jnoj
 */
public class CDProduct {
    String  name="";
    String  brand="";
    String unit="";
    String category="";
    String sub_category="";
    long SKU=0;
    String SKU_s="";
    double price=0.0;
    String description="";
    String type="";
    String idInt="";
            
    String namenojpg="";
    String tokenNames[];
    String pathToFile="";
    String pathtoCSV="";
    double score=0.0;
    String extensionimage="";
    
    
    public static int fase12Ids []={74,1439,575,199,202,514,1476,435,439,469,96,105,962,1391,679,1109,1011,826,828,1873,1879,1882,1886,39,46,52,63,64,68,69,70,83,1387,556,576,1833,1834,1835,1836,1839,1840,1841,1842,1843,1847,1850,1852,1855,1861,1862,1863,1583,1682,1790,1320,808,201,217,223,224,512,1473,1475,1479,1675,1519,1524,1530,431,462,495,84,92,95,106,109,654,655,661,662,663,664,668,669,670,671,672,673,674,959,963,901,683,685,694,696,1107,794,796,805,806,869,386,704,714,715,721,740,742,743,744,768,772,776,778,783,11,1197,1009,824,1793,1874,1895,1903,43,44,45,49,55,58,59,65,76,1325,545,552,559,568,569,583,1844,1848,1849,1864,1941,1536,1566,1579,1581,1582,1584,1596,1601,1608,1856,1888,1318,1319,196,197,206,207,208,216,218,219,385,508,511,521,1472,1474,1480,2,226,227,1287,1288,1289,1290,1513,1516,1517,1520,1525,1527,1531,132,416,426,427,428,429,443,445,449,451,452,457,458,459,461,463,473,483,485,487,490,505,1990,1991,1992,2011,602,603,606,607,1664,1324,928,609,613,614,27,85,87,88,89,90,91,854,855,1910,393,660,667,953,954,956,957,960,964,1164,1169,910,112,1241,680,681,682,684,686,689,691,693,695,698,1070,1081,1112,1853,242,793,799,800,804,1639,1032,1036,867,872,700,702,709,710,718,719,720,724,730,733,734,738,739,745,746,756,759,760,771,777,779,780,781,782,787,881,884,886,887,888,117,120,255,256,14,1192,1194,1195,1200,1201,1739,1969,1540,1550,1551,1006,1007,1008,1010,1013,1014,1018,1021,1023,1027,1044,1046};
    
    public CDProduct(String name,
            String brand,
            String unit,
            String category,
            String sub_category,
            long SKU,
            double price,
            String description,
            String Type){
        this.name=name;
        this.brand=brand;
        this.unit=unit;
        this.category=category;
        this.sub_category=sub_category;
        this.SKU=SKU;
        this.price=price;
        this.description=description;
         this.type=Type;
        this.tokenNames=name.split(" ");
    }
    
    public CDProduct(String name, String type){
        this.name = name;
        this.type = type;
        int mo=(name.indexOf(".jpg")+name.indexOf(".png")+name.indexOf(".jpeg")+name.indexOf(".webp")+3);
        if(mo>0)
        {
             this.namenojpg=name.substring(0,mo+1);
             this.namenojpg=this.namenojpg.toUpperCase();
             this.tokenNames=this.namenojpg.split(" ");
           
        }
          if(name.contains(".jpg")){
             this.extensionimage=".jpg";
             }else if(name.contains(".png")){
                              this.extensionimage=".png";

             
             }else if(name.contains(".jpeg")){
                              this.extensionimage=".jpeg";

             }else if(name.contains(".webp")){
                                               this.extensionimage=".webp";

             }
    }
    public CDProduct(String id){
        
        this.idInt=id.replaceAll("\\uFEFF", "");//fix weird 
        this.type = type;
        
    }
    
    public String tocsvOut(){
    //return this.name+", "+this.brand+", "+this.unit+", "+this.category+", "+this.sub_category+", "+this.SKU+", "+this.price+" ,"+this.pathToFile+" ,"+this.score;
    return this.idInt+"; "+
            this.name+"; "+
            this.brand+"; "+
            this.unit+"; "+
            this.category+"; "+
            this.sub_category+"; "+
            this.getSKU()+"; "+
            this.price+"; "+
            this.pathToFile+"; "+
            this.score;
    }
    
    /**
     * each product name has it's own name convention
     * @param n 
     */  
    public void setName(String n){
            this.name=n;
            this.tokenNames=this.name.split(" ");

    }
    
    
    public void setpathDirectory(String dir){
        this.pathToFile=dir+"/"+this.brand+"/"+this.name;
    }
    
    
    public String getnewpathDirectory(String id){
        String dir="/Users/jnoj/Downloads/jacobstuffs/consciencia/LABSUPLOAD";
        return dir+"/"+this.brand+"/IMG_FPROM"+id+this.extensionimage;
    }
    /*
    
    */
    public String getSKU(){
        if(this.SKU_s!=""){
            return this.SKU_s;
        }else{
           return String.valueOf(this.SKU);
        }
    }
    
   
    
}
