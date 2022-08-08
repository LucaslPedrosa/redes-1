package model;

import controller.MainController;

public class PhysicalTransmittingLayer {

  public static void physicalTransmittingLayer(int[] frames, MainController controller) {
    int codeType = controller.getCodeType();
    Signal[] bitStream = new Signal[1];

    switch (codeType) {
      case 1:
        bitStream = binaryCoding(frames, controller);
        break;
      case 2:
        bitStream = manchesterCoding(frames, controller);
        break;

      default:
        break;
    }

    Communication.communication(bitStream, controller);
  }

  public static Signal[] binaryCoding(int frames[], MainController controller) {

    int size = (frames.length - 1) / 4 + 1; // a int have 32 bits, so we can store up to 32 signals, an ASCII table goes
                                            // up to 127 chars or more, so 8 bits for each is more than enough
    Signal memory[] = new Signal[size];
    int information;

    for (int i = 0; i < memory.length; i++) {
      memory[i] = new Signal();

      information = frames[i * 4]; // saving the bits on bits represented by x on 00000000 00000000 00000000
      // xxxxxxxxx

      if (i * 4 + 1 < frames.length)
        information |= (frames[i * 4 + 1] << 8); // saving the bits on bits represented by x on 00000000 00000000
                                                 // xxxxxxxxx
      // 00000000

      if (i * 4 + 2 < frames.length)
        information |= (frames[i * 4 + 2] << 16); // saving the bits on bits represented by x on 00000000 xxxxxxxxx
                                                  // 00000000
      // 00000000

      if (i * 4 + 3 < frames.length)
        information |= (frames[i * 4 + 3] << 24); // saving the bits on bits represented by x on xxxxxxxxx 00000000
                                                  // 00000000
      // 00000000

      // information var should be a really big number and strange like 218932194 but
      // its ok!
      // since we have 32 bits of information!

      memory[i].setBits(information);
      controller.addToBitsTextField(memory[i].bitsToString());
      try {
        Thread.sleep(controller.getSpeed());
      } catch (Exception e) {
      }
      controller.addToBitsCodedTextField(memory[i].toSignal() + '\n');
    }

    return memory;
  }

  public static Signal[] manchesterCoding(int frames[], MainController controller) {
    int size = (frames.length - 1) / 2 + 1;
    Signal memory[] = new Signal[size];
    int information;
    int bit;
    for (int i = 0; i < frames.length; i++)
      controller.addToBitsTextField(to8Bits(frames[i]) + ' ');

    for (int i = 0; i < size; i++) {
      memory[i] = new Signal();
      information = 0;
      bit = 1;

      for (int j = 0; j < 8; j++) {
        information |= ((((frames[i * 2] & bit) ^ 0) != 0) ? 1 : 0) << (j * 2);
        information |= (((((frames[i * 2] & bit) ^ bit) != 0) ? 1 : 0) << (j * 2 + 1));
        bit <<= 1;
      }

      bit = 1;
      if (i * 2 + 1 < frames.length)
        for (int j = 0; j < 8; j++) {
          information |= ((((frames[i * 2 + 1] & bit) ^ 0) != 0) ? 1 : 0) << (j * 2) + 16;
          information |= (((((frames[i * 2 + 1] & bit) ^ bit) != 0) ? 1 : 0) << (j * 2 + 1) + 16);
          bit <<= 1;
        }

      memory[i].setBits(information);

      controller.addToBitsCodedTextField(memory[i].bitsToString());
    }

    return memory;
  }

  public static String to8Bits(int n) {
    String toReturn = "";
    int bit = 1;
    for (int i = 0; i < 8; i++) {
      toReturn = ((bit & n) != 0 ? "1" : "0") + toReturn;
      bit <<= 1;
    }
    return toReturn;
  }

}
