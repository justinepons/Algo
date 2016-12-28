import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.sun.istack.internal.Nullable;
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
    
    public static Long intervalle(Date date_entree,int n){
        
        
        News N_min = (News) stock.first();
        News N_max = (News) stock.last();
        Date Date_max = N_max.getdate();
        Date Date_min = N_min.getdate();
        
        
        Long diff = Date_max.getTime() - Date_min.getTime();
        Long in = diff/n;
        
       Long n_inter = (date_entree.getTime() - Date_min.getTime()) / in;
        
        
        
        return n_inter+1;
        
                
    } 
    
    public static void alimenter_stock(List <File> file, Date debut, Date fin) throws IOException{
         
        
        
        
        ListIterator <File> it = file.listIterator();
            while(it.hasNext()){
        File fichier = it.next();

                                
        CSVReader reader = new CSVReader(new FileReader(fichier.getAbsoluteFile()), '\t');
        String [] nextLine ;
        while ((nextLine = reader.readNext()) != null) {
        try{
        String titre = nextLine[0];
        String description = nextLine[1];
        Date date = change_date(nextLine[2]);
        String rss = nextLine[3];
        String author = nextLine[4];
        String link = nextLine[5];
        if(date.getTime()>debut.getTime() && date.getTime()<fin.getTime() ){
        News n = new News(titre,description,date,rss,author,link);
       
        stock.add(n);
        }
        
     } catch(NullPointerException ex){
            
        }
     }
        
            }
        
        
        
    }
    
    
    
    /**
     *
     */
    
    /**
     *
     * 
     */
    public static void sauvegarder_stock(String nom_file, int inter) throws IOException{
         CSVWriter writer = new CSVWriter(new FileWriter(nom_file,true), '\t');
     Iterator it;
     it = stock.iterator();
     List<String[]> entries = new ArrayList<>();
     
    		
    		while(it.hasNext()){
                    News n = (News)(it.next());
    			String[] item = n.toString(inter).split("\n");
                        entries.add(item);
    			}
                writer.writeAll(entries);
                writer.close();
    		}
    
    
    
}