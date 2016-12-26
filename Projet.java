import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

public class Projet {
    
    
   
    
    
    
    private static TreeSet stock;
    public static void creer_stock(){
        stock = new TreeSet();
    }
    
    public static void alimenter_stock(String nom_file) throws IOException{
        
        CSVReader reader = new CSVReader(new FileReader(nom_file), '\t');
        String [] nextLine;
        while ((nextLine = reader.readNext()) != null) {
        // nextLine[] is an array of values from the line
        	
        String titre = nextLine[0];
        String description = nextLine[1];
        String date = nextLine[2];
        String rss = nextLine[3];
        String author = nextLine[4];
        String link = nextLine[5];
        
        News n = new News(titre,description,date,rss,author,link);
        stock.add(n);
        
     }
        
     
        
        
        
    }
    
    
    
    /**
     *
     */
    public static void afficher_der_news(){
    		Iterator it;
    		int compteur = 1;
    		it = stock.iterator();
    		
    		while(it.hasNext()){
    			News n = (News)(it.next());
    			for(int i = 1; i<=10; i++ ){
    				System.out.println(n.toString());
    				compteur++;
    			}
    		}
    }
    
    public static void sauvegarder_stock(String nom_file) throws IOException{
         CSVWriter writer = new CSVWriter(new FileWriter(nom_file), '\t');
     // feed in your array (or convert your data to an array)
     Iterator it;
     it = stock.iterator();
     List<String[]> entries = new ArrayList<>();
     
    		
    		while(it.hasNext()){
                    News n = (News)(it.next());
    			String[] item = n.toString().split("\n");
                        entries.add(item);
    			}
                writer.writeAll(entries);
                writer.close();
    		}
            
        
	
     }
    
    

