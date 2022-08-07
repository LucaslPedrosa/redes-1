package model;

import controller.MainController;

public class Communication {
  public static void communication(Signal bitStream[], MainController controller) {
    Signal bitStreamPointA[] = bitStream;
    Signal bitStreamPointB[] = new Signal[bitStream.length];
    int bitToSend;// bit used to send from point A to point B

    for (int i = 0; i < bitStream.length; i++) {
      bitStreamPointB[i] = new Signal();
      int bits = bitStreamPointA[i].getBits();
      int comparissonBit = 1;
      bitToSend = 0;

      for (int x = 0; x < 32; x++) {
        System.out.println("Signal: " + Integer.toString(i + 1) + " Bit:" + Integer.toString(x + 1) + ": "
            + Integer.toString((bits & comparissonBit) != 0 ? 1 : 0));
        bitToSend |= (bits & comparissonBit);
        controller.refresh();
        controller.giveSignal((bits & comparissonBit) != 0 ? 1 : 0);

        if (controller.getCodeType() == 1) {
          controller.refresh();
          controller.giveSignal((bits & comparissonBit) != 0 ? 1 : 0); //
        }

        comparissonBit <<= 1;
        try {
          Thread.sleep(300);
        } catch (Exception e) {
        }
      }
      bitStreamPointB[i].setBits(bitToSend);
      System.out.println("Bits passed: " + bitStreamPointA[i].bitsToString());
      System.out.println("Bits received: " + bitStreamPointB[i].bitsToString());
    }
    PhysicalReceiverLayer.physicalReceiverLayer(bitStreamPointB,controller);
  }
}
