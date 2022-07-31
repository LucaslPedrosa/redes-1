package model;

import controller.MainController;

public class PhysicalTransmittingLayer {

  public static void physicalTransmittingLayer(int[] frames, MainController controller) {
    int codeType = controller.getCodeType();
    String[] bitStream;

    switch (codeType) {
      case 1:
        bitStream = binaryCoding(frames, controller);
        break;

      default:
        break;
    }

    

  }

  public static int[] calculateBits(int chars[]) {
    int size = chars.length;
    int bitStream[] = new int[size];
    int multiplier = 1;

    for (int i = 0; i < size; i++) {
      int bit = 1;
      bitStream[i] = 0;
      multiplier = 1;

      for (int x = 0; x < 8; x++) {
        bitStream[i] += ((chars[i] & bit) > 0) ? multiplier : 0;
        bit <<= 1;
        multiplier *= 10;
      }

    }
    return bitStream;
  }

  public static String[] binaryCoding(int frames[], MainController controller) {

    for (int i = 0; i < frames.length; i++) {
      controller.addToBitsTextField(to8Bits(frames[i]));
    }

    int size = frames.length / 4 + 1;

    String information[] = new String[size];

    for (int i = 0; i < size; i++) {
      information[i] = to8Bits(frames[i * 4]);
      if (frames.length > i * 4 + 1) {
        information[i] += ' ' + to8Bits(frames[i * 4 + 1]);
      } else
        information[i] += ' ' + to8Bits(0);
      if (frames.length > i * 4 + 2) {
        information[i] += ' ' + to8Bits(frames[i * 4 + 2]);
      } else
        information[i] += ' ' + to8Bits(0);
      if (frames.length > i * 4 + 3) {
        information[i] += ' ' + to8Bits(frames[i * 4 + 3]);
      } else
        information[i] += ' ' + to8Bits(0);

      information[i] += '\n';
      controller.addToBitsCodedTextField(information[i]);
    }

    return information;
  }

  // public static int[] manchesterCoding(int frames[], MainController controller)
  // {

  // }

  public static String to8Bits(int bit32) {

    if(bit32 == 0)
      return "00000000";

    String toReturn = "";
    int x = bit32;

    int bit = 1;

    for (int i = 0; i < 8; i++) {
      toReturn = Integer.toString(((x & bit) > 0) ? 1 : 0) + toReturn;
      bit <<= 1;
    }
    return toReturn;
  }

}
