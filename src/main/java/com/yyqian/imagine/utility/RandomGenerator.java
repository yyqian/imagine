package com.yyqian.imagine.utility;

import java.time.Instant;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by yyqian on 12/5/15.
 * Generator random string
 */
public class RandomGenerator {

  private static final String lowCaseCharacters = "abcdefghijklmnopqrstuvwxyz";
  private static final String upperCaseCharacters = lowCaseCharacters.toUpperCase();
  private static final String numbers = "0123456789";
  private static final String charactersAndNumbers = lowCaseCharacters + upperCaseCharacters +
      numbers;
  private static final String lowCaseCharactersAndSpace = lowCaseCharacters + "         ";
  private static final String MostCharacters = lowCaseCharactersAndSpace +
      "呦呦鹿鸣，食野之苹。我有嘉宾，鼓瑟吹笙。吹笙鼓簧，承筐是将。人之好我，示我周行。\n" +
      "呦呦鹿鸣，食野之蒿。我有嘉宾，德音孔昭。视民不恌，君子是则是效。我有旨酒，嘉宾式燕以敖。\n" +
      "呦呦鹿鸣，食野之芩。我有嘉宾，鼓瑟鼓琴。鼓瑟鼓琴，和乐且湛。我有旨酒，以燕乐嘉宾之心。";

  private static final Random random = new Random(Instant.now().getEpochSecond());

  static String genStr(String characters, int length) {
    char[] text = new char[length];
    IntStream.range(0, length).forEach(
        i -> text[i] = characters.charAt(random.nextInt(characters.length())));
    return new String(text);
  }

  static String genLCCharStr(int length) {
    return genStr(lowCaseCharacters, length);
  }

  static String genUCCharStr(int length) {
    return genStr(upperCaseCharacters, length);
  }

  static String genNumStr(int length) {
    return genStr(numbers, length);
  }

  static String genCharNumStr(int length) {
    return genStr(charactersAndNumbers, length);
  }

  static String genLowCaseCharactersAndSpace(int length) {
    return genStr(lowCaseCharactersAndSpace, length);
  }

  static String genMostCharacters(int length) {
    return genStr(MostCharacters, length);
  }

  static int genInt(int max) {
    return random.nextInt(max);
  }

}
