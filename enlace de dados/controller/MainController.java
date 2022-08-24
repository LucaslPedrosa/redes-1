/**
 *  @Author : Lucas Pedrosa Larangeira
 *
 * 
 *  Enrollment : 202011430
 *  Created: 22/07/22
 *  last change at : 09/08/22
 *  Name: MainController.java 
 * 
 *  
 *  MainController is the controller of the main_view.fxml
 *  
 * 
 *
 */

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import model.Enlacing;
import model.TransmitterApplicationLayer;

public class MainController implements Initializable {

  @FXML
  private Button SendButton;

  @FXML
  private TextArea ASCIIToNumsTextField;

  @FXML
  private TextArea BitsTextField;

  @FXML
  private TextArea BitsCodedTextFied;

  @FXML
  private TextArea NumsToAsciiTextField;

  @FXML
  private TextArea BitsDecodedTextField;

  @FXML
  private TextArea BitsReceivedTextField;

  @FXML
  private TextArea sendMsgTextField;

  @FXML
  private TextArea receiveMsgTextField;

  @FXML
  private ComboBox<String> enlaceTypeSelectionPanel;

  @FXML
  private ImageView lowimg0;

  @FXML
  private ImageView lowimg2;

  @FXML
  private ImageView lowimg1;

  @FXML
  private ImageView lowimg3;

  @FXML
  private ImageView lowimg4;

  @FXML
  private ImageView lowimg5;

  @FXML
  private ImageView lowimg6;

  @FXML
  private ImageView lowimg7;

  @FXML
  private ImageView lowimg8;

  @FXML
  private ImageView lowimg9;

  @FXML
  private ImageView lowimg10;

  @FXML
  private ImageView lowimg11;

  @FXML
  private ImageView midimg1;

  @FXML
  private ImageView midimg2;

  @FXML
  private ImageView midimg3;

  @FXML
  private ImageView midimg4;

  @FXML
  private ImageView midimg5;

  @FXML
  private ImageView midimg6;

  @FXML
  private ImageView midimg7;

  @FXML
  private ImageView midimg8;

  @FXML
  private ImageView midimg9;

  @FXML
  private ImageView midimg10;

  @FXML
  private ImageView midimg11;

  @FXML
  private ImageView midimg12;

  @FXML
  private ImageView highimg1;

  @FXML
  private ImageView highimg2;

  @FXML
  private ImageView highimg3;

  @FXML
  private ImageView highimg4;

  @FXML
  private ImageView highimg5;

  @FXML
  private ImageView highimg6;

  @FXML
  private ImageView highimg7;

  @FXML
  private ImageView highimg8;

  @FXML
  private ImageView highimg9;

  @FXML
  private ImageView highimg10;

  @FXML
  private ImageView highimg11;

  @FXML
  private ImageView highimg12;

  @FXML
  private Slider speedSlider;

  @FXML
  private ComboBox<String> codeTypeSelectionPanel;

  private ImageView lowImgs[];
  private ImageView highImgs[];
  private ImageView midImgs[];

  // types of encoding
  private ObservableList<String> codeTypes = FXCollections.observableArrayList(
      "Codificacao Binaria",
      "Codificacao Manchester",
      "Codificacao Manchester diferencial");

  // types of enlacing
  private ObservableList<String> enlacingTypes = FXCollections.observableArrayList(
      "Framing",
      "Character stufing",
      "Bit stuffing",
      "Physical layer coding violations");

  private int lastSignal = 0; // the lastSignal sent by physical layer, 0 = low, 1 = high
  private int miliseconds = 600; // amount of miliseconds the thread will sleep

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    // These imageviews are used to create the signals, its easier to put everything
    // on an array
    ImageView l[] = { lowimg0, lowimg1, lowimg2, lowimg3, lowimg4, lowimg5,
        lowimg6, lowimg7, lowimg8, lowimg9,
        lowimg10, lowimg11 };
    lowImgs = l;

    // These imageviews are used to create the signals, its easier to put everything
    // on an array
    ImageView h[] = { highimg1, highimg2, highimg3, highimg4, highimg5, highimg6,
        highimg7, highimg8, highimg9,
        highimg10, highimg11, highimg12 };
    highImgs = h;

    // These imageviews are used to create the signals, its easier to put everything
    // on an array
    ImageView m[] = { midimg1, midimg2, midimg3, midimg4, midimg5, midimg6,
        midimg7, midimg8, midimg9, midimg10,
        midimg11, midimg12 };
    midImgs = m;

    // set the codification types on selection panel
    codeTypeSelectionPanel.setItems(codeTypes);
    enlaceTypeSelectionPanel.setItems(enlacingTypes);
    // select the "codificacao binaria" option
    codeTypeSelectionPanel.setValue("Codificacao Binaria");
    enlaceTypeSelectionPanel.setValue("Framing");

    // everything the low signals must be invisible
    for (int i = 0; i < 12; i++) {
      midImgs[i].setVisible(false);
      highImgs[i].setVisible(false);
    }

    // the program does what it should do
    SendButton.setOnAction(event -> {
      SendButton.setDisable(true);
      codeTypeSelectionPanel.setDisable(true);
      enlaceTypeSelectionPanel.setDisable(true);
      cleanScreens();
      
      String message = sendMsgTextField.getText();
      // TransmitterApplicationLayer transmitterApplicationLayer = new TransmitterApplicationLayer();
      // transmitterApplicationLayer.toTransmitter(message, this); before, we would go
      // instantly to Physical layer, now we are going to enlacing

      Enlacing enlacing = new Enlacing(3);
      enlacing.encode(message,this);


    });

