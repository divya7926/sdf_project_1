package arbitraryarithmetic; 

public class AFloat { 
    protected String value; // Stores number as string

    public AFloat() { 
        this.value = "0.0"; // Sets value to "0.0"
    }

    public AFloat(String value) { // Constructor with string input
        this.value = value; // Sets value to input string
    }

    public AFloat(AFloat other) {
        this.value = other.value; // Copies value from another AFloat
    }

    public AFloat parse(String value) { // Creates AFloat from string
        return new AFloat(value); // Returns new AFloat with input string
    }

    public String getString() { // Gets value as string
        return this.value; // Returns value
    }

    @Override
    public String toString() { // Returns string form of number
        return this.value; // Gives value as string
    }

    public AFloat add(AFloat other) { // Adds another AFloat
        String num1 = this.value; // Gets this number
        String num2 = other.value; // Gets other number
        boolean neg1 = num1.startsWith("-"); // Checks if num1 is negative
        boolean neg2 = num2.startsWith("-"); // Checks if num2 is negative
        if (neg1) num1 = num1.substring(1); // Removes negative sign from num1
        if (neg2) num2 = num2.substring(1); // Removes negative sign from num2
        if (!num1.contains(".")) num1 += ".0"; // Adds .0 if no decimal
        if (!num2.contains(".")) num2 += ".0"; // Adds .0 if no decimal
        if (num1.endsWith(".")) num1 += "0"; // Adds 0 if ends with dot
        if (num2.endsWith(".")) num2 += "0"; // Adds 0 if ends with dot
        if (neg1 && !neg2) { // Negative plus positive: subtract
            return new AFloat(num2).subtract(new AFloat(num1)); // Subtracts num1 from num2
        }
        if (!neg1 && neg2) { // Positive minus negative: subtract
            return this.subtract(new AFloat(num2)); // Subtracts num2
        }
        if (num1.startsWith(".")) num1 = "0" + num1; // Adds 0 before decimal
        if (num2.startsWith(".")) num2 = "0" + num2; // Adds 0 before decimal
        int int1 = num1.indexOf("."); // Finds decimal position in num1
        int int2 = num2.indexOf("."); // Finds decimal position in num2
        int dec1 = num1.length() - int1 - 1; // Counts decimal digits in num1
        int dec2 = num2.length() - int2 - 1; // Counts decimal digits in num2
        while (dec1 < dec2) { // Aligns decimal digits
            num1 += "0"; // Adds 0 to num1
            dec1++; // Updates dec1
        }
        while (dec2 < dec1) { // Aligns decimal digits
            num2 += "0"; // Adds 0 to num2
            dec2++; // Updates dec2
        }
        while (int1 < int2) { // Aligns integer part
            num1 = "0" + num1; // Adds 0 to num1
            int1++; // Updates int1
        }
        while (int2 < int1) { // Aligns integer part
            num2 = "0" + num2; // Adds 0 to num2
            int2++; // Updates int2
        }
        num1 = num1.replace(".", ""); // Removes decimal point
        num2 = num2.replace(".", ""); // Removes decimal point
        String res = new AInteger(num1).add(new AInteger(num2)).value; // Adds as integers
        StringBuilder result = new StringBuilder(res); // Builds result
        int decimalPos = Math.max(dec1, dec2); // Finds decimal position
        if (decimalPos > 0) { // Adds decimal point if needed
            result.insert(result.length() - decimalPos, "."); // Inserts decimal
        }
        String resStr = result.toString(); // Gets result string
        while (resStr.length() > 1 && resStr.charAt(0) == '0' && resStr.charAt(1) != '.') { // Removes leading zeros
            resStr = resStr.substring(1); // Skips leading 0
        }
        if (resStr.contains(".")) { // Cleans up decimal part
            while (resStr.endsWith("0")) resStr = resStr.substring(0, resStr.length() - 1); // Removes trailing zeros
            if (resStr.endsWith(".")) resStr = resStr.substring(0, resStr.length() - 1); // Removes trailing dot
        }
        if (neg1 && neg2 && !resStr.equals("0")) { // Both negative: add negative sign
            resStr = "-" + resStr; // Adds negative sign
        }
        if (resStr.equals("-0")) { // Avoids negative zero
            resStr = "0"; // Sets to 0
        }
        return new AFloat(resStr); // Returns result as AFloat
    }

