
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindow extends Application {
    //Liste des fichiers choisis
    List <File> file;
    //Date de début de l'étude
    Date deb;
    //Date de fin
    static Date fini;
    //Tableau des intervalles
   static List <Long> intervalle;
   //fonction qui prend en entrée un GridPane, et les indices de colonne et de ligne
   private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
    for (Node node : gridPane.getChildren()) {
        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
            //renvoie le noeu correspondant aux indices entrés en paramètre
            return node;
        }
    }
    return null; //renvoie null si il ne trouve pas la ligne et la colonne
}
    //getter pour le tableau d'intervalle
    public static List <Long>  getintervalle(){
        return intervalle;
    }
    
    public static Long getfini(){
        return fini.getTime();
    }
	public Scene construitScene() {
		GridPane pane = new GridPane();
		
		Label lbl_chemin = new Label("Entrez le chemin du fichier");
		pane.add(lbl_chemin,0,0);
                //ce bouton permet d'ouvrir l'explorateur de fichier
                Button filech= new Button("File Chooser");
                filech.setText("Fichier");
                pane.add(filech,1,0);
                
                filech.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            // ouverture de l'explorateur de fichier
                            Stage filchoo = new Stage();
                            FileChooser fileChooser = new FileChooser();
                            //filtre les fichiers pour avoir seulement les fichiers sous format csv
                            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
                            
                            
                            
                            file = fileChooser.showOpenMultipleDialog(filchoo);
                           
                            
                        }
                });
                
		Label date_debut = new Label("Entrez le debut de l'analyse (format : dd mm yyyy)");
                pane.add(date_debut,0,1);
                //permet d'entrer la date de début
                TextField txt_debut = new TextField();
		pane.add(txt_debut,1,1);
                Label date_fin = new Label("Entrez la fin de l'analyse (format : dd mm yyyy)");
                pane.add(date_fin,0,2);
                //permet d'entrer la date de fin
                TextField txt_fin = new TextField();
		pane.add(txt_fin,1,2);
                
                
                Label lbl_inter = new Label("Intervalles de temps égaux");
		pane.add(lbl_inter,0,3);
                //On entre le nombre d'intervalle que l'on souhaite
                TextField txt_inter = new TextField();
		pane.add(txt_inter,1,3);
                //si ce bouton radio est coché, celà veut dire que l'utilisateur souhaite
                // avoir des intervalles temporels de tailles équivalentes
                RadioButton rb1 = new RadioButton();
                pane.add(rb1,2,3);
                
                Label lbl_inter2 = new Label("Intervalles de temps définis manuellement");
		pane.add(lbl_inter2,0,4);
                //On entre le nombre d'intervalle que l'on souhaite
                TextField txt_inter2 = new TextField();
		pane.add(txt_inter2,1,4);
                //si ce bouton radio est coché, celà veut dire que l'utilisateur souhaite
                // entrer lui-même les dates charnières des intervalles
                RadioButton rb2 = new RadioButton();
                pane.add(rb2,2,4);
                // permet de ne pas pouvoir coché les 2 solution à la fois
                final ToggleGroup group = new ToggleGroup();
                rb1.setToggleGroup(group);
                rb2.setToggleGroup(group);
                
                Label lbl_sauv = new Label("Entrez le fichier de sauvegarde");
		pane.add(lbl_sauv,0,5);
                //on écrira le chemin du fichier dans lequel on veut sauvegarder le fichier
		TextField txt_sauv = new TextField();
		pane.add(txt_sauv,1,5);
                //bouton qui permet d'ouvrir l'explorateur de fichier
                Button filechsv= new Button("File Chooser sv");
                filechsv.setText("Fichier");
                pane.add(filechsv,2,5);
                
                filechsv.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            //explorateur de fichier
                            Stage filchoo = new Stage();
                            FileChooser fileChooser = new FileChooser();
                            //filtre les fichiers pour avoir seulement les fichiers sous format csv
                            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
                            
                            
                            
                            File file2 = fileChooser.showSaveDialog(filchoo);
                            //inscrit le chemin du fichier dans la zone de texte
                            txt_sauv.setText(file2.getAbsolutePath());
                        }
                });
                
                
                //bouton qui permet de lancer le traitement des fichiers
                Button valider = new Button("Valider");
                    valider.setText("Valider");
                    pane.add(valider,1,6);
                    
                    valider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            
                            try {
                                
                                
                                    //on voit si l'utilisateur a entré un fichier de sauvegarde
                                    if(txt_sauv.getText().trim().isEmpty()){
                                    Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Veuillez entrer un fichier de sauvegarde"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                }else{
                                        //on va parser les dates de début et de fin
                                SimpleDateFormat formater = new SimpleDateFormat("dd MM yyyy");
                                        try {
                                            
            //on donne la valeur de la date de début à la variable deb
            deb = formater.parse(txt_debut.getText());
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            //on donne la valeur de la date de fin à la variable fini
            fini = formater.parse(txt_fin.getText());
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
                                   //On créé le stock de news
                                Projet.creer_stock();
                                //on vérifie la cohérence des dates entrées par l'utilisateur
                                        if(deb.getTime()>fini.getTime()){
                                             Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("La date de début est supérieur à la date de fin "));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                        }else{       
                                            //on alimenter le stock de news
                                        Projet.alimenter_stock(file,deb,fini);

                                //si l'utilisateur a choisi des intervalles de tailles égales 
                                if(rb1.isSelected()==true){
                                 //on récupère les intervalles
                                intervalle = Projet.intervalle_taille_egale(Integer.parseInt(txt_inter.getText()));
                                // on sauvegarde le stock
                                Projet.sauvegarder_stock(txt_sauv.getText(),1);
                                 Stage dialogStage = new Stage();
                                   
                                    //on notifie dl'utilisateur que la sauvegarde s'est bien passé
                                    VBox vbox = new VBox(new Text("Fichier Sauvegarder avec succès"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                }else{
                                    //si l'utilisateur choisi manuellement ses intervalles
                                   if(rb2.isSelected() == true){
                                       //on ouvre une nouvelle fenetre
                                       Stage dialogStage = new Stage();
                                       //n correspond au nombre d'intervalle
                                       int n;
                                       
                                       n = Integer.parseInt(txt_inter2.getText());
                                       //on créé une gridPane
                                       GridPane pane2 = new GridPane();
                                       
                                       

                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
                                        

                                       //la première ligne nous montre la date de début de l'étude
                                       Label lbl11 = new Label("Debut de l'étude");
                                       pane2.add(lbl11, 0, 0);
                                       Label lbl12 = new Label(formatter.format(deb));
                                       pane2.add(lbl12, 1, 0);
                                       
                                       for(int i=1;i<n;i++){
                                           //on créé autant de TextField que nécessaire pour pouvoir entrer les limites
                                           // soit n-1
                                           Label lbl_dyn = new Label("Fin de l'intervalle (format dd mm yyyy) " + i);
                                           TextField txt_dyn = new TextField();
                                           pane2.add(lbl_dyn,0,i);
                                           pane2.add(txt_dyn,1,i);
                                           
                                           
                                       }
                                       //on montre la date de fin de l'étude
                                       Label lbln1 = new Label("Fin de l'étude");
                                       pane2.add(lbln1, 0, n);
                                       Label lbln2 = new Label(formatter.format(fini));
                                       pane2.add(lbln2, 1, n);
                                       
                                       //bouton qui permet de valider les intervalles entrer
                                       Button btn_inter2 = new Button();
                                       btn_inter2.setText("Valider");
                                       pane2.add(btn_inter2,1,n+1);
                                       
                                       btn_inter2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                                    intervalle = new ArrayList();
                            try {
                                //on ajoute la valeur du premier intervalle
                                intervalle.add(formatter.parse(lbl12.getText()).getTime());
                            } catch (ParseException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                    //on parcours tous les TextField créés
                                    for(int j=1;j<n;j++){
                                        Node noeu = getNodeFromGridPane(pane2,1,j);
                                        if(noeu instanceof TextField){
                                            // on récupère les valeurs entrée
                                            String chaine =  ((TextField)noeu).getText();
                                            try {
                                                //on récupère la valeur de l'intervalle
                                                intervalle.add(formatter.parse(chaine).getTime());
                                            } catch (ParseException ex) {
                                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }
                            //on vérifie la cohérences des intervalles
                            if(Projet.intervalle_trie(intervalle) == true){
                                
                            try {   
                                //on sauvegarde le stock
                                Projet.sauvegarder_stock(txt_sauv.getText(),1);
                                
                                Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Fichier Sauvegarder avec succès"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                
                            } catch (IOException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            dialogStage.hide();
                            }else{
                                Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Intervalles incohérents"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                            }
                        }
                                       });
                                       
                                       
                                       
                                       dialogStage.setScene(new Scene(pane2));
                                    dialogStage.show();
                                       
                                       
                                   }else{ //on regarde si l'utilisateur à choisi un découpage temporel
                                       // si non
                                       //on sauvegarde sans intervalle
                                       Projet.sauvegarder_stock(txt_sauv.getText(),0);
                                       
                                       Stage dialogStage = new Stage();
                                   
                                    //on notifie dl'utilisateur que la sauvegarde s'est bien passé
                                    VBox vbox = new VBox(new Text("Fichier Sauvegarder avec succès"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                   }
                                }
                                }
                                    }
                                
                            } catch (IOException ex) {
                                 Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Le fichier à traiter n'existe pas "));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                            }
                            }   
                        });
                    
                
                
		pane.setVgap(5);
		pane.setHgap(15);
                
                
		
		
		Scene scene = new Scene(pane,600,300);
		return scene;
	} 
	
	@Override
	public void start(Stage primaryStage) throws Exception {
            
                 
		 primaryStage.setTitle("News");
		 primaryStage.setScene(construitScene());
		 primaryStage.sizeToScene();
		 primaryStage.show();
	}
	
	public static void main(String[] args)
	 {
	 launch(args);
	 } 

}