/*
 * Author: Lucas Pedrosa Larangeira
 * 
 * 
 * TransmitterAplicationLayer will get the message from TextArea, 
 * show it on other TextArea then calls for Physicall transmitting layer
 * 
 * 
 */

package model;

import controller.MainController;
import javafx.scene.control.TextArea;

public class TransmitterApplicationLayer {

  public TransmitterApplicationLayer() {

  }

  public void toTransmitter(String message, MainController controller) {
    new Thread(() -> {
      try {
        TextArea painel = controller.getASCIIToNumsTextField();
        int frames[] = new int[message.length()]; // the message as ints
        String toAdd = ""; // the message that will be displayed

        for (int i = 0; i < message.length(); i++) {
          toAdd = ""; // start at ""
          toAdd += message.charAt(i); // get the char
          // toAdd += " = "; // equals it to
          frames[i] = message.charAt(i); // the ascii int
          // toAdd += frames[i];

          String add = toAdd;

          painel.setText(painel.getText() + add); // set text
          Thread.sleep(controller.getSpeed()); // sleeps
          System.out.println(add);

        }
        PhysicalTransmittingLayer.physicalTransmittingLayer(frames, controller); // go to physical transmitting Layer
      } catch (Exception e) {
        System.out.println(e.getStackTrace());
      }
    }).start();

  }

}
