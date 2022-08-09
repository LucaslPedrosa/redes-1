/**
 *  @Author : Lucas Pedrosa Larangeira
 *
 * 
 *  Enrollment : 202011430
 *  Created: 22/07/22
 *  last change at : 09/08/22
 *  Name: Signal.java 
 * 
 *  
 *  
 * 
 *
 */
package model;

public class Signal {

  private int bits = 0;

  public int getBits() {
    return bits;
  }

  public void setBits(int bits) {
    this.bits = bits;
  }

  public String toSignal() {
    String toReturn = "";
    int comparator = 1;

    for (int i = 0; i < 32; i++) {
      Boolean bit = (comparator & getBits()) != 0;
      comparator <<= 1;

      if (bit)
        toReturn = " HIGH" + toReturn;
      else
        toReturn = " LOW" + toReturn;
    }

    return toReturn;
  }

  public String bitsToString() {

    String toReturn = "";
    int comparator = 1; // used to compare if bits are active, start at bit 1

    for (int i = 0; i < 32; i++) {
      if (i % 8 == 0)
        toReturn = ' ' + toReturn;
      Boolean bit = (comparator & getBits()) != 0; // comparator in 2^31 will be negative due to two complement
      comparator <<= 1;
      if (bit)
        toReturn = "1" + toReturn;
      else
        toReturn = "0" + toReturn;
    }

    return toReturn;
  }

  public int[] binaryDecodification() {
    int signal = getBits();
    int bits[] = new int[4];

    for (int i = 0; i < 4; i++) {
      bits[i] = signal & 255; // 255 is represented by 11111111 so it will return first 8 active bits
      signal >>= 8;// bitShift 8
      /*
       * There is no need get worried about negative numbers,
       * java itfself put another bit in last 8 bits "11111111" but the & gets only
       * last ones
       */
    }

    return bits;
  }

}
