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

      information = frames[i]; // saving the bits on bits represented by x on 00000000 00000000 00000000
                               // xxxxxxxxx

      if (i + 1 < frames.length)
        information |= (frames[i + 1] << 8); // saving the bits on bits represented by x on 00000000 00000000 xxxxxxxxx
                                             // 00000000

      if (i + 2 < frames.length)
        information |= (frames[i + 2] << 16); // saving the bits on bits represented by x on 00000000 xxxxxxxxx 00000000
                                              // 00000000

      if (i + 3 < frames.length)
        information |= (frames[i + 3] << 24); // saving the bits on bits represented by x on xxxxxxxxx 00000000 00000000
                                              // 00000000

      // information var should be a really big number and strange like 218932194 but
      // its ok!
      // since we have 32 bits of information!

      memory[i].setBits(information);
      controller.addToBitsTextField(memory[i].bitsToString());
      controller.addToBitsCodedTextField(memory[i].toSignal() + '\n');
    }

    return memory;
  }

  public static Signal[] manchesterCoding(int frames[], MainController controller) {
    Signal memory[] = new Signal[1];
    return memory;
  }

}
