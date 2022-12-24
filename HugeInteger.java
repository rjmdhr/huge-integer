/* Rajdeep Mudhar mudharr 400088877
 * Instructor: Dr. Habib-ur Rehman
 * L06
 * 13/02/19
 */

import java.util.Random;
import java.lang.StringBuilder;

public class HugeInteger
{
    public StringBuilder num;
    public char sign;

    /**
     * Constructs new HugeInteger
     * Checks if val is a valid assignment, throws exception otherwise
     * @param val - the decimal String representation to be assigned to this
     */
    public HugeInteger(String val)
    {
      try{
        /*val empty or only has '-'*/
        if (val.length() == 0 || val.length() == 1 && val.charAt(0) == '-')
            throw new IllegalArgumentException("Invalid Argument");
        /*val is -0*/
        if (val.length() == 2 && val.charAt(0) == '-' && val.charAt(1) == '0' )
            throw new IllegalArgumentException("Invalid Argument");
        
        boolean temp;
        int index = (val.charAt(0) == '-') ? 1 : 0;
        
        /*Check if characters are digts if first index is '-'*/
        if (index == 1){
            for(int i = index; i < val.length(); i++){
                temp = (Character.isDigit(val.charAt(i)));
                if (temp == false){
                    throw new IllegalArgumentException("Invalid Argument");
                }
            }
        }
        /*Check if characters are digits if first index is not '-'*/
        else{
            for(int i = index; i < val.length(); i++){
                temp = (Character.isDigit(val.charAt(i)));
                if (temp == false){
                    throw new IllegalArgumentException("Invalid Argument");
                }
            }
        }
        
        /**
         * Constructs new HugeInteger if no exceptions were thrown
         * Deletes leading zeros
         */
        if (val.charAt(0) == '-'){
            this.sign = '-';
            num = new StringBuilder(val.substring(1));
            while(num.charAt(0) == '0' && num.length() > 1){
                num.deleteCharAt(0);
            }
        }
        else{
            this.sign = '+';
            num = new StringBuilder(val);
            while(num.charAt(0) == '0' && val.length() > 1)
                num.deleteCharAt(0);
        }
      }catch(IllegalArgumentException e){
            System.out.println("Invalid Argument");}
    }
    
    /**
     * Constructs random new HugeInteger
     * Checks if n is valid assignment, throws exception otherwise
     * @param n - the length of the new HugeInteger to be constructed
     */
    public HugeInteger(int n)
    {
        try{
            /*Check if n is valid*/
            if (n < 1)
                throw new IllegalArgumentException("Invalid Argument");

            /*Variable declerations*/
            Random ranNum = new Random();
            num = new StringBuilder();  
            int signVal = ranNum.nextInt(2);
            int firstDig = ranNum.nextInt(10);

            /*Assign sign value*/
            if (signVal == 0)
                sign = '-';
            else
                sign = '+';

            /*First digit cannot be zero*/
            while(firstDig == 0)
                firstDig = ranNum.nextInt(10);

            num.append(Character.forDigit(firstDig,10));

            /*Generate random integers for each digit*/
            for(int i = 0; i < n-1; i++)
                num.append(Character.forDigit(ranNum.nextInt(10), 10));
            
        }catch(IllegalArgumentException e){
            System.out.println("Invalid Argument");}
    }
    
