/**
 *  @Author : Lucas Pedrosa Larangeira
 *
 * 
 *  Enrollment : 202011430
 *  Created: 22/07/22
 *  last change at : 09/08/22
 *  Name: PysicalReceiverLayer.java 
 * 
 *  
 *  
 * 
 *
 */
package model;

import controller.MainController;

public class PhysicalReceiverLayer {

  /**
   * physicalReceiverLayer method will receive a signal and a controller, encode
   * as selected by controller the signal, then send it to Communitation
   * 
   * @param bitStream
   * @param controller
   */
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
        bits = manchesterDecodification(bitStream); // decode as manchester
        break;
      default:
        bits = differentialManchesterDecodification(bitStream); // decode as differential
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

  /**
   * read Taneabaum for more info
   * 
   * 
   * @param bitStream 8 bits information represeted as integers
   * @return decoded signal
   */
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
        num += ((((newBit & bit) != 0) ? 1 : 0) << j); // the manchester 0 start at 0 and 1 start at 1, so we know witch
                                                       // one we are working on
        bit <<= 2; // walk by 2 bits, since if we know one, we also know the other one
      }

      toReturn[i * 2] = num; // set the decrypted 16 bits
      num = 0; // resets
      bit = 1; // resets
      newBit >>= 16; // push the bits to the right

      for (int j = 0; j < 8; j++) {
        num += ((newBit & bit) != 0 ? 1 : 0) << j;
        bit <<= 2;
      }
      toReturn[i * 2 + 1] = num; // set the decrypted 16 bits
    }
    return toReturn;
  }

  public static int[] differentialManchesterDecodification(Signal bitStream[]) {
    int toReturn[] = new int[bitStream.length * 2];

    for (int i = 0; i < bitStream.length; i++) {
      Boolean lastSignal; // used to inform if last bit from tuple is either true = 1 or false = 0
      int sig = bitStream[i].getBits();
      int bit = 1; // comparisson bit
      int information; // decoded bits

      if ((sig & bit) != 0) {
        information = 1; // set first bit
        lastSignal = false; // if first is true, last is false
      } else {
        information = 0; // set last bit
        lastSignal = true; // if first is false, last is true
      }

      bit <<= 2; // walk by two (we are looking in a differential codification, if the first bit
                 // is x then the second must be false x)

      for (int j = 1; j < 8; j++) {

        if ((sig & bit) != 0) { // last bit was one?

          if (lastSignal) { // then this bit must be a one
            information |= (1 << j); // set info
            lastSignal = !lastSignal; // negate the last signal
          } else { // then this bit must be a zero
            information |= (0 << j); // unecessary but its here to know better whats happening
          }

        } else {
          if (lastSignal) { // then this bit must be a zero
            information |= 0 << j; // again, unecessary
          } else { // then this bit must be a one
            information |= 1 << j;
            lastSignal = !lastSignal;
          }
          // lastSignal = lastSignal; dead code, "why did u put this then? learn propurses
        }
        bit <<= 2;
      }
      toReturn[i * 2] = information; // first 16 bits from 32 decoded, time to go for the other 16
      information = 0;

      for (int j = 0; j < 8; j++) {

        if ((sig & bit) != 0) { // last bit was one?

          if (lastSignal) { // then this bit must be a one
            information |= 1 << j;
            lastSignal = !lastSignal;

          } else { // then this bit must be a zero
            information |= 0 << j;
          }

        } else {
          if (lastSignal) {
            information |= 0 << j;
          } else {
            information |= 1 << j;
            lastSignal = !lastSignal;

          }
          // lastSignal = lastSignal; dead code
        }
        bit <<= 2;
      }
      toReturn[i * 2 + 1] = information; // second 16-bits decoded
    }

    return toReturn; // return to sender
  }
}