    // change program speed
    speedSlider.setOnMouseDragged(Event -> {
      this.miliseconds = (int) speedSlider.getValue();
    });

  }

  /**
   * getCodeType returns the type of codification requested
   * 1 = binary
   * 2 = manchester
   * 3 = differential
   * 
   * @return codification requested
   */
  public int getCodeType() {
    int x = 1;

    if (codeTypeSelectionPanel.getValue().equals("Codificacao Binaria")) {
      return 1;
    }

    if (codeTypeSelectionPanel.getValue().equals("Codificacao Manchester")) {
      return 2;
    }

    if (codeTypeSelectionPanel.getValue().equals("Codificacao Manchester diferencial")) {
      return 3;
    }

    return x; // This return should be an impossible case, but case any error ocurrs, binary
              // coding will be aplied
  }

  public int getEnlaceType() {
    int x = 1;

    if (enlaceTypeSelectionPanel.getValue().equals("Framing")) {
      return 1;
    }

    if (enlaceTypeSelectionPanel.getValue().equals("Character stufing")) {
      return 2;
    }

    if (enlaceTypeSelectionPanel.getValue().equals("Bit stuffing")) {
      return 3;
    }

    if (enlaceTypeSelectionPanel.getValue().equals("Physical layer coding violations")) {
      return 4;
    }

    return x; // This return should be an impossible case, but case any error ocurrs, framing
              // enlacing will be executed
  }

  /**
   * this method adds a mesage to ASCIIToNumsText field
   * 
   * @param message string to be added
   */
  public void addASCIIToNumsTextField(String message) {
    Platform.runLater(() -> {
      ASCIIToNumsTextField.setText(ASCIIToNumsTextField.getText() + ' ' + message);
    });
  }

  /**
   * this method adds a message to BitsTextField
   * 
   * 
   * @param message string to be added
   */
  public void addToBitsTextField(String message) {
    Platform.runLater(() -> {
      BitsTextField.setText(BitsTextField.getText() + ' ' + message);
    });
  }

  /**
   * this method adds a message to bitsCodedTextField
   * 
   * @param message string to be added
   */
  public void addToBitsCodedTextField(String message) {
    Platform.runLater(() -> {
      BitsCodedTextFied.setText(BitsCodedTextFied.getText() + ' ' + message);
    });
  }

  /**
   * this method adds a message to numsToAsciiTextField
   * 
   * @param message string to be added
   */
  public void addToNumsToAsciiTextField(String message) {
    Platform.runLater(() -> {
      NumsToAsciiTextField.setText(NumsToAsciiTextField.getText() + message);
    });
  }

  /**
   * this method adds a message to bitsDecodedTextField
   * 
   * @param message string to be added
   */
  public void addToBitsDecodedTextField(String message) {
    Platform.runLater(() -> {
      BitsDecodedTextField.setText(BitsDecodedTextField.getText() + ' ' + message);
    });
  }

  /**
   * this method adds a message to bitsReceivedTextField
   * 
   * @param message string to be added
   */
  public void addToBitsReceivedTextField(String message) {
    Platform.runLater(() -> {
      BitsReceivedTextField.setText(BitsReceivedTextField.getText() + ' ' + message);

    });
  }

  /**
   * this methdo adds a message to receiveMsgTextField
   * 
   * @param message String text field
   */
  public void addToReceiveMsgTextField(String message) {
    Platform.runLater(() -> {
      receiveMsgTextField.setText(receiveMsgTextField.getText() + ' ' + message);
    });
  }

  /**
   * this method set the value of every text field to ""
   * (doesn't change value of sendMsgTextField)
   * 
   */
  public void cleanScreens() {
    ASCIIToNumsTextField.setText("");
    BitsTextField.setText("");
    BitsCodedTextFied.setText("");
    NumsToAsciiTextField.setText("");
    BitsDecodedTextField.setText("");
    BitsReceivedTextField.setText("");
    receiveMsgTextField.setText("");
  }

  /**
   * this method makes every image of the signal arrays "move" by 1
   * example: think about a snake game and the body moving following the head
   */
  public void refresh() {
    Platform.runLater(() -> {
      for (int i = 11; i >= 1; i--) {
        lowImgs[i].setVisible(lowImgs[i - 1].isVisible());
        highImgs[i].setVisible(highImgs[i - 1].isVisible());
        midImgs[i].setVisible(midImgs[i - 1].isVisible());
      }
    });

  }

  /**
   * giveSignal method will activate the image from receiving bit, if its
   * different from before, than activate also the mid img
   * 
   * @param bit
   */
  public void giveSignal(int bit) {
    Platform.runLater(() -> {
      highImgs[0].setVisible(false);
      midImgs[0].setVisible(false);
      lowImgs[0].setVisible(false);

      if (bit != lastSignal) {
        midImgs[1].setVisible(true);
      }

      if (bit == 0)
        lowImgs[0].setVisible(true);
      else
        highImgs[0].setVisible(true);

      lastSignal = bit;
    });

  }

  /**
   * getASCIIToNumsTextField will return the string on this TextArea
   * 
   * @return getASCIIToNumsTextField contents
   */
  public TextArea getASCIIToNumsTextField() {
    return this.ASCIIToNumsTextField;
  }

  /**
   * getSpeed method will return miliseconds to wait
   * 
   * @return this controller miliseconds
   */
  public int getSpeed() {
    return this.miliseconds;
  }

  /**
   * enableButton method will enable again the SendButton and
   * codeTypeSelectionPanel
   */
  public void enableButton() {
    SendButton.setDisable(false);
    codeTypeSelectionPanel.setDisable(false);
    enlaceTypeSelectionPanel.setDisable(false);
  }

}
