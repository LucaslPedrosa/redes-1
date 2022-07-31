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
import javafx.scene.control.TextArea;
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
  private ComboBox<String> codeTypeSelectionPanel;

  private int speed = 1000;

  ObservableList<String> codeTypes = FXCollections.observableArrayList(
      "Codificacao Binaria",
      "Codificacao Manchester",
      "Codificacao Manchester diferencial");

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    codeTypeSelectionPanel.setItems(codeTypes);
    codeTypeSelectionPanel.setValue("Codificacao Binaria");

    SendButton.setOnAction(Event -> {
      cleanScreens();

      String message = sendMsgTextField.getText();
      TransmitterApplicationLayer.transmitterApplicationLayer(message, this);
    });

  }

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

  public void addASCIIToNumsTextField(String message) {
    Platform.runLater(() -> {
      ASCIIToNumsTextField.setText(ASCIIToNumsTextField.getText() + ' ' + message);

    });
  }

  public void addToBitsTextField(String message) {
    Platform.runLater(() -> {
      BitsTextField.setText(BitsTextField.getText() + ' ' + message);

    });
  }

  public void addToBitsCodedTextField(String message) {
    Platform.runLater(() -> {
      BitsCodedTextFied.setText(BitsCodedTextFied.getText() + ' ' + message);
    });
  }

  public void addToNumsToAsciiTextField(String message) {
    Platform.runLater(( () -> {
      NumsToAsciiTextField.setText(NumsToAsciiTextField.getText() + ' ' + message);
    }));
  }

  public void addToBitsDecodedTextField(String message) {
    Platform.runLater(() -> {
      BitsDecodedTextField.setText(BitsDecodedTextField.getText() + ' ' + message);
    });
  }

  public void addToBitsReceivedTextField(String message) {
    Platform.runLater(() -> {
      BitsReceivedTextField.setText(BitsReceivedTextField.getText() + ' ' + message);
    });
  }

  public void addToReceiveMsgTextField(String message) {
    Platform.runLater(() -> {
      receiveMsgTextField.setText(receiveMsgTextField.getText() + ' ' + message);
    });
  }

  public void cleanScreens() {
    Platform.runLater(() -> {
      ASCIIToNumsTextField.setText("");
      BitsTextField.setText("");
      BitsCodedTextFied.setText("");
      NumsToAsciiTextField.setText("");
      BitsDecodedTextField.setText("");
      BitsReceivedTextField.setText("");
      receiveMsgTextField.setText("");
    });

    sleep();
  }

  public void sleep() {
    try {
      Thread.sleep(speed);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

  }

}
