import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindow extends Application {
    List <File> file;
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
		
                Label lbl_sauv = new Label("Entrez le fichier de sauvegarde");
		pane.add(lbl_sauv,0,1);
		TextField txt_sauv = new TextField();
		pane.add(txt_sauv,1,1);
                Button filechsv= new Button("File Chooser sv");
                filechsv.setText("Fichier");
                pane.add(filechsv,2,1);
                
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
                    pane.add(valider,1,2);
                    
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
                                Projet.creer_stock();
                                //ListIterator <File> it = file.listIterator();

                                     //while(it.hasNext()){

                                        //File fichier = it.next();

                                        Projet.alimenter_stock(file);

                                //} 
                                
                                Projet.sauvegarder_stock(txt_sauv.getText());
                                 Stage dialogStage = new Stage();
                                   

                                    VBox vbox = new VBox(new Text("Fichier Sauvegarder avec succès"));
                                  

                                    dialogStage.setScene(new Scene(vbox));
                                    dialogStage.show();
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
                
                
		
		
		Scene scene = new Scene(pane,500,500);
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