    public AFloat subtract(AFloat other) { // Subtracts another AFloat
        String num1 = this.value; // Gets this number
        String num2 = other.value; // Gets other number
        boolean neg1 = num1.startsWith("-"); // Checks if num1 is negative
        boolean neg2 = num2.startsWith("-"); // Checks if num2 is negative
        if (neg1) num1 = num1.substring(1); // Removes negative sign from num1
        if (neg2) num2 = num2.substring(1); // Removes negative sign from num2
        if (!num1.contains(".")) num1 += ".0"; // Adds .0 if no decimal
        if (!num2.contains(".")) num2 += ".0"; // Adds .0 if no decimal
        if (neg1 && !neg2) { // Negative minus positive: negate sum
            return new AFloat("-" + new AFloat(num1).add(new AFloat(num2)).value); // Negates sum
        } else if (!neg1 && neg2) { // Positive minus negative: add
            return this.add(new AFloat(num2)); // Adds num2
        } else if (neg1 && neg2) { // Both negative: swap subtraction
            return new AFloat(num2).subtract(new AFloat(num1)); // Subtracts num1 from num2
        }
        int dec1 = num1.length() - 1 - num1.indexOf('.'); // Counts decimal digits in num1
        int dec2 = num2.length() - 1 - num2.indexOf('.'); // Counts decimal digits in num2
        while (dec1 < dec2) { // Aligns decimal digits
            num1 += "0"; // Adds 0 to num1
            dec1++; // Updates dec1
        }
        while (dec2 < dec1) { // Aligns decimal digits
            num2 += "0"; // Adds 0 to num2
            dec2++; // Updates dec2
        }
        int int1 = num1.indexOf('.'); // Finds decimal position in num1
        int int2 = num2.indexOf('.'); // Finds decimal position in num2
        while (int1 < int2) { // Aligns integer part
            num1 = "0" + num1; // Adds 0 to num1
            int1++; // Updates int1
        }
        while (int2 < int1) { // Aligns integer part
            num2 = "0" + num2; // Adds 0 to num2
            int2++; // Updates int2
        }
        String num1Comp = num1.replace(".", ""); // Removes decimal point
        String num2Comp = num2.replace(".", ""); // Removes decimal point
        boolean negative = false; // Sets result sign
        if (num1Comp.compareTo(num2Comp) < 0) { // Checks if num1 < num2
            negative = true; // Marks result as negative
            String temp = num1Comp; // Swaps numbers
            num1Comp = num2Comp; // Sets num1Comp to larger
            num2Comp = temp; // Sets num2Comp to smaller
        }
        AInteger a1 = new AInteger(num1Comp); // Creates AInteger for num1
        AInteger a2 = new AInteger(num2Comp); // Creates AInteger for num2
        String res = a1.subtract(a2).value; // Subtracts as integers
        while (res.length() < num1Comp.length()) { // Pads result with zeros
            res = "0" + res; // Adds leading 0
        }
        StringBuilder result = new StringBuilder(res); // Builds result
        int decimalPos = Math.max(dec1, dec2); // Finds decimal position
        if (decimalPos > 0) { // Adds decimal point if needed
            result.insert(result.length() - decimalPos, "."); // Inserts decimal
        }
        String output = result.toString(); // Gets result string
        int start = 0; // Starts index at 0
        while (start < output.length() - 1 && output.charAt(start) == '0' && output.charAt(start + 1) != '.') { // Removes leading zeros
            start++; // Skips leading 0
        }
        output = output.substring(start); // Updates output
        if (output.contains(".")) { // Cleans up decimal part
            while (output.endsWith("0")) output = output.substring(0, output.length() - 1); // Removes trailing zeros
            if (output.endsWith(".")) output = output.substring(0, output.length() - 1); // Removes trailing dot
        }
        if (output.isEmpty()) output = "0"; // Sets 0 if empty
        if (negative && !output.equals("0")) output = "-" + output; // Adds negative sign if needed
        return new AFloat(output); // Returns result as AFloat
    }

