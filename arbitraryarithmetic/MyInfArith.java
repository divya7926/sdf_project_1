package arbitraryarithmetic;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: java MyInfArith <int/float> <add/sub/mul/div> <num1> <num2>");
            return;
        }
        
        String type = args[0].toLowerCase();
        String operation = args[1].toLowerCase();
        String num1 = args[2];
        String num2 = args[3];

        try {
            if (type.equals("int")) {
                AInteger a = new AInteger(num1);
                AInteger b = new AInteger(num2);
                AInteger result = null;

                switch (operation) {
                    case "add": result = a.add(b); break;
                    case "sub": result = a.subtract(b); break;
                    case "mul": result = a.mul(b); break;
                    case "div":
                        if (num2.equals("0")) {
                            System.out.println("Division by zero");
                            return;
                        }
                        result = a.div(b); break;
                    default:
                        System.out.println("Invalid operation");
                        return;
                }
                System.out.println(result.toString());
            } 
            else if (type.equals("float")) {
                AFloat a = new AFloat(num1);
                AFloat b = new AFloat(num2);
                AFloat result = null;

                switch (operation) {
                    case "add": result = a.add(b); break;
                    case "sub": result = a.subtract(b); break;
                    case "mul": result = a.mul(b); break;
                    case "div":
                        if (num2.equals("0") || num2.equals("0.0")) {
                            System.out.println("Division by zero");
                            return;
                        }
                        result = a.div(b); break; 
                    default:
                        System.out.println("Invalid operation");
                        return;
                }
                System.out.println(result.toString());

            } 
            else {
                System.out.println("Invalid type (must be 'int' or 'float')");
            }
        } 
        catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
