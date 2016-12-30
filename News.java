import java.net.URL;
import java.net.MalformedURLException;
import java.util.Date;



public class News implements Comparable{
   //champs d'un news
    private String title;
    private String description;
    private Date date;
    private String rss;
    private String author;
    private URL link;
    
    public News (String t, String d , Date da, String r, String a, String l) throws MalformedURLException {
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
    //fonction qui permet d'enlever la ponctuation et mettre en minuscules
    // ceci permet d'enlever les doublons
    public String low_rm_punct(String stg){
        
        stg = stg.replaceAll("\\p{P}", " ").toLowerCase();
        
        return stg;
    }
    //fonction de tri du TreeSet
    public int compareTo(Object o) {
    	  News a_comparer = (News)o;
    	  int compare;
          //on trie par date
          if(date == null || a_comparer.date == null){
                  compare = 1;
              } else{
             compare = date.compareTo(a_comparer.date);
          }
          
          if(compare == 0){
          //on trie ensuite par titre, en utilisant la fonciton low_rm_punct
          compare = low_rm_punct(title).compareTo(low_rm_punct(a_comparer.title));
}
    	  return compare;
    }
    //getters et setters
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
    
    public void setdate(Date date){
        this.date = date;
    }

    public Date getdate(){
        return date;
    }
    //foncton pour mettre une news sous format String
    public  String toString(int i){
        String chaine;
        chaine =  title +
                "\n " + description +
                "\n " + rss +
                "\n " + author +
                "\n "+ link +
                "\n "+ date;
                //si i est différent de 1, on n'ajoute pas d'intervalle
                if(i==1){
                //on calcule l'intervalle dans lequel se retrouve la news
                //on récupère le tableau d'intervalle que l'utilisateur à définit dans l'interface graphique
                chaine = chaine + "\n " + Projet.intervalle(MainWindow.getintervalle(), date);
                }
        return chaine;
    }
    
    



}