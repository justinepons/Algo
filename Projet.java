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
    
    
   
    
    
    // création du TreeSet dans lequel on stockera les news
    private static TreeSet stock;
    //initialisation du TreeSet
    public static void creer_stock(){
        stock = new TreeSet();
    }
    
    
    
    // fonction qui transforme les chaines en date
    public static Date change_date(String chaine){
        Date date_sortie = new Date();
        // format des dates dans les fichiers csv de base
        SimpleDateFormat formater = new SimpleDateFormat("EEEE, d MMM yyyy hh:mm:ss Z");
        try {
            date_sortie = formater.parse(chaine);
        } catch (ParseException ex) {
            // si on arrive pas à parser la date, la date est null
            date_sortie = null;
        }
        // on renvoie une date
        return date_sortie;
    }
    
    //fonction qui permet de calculer les intervalles si on choisit de faire des intervalles equivalentt
    public static List <Long> intervalle_taille_egale(int n){
        
        // on récupère la news la plus récente et le plus ancienne
        // le TreeSet est trié en fonction des dates
        News N_min = (News) stock.first();
        News N_max = (News) stock.last();
        
        // on récupère les dates et on les mets sous format Long
        Date Date_max = N_max.getdate();
        Date Date_min = N_min.getdate();
        
        //On calcule la différence entre la dernière et la première news
        Long diff = Date_max.getTime() - Date_min.getTime();
        
        //On calcule la taille d'un intervalle
        Long in = diff/n;
        
        // on créé un tableau dans lequel sera stocké les limites des intervalles
       List <Long> n_inter = new ArrayList<Long>();
       for(int i = 0; i<n; i++){
           //on entre les limites dans le tableau
           n_inter.add(Date_min.getTime() + i*in);
       }
        
        
        // on renvoie le tableau
        return n_inter;
        
                
    } 
    //fonction qui prend en entrée un tableau avec les limites des intervalles
    // ainsi qu'une date
    public static int intervalle(List <Long> inter, Date entree){
        
        ListIterator <Long> it = inter.listIterator();
        int i=0;
        // on parcours le tableau    
        while(it.hasNext()){
                Long inte = it.next();
                if(entree.getTime()<inte){
                    // on sort de la boucle si la date est inférieur à une limite d'un intervalle
                    break;
                }
                i++;
                
            }
        
        // on renvoit l'intervalle de la date
        return i;
    }
    
    //on vérifie que les intervalles sont bien triés
    public static boolean intervalle_trie(List <Long> inter){
        
        List <Long> inter_tmp = new ArrayList();
        
        inter_tmp.addAll(inter);
        inter_tmp.add(MainWindow.getfini());
        System.out.println(inter_tmp);
        ListIterator <Long> it = inter_tmp.listIterator();
        boolean trie = true;
        Long n1,n2;
        n1 = 0L;
        while(it.hasNext()){
            n2 = it.next();
            
            if(n1>n2){
                trie = false;
                break;
            }
            
            n1 = n2;
        }
        
        return trie;
    }
    //procédure qui prend en entrée un ensemble de fichier csv
    //ainsi qu'une date de début et de fin de l'analyse
    public static void alimenter_stock(List <File> file, Date debut, Date fin) throws IOException{
         
        
        
        
        ListIterator <File> it = file.listIterator();
            while(it.hasNext()){
        File fichier = it.next();

        //on lit chaque fichier csv
        // le séparateur est la tabulation
        CSVReader reader = new CSVReader(new FileReader(fichier.getAbsoluteFile()), '\t');
        //tableau dans lequel on entrera les valeurs de chaque ligne
        String [] nextLine ;
        //on parcours le fichier csv
        while ((nextLine = reader.readNext()) != null) {
        try{
        String titre = nextLine[0];
        String description = nextLine[1];
        Date date = change_date(nextLine[2]);
        String rss = nextLine[3];
        String author = nextLine[4];
        String link = nextLine[5];
        
        //on vérifie que la news correspondent aux bonnes dates de début et de fin
        if(date.getTime()>debut.getTime() && date.getTime()<fin.getTime() ){
        News n = new News(titre,description,date,rss,author,link);
       // on ajoute la news
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
    
    //procédure de sauvegarde du Treeset dans un fichier csv
    //la variable i permet de voir si la personne souhaite sauvergarder la valeur de l'intervalle
    public static void sauvegarder_stock(String nom_file, int i) throws IOException{
         CSVWriter writer = new CSVWriter(new FileWriter(nom_file), '\t');
     Iterator it;
     //on parcours le TreeSet
     it = stock.iterator();
     // Liste de tableau de string 
     List<String[]> entries = new ArrayList<>();
     
     //ligne des titres
     String first_row = "titre \n description \n rss \n author \n link \n date";
     if(i==1){
         //on regarde si l'utilisateur veut l'intervalle
         first_row = first_row + "\n intervalle";
     }
    		entries.add(first_row.split("\n"));
    		while(it.hasNext()){
                    News n = (News)(it.next());
                        //on récupère la news
                        // on créé un tableau item
                        //les valeurs du tableau correspondent aux champs du fichier csv pour une ligne
    			String[] item = n.toString(i).split("\n");
                        //on ajoute le tableau à la Liste entries
                        entries.add(item);
    			}
                //on écrit la Liste sous format csv
                writer.writeAll(entries);
                writer.close();
                //on réinitialise le stock
                //creer_stock();
    		}
    
    
    
}