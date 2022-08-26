package model;

import controller.MainController;

public class Enlacing {

  private int size;

  public Enlacing(int size) {
    this.size = size;
    // theres no error handling on this size, so if you set it to 0 things will get
    // weird
  }

  public void encode(String msg, MainController controller) {

    int enlaceType = controller.getEnlaceType();
    String newMsg = "";

    switch (enlaceType) {
      case 1:
        newMsg = framingEncode(msg);
        break;

      case 2:
        newMsg = byteEncoding(msg);
        break;
      case 3:
        newMsg = bitStuffingEncoding(msg);
        break;
      case 4:
        newMsg = msg;// the enlacing will be done at phisical layer
        break;
      default:
        break;
    }
    TransmitterApplicationLayer transmitterApplicationLayer = new TransmitterApplicationLayer();
    transmitterApplicationLayer.toTransmitter(newMsg, controller);
  }

  public String decode(String msg, MainController controller) {
    int enlaceType = controller.getEnlaceType();
    String newMsg = "";

    switch (enlaceType) {
      case 1:
        newMsg = framingDecode(msg);
        break;
      case 2:
        newMsg = byteDecoding(msg);
        break;
      case 3:
        newMsg = bitDestuffing(msg);
        break;
      case 4:
        newMsg = msg;
        break;
      default:
        System.out.println("Impossible case reached, revise code");
        break;
    }

    return newMsg;
  }

  /**
   * framing encoding will count the chars and put that to the message, its a
   * deprecated method of enlacing
   * 
   * @param msg // message to enlace
   * @return // message enlaced
   */

  public String framingEncode(String msg) {
    String newMsg = "";

    for (int i = 0; i < msg.length(); i++) {
      if (i % size == 0) {
        newMsg += Integer.toString(Math.min(size, msg.length() - i));
      }
      newMsg += msg.charAt(i);
    }
    return newMsg;
  }

  /**
   * FramingDecode method will restore the original information before going
   * thought encode
   * 
   * @param msg // message enlaced
   * @return // message unlaced
   */
  public String framingDecode(String msg) {
    String newMsg = "";

    int frameCount = 0;
    int i = 0;

    while (i < msg.length()) {
      if (frameCount == 0) {
        frameCount = (msg.charAt(i) - '0');
      } else {
        newMsg += msg.charAt(i);
        frameCount--;
      }
      i++;
    }

    return newMsg;
  }

  /**
   * 
   * 
   * @param msg
   * @return
   */
  public String byteEncoding(String msg) {

    String newMsg = "S";
    int enlaceSize = Math.min(size, msg.length());

    for (int i = 0; i < msg.length(); i++) {

      if (msg.charAt(i) == '|' || msg.charAt(i) == 'S' || msg.charAt(i) == 'E') {
        newMsg += '|';
      }

      newMsg += msg.charAt(i);
      enlaceSize--;

      if (enlaceSize == 0) {
        newMsg += 'E';
        enlaceSize = Math.min(size, msg.length() - i - 1);

        if (enlaceSize != 0)
          newMsg += 'S';
      }
    }

    return newMsg;
  }

  public String byteDecoding(String msg) {
    String newMsg = "";
    int i = 0;
    int frame = 1;

    while (i < msg.length()) {

      if (msg.charAt(i) == 'S') {
        System.out.println("Start of Frame: " + Integer.toString(frame));
        i++;
        continue;
      }

      if (msg.charAt(i) == 'E') {
        System.out.println("End of Frame: " + Integer.toString(frame));
        frame++;
        i++;
        continue;
      }

      if (msg.charAt(i) == '|') {
        i++;
      }
      newMsg += msg.charAt(i);
      System.out.println(msg.charAt(i));
      i++;
    }

    return newMsg;
  }

  public String bitStuffingEncoding(String msg) {
    String newMsg = ""; // msg after stuffing
    int i = 0; // msg index
    int enlaceFrame = 0; // size of frame being used (everytime we add one bit, it decreases)
    int frame = 1; // frame identification
    int count = 0; // number of times '1' has appeared in a row

    newMsg += (char) 126; // 126 is 0111110 as decimal, this is the start
    System.out.println("Start of frame: " + frame);
    enlaceFrame = Math.min(msg.length(), size);

    while (i < msg.length()) {

      // 126 in binary == 011111110, we need to stuff this
      // my algorithm reads ANY information 8 bits then resets, so a case as:
      // 00001111 11000000 should never be a problem, i took care of this case just
      // for learn propurses, but again, it will never be a problem

      int bit = 1 << 7;

      /**
       * heres the logic: ANY char only needs to be stuffed AT MOST two times,
       * example:
       * 00011111 11111111
       * the second byte needs to be stuffed in the start and in the 'end' after six
       * more ones, worst case dynamic stuff, we create +8 bits, best case we still
       * need to create +8 bits, so here's the thing, IF we need to stuff a byte, we
       * are going to stuff them two times in a row, this way the implemetation is
       * easier and memory usage still the same
       * 
       * 
       * WARNING: DO NOT INSERT ANY ESPECIAL CHARACTER, FOR SOME REASON JAVA JUST
       * INVENTS (????) A NEW CHARACTER AND PUT THEM IN PLACE
       * 
       * 
       */

      int char1 = 0; // original 8 bits
      int char2 = 0; // we are going to use this if we need to stuff
      boolean stuffed = false;

      for (int j = 0; j < 8; j++) {

        if (((bit & ((int) msg.charAt(i))) != 0)) {
          count++;

          if (count == 6) {
            stuffed = true;
            newMsg += '|'; // ESC key means we stuffed a byte
            char1 |= (int) msg.charAt(i);

            char2 |= char1;
            char1 >>= 4;
            char1 <<= 2;

            char2 <<= 1;
            char2 = char2 & 30;

            char1 |= 64;// char1 now equals 01xx xx00
            char2 |= 64;// char2 now equals 010x xxx0

            newMsg += (char) char1;
            newMsg += (char) char2;
            count = 0;
            break;
          }

        } else {
          count = 0;
        }

        bit >>= 1;

      }

      if (!stuffed) {
        newMsg += msg.charAt(i);
      }

      enlaceFrame--;
      i++;

      if (enlaceFrame == 0) { // we need another flag?
        newMsg += (char) 126; // 126 is 0111110 as decimal
        System.out.println("End of frame: " + frame);

        enlaceFrame = Math.min(msg.length() - i, size);

        if (enlaceFrame != 0) {
          frame++;
          newMsg += (char) 126; // 126 is 0111110 as decimal
          System.out.println("Start of frame: " + frame);

        }
      }

    }

    return newMsg;
  }

  public String bitDestuffing(String msg) {
    String newMsg = "";
    Boolean start = false;
    int i = 0;
    int frame = 1;

    while (i < msg.length()) {

      if (msg.charAt(i) == '|') {
        i++;
        if (msg.charAt(i) == '|')
          newMsg += msg.charAt(i);
        else {
          int char1 = msg.charAt(i);// 01xx xx00
          i++;
          int char2 = msg.charAt(i);// 010x xxx0
          char1 ^= 64;
          char2 ^= 64;

          char1 <<= 2;
          char2 >>= 1;

          char1 |= char2;
          newMsg += (char) char1;
        }
        i++;
        continue;
      }

      if (msg.charAt(i) == (char) 126) {
        if (start) {
          System.out.println("End of frame: " + frame);
        } else {
          System.out.println("Start of frame: " + frame);
        }
        start = !start;
        i++;
        continue;
      }

      newMsg += msg.charAt(i);
      System.out.println(msg.charAt(i));
      i++;
    }
    return newMsg;
  }

}
