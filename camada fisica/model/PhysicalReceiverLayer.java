package model;

import controller.MainController;

public class PhysicalReceiverLayer {

  public static void physicalReceiverLayer(Signal bitStream[], MainController controller) {
    for (int i = 0; i < bitStream.length; i++) {
      controller.addToBitsReceivedTextField(bitStream[i].bitsToString() + '\n');
    }
    try {
      Thread.sleep(controller.getSpeed());
    } catch (Exception e) {
    }
    int bits[] = new int[1]; // we dont declare this int[] because its supposed he will always be defined
                             // after switch case, this means faster code
    switch (controller.getCodeType()) {
      case 1:
        bits = binaryDecodification(bitStream); // going back to integers
        for (int i = 0; i < bits.length; i++) {
          System.out.print((char) bits[i]);
        }
        break;
      case 2:
        bits = manchesterDecodification(bitStream);

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

  public static int[] manchesterDecodification(Signal bitStream[]) {
    int toReturn[] = new int[bitStream.length * 2];
    int num; // var used to add get back normal ints and push toReturn array
    int bit; // var used to compare each of 32 bits
    int newBit; // var used to receive all signal bits
    for (int i = 0; i < bitStream.length; i++) {
      num = 0;
      bit = 1;
      newBit = bitStream[i].getBits();

      for (int j = 0; j < 8; j++) {
        num += ((((newBit & bit) != 0) ? 1 : 0) << j);
        bit <<= 2;
      }

      toReturn[i * 2] = num;
      num = 0;
      bit = 1;
      newBit >>= 16;

      for (int j = 0; j < 8; j++) {
        num += ((newBit & bit) != 0 ? 1 : 0) << j;
        bit <<= 2;
      }
      toReturn[i * 2 + 1] = num;
    }
    return toReturn;
  }
}