    /**
     * Returns new HugeInteger representing the sum of this and h
     * @param h - the HugeInteger to be added
     */
    public HugeInteger add(HugeInteger h)
    {
        /*Variable declerations*/
        int carry = 0;
        int dig;
        StringBuilder sum = new StringBuilder("");
        int indexT = num.length()-1; //this
        int indexH = h.num.length()-1; //h
        
        if (sign == '+' && h.sign == '+'){
            
            /*Compute for the largest length*/
            while(indexT >= 0 && indexH >= 0){
                
                dig = (getVal(this,indexT)+getVal(h,indexH)+carry)%10;
                
                if (getVal(this,indexT)+getVal(h,indexH)+carry>=10)
                    carry = 1;
                
                else if (getVal(this,indexT)+getVal(h,indexH)+carry<10)
                    carry = 0;
                
                sum.append(getCharVal(dig));
                
                indexT--;
                indexH--;
                
                /*Add extra carry at end*/
                if (indexT == -1 && indexH == -1 && carry == 1)
                   sum.append(carry);
            }
        
            /*If this is larger than h*/
            while(indexT >= 0){
                
                dig = (getVal(this,indexT)+carry)%10;
                
                if (getVal(this,indexT)+carry>=10)
                    carry = 1;
                
                else if (getVal(this,indexT)+carry<10)
                    carry = 0;
                
                sum.append(getCharVal(dig));
                indexT--;
                
                /*Add extra carry at end*/
                if(indexT == -1 && carry == 1)
                    sum.append(carry);
            }
           
            /*If h is larger than this*/
            while(indexH >= 0){     
                
                dig = (getVal(h,indexH)+carry)%10;
                
                if (getVal(h,indexH)+carry>=10)
                    carry = 1;
                
                else if (getVal(h,indexH)+carry<10)
                    carry = 0;
                
                sum.append(getCharVal(dig));
                indexH--;
                
                /*Add extra carry at end*/
                if(indexH == -1 && carry == 1)
                    sum.append(carry);
            }
            return new HugeInteger(sum.reverse().toString());
        }
        
        
        /*Subtract h from this*/
        else if (sign == '+' && h.sign == '-'){
            HugeInteger temp = new HugeInteger(h.num.toString());
            temp.sign = h.sign;
            temp.sign = '+';
            return this.subtract(temp);
        }
        
        /*Subtract this from h*/
        else if (sign == '-' && h.sign == '+'){
            HugeInteger temp = new HugeInteger(h.num.toString());
            temp.sign = h.sign;
            return h.subtract(temp);
        }
        
        else if (sign == '-' && h.sign == '-'){
            HugeInteger temp = new HugeInteger(h.num.toString());
            return h.subtract(temp);
        }
        return null;
    }
    
    /**
     * Returns the difference this by h
     * @param h - the HugeInteger to be subtracted
     */ 
    public HugeInteger subtract(HugeInteger h)
    {
        /*Variable declerations*/
        int dig = 0;
        StringBuilder diff = new StringBuilder("");
        int indexT = num.length()-1; //this
        int indexH = h.num.length()-1; //h
        
        if (sign == '+' && h.sign == '+'){
            
            /*If this > h, subtract h from this*/
            if (this.compareTo(h) == 1){
                autoSub(indexH, indexT, dig, diff, h, this);
                return new HugeInteger(diff.reverse().toString());
            }
            
            /*If this < h, subtract this from h and negate*/
            else if (this.compareTo(h) == -1){
                autoSub(indexT, indexH, dig, diff, this, h);
                return new HugeInteger("-"+diff.reverse().toString());
            }
            
            /*If this = h*/
            else
                return new HugeInteger("0");
        }
        
        else if (sign == '-' && h.sign == '-'){
            
            /*If this > h, subtract h from this and negate*/
            if (this.compareTo(h) == -1){
                autoSub(indexT, indexH, dig, diff, this, h);
                return new HugeInteger("-"+diff.reverse().toString());
            }
            
            /*If this < h, subtract this from h and make positive*/
            else if (this.compareTo(h) == 1){
                autoSub(indexT, indexH, dig, diff, this, h);
                return new HugeInteger(diff.reverse().toString());
            }
            
            else
                return new HugeInteger("0");
        }
        
        /*Add h to this*/
        else if (sign == '+' && h.sign == '-'){
            HugeInteger temp = new HugeInteger(h.num.toString());
            temp.sign = '+';
            return this.add(temp);
        }
        
        /*Add this to h and negate*/
        else if (sign == '-' && h.sign == '+'){
            HugeInteger temp = new HugeInteger(num.toString());
            temp.sign = '+';   
            temp = temp.add(h);
            temp.sign = '-';
            return temp;
        }
        
        return null;
    }
    
    /**
     * Multiplies this HugeInteger by HugeInteger h
     * @param h - the HugeInteger to be multiplied by (Number of iterations)
     */
    public HugeInteger multiply(HugeInteger h)
    {
        /*Variable declerations*/
        HugeInteger mult = new HugeInteger(num.toString());
        HugeInteger iter = new HugeInteger("1");
        HugeInteger increase = new HugeInteger("1");
        HugeInteger numIter = new HugeInteger(h.num.toString());
        numIter.sign = '+';
        char signT = sign;
        char signH = h.sign;
        
        if(num.toString().equals("0") || h.num.toString().equals("0")){
            return new HugeInteger("0");
        }
        
        /*Adds this to this h times*/
        while(iter.compareTo(numIter)!=0){
            mult = mult.add(this);
            iter = iter.add(increase);
        }
        
        /*Check sign*/
        if(signT == '+' && signH == '+' || signT == '-' && signH == '-'){
            return mult;
        }
        
        else
            mult.sign = '-';
        
        return mult;
    }
    
