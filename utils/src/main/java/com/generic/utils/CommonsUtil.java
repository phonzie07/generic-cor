package com.generic.utils;

import java.lang.reflect.Field;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CommonsUtil {

  public static boolean isInvalidPattern(String obj) {
    String pattern = "^(?=[\\w\\d])(?=[\\w\\s\\d])[\\d\\w\\W].+\\S$";
    return !obj.matches(pattern);
  }

  /**
   * Convert Cell Phone Number to International Number
   *
   * @param contactNumber
   * @return
   */
  public static String convertCPNumberToIntl(String contactNumber, boolean withPlus) {
    contactNumber = convertCPNumberToLocal(contactNumber);
    return withPlus ? "+63" + contactNumber.substring(1) : "63" + contactNumber.substring(1);
  }

  public static String convertCPNumberToLocal(String cpNumForChecking) {
    log.info("cellno: " + cpNumForChecking);

    if (cpNumForChecking.length() == 13) {
      char first = cpNumForChecking.charAt(0);
      char second = cpNumForChecking.charAt(1);
      String checkNo = Character.toString(first) + Character.toString(second);
      if ("+6".equalsIgnoreCase(checkNo)) {
        return "09" + cpNumForChecking.substring(4);
      }
    } else if (cpNumForChecking.length() == 12) {
      char first = cpNumForChecking.charAt(0);
      char second = cpNumForChecking.charAt(1);
      String checkNo = Character.toString(first) + Character.toString(second);
      if ("63".equalsIgnoreCase(checkNo)) {
        return "09" + cpNumForChecking.substring(3);
      }
    } else if (cpNumForChecking.length() == 10) {
      return "0" + cpNumForChecking;
    }
    return cpNumForChecking;
  }

  public static <T> T trimFields(T t){
    Field[] fields = t.getClass().getFields();
    for (Field field : fields) {
      try {
        if (field.get(t) instanceof String) {
          Object o = field.get(t);
          String s = (String) o;
          field.set(t, s.trim());
        }
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    return t;
  }

}
