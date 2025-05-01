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
    
}
