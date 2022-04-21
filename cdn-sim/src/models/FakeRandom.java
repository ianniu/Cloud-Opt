package models;


import java.util.Random;

//Get a random object globally
public class FakeRandom {

  private static Random random = new Random(1);

  private FakeRandom() {
  }

  public static Random getInstance() {
    return random;
  }


}
