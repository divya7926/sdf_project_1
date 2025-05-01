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
    
    
    
}
