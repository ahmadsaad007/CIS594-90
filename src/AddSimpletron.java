import java.util.Scanner;

public class AddSimpletron {
    private int[] memory = new int[100];
    private int accumulator = 0;
    private int instructionCounter = 0;
    private int instructionRegister = 0;
    private int operationCode = 0;
    private int operand = 0;
    private Scanner scanner = new Scanner(System.in);

    public void loadProgram() {
        memory[0] = 1009; // READ into memory[9]
        memory[1] = 1010; // READ into memory[10]
        memory[2] = 2009; // LOAD from memory[9]
        memory[3] = 3010; // ADD memory[10] to accumulator
        memory[4] = 2111; // STORE result in memory[11]
        memory[5] = 1111; // WRITE memory[11]
        memory[6] = 4300; // HALT
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
                default:
                    System.out.println("Error: Invalid operation code");
                    return;
            }
            instructionCounter++;
        }
    }

    public static void main(String[] args) {
        AddSimpletron addSimpletron = new AddSimpletron();
        addSimpletron.loadProgram();
        addSimpletron.execute();
    }
}
