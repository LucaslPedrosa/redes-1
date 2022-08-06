package model;

public class Signal {

  // private static int type = 1;
  private int bits = 0;

  public int getBits() {
    return bits;
  }

  public void setBits(int bits) {
    this.bits = bits;
  }

  // public static int getType() {
  // return type;
  // }

  // public static void setType(int typ) {
  // type = typ;
  // }

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
    int comparator = 1;

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

}
