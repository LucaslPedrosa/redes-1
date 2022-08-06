package model;

import controller.MainController;

public class Communication {
  public static void communication(Signal[] bitStream, MainController controller) {

    for (int i = 0; i < bitStream.length; i++) {
      int bits = bitStream[i].getBits();
      int finalBit = 1;

      for (int x = 0; x < 32; x++) {

        controller.refresh();
        controller.giveSignal((bits & finalBit) != 0 ? 1 : 0);
        if (controller.getCodeType() == 1) {
          controller.refresh();
          controller.giveSignal((bits & finalBit) != 0 ? 1 : 0);
        }

        finalBit <<= 1;
        try {
          Thread.sleep(300);
        } catch (Exception e) {
        }
      }

    }

  }
}
