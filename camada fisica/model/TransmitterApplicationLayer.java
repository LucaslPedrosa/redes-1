/*
 * Author: Lucas Pedrosa Larangeira
 * 
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
        int frames[] = new int[message.length()];
        String toAdd;

        for (int i = 0; i < message.length(); i++) {
          toAdd = "";
          toAdd += message.charAt(i);
          toAdd += " = ";
          frames[i] = message.charAt(i);
          toAdd += frames[i];

          String add = toAdd;

          painel.setText(painel.getText() + ' ' + add);
          Thread.sleep(1000);
          System.out.println(add);
          
        }
        PhysicalTransmittingLayer.physicalTransmittingLayer(frames, controller);
      } catch (Exception e) {
        System.out.println("Lol");
      }
    }).start();

  }

}
