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
    
        boolean neg1 = num1.startsWith("-");
        boolean neg2 = num2.startsWith("-");
    
        if (neg1) num1 = num1.substring(1);
        if (neg2) num2 = num2.substring(1);
    
        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";
    
        if (num1.endsWith(".")) num1 += "0";
        if (num2.endsWith(".")) num2 += "0";
    

        if (neg1 && !neg2) {
            return new AFloat(num2).subtract(new AFloat(num1));
        }

        if (!neg1 && neg2) {
            return this.subtract(new AFloat(num2));
        }
    
        if (num1.startsWith(".")) num1 = "0" + num1;
        if (num2.startsWith(".")) num2 = "0" + num2;
    
        int int1 = num1.indexOf(".");
        int int2 = num2.indexOf(".");
        int dec1 = num1.length() - int1 - 1;
        int dec2 = num2.length() - int2 - 1;
    
        while (dec1 < dec2) {
            num1 += "0";
            dec1++;
        }
        while (dec2 < dec1) {
            num2 += "0";
            dec2++;
        }
    
        while (int1 < int2) {
            num1 = "0" + num1;
            int1++;
        }
        while (int2 < int1) {
            num2 = "0" + num2;
            int2++;
        }
    
        num1 = num1.replace(".", "");
        num2 = num2.replace(".", "");
    
        String res = new AInteger(num1).add(new AInteger(num2)).value;
        StringBuilder result = new StringBuilder(res);
    
        int decimalPos = Math.max(dec1, dec2);
        if (decimalPos > 0) {
            result.insert(result.length() - decimalPos, ".");
        }
    
        String resStr = result.toString();
    
        while (resStr.length() > 1 && resStr.charAt(0) == '0' && resStr.charAt(1) != '.') {
            resStr = resStr.substring(1);
        }

        if (resStr.contains(".")) {
            while (resStr.endsWith("0")) resStr = resStr.substring(0, resStr.length() - 1);
            if (resStr.endsWith(".")) resStr = resStr.substring(0, resStr.length() - 1);
        }

        if (neg1 && neg2 && !resStr.equals("0")) {
            resStr = "-" + resStr;
        }
  
        if (resStr.equals("-0")) {
            resStr = "0";
        }
    
        return new AFloat(resStr);
    }
       
    public AFloat subtract(AFloat other) {
        String num1 = this.value;
        String num2 = other.value;
        boolean neg1 = num1.startsWith("-");
        boolean neg2 = num2.startsWith("-");
        
        if (neg1) num1 = num1.substring(1);
        if (neg2) num2 = num2.substring(1);
        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";

        if (neg1 && !neg2) {
            return new AFloat("-" + new AFloat(num1).add(new AFloat(num2)).value);
        } else if (!neg1 && neg2) {
            return this.add(new AFloat(num2));
        } else if (neg1 && neg2) {
            return new AFloat(num2).subtract(new AFloat(num1));
        }

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
    
        boolean negative = false;
        if (num1Comp.compareTo(num2Comp) < 0) {
            negative = true;
            String temp = num1Comp;
            num1Comp = num2Comp;
            num2Comp = temp;
        }
    
        AInteger a1 = new AInteger(num1Comp);
        AInteger a2 = new AInteger(num2Comp);
        String res = a1.subtract(a2).value;

        while (res.length() < num1Comp.length()) {
            res = "0" + res;
        }
    
        StringBuilder result = new StringBuilder(res);
        int decimalPos = Math.max(dec1, dec2);
        if (decimalPos > 0) {
            result.insert(result.length() - decimalPos, ".");
        }
    
        String output = result.toString();

        int start = 0;
        while (start < output.length() - 1 && output.charAt(start) == '0' && output.charAt(start + 1) != '.') {
            start++;
        }
        output = output.substring(start);

        if (output.contains(".")) {
            while (output.endsWith("0")) output = output.substring(0, output.length() - 1);
            if (output.endsWith(".")) output = output.substring(0, output.length() - 1);
        }
    
        if (output.isEmpty()) output = "0";
        if (negative && !output.equals("0")) output = "-" + output;
    
        return new AFloat(output);
    }
    
    public AFloat mul(AFloat other) {
        String num1 = this.value;
        String num2 = other.value;
        boolean negative = false;
    
        if (num1.startsWith("-")) {
            negative = !negative;
            num1 = num1.substring(1);
        }
        if (num2.startsWith("-")) {
            negative = !negative;
            num2 = num2.substring(1);
        }
    
        if (!num1.contains(".")) num1 += ".0";
        if (!num2.contains(".")) num2 += ".0";
    
        num1 = AInteger.removeZeroes(num1);
        num2 = AInteger.removeZeroes(num2);
    
        if (num1.equals("0") || num2.equals("0")) return new AFloat("0");
    
        int dec1 = num1.length() - 1 - num1.indexOf('.');
        int dec2 = num2.length() - 1 - num2.indexOf('.');
        int totalDec = dec1 + dec2;

        num1 = num1.replace(".", "");
        num2 = num2.replace(".", "");
    
        AInteger int1 = new AInteger(num1);
        AInteger int2 = new AInteger(num2);
    
        String resultRaw = int1.mul(int2).value;
    
        while (resultRaw.length() <= totalDec) {
            resultRaw = "0" + resultRaw;
        }
    
        StringBuilder resultBuilder = new StringBuilder(resultRaw);
        if (totalDec > 0) {
            resultBuilder.insert(resultBuilder.length() - totalDec, ".");
        }
    
        String result = resultBuilder.toString();

        while (result.length() > 1 && result.charAt(0) == '0' && result.charAt(1) != '.') {
            result = result.substring(1);
        }

        if (result.contains(".")) {
            while (result.endsWith("0")) result = result.substring(0, result.length() - 1);
            if (result.endsWith(".")) result = result.substring(0, result.length() - 1);
        }
    
        if (result.isEmpty()) result = "0";
        if (negative && !result.equals("0")) result = "-" + result;
    
        return new AFloat(result);
    }
    
    public AFloat div(AFloat other) {
        String dividend = this.value;
        String divisor = other.value;
    
        boolean negative = false;
        if (dividend.startsWith("-")) {
            negative = !negative;
            dividend = dividend.substring(1);
        }
        if (divisor.startsWith("-")) {
            negative = !negative;
            divisor = divisor.substring(1);
        }
    
        int first_decimal = dividend.indexOf('.');
        int second_decimal = divisor.indexOf('.');
    
        int decimal_num1 = (first_decimal == -1) ? 0 : (dividend.length() - first_decimal - 1);
        int decimal_num2 = (second_decimal == -1) ? 0 : (divisor.length() - second_decimal - 1);
    
        if (first_decimal != -1) {
            dividend = dividend.substring(0, first_decimal) + dividend.substring(first_decimal + 1);
        }
        if (second_decimal != -1) {
            divisor = divisor.substring(0, second_decimal) + divisor.substring(second_decimal + 1);
        }
    
        dividend = AInteger.removeZeroes(dividend);
        divisor = AInteger.removeZeroes(divisor);
    
        if (divisor.equals("0")) throw new ArithmeticException("Division by zero");
    
        int shift = decimal_num2 - decimal_num1;
    
        StringBuilder result = new StringBuilder();
        String current = "";
    
        for (int i = 0; i < dividend.length(); i++) {
            current += dividend.charAt(i);
            current = AInteger.removeZeroes(current);
            if (AInteger.compare(current, divisor) < 0) {
                result.append(result.length() == 0 ? "0" : "0");
                continue;
            }
            int count = 0;
            while (AInteger.compare(current, divisor) >= 0) {
                current = new AFloat(current).subtract(new AFloat(divisor)).value;
                count++;
            }
            result.append(count);
        }
    
        result.append('.');
        int precision = 1000;
        while (precision > 0) {
            current += "0";
            current = AInteger.removeZeroes(current);
            if (AInteger.compare(current, divisor) < 0) {
                result.append('0');
            } else {
                int count = 0;
                while (AInteger.compare(current, divisor) >= 0) {
                    current = new AFloat(current).subtract(new AFloat(divisor)).value;
                    count++;
                }
                result.append(count);
            }
            precision--;
        }
    
        int decimal_index = result.indexOf(".");
        result.deleteCharAt(decimal_index);
    
        int new_index = decimal_index + shift;
    
        if (new_index <= 0) {
            while (new_index < 0) {
                result.insert(0, '0');
                new_index++;
            }
            result.insert(0, "0.");
        } else {
            while (result.length() <= new_index) {
                result.append('0');
            }
            result.insert(new_index, '.');
        }
    
        String finalResult = result.toString();
        if (finalResult.contains(".")) {
            finalResult = finalResult.replaceAll("0+$", "");
            if (finalResult.endsWith(".")) {
                finalResult = finalResult.substring(0, finalResult.length() - 1);
            }
        }
        finalResult = AInteger.removeZeroes(finalResult);
        if (negative && !finalResult.equals("0")) {
            finalResult = "-" + finalResult;
        }
    
        return new AFloat(finalResult);
    }
}
