
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
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
    List <File> file;
    Date deb;
    Date fini;
   static List <Long> intervalle;
   
   private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
    for (Node node : gridPane.getChildren()) {
        if (GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
            return node;
        }
    }
    return null;
}
    
    public static List <Long>  getintervalle(){
        return intervalle;
    }
	public Scene construitScene() {
		GridPane pane = new GridPane();
		
		Label lbl_chemin = new Label("Entrez le chemin du fichier");
		pane.add(lbl_chemin,0,0);
                Button filech= new Button("File Chooser");
                filech.setText("Fichier");
                pane.add(filech,1,0);
                
                filech.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            
                            Stage filchoo = new Stage();
                            FileChooser fileChooser = new FileChooser();
                            
                            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
                            
                            
                            
                            file = fileChooser.showOpenMultipleDialog(filchoo);
                           
                            
                        }
                });
		Label date_debut = new Label("Entrez le debut de l'analyse (format : dd mm yyyy)");
                pane.add(date_debut,0,1);
                TextField txt_debut = new TextField();
		pane.add(txt_debut,1,1);
                Label date_fin = new Label("Entrez la fin de l'analyse (format : dd mm yyyy)");
                pane.add(date_fin,0,2);
                TextField txt_fin = new TextField();
		pane.add(txt_fin,1,2);
                
                
                Label lbl_inter = new Label("Intervalles de temps égaux");
		pane.add(lbl_inter,0,3);
                TextField txt_inter = new TextField();
		pane.add(txt_inter,1,3);
                RadioButton rb1 = new RadioButton();
                pane.add(rb1,2,3);
                
                Label lbl_inter2 = new Label("Intervalles de temps définis manuellement");
		pane.add(lbl_inter2,0,4);
                TextField txt_inter2 = new TextField();
		pane.add(txt_inter2,1,4);
                RadioButton rb2 = new RadioButton();
                pane.add(rb2,2,4);
                
                final ToggleGroup group = new ToggleGroup();
                rb1.setToggleGroup(group);
                rb2.setToggleGroup(group);
                
                Label lbl_sauv = new Label("Entrez le fichier de sauvegarde");
		pane.add(lbl_sauv,0,5);
		TextField txt_sauv = new TextField();
		pane.add(txt_sauv,1,5);
                Button filechsv= new Button("File Chooser sv");
                filechsv.setText("Fichier");
                pane.add(filechsv,2,5);
                
                filechsv.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Stage filchoo = new Stage();
                            FileChooser fileChooser = new FileChooser();
                            fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV", "*.csv")
        );
                            
                            
                            
                            File file2 = fileChooser.showSaveDialog(filchoo);
                            txt_sauv.setText(file2.getAbsolutePath());
                        }
                });
                
                
                
                Button valider = new Button("Valider");
                    valider.setText("Valider");
                    pane.add(valider,1,6);
                    
                    valider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            
                            try {
                                
                                
                                    
                                    if(txt_sauv.getText().trim().isEmpty()){
                                    Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Veuillez entrer un fichier de sauvegarde"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                }else{
                                SimpleDateFormat formater = new SimpleDateFormat("dd MM yyyy");
                                        try {
                                            
            
            deb = formater.parse(txt_debut.getText());
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
                
        try {
            fini = formater.parse(txt_fin.getText());
        } catch (ParseException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
                                Projet.creer_stock();
                                //ListIterator <File> it = file.listIterator();

                                     //while(it.hasNext()){

                                        //File fichier = it.next();
                                        if(deb.getTime()>fini.getTime()){
                                             Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("La date de début est supérieur à la date de fin "));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                        }else{                                                
                                        Projet.alimenter_stock(file,deb,fini);

                                //} 
                                if(rb1.isSelected()==true){
                                 
                                intervalle = Projet.intervalle_taille_egale(Integer.parseInt(txt_inter.getText()));
                                
                                Projet.sauvegarder_stock(txt_sauv.getText());
                                 Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Fichier Sauvegarder avec succès"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                }else{
                                   if(rb2.isSelected() == true){
                                       Stage dialogStage = new Stage();
                                       int n;
                                       
                                       n = Integer.parseInt(txt_inter2.getText());
                                       TextField[] tf = new TextField[n];
                                       GridPane pane2 = new GridPane();
                                       
                                       

                                        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
                                        


                                       Label lbl11 = new Label("Debut de l'étude");
                                       pane2.add(lbl11, 0, 0);
                                       Label lbl12 = new Label(formatter.format(deb));
                                       pane2.add(lbl12, 1, 0);
                                       
                                       for(int i=1;i<n;i++){
                                           Label lbl_dyn = new Label("Fin de l'intervalle (format dd mm yyyy) " + i);
                                           TextField txt_dyn = new TextField();
                                           pane2.add(lbl_dyn,0,i);
                                           pane2.add(txt_dyn,1,i);
                                           
                                           
                                       }
                                       
                                       Label lbln1 = new Label("Fin de l'étude");
                                       pane2.add(lbln1, 0, n);
                                       Label lbln2 = new Label(formatter.format(fini));
                                       pane2.add(lbln2, 1, n);
                                       
                                       
                                       Button btn_inter2 = new Button();
                                       btn_inter2.setText("Valider");
                                       pane2.add(btn_inter2,1,n+1);
                                       
                                       btn_inter2.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                                    intervalle = new ArrayList();
                            try {
                                intervalle.add(formatter.parse(lbl12.getText()).getTime());
                            } catch (ParseException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                                    for(int j=1;j<n;j++){
                                        Node noeu = getNodeFromGridPane(pane2,1,j);
                                        if(noeu instanceof TextField){
                                            String chaine =  ((TextField)noeu).getText();
                                            try {
                                                intervalle.add(formatter.parse(chaine).getTime());
                                            } catch (ParseException ex) {
                                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }
                                    }
                            /*try {
                                intervalle.add(formatter.parse(lbln2.getText()).getTime());
                            } catch (ParseException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }*/
                            
                                
                            try {        
                                Projet.sauvegarder_stock(txt_sauv.getText());
                                
                                Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Fichier Sauvegarder avec succès"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
                                
                            } catch (IOException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            dialogStage.hide();
                            }
                                       });
                                       
                                       
                                       
                                       dialogStage.setScene(new Scene(pane2));
                                    dialogStage.show();
                                       
                                       
                                   }else{
                                       Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Veuillez choisir le type de découpage temporel"));
                                  

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