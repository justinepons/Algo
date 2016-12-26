import java.net.URL;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class News implements Comparable{
    private String title;
    private String description;
    private String date;
    private String rss;
    private String author;
    private URL link;
    
    public News (String t, String d , String da, String r, String a, String l) throws MalformedURLException {
        this.title=t;
        this.description=d;
        this.date=da;
        this.rss=r;
        this.author=a;
        try {
            this.link = new URL (l);
        } catch (MalformedURLException ex) {
            //Logger.getLogger(News.class.getName()).log(Level.SEVERE, null, ex);
            this.link = null;
        }
    }
    
    public int compareTo(Object o) {
    	  News a_comparer = (News)o;
    	  int compare = title.compareTo(a_comparer.title);
    	    return compare;
    }
    
    public void settitle(String title){
        this.title = title;
    }

    public String gettitle(){
        return title;
    }
    
    public void setdescription(String description){
        this.description = description;
    }

    public String getdescription(){
        return description;
    }
    
    public void setrss(String rss){
        this.rss = rss;
    }

    public String getrss(){
        return rss;
    }

    public void setauthor(String author){
        this.author = author;
    }

    public String getauthor(){
        return author;
    }

    public void setlink(URL link){
        this.link = link;
    }

    public URL getlink(){
        return link;
    }

    public String toString(){
        String chaine =
            "\n Titre : " + title +
            "\n Description : " + description +
            "\n Flux RSS : " + rss +
            "\n Auteur : " + author +
            "\n Source : "+ link +
            "\n Date : "+ date + "\n";
        return chaine;
    }
    
    public static void afficher() {
        System.out.println("Afficher");
        
    }
    
    public void main(){
    	
    	
    }



}

