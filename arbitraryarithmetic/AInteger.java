```java
package arbitraryarithmetic; // Sets package name for this class

public class AInteger {
    protected String value; // Stores number as string

    public AInteger() {
        this.value = "0";
    }

    public AInteger(String value) {
        this.value = value;
    }

    public AInteger(AInteger other) {
        this.value = other.value;
    }

    public AInteger parse(String value) { // Creates AInteger from string
        return new AInteger(value);
    }
    
    @Override
    public String toString() {
        return this.value;
    }

    public static int compare(String num1, String num2) { // Compares two number strings
        num1 = removeZeroes(num1); // Removes leading zeros from num1
        num2 = removeZeroes(num2); // Removes leading zeros from num2
        if (num1.length() != num2.length()) return num1.length() - num2.length(); // Compares lengths
        return num1.compareTo(num2); // Compares strings if lengths equal
    }

    public static String removeZeroes(String value) { // Removes leading zeros
        int i = 0;
        while (i < value.length() - 1 && value.charAt(i) == '0') i++; // Skips leading zeros
        return value.substring(i);
    }

    public AInteger add(AInteger other) { // Adds another AInteger
        String num1 = this.value;
        String num2 = other.value;
        boolean negative1 = num1.charAt(0) == '-'; // Checks if num1 is negative
        boolean negative2 = num2.charAt(0) == '-'; // Checks if num2 is negative
        if (negative1) num1 = num1.substring(1); // Removes negative sign from num1
        if (negative2) num2 = num2.substring(1); // Removes negative sign from num2

        if (negative1 && negative2) { // Both negative: add and add negative sign
            return new AInteger("-" + new AInteger(num1).add(new AInteger(num2)).value);
        } else if (!negative1 && negative2) { // Positive minus negative: subtract
            return this.subtract(new AInteger(num2));
        } else if (negative1 && !negative2) { // Negative plus positive: swap order
            return other.add(this);
        }

        int len1 = num1.length();
        int len2 = num2.length();
        StringBuilder answer = new StringBuilder();
        int i = len1 - 1;
        int j = len2 - 1;
        int carry = 0;
        while (i >= 0 || j >= 0 || carry > 0) { // Loops until no digits or carry
            int digitOf1 = (i >= 0) ? num1.charAt(i) - '0' : 0; // Gets digit from num1 or 0
            int digitOf2 = (j >= 0) ? num2.charAt(j) - '0' : 0; // Gets digit from num2 or 0
            int sum = digitOf1 + digitOf2 + carry; // Adds digits and carry
            answer.append(sum % 10); // Adds last digit to result
            carry = sum / 10; // Updates carry for next digit
            i--;
            j--;
        }

        answer = new StringBuilder(removeZeroes(answer.reverse().toString())); // Fixes order, removes zeros
        return new AInteger(answer.toString());
    }

    public AInteger subtract(AInteger other) { // Subtracts another AInteger
        String num1 = this.value;
        String num2 = other.value;

        boolean negative1 = num1.charAt(0) == '-'; // Checks if num1 is negative
        boolean negative2 = num2.charAt(0) == '-'; // Checks if num2 is negative

        if (negative1) num1 = num1.substring(1); // Removes negative sign from num1
        if (negative2) num2 = num2.substring(1); // Removes negative sign from num2

        if (!negative1 && negative2) { // Positive minus negative: add
            return this.add(new AInteger(num2));
        } else if (negative1 && !negative2) { // Negative minus positive: negate sum
            AInteger sum = new AInteger(num1).add(new AInteger(num2));
            return new AInteger("-" + sum.value);
        } else if (negative1 && negative2) { // Both negative: swap subtraction
            return new AInteger(num2).subtract(new AInteger(num1));
        }

        boolean negative = false;

        if (num1.length() < num2.length() || (num1.length() == num2.length() && num1.compareTo(num2) < 0)) { // Checks if num1 < num2
            String temp = num1;
            num1 = num2;
            num2 = temp;
            negative = true; // Marks result as negative
        }

        int len1 = num1.length();
        int len2 = num2.length();
        StringBuilder answer = new StringBuilder();
        int carry = 0;
        int i = len1 - 1;
        int j = len2 - 1;

        while (i >= 0 || j >= 0) { // Loops until no digits
            int digit1 = (i >= 0) ? num1.charAt(i) - '0' : 0; // Gets digit from num1 or 0
            digit1 += carry; // Adds borrow to digit1
            int digit2 = (j >= 0) ? num2.charAt(j) - '0' : 0; // Gets digit from num2 or 0
            if (digit1 < digit2) { // Checks if borrowing needed
                digit1 += 10; // Borrows 10 for subtraction
                carry = -1; // Sets borrow for next digit
            } else {
                carry = 0; // No borrow needed
            }
            answer.append(digit1 - digit2); // Adds difference to result
            i--;
            j--;
        }

        String string = removeZeroes(answer.reverse().toString()); // Fixes order, removes zeros
        if (negative) string = "-" + string; // Adds negative sign if needed
        if (string.equals("0") || string.equals("-0")) string = "0"; // Sets 0 for zero results

        return new AInteger(string);
    }

    public AInteger mul(AInteger other) { // Multiplies another AInteger
        String num1 = this.value;
        String num2 = other.value;
        boolean negative = false;

        if (num1.charAt(0) == '-') { // Checks if num1 is negative
            negative = !negative; // Flips sign (negative if one is negative)
            num1 = num1.substring(1);
        }
        if (num2.charAt(0) == '-') { // Checks if num2 is negative
            negative = !negative; // Flips sign (positive if both negative)
            num2 = num2.substring(1);
        }
        if (num1.equals("0") || num2.equals("0")) return new AInteger(); // Returns 0 if either is 0

        String output = "0";
        int len1 = num1.length();
        int len2 = num2.length();
        int i = len1 - 1;

        while (i >= 0) { // Loops through num1 digits
            StringBuilder sub = new StringBuilder();
            int carry = 0;

            for (int j = len2 - 1; j >= 0; j--) { // Loops through num2 digits
                int sum = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + carry; // Multiplies digits
                sub.append(sum % 10); // Adds last digit to partial product
                carry = sum / 10; // Updates carry
            }
            if (carry != 0) { // Checks for remaining carry
                sub.append(carry);
            }
            sub.reverse(); // Fixes digit order
            for (int z = 0; z < len1 - 1 - i; z++) sub.append('0'); // Adds zeros for place value
            AInteger a = new AInteger(output);
            output = a.add(new AInteger(sub.toString())).value; // Adds partial product
            i--;
        }
        output = removeZeroes(output); // Removes leading zeros
        if (output.equals("0")) negative = false; // No negative zero
        String finalStr = negative ? "-" + output : output; // Adds negative sign if needed
        return new AInteger(finalStr);
    }

    public AInteger div(AInteger other) { // Divides by another AInteger
        String dividend = this.value;
        String divisor = other.value;

        if (divisor.equals("0") || divisor.equals("-0")) { // Checks for division by zero
            return new AInteger(""); // Returns empty AInteger for error
        }

        boolean negative = false;
        if (dividend.charAt(0) == '-') { // Checks if dividend is negative
            negative = !negative; // Flips sign
            dividend = dividend.substring(1);
        }
        if (divisor.charAt(0) == '-') { // Checks if divisor is negative
            negative = !negative; // Flips sign
            divisor = divisor.substring(1);
        }
        dividend = removeZeroes(dividend); // Removes leading zeros from dividend
        int m = 0;
        while (m < divisor.length() - 1 && divisor.charAt(m) == '0') m++; // Skips leading zeros
        divisor = divisor.substring(m);

        StringBuilder result = new StringBuilder();
        String current = "";

        for (int i = 0; i < dividend.length(); i++) { // Loops through dividend digits
            current += dividend.charAt(i); // Adds digit to working number
            int j = 0;
            while (j < current.length() - 1 && current.charAt(j) == '0') j++; // Skips leading zeros
            current = current.substring(j);

            int count = 0;
            while (compare(current, divisor) >= 0) { // Checks if can subtract
                AInteger a = new AInteger(current);
                current = a.subtract(new AInteger(divisor)).value; // Subtracts divisor
                count++; // Increments count
            }

            result.append(count); // Adds count to quotient
        }

        String quotient = result.toString();
        if (quotient.isEmpty()) quotient = "0"; // Sets 0 if quotient empty
        else {
            int n = 0;
            while (n < quotient.length() - 1 && quotient.charAt(n) == '0') n++; // Skips leading zeros
            quotient = quotient.substring(n);
        }

        if (quotient.equals("0")) negative = false; // No negative zero

        return new AInteger(negative ? "-" + quotient : quotient);
    }
}