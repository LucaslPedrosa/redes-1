package model;

import controller.MainController;

public class ReceiverApplicationLayer {
  public static void receiverApplicationLayer(int bits[], MainController controller) {
    String fullBits = "";
    String msg = "";
    for (int i = 0; i < bits.length; i++) {
      if (bits[i] == 0)
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
