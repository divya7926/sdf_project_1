package arbitraryarithmetic; // Sets package name for this class

public class MyInfArith {
    public static void main(String[] args) { // Main method to run arithmetic operations
        if (args.length != 4) { // Checks if exactly 4 arguments are provided
            System.out.println("Usage: java MyInfArith <int/float> <add/sub/mul/div> <num1> <num2>");
            return;
        }
        
        String type = args[0].toLowerCase();
        String operation = args[1].toLowerCase();
        String num1 = args[2];
        String num2 = args[3];

        try {
            if (type.equals("int")) { // Handles integer operations
                AInteger a = new AInteger(num1);
                AInteger b = new AInteger(num2);
                AInteger result = null;

                switch (operation) {
                    case "add": result = a.add(b); // Adds two integers
                        break;
                    case "sub": result = a.subtract(b); // Subtracts second from first
                        break;
                    case "mul": result = a.mul(b); // Multiplies two integers
                        break;
                    case "div":
                        if (num2.equals("0")) { // Checks for division by zero
                            System.out.println("Division by zero");
                            return;
                        }
                        result = a.div(b); // Divides first by second
                        break;
                    default:
                        System.out.println("Invalid operation"); // Handles invalid operation
                        return;
                }
                System.out.println(result.toString());
            } 
            else if (type.equals("float")) { // Handles floating-point operations
                AFloat a = new AFloat(num1);
                AFloat b = new AFloat(num2);
                AFloat result = null;

                switch (operation) {
                    case "add": result = a.add(b); // Adds two floats
                        break;
                    case "sub": result = a.subtract(b); // Subtracts second from first
                        break;
                    case "mul": result = a.mul(b); // Multiplies two floats
                        break;
                    case "div":
                        if (num2.equals("0") || num2.equals("0.0")) { // Checks for division by zero
                            System.out.println("Division by zero error");
                            return;
                        }
                        result = a.div(b); // Divides first by second
                        break; 
                    default:
                        System.out.println("Invalid operation"); // Handles invalid operation
                        return;
                }
                System.out.println(result.toString());
            } 
            else {
                System.out.println("Invalid type (must be 'int' or 'float')"); // Handles invalid type
            }
        } 
        catch (Exception e) { // Catches errors
            System.out.println("Error: " + e.getMessage());
        }
    }
}