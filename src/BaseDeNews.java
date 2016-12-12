import java.io.Serializable;
import java.util.Iterator;
import java.util.TreeSet;

public class BaseDeNews implements Serializable
{
    
    private TreeSet<News> maliste;
    
    public BaseDeNews()
    {
	maliste = new TreeSet<News>();
    }
   
    public void insertNews(News n)
    {
        maliste.add(n);
    }
    
    public void supprimerNews(int i)
    {
        int num = 1;
        Iterator iter=maliste.iterator();
        while(iter.hasNext())
        {
            News n = (News)iter.next();
            if (num == i)
            {
                maliste.remove(n);
                return;
            }
            num++;
        }
        System.out.println("Ce numéro n'existe pas");
    }
    
    public String toString()
    {
        String s = "";
        int num = 1;
        Iterator iter=maliste.iterator();
        while(iter.hasNext())
        {
            News n = (News)iter.next();
            s += "-- Actualité " + num + " --\n";
            s += n.toString();
            num++;
        }
        return s;
    }    
    
}