    public AFloat mul(AFloat other) { // Multiplies another AFloat
        String num1 = this.value; // Gets this number
        String num2 = other.value; // Gets other number
        boolean negative = false; // Sets result sign
        if (num1.startsWith("-")) { // Checks if num1 is negative
            negative = !negative; // Flips sign
            num1 = num1.substring(1); // Removes negative sign
        }
        if (num2.startsWith("-")) { // Checks if num2 is negative
            negative = !negative; // Flips sign
            num2 = num2.substring(1); // Removes negative sign
        }
        if (!num1.contains(".")) num1 += ".0"; // Adds .0 if no decimal
        if (!num2.contains(".")) num2 += ".0"; // Adds .0 if no decimal
        num1 = AInteger.removeZeroes(num1); // Removes leading zeros
        num2 = AInteger.removeZeroes(num2); // Removes leading zeros
        if (num1.equals("0") || num2.equals("0")) return new AFloat("0"); // Returns 0 if either is 0
        int dec1 = num1.length() - 1 - num1.indexOf('.'); // Counts decimal digits in num1
        int dec2 = num2.length() - 1 - num2.indexOf('.'); // Counts decimal digits in num2
        int totalDec = dec1 + dec2; // Sums decimal digits
        num1 = num1.replace(".", ""); // Removes decimal point
        num2 = num2.replace(".", ""); // Removes decimal point
        AInteger int1 = new AInteger(num1); // Creates AInteger for num1
        AInteger int2 = new AInteger(num2); // Creates AInteger for num2
        String resultRaw = int1.mul(int2).value; // Multiplies as integers
        while (resultRaw.length() <= totalDec) { // Pads result with zeros
            resultRaw = "0" + resultRaw; // Adds leading 0
        }
        StringBuilder resultBuilder = new StringBuilder(resultRaw); // Builds result
        if (totalDec > 0) { // Adds decimal point if needed
            resultBuilder.insert(resultBuilder.length() - totalDec, "."); // Inserts decimal
        }
        String result = resultBuilder.toString(); // Gets result string
        while (result.length() > 1 && result.charAt(0) == '0' && result.charAt(1) != '.') { // Removes leading zeros
            result = result.substring(1); // Skips leading 0
        }
        if (result.contains(".")) { // Cleans up decimal part
            while (result.endsWith("0")) result = result.substring(0, result.length() - 1); // Removes trailing zeros
            if (result.endsWith(".")) result = result.substring(0, result.length() - 1); // Removes trailing dot
        }
        if (result.isEmpty()) result = "0"; // Sets 0 if empty
        if (negative && !result.equals("0")) result = "-" + result; // Adds negative sign if needed
        return new AFloat(result); // Returns result as AFloat
    }

