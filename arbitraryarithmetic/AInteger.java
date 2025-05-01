package arbitraryarithmetic;

public class AInteger {
    protected String value;

    public AInteger(){
        this.value = "0";
    }

    public AInteger(String value){
        this.value = value;
    }

    public AInteger(AInteger other){
        this.value = other.value;
    }

    public AInteger parse(String value){
        return new AInteger(value);
    }

    public static int compare(String num1, String num2){
        num1 = removeZeroes(num1);
        num2 = removeZeroes(num2);
        if(num1.length() != num2.length()) return num1.length() - num2.length();
        return num1.compareTo(num2);
    }

    public static String removeZeroes(String value){ 
        int i = 0; // we have to initialize index i to 0 
        while(i< value.length()-1 && value.charAt(i) == '0') i++; //we have to check for the first non zero character
        return value.substring(i);  // it will effectivily remove the leading zero and returns the final value
    }

    public AInteger add(AInteger other){
        String num1 = this.value;
        String num2 = other.value;
        boolean negative1 = num1.charAt(0) == '-';
        boolean negative2 = num2.charAt(0) == '-';
        if (negative1) num1 = num1.substring(1);
        if (negative2) num2 = num2.substring(1);

        if (negative1 && negative2) {
            return new AInteger("-" + new AInteger(num1).add(new AInteger(num2)).value);
        } else if (!negative1 && negative2) {
            return this.subtract(new AInteger(num2));
        } else if(negative1 && !negative2) {
            return other.add(this);
        }

        int len1 = num1.length();
        int len2 = num2.length();
        StringBuilder answer = new StringBuilder();
        int i = len1-1;
        int j = len2-1;
        int carry = 0;
        while(i>=0 || j>=0 || carry > 0){
            int digitOf1 = (i>=0) ? num1.charAt(i)-'0' : 0;
            int digitOf2 = (j>=0) ? num2.charAt(j)-'0' : 0;
            int sum = digitOf1 + digitOf2 + carry;
            answer.append(sum%10);
            carry = sum / 10;
            i--;
            j--;
        }

    
        answer = new StringBuilder(removeZeroes(answer.reverse().toString()));

        return new AInteger(answer.toString());
    }

    public AInteger subtract(AInteger other) {
        String num1 = this.value;
        String num2 = other.value;
    
        boolean negative1 = num1.charAt(0) == '-';
        boolean negative2 = num2.charAt(0) == '-';

        if (negative1) num1 = num1.substring(1);
        if (negative2) num2 = num2.substring(1);
    
        if (!negative1 && negative2) {
            return this.add(new AInteger(num2));
        }

        else if (negative1 && !negative2) {
            AInteger sum = new AInteger(num1).add(new AInteger(num2));
            return new AInteger("-" + sum.value);
        }
        
        else if (negative1 && negative2) {
            return new AInteger(num2).subtract(new AInteger(num1));
        }
    
        boolean negative = false;
    
        if (num1.length() < num2.length() || (num1.length() == num2.length() && num1.compareTo(num2) < 0)) {
            String temp = num1;
            num1 = num2;
            num2 = temp;
            negative = true;
        }
    
        int len1 = num1.length();
        int len2 = num2.length();
        StringBuilder answer = new StringBuilder();
        int carry = 0;
        int i = len1 - 1;
        int j = len2 - 1;
    
        while (i >= 0 || j >= 0) {
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0;
            digit1 += carry;
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0;
            if (digit1 < digit2) {
                digit1 += 10;
                carry = -1;
            } else {
                carry = 0;
            }
            answer.append(digit1 - digit2);
            i--;
            j--;
        }

        String string = removeZeroes(answer.reverse().toString());
        if (negative) string  = "-" + string;
        if (string.equals("0") || string.equals("-0")) string = "0";

        return new AInteger(string);
    }

    public AInteger mul(AInteger other){
        String num1 = this.value;
        String num2 = other.value;
        boolean negative = false;

        if (num1.charAt(0) == '-') {
            negative = !negative;
            num1 = num1.substring(1);
        }
        if (num2.charAt(0) == '-') {
            negative = !negative;
            num2 = num2.substring(1);
        }
        if (num1.equals("0") || num2.equals("0")) return new AInteger();

        String output = "0";
        int len1 = num1.length();
        int len2 = num2.length();
        int i = len1-1;

        while(i>= 0){
            StringBuilder sub = new StringBuilder();
            int carry = 0;

            for(int j=len2-1;j >= 0;j--){
                int sum = (num1.charAt(i)-'0')*(num2.charAt(j)-'0') + carry;
                sub.append(sum%10);
                carry = sum/10;
            }
            if(carry != 0){
                sub.append(carry);
            }
            sub.reverse();
            for (int z = 0; z < len1 - 1 - i; z++) sub.append('0');
            AInteger a = new AInteger(output);
            output = a.add(new AInteger(sub.toString())).value;
            System.out.println(output + " " + sub.toString());
            i--;
            
        }
        output = removeZeroes(output);
        if (output.equals("0")) negative = false;
        String finalStr =  negative ? "-" + output: output;
        return new AInteger(finalStr);
    }

    public AInteger div(AInteger other) {
        String dividend = this.value;
        String divisor = other.value;
    
        if (divisor.equals("0") || divisor.equals("-0")) {
            return new AInteger("");
        }
    
        boolean negative = false;
        if (dividend.charAt(0) == '-') {
            negative = !negative;
            dividend = dividend.substring(1);
        }
        if (divisor.charAt(0) == '-') {
            negative = !negative;
            divisor = divisor.substring(1);
        }
        dividend=removeZeroes(dividend);
        int m = 0;
        while (m < divisor.length() - 1 && divisor.charAt(m) == '0') m++;
        divisor = divisor.substring(m);
    
        StringBuilder result = new StringBuilder();
        String current = "";
    
        for (int i = 0; i < dividend.length(); i++) {
            current += dividend.charAt(i);
            int j = 0;
            while (j < current.length() - 1 && current.charAt(j) == '0') j++;
            current = current.substring(j);
    
            int count = 0;
            while (compare(current, divisor) >= 0) {
                AInteger a = new AInteger(current);
                current = a.subtract(new AInteger(divisor)).value;
                count++;
            }
    
            result.append(count);
        }

        String quotient = result.toString();
        if (quotient.isEmpty()) quotient = "0";
        else{
        int n = 0;
        while (n < quotient.length() - 1 && quotient.charAt(n) == '0') n++;
       
         quotient = quotient.substring(n);}
    
        if (quotient.equals("0")) negative = false; 
    
        return new AInteger(negative ? "-" + quotient : quotient);
    }

    public static void main(String[] args) {
        AInteger num1 = new AInteger("132");
        AInteger num2 = new AInteger("22");
        System.out.println(num1.div(num2).value);
    }
}
