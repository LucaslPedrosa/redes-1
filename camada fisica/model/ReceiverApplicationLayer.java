/**
 *  @Author : Lucas Pedrosa Larangeira
 *
 * 
 *  Enrollment : 202011430
 *  Created: 22/07/22
 *  last change at : 09/08/22
 *  Name: ReceiverApplicationLayer.java 
 * 
 *  
 *  
 * 
 *
 */
package model;

import controller.MainController;

public class ReceiverApplicationLayer {
  public static void receiverApplicationLayer(int bits[], MainController controller) {
    String fullBits = ""; // get every 8 bit and adds to textField
    String msg = "";
    for (int i = 0; i < bits.length; i++) {
      if (bits[i] == 0) // ints equal to 0 must be ignored
        break;
      fullBits = "";
      int comparator = 1;

      for (int j = 0; j < 32; j++) {
        if ((j % 8 == 0) && j != 0) {
          fullBits = " " + fullBits;
        }
        fullBits = (((comparator & bits[i]) != 0) ? "1" : "0") + fullBits;
        comparator <<= 1;
      }
      msg += ((char) bits[i]);
      controller.addToBitsDecodedTextField(fullBits + '\n');
      try {
        Thread.sleep(controller.getSpeed());
      } catch (Exception e) {
      }
    }
    for (int i = 0; i < bits.length; i++) {
      controller.addToNumsToAsciiTextField(Integer.toString(bits[i]) + " = " + (char) (bits[i]));
      try {

        Thread.sleep(controller.getSpeed());
      } catch (Exception e) {
      }
    }

    controller.addToReceiveMsgTextField(msg);
    controller.enableButton();
  }
}
