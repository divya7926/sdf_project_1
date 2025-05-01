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
        int i = 0;
        while(i< value.length()-1 && value.charAt(i) == '0') i++;
        return value.substring(i);
    }

    public AInteger add(AInteger other){
        String num1 = this.value;
        String num2 = other.value;
        boolean negative1 = num1.charAt(0) == '-';
        boolean negative2 = num2.charAt(0) == '-';
        if (negative1) num1 = num1.substring(1);
        if (negative2) num2 = num2.substring(1);

        if (negative1 && negative2) {
            return other.subtract(new AInteger(num1));
        } else if (!negative1 && negative2) {
            return this.subtract(new AInteger(num2));
        }

        int len1 = num1.length();
        int len2 = num2.length();
        StringBuilder answer = new StringBuilder();
        int i = len1-1;
        int j = len2-1;
        int carry = 0;
        while(i>=0 || j>=0 || carry > 0){
            int digitOf1 = (i> 0) ? num1.charAt(i)-'0' : 0;
            int digitOf2 = (j>=0) ? num2.charAt(j)-'0' : 0;
            int sum = digitOf1 + digitOf2 + carry;
            answer.append(sum%10);
            carry = sum / 10;
            i--;
            j--;
        }

        if(negative1 && negative2) answer.append('-');
        answer = new StringBuilder(removeZeroes(answer.reverse().toString()));

        return new AInteger(new String(answer.toString()));
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
        return new AInteger(string);
    }
}
