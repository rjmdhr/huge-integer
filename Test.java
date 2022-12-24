/* Rajdeep Mudhar mudharr 400088877
 * Instructor: Dr. Habib-ur Rehman
 * L06
 * 13/02/19
 */

import java.lang.Byte;
import java.util.Random;

public class Test
{
    public static void main(String [] args)
    {
        /*New HugeInteger Validity Test*/
        System.out.println("HUGEINTEGER VALIDITY TEST");
        System.out.println("-------------------------");
        System.out.println(new HugeInteger(""));
        System.out.println(new HugeInteger("-0"));
        System.out.println(new HugeInteger("-"));
        System.out.println(new HugeInteger("abc"));
        System.out.println(new HugeInteger(-257));
        System.out.println(new HugeInteger(0));
        System.out.println(new HugeInteger("-99999999999999999"));
        System.out.println(new HugeInteger(17)+"\n");
        
        /*HugeInteger Addition Test*/
        System.out.println("HUGEINTEGER ADD TEST");
        System.out.println("-------------------------");
        HugeInteger a = new HugeInteger("123");
        HugeInteger b = new HugeInteger("45");
        System.out.println(a.add(b));
        System.out.println(b.add(a));
        b.num = new StringBuilder("100");
        b.sign = '-';
        System.out.println(a.add(b));
        System.out.println(b.add(a)+"\n");
        
        /*HugeInteger Subtraction Test*/
        System.out.println("HUGEINTEGER SUBTRACTION TEST");
        System.out.println("--------------------------");
        HugeInteger c = new HugeInteger("199");
        HugeInteger d = new HugeInteger("100");
        System.out.println(c.subtract(d));
        System.out.println(d.subtract(c));
        d.sign = '-';
        System.out.println(c.subtract(d));
        System.out.println(d.subtract(c)+"\n");
        
        /*HugeInteger Multiplication Test*/
        System.out.println("HUGEINTEGER MULTIPLICATION TEST");
        System.out.println("--------------------------");
        HugeInteger e = new HugeInteger("100");
        HugeInteger f = new HugeInteger("8");
        System.out.println(e.multiply(f));
        f.sign = '-';
        System.out.println(e.multiply(f));
        f.num = new StringBuilder("0");
        System.out.println(e.multiply(f)+"\n");
        
        /*HugeInteger Division Test*/
        System.out.println("HUGEINTEGER DIVISION TEST");
        System.out.println("--------------------------");
        HugeInteger g = new HugeInteger("-100");
        HugeInteger h = new HugeInteger("-25");
        System.out.println(g.divide(h));
        h.num = new StringBuilder("0");
        System.out.println(g.divide(h)+"\n");
         
        /*HugeInteger CompareTo Test*/
        System.out.println("HUGEINTEGER COMPARETO TEST");
        System.out.println("--------------------------");
        HugeInteger i = new HugeInteger("1000");
        HugeInteger j = new HugeInteger("100");
        System.out.println(i.compareTo(j));
        System.out.println(j.compareTo(i));
        j.num = new StringBuilder("-500");
        System.out.println(j.compareTo(i));
        System.out.println(i.compareTo(j));
        
        HugeInteger huge1, huge2, huge3;
        long startTime, endTime;
        double runTime = 0.0;
        int MAXNUMINTS = 100;
        int MAXRUN = 900000;
        int n = 1;
        for(int numInts = 0; numInts < MAXNUMINTS; numInts++){
            huge1 = new HugeInteger(n);
            huge2 = new HugeInteger(n);
            startTime = System.currentTimeMillis();
            for(int numRun = 0; numRun < MAXRUN; numRun++){
                huge3 = huge1.add(huge2);
            }
            endTime = System.currentTimeMillis();
            runTime += (double)(endTime - startTime)/((double)MAXRUN);
            System.out.println(endTime-startTime);
        }
        runTime = runTime/((double)(MAXNUMINTS));
        System.out.println("Runtime = "+runTime);
    }
}
