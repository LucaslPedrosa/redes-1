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
      default:
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
    String newMsg = "";
    int i = 0;
    int enlaceFrame = 0;
    int frame = 1;

    newMsg += (char) 126; // 126 is 0111110 as decimal, this is the start
    System.out.println("Start of frame: " + frame);
    enlaceFrame = Math.min(msg.length(), size);

    while (i < msg.length()) {

      if (msg.charAt(i) == (char) 126) {
        // does the stuffing
        newMsg += (char) 125; // 01111101
        newMsg += (char) 255; // the 0 remaining and 7 useless bytes, im using one's just so there will be a
                              // char, the 125 and 255 chars are going to need an ESC key
      } else {

        if (msg.charAt(i) == (char) 125 || msg.charAt(i) == (char) 255 || msg.charAt(i) == '|') {
          newMsg += '|'; // ESC KEY
        }

        newMsg += msg.charAt(i);
        System.out.println(msg.charAt(i));
      }

      enlaceFrame--;
      i++;
      if (enlaceFrame == 0) {
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

      if (msg.charAt(i) == (char) 125) { // 125 is the stuffed char, recompose it
        i++;
        i++;
        newMsg += (char) 126;
        continue;
      }

      if (msg.charAt(i) == '|') {
        i++;
        newMsg += msg.charAt(i);
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
