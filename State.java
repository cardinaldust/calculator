import java.util.Stack;

public class State {
    private Stack<Double> stack;
    private double accumulator;
    private String currentOperator;

    public State() {
        stack = new Stack<>();
        accumulator = 0.0;
        currentOperator = null;
    }

    public void inputNumber(double number) throws Exception{
        if (currentOperator == null) {
            accumulator = number;
        } else {
            stack.push(accumulator);
            stack.push(number);
            calculate();
        }
    }

    public void inputOperator(String operator) throws Exception{
        if (currentOperator != null) {
            calculate();
        }
        currentOperator = operator;
    }

    public double getResult() {
        return accumulator;
    }

    public void reset() {
        stack.clear();
        accumulator = 0.0;
        currentOperator = null;
    }

    private void calculate() throws Exception{
        if (stack.size() < 2 || currentOperator == null) return;
        double b = stack.pop();
        double a = stack.pop();

        switch (currentOperator) {
            case "+":
                accumulator = a + b;
                break;
            case "-":
                accumulator = a - b;
                break;
            case "*":
                accumulator = a * b;
                break;
            case "/":
                accumulator = a / b;
                break;
        }
        currentOperator = null;
    }
}
