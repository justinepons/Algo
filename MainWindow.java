
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindow extends Application {
    List <File> file;
    Date deb;
    Date fini;
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
                
                
                
        
                
                
                Label lbl_sauv = new Label("Entrez le fichier de sauvegarde");
		pane.add(lbl_sauv,0,4);
		TextField txt_sauv = new TextField();
		pane.add(txt_sauv,1,4);
                Button filechsv= new Button("File Chooser sv");
                filechsv.setText("Fichier");
                pane.add(filechsv,2,4);
                
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
                
                Label lbl_inter = new Label("Entrez le nombre d'intervalle");
		pane.add(lbl_inter,0,3);
                TextField txt_inter = new TextField();
		pane.add(txt_inter,1,3);
                
                Button valider = new Button("Valider");
                    valider.setText("Valider");
                    pane.add(valider,1,5);
                    
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
                                
                                Projet.sauvegarder_stock(txt_sauv.getText(), Integer.parseInt(txt_inter.getText()));
                                 Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Fichier Sauvegarder avec succès"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
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