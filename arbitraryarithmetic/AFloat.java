package arbitraryarithmetic;

public class AFloat {
    protected String value;
    public AFloat(){
        this.value = "0.0";
    }

    public AFloat(String value){
        this.value = value;
    }

    public AFloat(AFloat other){
        this.value = other.value;
    }

    public AFloat parse(String value){
        return new AFloat(value);
    }

    public String getString(){
        return this.value;
    }
    @Override
    public String toString(){
        return this.value;
    }

    public AFloat add(AFloat other) {
        String num1 = this.value;
        String num2 = other.value;

        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";
        
        boolean negative1 = num1.startsWith("-");
        boolean negative2 = num2.startsWith("-");

        if (negative1) num1 = num1.substring(1);
        if (negative2) num2 = num2.substring(1);

        if (negative1 && negative2) {
            return new AFloat("-" + new AFloat(num1).add(new AFloat(num2)).value);
        } else if (negative1 && !negative2) {
            return new AFloat(num2).subtract(new AFloat(num1));
        } else if (!negative1 && negative2) {
            return this.subtract(new AFloat(num2));
        }

        int decimal_index1 = num1.indexOf(".");
        int decimal_index2 = num2.indexOf(".");
        int decimal_digits1 = num1.length() - decimal_index1 - 1;
        int decimal_digits2 = num2.length() - decimal_index2 - 1;

        while (decimal_digits1 < decimal_digits2) {
            num1 += "0";
            decimal_digits1++;
        }
        while (decimal_digits2 < decimal_digits1) {
            num2 += "0";
            decimal_digits2++;
        }
    
    
        num1 = num1.replace(".", "");
        num2 = num2.replace(".", "");
    
        StringBuilder result = new StringBuilder();
        int carry = 0;
    
        for (int i = num1.length() - 1; i >= 0; i--) {
            int digit1 = num1.charAt(i) - '0';
            int digit2 = num2.charAt(i) - '0';
            int sum = digit1 + digit2 + carry;
            result.append((char) ((sum % 10) + '0'));
            carry = sum / 10;
        }
    
        if (carry > 0) {
            result.append((char) (carry + '0'));
        }
    
        result.reverse();
    
        int decPos = Math.max(decimal_digits1, decimal_digits2);
        if (decPos > 0) {
            result.insert(result.length() - decPos, ".");
        }
    
        String resultStr = result.toString();
        while (resultStr.length() > 1 && resultStr.charAt(0) == '0' && resultStr.charAt(1) != '.') {
            resultStr = resultStr.substring(1);
        }
    
        if (resultStr.contains(".")) {
            while (resultStr.endsWith("0")) resultStr = resultStr.substring(0, resultStr.length() - 1);
            if (resultStr.endsWith(".")) resultStr = resultStr.substring(0, resultStr.length() - 1);
        }
    
        return new AFloat(resultStr);
    }
    
    public AFloat subtract(AFloat other) {
        String num1 = this.value;
        String num2 = other.value;
        boolean negative = false;
    
        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";
    
        int dec1 = num1.length() - 1 - num1.indexOf('.');
        int dec2 = num2.length() - 1 - num2.indexOf('.');
        while (dec1 < dec2) {
            num1 += "0";
            dec1++;
        }
        while (dec2 < dec1) {
            num2 += "0";
            dec2++;
        }
    
        int int1 = num1.indexOf('.');
        int int2 = num2.indexOf('.');
        while (int1 < int2) {
            num1 = "0" + num1;
            int1++;
        }
        while (int2 < int1) {
            num2 = "0" + num2;
            int2++;
        }
    
        String num1Comp = num1.replace(".", "");
        String num2Comp = num2.replace(".", "");
        if (num1Comp.compareTo(num2Comp) < 0) {
            negative = true;
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }
        
        AInteger num1AInteger = new AInteger(num1);
        AInteger num2AInteger = new AInteger(num2);
        AInteger res = num1AInteger.subtract(num2AInteger);
        StringBuilder result = new StringBuilder(res.value); 
    
        if (negative) result.append('-');
    
        String output = result.reverse().toString();
    
        int start = 0;
        while (start < output.length() - 1 && output.charAt(start) == '0' && output.charAt(start + 1) != '.') {
            start++;
        }
        output = output.substring(start);
    
        if (output.contains(".")) {
            while (output.endsWith("0")) output = output.substring(0, output.length() - 1);
            if (output.endsWith(".")) output = output.substring(0, output.length() - 1);
        }
    
        return new AFloat(output);
    }
    
