
/**
 *  @Author : Lucas Pedrosa Larangeira
 *
 * 
 *  Enrollment : 202011430
 *  Created: 22/07/22
 *  last change at : 09/08/22
 *  Name: Principal.java 
 * 
 *  
 *  Principal.java class is the main class from the physical layer
 *  simulation, it should be used
 *  to extend the Application method and use it to launch javaFX
 *  application
 * 
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import controller.MainController;
import model.Communication;
import model.PhysicalTransmittingLayer;
import model.TransmitterApplicationLayer;

public class Principal extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  /*
   * The principal class will handle the lauch of the javaFX
   * application.
   * For the interface it extends the Application abstract class
   * 
   * The Application will:
   * Construct a instance of Application class
   * Calls the Init and start methods
   * Wait for the Application to finish
   * 
   */

  @Override
  public void start(Stage primaryStage) throws Exception {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main_view.fxml"));
      Parent root = loader.load();
      Scene scene = new Scene(root);


      // primaryStage.getIcons().add(new Image("img/icon.png"));
      primaryStage.setResizable(false);
      primaryStage.setTitle("Camada Fisica");
      primaryStage.setScene(scene);
      primaryStage.sizeToScene();
      primaryStage.show();

    } catch (Exception e) {
      System.out.println("arranca por la derecha el genio del futbol mundial");
      System.out.println(e.getMessage());
    }
  }

}