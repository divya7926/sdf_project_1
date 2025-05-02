import sys
import os
import subprocess

def compile_run(args):
    # Debug message: check if build directory exists
    print("Checking if 'build' directory exists...")
    
    if not os.path.exists("build"):
        print("'build' directory not found. Running 'ant' to compile...")
        subprocess.run(["ant"])
    else:
        print("'build' directory found. Skipping 'ant'.")

    # Debug message: running the java command
    print(f"Running Java with classpath 'build' and arguments: {'arbitraryarithmetic.MyInfArith'} + {args}")
    
    result = subprocess.run(["java", "-cp", "build", "arbitraryarithmetic.MyInfArith"] + args, capture_output=True)
    
    # Debug: output of the Java command
    print("Java output:", result.stdout.decode())
    print("Java error (if any):", result.stderr.decode())
    
# Run the compile_run function with command line arguments
compile_run(sys.argv[1:])
