import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeSet;
public class Projet {
    
    
   
    
    
    
    private static TreeSet stock;
    public static void creer_stock(){
        stock = new TreeSet();
    }
    
    public static Date change_date(String chaine){
        Date date_sortie = new Date();
        
        SimpleDateFormat formater = new SimpleDateFormat("EEEE, d MMM yyyy hh:mm:ss Z");
        try {
            date_sortie = formater.parse(chaine);
        } catch (ParseException ex) {
            date_sortie = null;
        }
        return date_sortie;
    }
    
    public static void alimenter_stock(List <File> file) throws IOException{
         ListIterator <File> it = file.listIterator();
            while(it.hasNext()){
        File fichier = it.next();

                                
        CSVReader reader = new CSVReader(new FileReader(fichier.getAbsoluteFile()), '\t');
        String [] nextLine ;
        while ((nextLine = reader.readNext()) != null) {
      
        String titre = nextLine[0];
        String description = nextLine[1];
        Date date = change_date(nextLine[2]);
        String rss = nextLine[3];
        String author = nextLine[4];
        String link = nextLine[5];
        
        News n = new News(titre,description,date,rss,author,link);
       
        stock.add(n);
        }
        
     }
        
     
        
        
        
    }
    
    
    
    /**
     *
     */
    
    
    public static void sauvegarder_stock(String nom_file) throws IOException{
         CSVWriter writer = new CSVWriter(new FileWriter(nom_file,true), '\t');
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