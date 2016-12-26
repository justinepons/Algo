import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainWindow extends Application {

	public Scene construitScene() {
		GridPane pane = new GridPane();
		
		Label lbl_chemin = new Label("Entrez le chemin du fichier");
		pane.add(lbl_chemin,0,0);
		TextField txt_chemin = new TextField();
		pane.add(txt_chemin,1,0);
		
                Label lbl_sauv = new Label("Entrez le fichier de sauvegarde");
		pane.add(lbl_sauv,0,1);
		TextField txt_sauv = new TextField();
		pane.add(txt_sauv,1,1);
                
                
                
                Button valider = new Button("Valider");
                    valider.setText("Valider");
                    pane.add(valider,1,2);
                    
                    valider.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            Projet.creer_stock();
                            try {
                                Projet.alimenter_stock(txt_chemin.getText());
                                Projet.sauvegarder_stock(txt_sauv.getText());
                            } catch (IOException ex) {
                                Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
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
