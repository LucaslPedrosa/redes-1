package model;

import controller.MainController;

public class ReceiverApplicationLayer {
  public static void receiverApplicationLayer(int bits[], MainController controller) {
    for (int i = 0; i < bits.length; i++) {
      if (i % 8 == 0 && i != 0)
        controller.addToBitsDecodedTextField(" ");  // space bits
      if (i % 32 == 0 && i != 0)
        controller.addToBitsDecodedTextField("\n"); // break line

      controller.addToBitsDecodedTextField(Integer.toString(bits[i]));
    }
  }
}