    public AFloat mul(AFloat other){
        String num1 = this.value;
        String num2 = other.value;
        boolean negative = false;

        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";

        if (num1.charAt(0) == '-') {
            negative = !negative;
            num1 = num1.substring(1);
        }
        if (num2.charAt(0) == '-') {
            negative = !negative;
            num2 = num2.substring(1);
        }
        if (num1.equals("0") || num2.equals("0")) return new AFloat();
        int dec1 = num1.length()-1-num1.indexOf('.');
        int dec2 = num2.length()-1-num2.indexOf('.');
        int totalDec = dec1+dec2;
        num1 = num1.replace(".", "");
        num2 = num2.replace(".", "");

        String output = "0";
        int len1 = num1.length();
        int len2 = num2.length();
        int i = len1-1;

        while(i>= 0){
            int[] sub = new int[len2+1];
            int carry = 0;
            int k=0;
            for(int j=len2-1;j >= 0;j--){
                int sum = (num1.charAt(i)-'0')*(num2.charAt(j)-'0') + carry;
                sub[k++]= sum%10;
                carry = sum/10;
            }
            if(carry != 0){
                sub[k++] = carry;
            }

            char[] string = new char[k];
            for(int m = k-1,n=0; m>=0;m--,n++){
                string[n] = (char)(sub[m]+'0');
            }
            String Str = new String(string);
            for(int m =(len1-1-i);m>0;m--){
                Str+="0";
            }
            AInteger a = new AInteger(output);
            output = a.add(new AInteger(Str)).value;
            i--;
        }

        while (output.length() <= totalDec) output = "0" + output;
        String result = output.substring(0, output.length() - totalDec) + "." + output.substring(output.length() - totalDec);

        while (result.endsWith("0")) result = result.substring(0, result.length() - 1);
        if (result.endsWith(".")) result = result.substring(0, result.length() - 1);

        result = negative ? "-" + result : result;
        return new AFloat(result);   
}
    
    // public AFloat div(AFloat other) {
    //     String dividend = this.value;
    //     String divisor = other.value;
    
    //     boolean negative = false;
    //     if (dividend.startsWith("-")) {
    //         negative = !negative;
    //         dividend = dividend.substring(1);
    //     }
    //     if (divisor.startsWith("-")) {
    //         negative = !negative;
    //         divisor = divisor.substring(1);
    //     }
    
    //     int first_decimal = dividend.indexOf('.');
    //     int second_decimal = divisor.indexOf('.');
    
    //     int decimal_num1 = (first_decimal == -1) ? 0 : (dividend.length() - first_decimal - 1);
    //     int decimal_num2 = (second_decimal == -1) ? 0 : (divisor.length() - second_decimal - 1);
    
    //     if (first_decimal != -1) {
    //         dividend = dividend.substring(0, first_decimal) + dividend.substring(first_decimal + 1);
    //     }
    //     if (second_decimal != -1) {
    //         divisor = divisor.substring(0, second_decimal) + divisor.substring(second_decimal + 1);
    //     }
    
    //     dividend = commonMethod.removeLeadingZeros(dividend);
    //     divisor = commonMethod.removeLeadingZeros(divisor);
    
    //     if (divisor.equals("0")) throw new ArithmeticException("Division by zero");
    
    //     int shift = decimal_num2 - decimal_num1;
    
    //     StringBuilder result = new StringBuilder();
    //     String current = "";
    
    //     for (int i = 0; i < dividend.length(); i++) {
    //         current += dividend.charAt(i);
    //         current = commonMethod.removeLeadingZeros(current);
    //         if (commonMethod.compare(current, divisor) < 0) {
    //             result.append(result.length() == 0 ? "0" : "0");
    //             continue;
    //         }
    //         int count = 0;
    //         while (commonMethod.compare(current, divisor) >= 0) {
    //             current = new AFloat(current).sub(new AFloat(divisor)).value;
    //             count++;
    //         }
    //         result.append(count);
    //     }
    
    //     result.append('.');
    //     int precision = 1000;
    //     while (precision > 0) {
    //         current += "0";
    //         current = commonMethod.removeLeadingZeros(current);
    //         if (commonMethod.compare(current, divisor) < 0) {
    //             result.append('0');
    //         } else {
    //             int count = 0;
    //             while (commonMethod.compare(current, divisor) >= 0) {
    //                 current = new AFloat(current).sub(new AFloat(divisor)).value;
    //                 count++;
    //             }
    //             result.append(count);
    //         }
    //         precision--;
    //     }
    
    //     int decimal_index = result.indexOf(".");
    //     result.deleteCharAt(decimal_index);
    
    //     int new_index = decimal_index + shift;
    
    //     if (new_index <= 0) {
    //         while (new_index < 0) {
    //             result.insert(0, '0');
    //             new_index++;
    //         }
    //         result.insert(0, "0.");
    //     } else {
    //         while (result.length() <= new_index) {
    //             result.append('0');
    //         }
    //         result.insert(new_index, '.');
    //     }
    
    //     String finalResult = result.toString();
    //     if (finalResult.contains(".")) {
    //         finalResult = finalResult.replaceAll("0+$", "");
    //         if (finalResult.endsWith(".")) {
    //             finalResult = finalResult.substring(0, finalResult.length() - 1);
    //         }
    //     }
    //     finalResult = commonMethod.removeLeadingZeros(finalResult);
    //     if (negative && !finalResult.equals("0")) {
    //         finalResult = "-" + finalResult;
    //     }
    
    //     return new AFloat(finalResult);
    // }
    
    
        
    public static void main(String[] args) {
        testMod("12.36", "12");
        testMod("-10.75", "3.5");
        testMod("10.75", "-3.5");
        testMod("-10.75", "-3.5");
        testMod("5.3", "2.1");
        testMod("0", "3.14");
    }

    private static void testMod(String a, String b) {
        AFloat num1 = new AFloat(a);
        AFloat num2 = new AFloat(b);
        System.out.println(num1.add(num2).value);
    }  
}
