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
import javafx.scene.image.ImageView;
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

  private ImageView lowImgs[];
  private ImageView highImgs[];
  private ImageView midImgs[];

  int dale = 0;

  private ObservableList<String> codeTypes = FXCollections.observableArrayList(
      "Codificacao Binaria",
      "Codificacao Manchester",
      "Codificacao Manchester diferencial");

  private int lastSignal = 0;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    ImageView l[] = { lowimg0, lowimg1, lowimg2, lowimg3, lowimg4, lowimg5,
        lowimg6, lowimg7, lowimg8, lowimg9,
        lowimg10, lowimg11 };
    lowImgs = l;

    ImageView h[] = { highimg1, highimg2, highimg3, highimg4, highimg5, highimg6,
        highimg7, highimg8, highimg9,
        highimg10, highimg11, highimg12 };
    highImgs = h;

    ImageView m[] = { midimg1, midimg2, midimg3, midimg4, midimg5, midimg6,
        midimg7, midimg8, midimg9, midimg10,
        midimg11, midimg12 };
    midImgs = m;

    codeTypeSelectionPanel.setItems(codeTypes);
    codeTypeSelectionPanel.setValue("Codificacao Binaria");

    for (int i = 0; i < 12; i++) {
      midImgs[i].setVisible(false);
      highImgs[i].setVisible(false);
    }

    SendButton.setOnAction(event -> {
      cleanScreens();
      String message = sendMsgTextField.getText();
      TransmitterApplicationLayer transmitterApplicationLayer = new TransmitterApplicationLayer();
      transmitterApplicationLayer.toTransmitter(message, this);
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
    // Platform.runLater(() -> {
    ASCIIToNumsTextField.setText(ASCIIToNumsTextField.getText() + ' ' + message);
    // });
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
    Platform.runLater(() -> {
      NumsToAsciiTextField.setText(NumsToAsciiTextField.getText() + ' ' + message);
    });
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
    ASCIIToNumsTextField.setText("");
    BitsTextField.setText("");
    BitsCodedTextFied.setText("");
    NumsToAsciiTextField.setText("");
    BitsDecodedTextField.setText("");
    BitsReceivedTextField.setText("");
    receiveMsgTextField.setText("");
  }

  public void refresh() {
    Platform.runLater(() -> {
      for (int i = 11; i >= 1; i--) {
        lowImgs[i].setVisible(lowImgs[i - 1].isVisible());
        highImgs[i].setVisible(highImgs[i - 1].isVisible());
        midImgs[i].setVisible(midImgs[i - 1].isVisible());
      }
    });

  }

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

  public TextArea getASCIIToNumsTextField() {
    return this.ASCIIToNumsTextField;
  }

}
