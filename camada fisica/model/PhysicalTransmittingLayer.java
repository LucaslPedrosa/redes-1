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
        bitStream = differentialManchesterCoding(frames, controller);
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

  public static Signal[] differentialManchesterCoding(int frames[], MainController controller) {
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
      Boolean lastBit;// lastBit value will be true if HIGH and false if LOW

      if ((frames[i * 2] & bit) != 0) { // first bit deppends only on clock witch starts at HIGH
        // if start at 1, then it wont change the clock
        information |= 1; // first bit does change
        information |= (0 << 1); // kinda useless
        lastBit = false;
      } else {
        information |= 0; // kinda useless;
        information |= (1 << 1); // activate second bit (this moment i realize its more easy just bit or with
                                 // number 2)
        lastBit = true;
      }
      bit <<= 1;
      // for now on, every bit deppends on the last bit
      for (int j = 1; j < 8; j++) {
        int info = (((frames[i * 2] & bit) != 0) ? 1 : 0); // witch bit we are working on right now
        int toAdd; // witch bit will be added

        if (info == 1) {
          if (lastBit)
            toAdd = 1; // 1 its coming from behind(la ele), so we keep the one then switch to a zero
          else
            toAdd = 2; // 10

          lastBit = !lastBit;
        } else {
          if (lastBit)
            toAdd = 2; // 10
          else
            toAdd = 1; // 01
          // lastBit = lastBit; code with no effect
        }

        information |= toAdd << (j * 2);
        bit <<= 1;
      }

      bit = 1;
      if (i * 2 + 1 < frames.length) {
        for (int j = 0; j < 8; j++) {
          int toAdd;
          int info = (((frames[i * 2 + 1] & bit) != 0) ? 1 : 0);

          if (info == 1) {
            if (lastBit)
              toAdd = 1; // 1 its coming from behind(la ele), so we keep the one then switch to a zero
            else
              toAdd = 2; // 10

            lastBit = !lastBit;
          } else {
            if (lastBit)
              toAdd = 2; // 10
            else
              toAdd = 1; // 01
            // lastBit = lastBit; code with no effect
          }

          information |= toAdd << (j * 2 + 16);
          bit <<= 1;
        }
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
