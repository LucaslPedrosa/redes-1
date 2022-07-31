/*
 * Author: Lucas Pedrosa Larangeira
 * 
 * 
 * 
 */

package model;

import controller.MainController;

public class TransmitterApplicationLayer {

  public static void transmitterApplicationLayer(String message, MainController controller) {

    int frames[] = new int[message.length()];
    String toAdd;

    for (int i = 0; i < message.length(); i++) {
      toAdd = "";
      // toAdd += "(";
      toAdd += message.charAt(i);
      toAdd += " = ";
      frames[i] = message.charAt(i);
      toAdd += frames[i];
      // toAdd += ")";

      controller.addASCIIToNumsTextField(toAdd);
    }
    PhysicalTransmittingLayer.physicalTransmittingLayer(frames, controller);
  }

}
