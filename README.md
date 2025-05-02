ARBITARY ARITHMETIC LIBRARY

OVERVIEW

The Arbitrary Arithmetic Library is a Java-based library for performing arbitrary-precision arithmetic operations on integers and floating-point numbers. It includes two core classes: "AInteger" for integer arithmetic and "AFloat" for floating-point arithmetic. Supported operations include addition, subtraction, multiplication, and division, designed to handle large numbers beyond Java's built-in numeric types. The "MyInfArith" class provides a command-line interface to execute these operations based on user input.

This project was developed as part of a software development course to demonstrate arbitrary-precision arithmetic in Java.

PREREQUASITES

To build and run the project, ensure the following tools are installed:
- Java Development Kit (JDK): Version 8 or higher (e.g., OpenJDK or Oracle JDK).
- Apache Ant: For compiling the project using the "build.xml" file.
- Python 3: For running the "run.py" script to compile and execute the program.
- Git: For cloning the repository and managing version control.
- A command-line interface (e.g., Terminal on macOS/Linux, Git Bash or PowerShell on Windows).

Verify Installation:

java -version
ant -version
python3 --version
git --version


Installation
Follow these steps to set up the project:

Clone the Repository:
git clone <repository-url>
cd <repository-directory>

Replace <repository-url> with the GitHub Classroom repository URL.

Compile the Project:Use the Ant build script to compile the Java source files:
ant

This creates a build directory containing compiled .class files.

Clean Build (Optional):To remove compiled files and start fresh:
ant clean

Usage
The library can be used via the MyInfArith command-line driver or by integrating the AInteger and AFloat classes into your Java code.
Using the Command-Line Driver (MyInfArith)
The MyInfArith class performs arithmetic operations on integers or floating-point numbers via command-line arguments.
Syntax (Preferred Method):
python3 run.py <type> <operation> <num1> <num2>


<type>: int (for AInteger) or float (for AFloat).
<operation>: add, sub, mul, or div.
<num1>: First number (e.g.= "123", "-45.67").
<num2>: Second number.

Examples:

Integer addition:
python3 run.py int add 123 456

Output: 579

Integer division:
python3 run.py int div 132 22

Output: 6

Float subtraction:
python3 run.py float sub 45.67 -12.34

Output: 58.01

Float multiplication:
python3 run.py float mul 3.14 2.0

Output: 6.28


Alternative (Direct Java Command):After compiling, run directly:
java -cp build arbitraryarithmetic.MyInfArith int add 123 456

Output: 579
Notes:

The run.py script automatically compiles the project if the build directory is missing.
Division by zero outputs: Division by zero.
Invalid inputs (e.g., non-numeric strings) may cause exceptions, displayed as Error: <message>.

Using the Library in Code
To use AInteger or AFloat in your Java program:

Add the Library:Ensure the compiled .class files in the build directory are in your classpath, or include the source files in your project.

Example Code:
import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class Example {
    public static void main(String[] args) {
        // Integer arithmetic
        AInteger a = new AInteger("123");
        AInteger b = new AInteger("456");
        System.out.println("Sum: " + a.add(b)); // Output: 579
        System.out.println("Product: " + a.mul(b)); // Output: 56088

        // Float arithmetic
        AFloat x = new AFloat("3.14");
        AFloat y = new AFloat("2.0");
        System.out.println("Product: " + x.mul(y)); // Output: 6.28
        System.out.println("Difference: " + x.subtract(y)); // Output: 1.14
    }
}


Compile and Run:
javac -cp build Example.java
java -cp .:build Example

On Windows, use ; instead of : in the classpath.


Directory Structure
<repository-root>/
├── arbitraryarithmetic/        
│   ├── AInteger.java           
│   ├── AFloat.java             
│   └── MyInfArith.java         
├── build.xml                   
├── run.py                      
├── build/                      
└── README.md                  


Git Usage
The project uses Git for version control. To view commits and tags:
git log --oneline
git tag

Tags were assigned for major milestones (e.g., v1.0-initial, v1.3-final).
