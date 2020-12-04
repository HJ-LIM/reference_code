package abstrack;

import java.util.Date;

public class SimplePrint {

  private SimplePrint() throws IllegalAccessException {
     throw new IllegalAccessException("Utility class");
  }
   public static void print(int num) {
      System.out.println("Starting " + Thread.currentThread().getName()
          + ", index=" + num + ", " + new Date());
   }

   public static String getRandomStr(int size) {
      if(size > 0) {
         char[] tmp = new char[size];
         for(int i=0; i<tmp.length; i++) {
            int div = (int) Math.floor( Math.random() * 2 );

            if(div == 0) { // 0이면 숫자로
               tmp[i] = (char) (Math.random() * 10 + '0') ;
            }else { //1이면 알파벳
               tmp[i] = (char) (Math.random() * 26 + 'A') ;
            }
         }
         return new String(tmp);
      }
      return "ERROR : Size is required.";
   }
}
