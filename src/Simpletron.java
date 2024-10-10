import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Simpletron {
    private final int[] memory = new int[100];
    private int accumulator = 0;
    private int instructionCounter = 0;
    private int instructionRegister = 0;
    private int operationCode = 0;
    private int operand = 0;
    private final Scanner scanner = new Scanner(System.in);

    public void loadProgramFromFile(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            int lineNumber = 0;
            while (fileScanner.hasNextInt() && lineNumber < 100) {
                memory[lineNumber] = fileScanner.nextInt();
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
        }
    }

    public void memoryDump() {
        System.out.println("Memory Dump:");
        for (int i = 0; i < memory.length; i++) {
            System.out.printf("%02d: %04d\n", i, memory[i]);
        }
    }

    public void execute() {
        while (true) {
            instructionRegister = memory[instructionCounter];
            operationCode = instructionRegister / 100;
            operand = instructionRegister % 100;

            switch (operationCode) {
                case 10:  // READ
                    System.out.print("Enter an integer: ");
                    memory[operand] = scanner.nextInt();
                    break;
                case 11:  // WRITE
                    System.out.println("Value at location " + operand + ": " + memory[operand]);
                    break;
                case 20:  // LOAD
                    accumulator = memory[operand];
                    break;
                case 21:  // STORE
                    memory[operand] = accumulator;
                    break;
                case 30:  // ADD
                    accumulator += memory[operand];
                    break;
                case 31:  // SUBTRACT
                    accumulator -= memory[operand];
                    break;
                case 32:  // DIVIDE
                    if (memory[operand] != 0) {
                        accumulator /= memory[operand];
                    } else {
                        System.out.println("Error: Division by zero");
                        return;
                    }
                    break;
                case 33:  // MULTIPLY
                    accumulator *= memory[operand];
                    break;
                case 43:  // HALT
                    System.out.println("Program halted.");
                    return;
                case 50:  // REMAINDER
                    accumulator %= memory[operand];
                    break;
                case 51:  // EXPONENTIATION
                    accumulator = (int) Math.pow(accumulator, memory[operand]);
                    break;
                case 52:  // NEWLINE
                    System.out.println();
                    break;
                default:
                    System.out.println("Error: Invalid operation code at " + instructionCounter);
                    return;
            }

            // Ensure accumulator stays within bounds
            if (accumulator < -9999 || accumulator > 9999) {
                System.out.println("Error: Accumulator overflow at operation code " + operationCode);
                return;
            }

            instructionCounter++;
        }
    }


    public static void main(String[] args) {
        Simpletron simpletron = new Simpletron();
        simpletron.loadProgramFromFile("src/program.txt");
        simpletron.execute();
        simpletron.memoryDump();
    }
}