    /**
     * Returns the integer division of this divided by HugeIntger h
     * Throws exception if h is zero
     * @param h - the HugeInteger to be divided by
     */
    public HugeInteger divide(HugeInteger h)
    {
        /*Variable declerations*/
        HugeInteger tempT = new HugeInteger(num.toString()); 
        tempT = tempT.subtract(new HugeInteger("1"));
        HugeInteger tempH = new HugeInteger(h.num.toString());
        HugeInteger itself = new HugeInteger(h.num.toString());
        HugeInteger numIter = new HugeInteger("1");
        tempH.sign = '+';
        tempT.sign = '+';
             
        try{
            if (num.toString().equals("0"))
                return new HugeInteger("0");
            else if (h.num.toString().equals("1"))
                return this;
            else if (this.compareTo(h)==0)
                return new HugeInteger("1");
            else if (h.num.toString().equals("0"))
                throw new IllegalArgumentException("Invalid Argument");   
            else if (tempT.compareTo(h)==-1)
                return new HugeInteger("0");
            

            /*Check how many times h fits into this*/
            else
                while(tempT.compareTo(tempH)==1){
                    tempH = tempH.add(itself);
                    numIter = numIter.add(new HugeInteger("1")); //increment
                }

            if (sign == '-' && h.sign == '-' || sign == '+' && h.sign == '-')
                numIter.sign = '+';
            else
                numIter.sign = '-';
                
        
        }catch(IllegalArgumentException e){
                System.out.print("Invalid Argument: Divsion by zero\n");
                return null;
        }
        
        return numIter;
    }
    
    /**
     * Compares this to h
     * Returns [1] if this > h, [0] if this = h, [-1] if this < h
     * @param h - the HugeInteger to be compared to 
     */
    public int compareTo(HugeInteger h)
    {
        /*Compare size of this and h*/
        if (sign == '+' && h.sign == '+'){
            
            if (num.length() < h.num.length())
                return -1;
            
            else if (num.length() > h.num.length())
                return 1;
            
            else{
                for(int i = 0; i <= num.length()-1; i++){
                    
                    if (getVal(this,i) < getVal(h,i))
                        return -1;
                    
                    else if (getVal(this,i) > getVal(h,i))
                        return 1;
                }
            }
        }
        
        /*Compare size of this and h*/
        else if (sign == '-' && h.sign == '-'){
            
            if (num.length() < h.num.length())
                return 1;
            
            else if (num.length() > h.num.length())
                return 1;
            
            else{
                for(int i = 0; i <= num.length()-1; i++){
                    
                    if (getVal(this,i) < getVal(h,i))
                        return 1;
                    
                    else if (getVal(this,i) > getVal(h,i))
                        return -1;
                }
            }
        }
                
        else if (sign == '+' && h.sign == '-')
            return 1;
        
        else if (sign == '-' && h.sign == '+')
            return -1;
        
        return 0;
    }
    
    /**
     * Returns String representation of this HugeInteger 
     * Checks if this is > or = to zero, returns accordingly
     */
    public String toString()
    {
        try{ 
        if (this.compareTo(new HugeInteger("0"))==1)
            return num.toString();
        else if (this.compareTo(new HugeInteger("0"))==0)
            return num.toString();
        else
            return Character.toString(sign)+num;
        }catch(Exception e){return null;}
    }
    
    /**
     * Shortens function to get integer value from index in h
     * @param h - the HugeInteger to be accessed
     * @param index - the index of this HugeInteger
     */
    public int getVal(HugeInteger h, int index)
    {
        return Character.getNumericValue(h.num.charAt(index));
    }
    
    /**
     * Shortens function to get character value fo single digit
     * @param dig - the digit to be converted to char
     */
    public char getCharVal(int dig)
    {
        return Character.forDigit(dig, 10);
    }
    
    /**
     * Subtracts h from a
     * Assumes absolute value of a is larger than h
     * @param indexH - index of HugeInteger h
     * @param indexT - index of HugeInteger t
     * @param dig - the difference of each digit at indexH or indexT
     * @param diff - the difference to be returned
     * @param a - HugeInteger h
     * @param h - HugeInteger a
     */
    public void autoSub
    (int indexH, int indexT, int dig, StringBuilder diff, HugeInteger h, 
            HugeInteger a)
    {
        int carry = 0;
        while(indexH >= 0){
            dig = getVal(a,indexT)-getVal(h,indexH)-carry;
            if(dig < 0){
                dig += 10;
                carry = 1;
            }
            else if (dig >= 0)
                carry = 0;
            diff.append(getCharVal(dig));
            indexH--;
            indexT--;
        }
        while(indexT >= 0){
            dig = getVal(a,indexT)-carry;
            if(dig < 0){
                dig += 10;
                carry = 1;
            }
            else if (dig >= 0)
                carry = 0;
            
            diff.append(getCharVal(dig));
            indexT--;
        }
    }
}
