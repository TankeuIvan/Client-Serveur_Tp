package tp_prog_res;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import sun.security.util.Length;

public class ClientChat extends Application{  //Ebauche Chat de clients en utilisant des interfaces

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Client Chat");
		BorderPane borderPane = new BorderPane(); 	//BorderPane est un élément conteneur nord sud est ouest centre
		Scene scene = new Scene(borderPane,500,400); //Une scène est espace dans lequel on va afficher quelque chose largeur = 500 hauteur =400
		
		// Créer un bouton pour se connecter au serveur
		Label labelHost = new Label("Host :");
		TextField textFieldHost = new TextField("localHost");
		Label labelPort = new Label("Port :");
		TextField textFieldPort = new TextField("1234");
		Button buttonConnecter = new Button("Connecter");
		
		HBox hBox = new HBox(); // HBox pour organiser le éléments de manière horizontale
		hBox.getChildren().addAll(labelHost, textFieldHost, textFieldPort, buttonConnecter);
		
		borderPane.setTop(hBox);
		
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