    public AFloat div(AFloat other) { // Divides by another AFloat
        String dividend = this.value; // Gets this number
        String divisor = other.value; // Gets other number
        boolean negative = false; // Sets result sign
        if (dividend.startsWith("-")) { // Checks if dividend is negative
            negative = !negative; // Flips sign
            dividend = dividend.substring(1); // Removes negative sign
        }
        if (divisor.startsWith("-")) { // Checks if divisor is negative
            negative = !negative; // Flips sign
            divisor = divisor.substring(1); // Removes negative sign
        }
        int firstDecimal = dividend.indexOf('.'); // Finds decimal in dividend
        int secondDecimal = divisor.indexOf('.'); // Finds decimal in divisor
        int decimalNum1 = (firstDecimal == -1) ? 0 : (dividend.length() - firstDecimal - 1); // Counts decimal digits in dividend
        int decimalNum2 = (secondDecimal == -1) ? 0 : (divisor.length() - secondDecimal - 1); // Counts decimal digits in divisor
        if (firstDecimal != -1) { // Removes decimal from dividend
            dividend = dividend.substring(0, firstDecimal) + dividend.substring(firstDecimal + 1); // Joins parts
        }
        if (secondDecimal != -1) { // Removes decimal from divisor
            divisor = divisor.substring(0, secondDecimal) + divisor.substring(secondDecimal + 1); // Joins parts
        }
        dividend = AInteger.removeZeroes(dividend); // Removes leading zeros
        divisor = AInteger.removeZeroes(divisor); // Removes leading zeros
        if (divisor.equals("0")) throw new ArithmeticException("Division by zero error"); // Checks for division by zero
        int shift = decimalNum2 - decimalNum1; // Calculates decimal shift
        StringBuilder result = new StringBuilder(); // Builds quotient
        String current = ""; // Starts empty working number
        for (int i = 0; i < dividend.length(); i++) { // Loops through dividend digits
            current += dividend.charAt(i); // Adds digit to working number
            current = AInteger.removeZeroes(current); // Removes leading zeros
            if (AInteger.compare(current, divisor) < 0) { // Checks if too small to divide
                result.append(result.length() == 0 ? "0" : "0"); // Adds 0 to quotient
                continue; // Skips to next digit
            }
            int count = 0; // Counts subtractions
            while (AInteger.compare(current, divisor) >= 0) { // Subtracts divisor
                current = new AFloat(current).subtract(new AFloat(divisor)).value; // Updates current
                count++; // Increments count
            }
            result.append(count); // Adds count to quotient
        }
        result.append('.'); // Adds decimal point
        int precision = 30; // Sets precision for decimal part
        while (precision > 0) { // Loops for decimal digits
            current += "0"; // Adds 0 to working number
            current = AInteger.removeZeroes(current); // Removes leading zeros
            if (AInteger.compare(current, divisor) < 0) { // Checks if too small
                result.append('0'); // Adds 0 to quotient
            } else {
                int count = 0; // Counts subtractions
                while (AInteger.compare(current, divisor) >= 0) { // Subtracts divisor
                    current = new AFloat(current).subtract(new AFloat(divisor)).value; // Updates current
                    count++; // Increments count
                }
                result.append(count); // Adds count to quotient
            }
            precision--; // Reduces precision
        }
        int decimalIndex = result.indexOf("."); // Finds decimal position
        result.deleteCharAt(decimalIndex); // Removes decimal point
        int newIndex = decimalIndex + shift; // Calculates new decimal position
        if (newIndex <= 0) { // Handles negative shift
            while (newIndex < 0) { // Adds leading zeros
                result.insert(0, '0'); // Inserts 0
                newIndex++; // Updates index
            }
            result.insert(0, "0."); // Adds 0. prefix
        } else {
            while (result.length() <= newIndex) { // Pads with zeros
                result.append('0'); // Adds 0
            }
            result.insert(newIndex, '.'); // Inserts decimal
        }
        String finalResult = result.toString(); // Gets final result
        int dotIndex = finalResult.indexOf('.'); // Finds decimal position
        if (dotIndex != -1) { // Cleans up decimal part
            int end = finalResult.length() - 1; // Starts at end
            while (end > dotIndex && finalResult.charAt(end) == '0') { // Removes trailing zeros
                end--; // Moves left
            }
            finalResult = finalResult.substring(0, end + 1); // Updates result
            if (finalResult.endsWith(".")) { // Removes trailing dot
                finalResult = finalResult.substring(0, finalResult.length() - 1); // Updates result
            }
        }
        finalResult = AInteger.removeZeroes(finalResult); // Removes leading zeros
        if (negative && !finalResult.equals("0")) { // Adds negative sign if needed
            finalResult = "-" + finalResult; // Adds negative sign
        }
        return new AFloat(finalResult); // Returns result as AFloat
    }
}