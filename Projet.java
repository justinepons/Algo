
import com.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.TreeSet;

public class Projet {
    private TreeSet stock;
    public void creer_stock(){
        stock = new TreeSet();
    }
    
    public void alimenter_stock() throws IOException{
        
        CSVReader reader = new CSVReader(new FileReader("/Users/justinepons/Documents/Master 1/Algorithmique/TD/Projet/huffpost-fr/fr-blogs-1.csv"));
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
    
    public void afficher_der_news(){
    		Iterator it;
    		int compteur = 1;
    		it = stock.iterator();
    		
    		while(it.hasNext()){
    			News n = (News)(it.next());
    			if(compteur == 1){
    				System.out.println(n.toString());
    				compteur++;
    			}
    		}
    }

}