package CD_InventoryImages;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.LinkedList;

    import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jnoj
 */
public class DirectoryReader {
    
    
    ArrayList<ProductList> Marcas = new ArrayList<ProductList>();
    ArrayList<ProductList> MarcasInventario = new ArrayList<ProductList>();

    ArrayList<CDProduct> Productos = new ArrayList<CDProduct>();

    
    public DirectoryReader(){
        
            this.readcsvFile();//leemos el directorio de imagenes
          ////  this.ScanFiles();//leemos las Marcas que tenemos en el archivo de entrada
        ////   this.countImages();
           this.readMarcasinvetariocsvFile();
          this.readimagecsvFile();//voy a leer el archivo de excel que tiene todos los productos
          this.removeInventoryOtherphases();
         //this.crossInformation();
          
           
    }
    
    
    public void readimagecsvFile() {
           
        BufferedReader br = null;
        try {
            String line = "";
            br = new BufferedReader(new FileReader("/Users/jnoj/Downloads/jacobstuffs/consciencia/LABORATORIOS/ProductosListS20Nov.csv"));
            int a=0;
            //Los headers del archivo son:
            // NAME, BRAND, UNIT, CATEGORY, SUBCATEGORY, SKU, SELLING PRICE, PRODUCT DESCRIPTION
            CDProduct tmp=null;
 
            boolean changeCarriage=false;
            int linecounter=0;
            while ((line = br.readLine()) != null) //returns a Boolean value
            {
                linecounter++;
                 String[] array = line.split(";",-1);
                // System.out.println(array.length+" "+line);
                 String temp_description="";
                 for (int i=0;i<array.length;i++){
                  if(array[i]!=null&&array[i]!=""){
                    
                      
                     if (i==0&&!changeCarriage){
                     
                    
                        tmp=new CDProduct(array[i]);
                        tmp.type="PRODUCT";
                      ///  System.out.println(tmp.idInt);
                        
                     }else if(i==1&&!changeCarriage){
                        tmp.setName(array[i]);
                      //  if (array[i].contains("DORIVAL GEL")){
                      //  int ar=20;
                      //  } 
                  
                     }else if(i==2&&!changeCarriage){
                         tmp.brand=array[i];
                     }else if (i==3&&!changeCarriage){
                         tmp.unit=array[i];
                     }else if(i ==4&&!changeCarriage){
                        tmp.category=array[i];

                     }else if(i ==5&&!changeCarriage){
                         if(array[i]!=""){
                             tmp.sub_category=array[i];
                            }
                     }else if(i ==6&&!changeCarriage){
                         try{
                            tmp.SKU=Long.parseLong(array[i].toString());
                         }catch(java.lang.NumberFormatException e){
                         //    System.out.println(e.getStackTrace());
                         // this means the sku is something fmpromesa.
                              tmp.SKU_s=array[i].toString();
                         }                        
                     }else if(i ==7&&!changeCarriage){
                            try {

                             tmp.price = Double.valueOf(array[i].replace(",", "."));
                         } catch (java.lang.NumberFormatException e) {
                            System.out.println(e.getStackTrace());

                             System.out.println("ID"+linecounter+" --"+tmp.name+" "+tmp.brand+" "+tmp.type+" "+tmp.SKU);
                             

                         }                  

                     }else{
                         if(array[i].equals("***") || array[i].contains("***")){
                          tmp.description=temp_description;
                          Productos.add(tmp);
                          this.addProductToInventory(tmp);
                        //  System.out.println(tmp.name+"; "+ tmp.brand+" "+tmp.description);
                          changeCarriage=false;
                           temp_description="";
                         }else{
                         temp_description+=array[i];
                         changeCarriage=true;
                         }
                     }
                 }   
                 
                }
                
            }
            
            System.out.println("Finalizo de cargar todos los productos de inventario");
        } catch (Exception ex) {
            Logger.getLogger(DirectoryReader.class.getName()).log(Level.SEVERE, null, ex);
          //  System.out.println("out.");
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(DirectoryReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
    /**
     * This method reads the directory with images
     */
    public void readcsvFile(){
        String masterPath="/Users/jnoj/Downloads/jacobstuffs/consciencia/LABORATORIOS";
        File directoryPath = new File(masterPath);
      //List of all files and directories
      String contents[] = directoryPath.list();
      System.out.println("List of files and directories in the specified directory:");
      
        for (int i = 0; i < contents.length; i++) {
            ProductList pnow=new ProductList(contents[i]);
            Marcas.add(pnow);//now we have them on the arraylist
            
           // System.out.println("Padre: "+i + ": " + contents[i]);
            File infiles[] =new File(masterPath+"/"+contents[i]).listFiles();
            
            if (infiles!=null){//validamos que sea un directorio con imagenes
                for (int x = 0; x < infiles.length; x++) {
              //  System.out.println("Hijo: " +infiles[x].getName());
                CDProduct tmp=new CDProduct(infiles[x].getName(),"IMAGE");
                tmp.brand=contents[i];
                tmp.setpathDirectory(masterPath);
                pnow.milist.add(tmp);
                }            
            }
            
        }
        
              System.out.println("Data fullfilled: directories and files");

            
    }
    
    public void readMarcasinvetariocsvFile(){
        
        BufferedReader br = null;

        
           String line = "";
        try {
            br = new BufferedReader(new FileReader("/Users/jnoj/Downloads/jacobstuffs/consciencia/LABORATORIOS/MARCASINVENTARIO.csv"));
     
      
            int a=0;
            //Los headers del archivo son:
            // NAME, BRAND, UNIT, CATEGORY, SUBCATEGORY, SKU, SELLING PRICE, PRODUCT DESCRIPTION
            CDProduct tmp=null;
 
            boolean changeCarriage=false;
            int linecounter=0;
            while ((line = br.readLine()) != null) //returns a Boolean value
            {                
                
                String[] array = line.split(";",-1);

                
            ProductList pnow=new ProductList(array[0]);
            this.MarcasInventario.add(pnow);//now we have them on the arraylist
                
            }  
            
            
        } catch (Exception ex) {
            Logger.getLogger(DirectoryReader.class.getName()).log(Level.SEVERE, null, ex);
        }
     
        System.out.println("Marcas de Inventarios cargadas");

            
    }
    
     public void addProductToInventory(CDProduct e){
        String currentBrand=e.brand;
       for(int x=0;x<this.MarcasInventario.size();x++){
       if(MarcasInventario.get(x).BRAND.equals(currentBrand)){
            this.MarcasInventario.get(x).AddProductToList(e);
           break;
       }
       }
    
    }
    

    public void ScanFiles() {

        String withImageref="";
        String withNoImageref="";
        
        String line = "";
        String splitBy = ",";
        try {
//parsing a CSV file into BufferedReader class constructor  
            BufferedReader br = new BufferedReader(new FileReader("/Users/jnoj/Downloads/jacobstuffs/consciencia/LABORATORIOS/Marcas.csv"));
           int a=0;
            while ((line = br.readLine()) != null) //returns a Boolean value  
            {
                
                ///let's find if the current one have one coincidence on the ArrayList
              int isInlist=  this.findInList(line);
              if (isInlist!=-1){
                  System.out.println("Image in "+isInlist+" "+line);
                  withImageref+=line+"\n";

              }else{
                              System.out.println("No image  "+line);
                  withNoImageref+=line+"\n";


              }
                        
                a++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
               //write outputfiles
        try {
            FileWriter myWriter = new FileWriter("withImages.csv");
            myWriter.write(withImageref);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
                
               //write outputfiles
        try {
            FileWriter myWriter2 = new FileWriter("withNoImages.csv");
            myWriter2.write(withNoImageref);
            myWriter2.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private int findInList(String line) {
        int position = -1;
        Iterator parse = Marcas.iterator();
        boolean flag = false;
        int counter = 0;

        while (parse.hasNext() && !flag) {

            if (parse.next().toString().equals(line)) {
                position = counter;
                flag = true;
            } else {
                counter++;
            }

        }
        return position;
    }

    private void crossInformation() {
//bueno vamos a hcer un for de fors hahaha

   // ArrayList<ProductList> Marcas = new ArrayList<ProductList>();
   // ArrayList<ProductList> MarcasInventario = new ArrayList<ProductList>();
    int top =this.MarcasInventario.size();
    for (int a=0;a<top;a++){
         ProductList plist=MarcasInventario.get(a);//obtenemos la lista actual dle inventario
         String marcaplist=plist.BRAND;//su nombre es mejor tenerlo a la mano
         
        // System.out.println("BRAND :"+marcaplist);
         int topplist=plist.milist.size();
            for (int b=0; b< topplist;b++){
                     CDProduct pproduct=plist.milist.get(b);//obtenemos el producto actual
                     // i think this method can be in another cycle but lets try something. 
                     
                     int topmarcaslist=this.Marcas.size();
                     for (int c=0;c<topmarcaslist;c++){
                            if(this.Marcas.get(c).BRAND.equals(marcaplist)
                                    || this.Marcas.get(c).BRAND.equals("VITAMINASN")
                                    || this.Marcas.get(c).BRAND.equals("VITAMINASVIT")
                                    || this.Marcas.get(c).BRAND.equals("VARIOS")){
                            // this means la marca esta dentro de lo que hay now iterate to find a similar product. 
                                
                            LinkedList <CDProduct>tmpproductslist=this.Marcas.get(c).milist;//get the list of images per brand
                            
                                int topcdplist=tmpproductslist.size();
                                double topScore=0;//this flag is to understand what's the best coincidence.
                                int pointeronArray=-1;
                                 for (int d=0;d<topcdplist;d++){
                                    CDProduct tmpproduct=tmpproductslist.get(d);
                                    if (pproduct.name.contains("FORTALENE")){
                                    int bb=0;
                                    }
                                   //int tmpscore=  this.compareTokenNames(pproduct,tmpproduct );
                                   //this is my own algorithm code 
                                  double tmpscore= diceCoefficientOptimized(tmpproduct.namenojpg,pproduct.name);
                                //   System.out.println("the comparisson is: "+tmpscore);
                                   
                                //   if (tmpscore>topScore && tmpscore<0.75 && tmpscore>=0.5){
                                     if (tmpscore>topScore && tmpscore>=0.75){
                                       topScore=tmpscore;
                                       pointeronArray=d;
                                   }
                                  // if (tmpproductslist.get(d).namenojpg.contains(pproduct.name)){
                                  // System.out.println("Looks like it's a coincidence, "+ pproduct.name+", and ,"+tmpproductslist.get(d).namenojpg);
                                   //}
                                 }
                                if(pointeronArray>-1){
                                //this means the score have a coincidence 
                                    pproduct.pathToFile=tmpproductslist.get(pointeronArray).pathToFile;
                                    pproduct.score=topScore;
                                    //aca es donde deberia de hacer el movimiento de las imagenes. 
                              //      System.out.println(tmpproductslist.get(pointeronArray).setnewpathDirectory(pproduct.idInt));
                                    
                                try {
                                   // this.copyFile(new File(pproduct.pathToFile), new File(tmpproductslist.get(pointeronArray).setnewpathDirectory(pproduct.idInt)));
                               
                                
                                     Path sourceDirectory = Paths.get(pproduct.pathToFile);
                                     Path targetDirectory = Paths.get(tmpproductslist.get(pointeronArray).getnewpathDirectory(pproduct.idInt));

                                      //copy source to target using Files Class
                                     Files.copy(sourceDirectory, targetDirectory);
                                        try {
                                            Thread.sleep(200); // Wait for 1 seconds
                                        } catch (InterruptedException ex) {
                                            Logger.getLogger(DirectoryReader.class.getName()).log(Level.SEVERE, null, ex);
                                        }

                                     String oldpath=pproduct.pathToFile;
                                     File deletedfile=new File(oldpath);
                                     pproduct.pathToFile=tmpproductslist.get(pointeronArray).getnewpathDirectory(pproduct.idInt);
                                    tmpproductslist.remove(pointeronArray);
                                  //  deletedfile.delete();//borro el archivo
                                     
                                     System.out.println(pproduct.tocsvOut());
                                     
                                     
                                } catch (IOException ex) {
                                    Logger.getLogger(DirectoryReader.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                }
                            
                            }
                         
                     }
                 
            }
    
    }
    
    
    }//fin del metodo

    
    public void countImages(){
        
       int amountofmarcas= Marcas.size();
       int counterofProducts=0;
       for (int a = 0; a < amountofmarcas; a++) {
            ProductList pnow=Marcas.get(a);//get the current brand
                for (int b = 0;b < pnow.milist.size(); b++) {
             //   System.out.println("Hijo: " +infiles[x].getName());
              //  CDProduct tmp=pnow.milist.get(b);
                counterofProducts++;
                }            
            
        }
       System.out.println("La cantidad de productos de imagenes son: "+counterofProducts);
    
    }// fin del metodo

    /**
     * 
     * @param d1 es el producto del inventario
     * @param d2 es el producto proveniente de las imagnees
     */
    public int compareTokenNames(CDProduct d1, CDProduct d2){
            int Scored1=0;
      if(d2.tokenNames!=null){
        for (int e=0;e<d1.tokenNames.length; e++){
            for(int f=0;f<d2.tokenNames.length;f++){
                if(d1.tokenNames[e].contains(d2.tokenNames[f]) &&d1.tokenNames[e]!=""&& d2.tokenNames[f]!=""){
                       if(d1.tokenNames[e].equals(d2.tokenNames[f])){
                            Scored1+=2;
                       }else{
                           Scored1+=1;
                       }
                }
            }//end of first for 
            
        }//end of second for
     }//end if 
            return Scored1;
    }
    
    

//Note that this implementation is case-sensitive!
public static double diceCoefficient(String s1, String s2)
{
	Set<String> nx = new HashSet<String>();
	Set<String> ny = new HashSet<String>();

	for (int i=0; i < s1.length()-1; i++) {
		char x1 = s1.charAt(i);
		char x2 = s1.charAt(i+1);
		String tmp = "" + x1 + x2;
		nx.add(tmp);
	}
	for (int j=0; j < s2.length()-1; j++) {
		char y1 = s2.charAt(j);
		char y2 = s2.charAt(j+1);
		String tmp = "" + y1 + y2;
		ny.add(tmp);
	}

	Set<String> intersection = new HashSet<String>(nx);
	intersection.retainAll(ny);
	double totcombigrams = intersection.size();

	return (2*totcombigrams) / (nx.size()+ny.size());
}//end of the method

/**
 * Here's an optimized version of the dice coefficient calculation. It takes
 * advantage of the fact that a bigram of 2 chars can be stored in 1 int, and
 * applies a matching algorithm of O(n*log(n)) instead of O(n*n).
 * 
 * <p>Note that, at the time of writing, this implementation differs from the
 * other implementations on this page. Where the other algorithms incorrectly
 * store the generated bigrams in a set (discarding duplicates), this
 * implementation actually treats multiple occurrences of a bigram as unique.
 * The correctness of this behavior is most easily seen when getting the
 * similarity between "GG" and "GGGGGGGG", which should obviously not be 1.
 * 
 * @param s The first string
 * @param t The second String
 * @return The dice coefficient between the two input strings. Returns 0 if one
 *         or both of the strings are {@code null}. Also returns 0 if one or both
 *         of the strings contain less than 2 characters and are not equal.
 * @author Jelle Fresen
 */
public static double diceCoefficientOptimized(String s, String t)
{
	// Verifying the input:
	if (s == null || t == null)
		return 0;
	// Quick check to catch identical objects:
	if (s == t)
		return 1;
        // avoid exception for single character searches
        if (s.length() < 2 || t.length() < 2)
            return 0;

	// Create the bigrams for string s:
	final int n = s.length()-1;
	final int[] sPairs = new int[n];
	for (int i = 0; i <= n; i++)
		if (i == 0)
			sPairs[i] = s.charAt(i) << 16;
		else if (i == n)
			sPairs[i-1] |= s.charAt(i);
		else
			sPairs[i] = (sPairs[i-1] |= s.charAt(i)) << 16;

	// Create the bigrams for string t:
	final int m = t.length()-1;
	final int[] tPairs = new int[m];
	for (int i = 0; i <= m; i++)
		if (i == 0)
			tPairs[i] = t.charAt(i) << 16;
		else if (i == m)
			tPairs[i-1] |= t.charAt(i);
		else
			tPairs[i] = (tPairs[i-1] |= t.charAt(i)) << 16;

	// Sort the bigram lists:
	Arrays.sort(sPairs);
	Arrays.sort(tPairs);

	// Count the matches:
	int matches = 0, i = 0, j = 0;
	while (i < n && j < m)
	{
		if (sPairs[i] == tPairs[j])
		{
			matches += 2;
			i++;
			j++;
		}
		else if (sPairs[i] < tPairs[j])
			i++;
		else
			j++;
	}
	return (double)matches/(n+m);
}//end of the method


public void copyFile(File src, File dest) throws IOException {
 InputStream is = null; 
 OutputStream os = null;
  try { 
  is = new FileInputStream(src);
   os = new FileOutputStream(dest); 
   // buffer size 1K
    byte[] buf = new byte[1024]; 
    int bytesRead; 
    while ((bytesRead = is.read(buf)) > 0) { 
    os.write(buf, 0, bytesRead); 
    }
  } finally { 
  is.close(); 
  os.close(); 
  } 
}

    private void printToCSV() {
    int topl=    this.MarcasInventario.size();//recorremos las marcas
          for (int a=0;a<topl;a++){
                 ProductList plist=MarcasInventario.get(a);//obtenemos la lista actual dle inventario
                 
                 for (int b=0;b<plist.milist.size();b++){
                     CDProduct tmp=plist.milist.get(b);
                           System.out.println(tmp.tocsvOut());
                 }
          }
    
    }

    /**
     * This method works for remove the ids from the list from the previous products already fixed
     **/
    private void removeInventoryOtherphases() {
        
        
       for(int x=0;x<this.MarcasInventario.size();x++){
          ProductList tmp= MarcasInventario.get(x);
           for (int y =0; y< tmp.milist.size(); y++){
               
              CDProduct currentProd= tmp.milist.get(y);
              
              for (int z=0; z< CDProduct.fase12Ids.length; z++){
                  
                 
               //  System.out.println("the cur product is "+currentProd.name+" ..."+currentProd.idInt+"...");
                  if (CDProduct.fase12Ids[z]==Integer.parseInt(currentProd.idInt.trim())) 
                  {//found it on the first fases 
                    //  System.out.println("removeit from the list "+currentProd.idInt+" "+currentProd.name);//
                      tmp.milist.remove(y);//delete the one is already in place
                      break;
                  }
                  
              }///end of for 
           }//end for 
       }// end for external
  }// end of the method 

    
    
}

