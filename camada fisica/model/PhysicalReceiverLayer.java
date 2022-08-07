package model;

import controller.MainController;

public class PhysicalReceiverLayer {

  public static void physicalReceiverLayer(Signal bitStream[], MainController controller) {
    for (int i = 0; i < bitStream.length; i++) {
      controller.addToBitsReceivedTextField(bitStream[i].bitsToString() + '\n');
    }
    int bits[] = new int[1]; // we dont declare this int[] because its supposed he will always be defined
                             // after switch case, this means faster code
    switch (controller.getCodeType()) {
      case 1:
        bits = binaryDecodification(bitStream); // going back to integers
        break;
      case 2:

        break;
      default:
        break;
    }

    ReceiverApplicationLayer.receiverApplicationLayer(bits, controller);

  }

  public static int[] binaryDecodification(Signal bitStream[]) {
    int toReturn[] = new int[bitStream.length * 4];

    for (int i = 0; i < bitStream.length; i++) {
      int newBits[] = bitStream[i].binaryDecodification(); // Transform signal into 4 new ints
      for (int x = 0; x < 4; x++) {
        // pass all 4 ints to the toReturn array, we dont care about '0' ints, just
        // ignore them
        toReturn[i * 4 + x] = newBits[x];
      }
    }

    return toReturn;
  }
}
