import java.io.*;
public class Evaluate
{
    public static void main(String[] args) throws IOException
    {
        System.setIn(new FileInputStream(new File("expression.txt")));
        Stack<String> ops = new Stack<String>();
        Stack<Double> vals = new Stack<Double>();
        while (!StdIn.isEmpty()) 
        { // Read token, push if operator.
            String s = StdIn.readString();
            if (s.equals("(")) ops.push(s);
            else if (s.equals("+")) ops.push(s);
            else if (s.equals("-")) ops.push(s);
            else if (s.equals("*")) ops.push(s);
            else if (s.equals("/")) ops.push(s);
            else if (s.equals("sqrt")) ops.push(s);
            else if (s.equals(")")) 
            { // Pop, evaluate, and push result if token is ")".
                double v = vals.pop();
                while(true){
                    String op = ops.pop();
                    if (op.equals("+")) v = vals.pop() + v;
                    else if (op.equals("-")) v = vals.pop() - v;
                    else if (op.equals("*")) v = vals.pop() * v;
                    else if (op.equals("/")) v = vals.pop() / v;
                    // Bo sung phep khai can  ......    
                    else if(op.equals("sqrt")) v = Math.sqrt(v);
                    else if (op.equals("(")) break;
                }
                while(!ops.isEmpty()){
                    String op = ops.pop();
                    if (op.equals("*")) v = vals.pop() * v;
                    else if (op.equals("/")) v = vals.pop() / v;
                    else if (op.equals("sqrt")) v = Math.sqrt(v);
                    else {
                        ops.push(op);
                        break;
                    }
                }
                vals.push(v);
            } // Token not operator or paren: push double value.
            else {
                double v = Double.parseDouble(s);
                while(!ops.isEmpty()){
                    String op = ops.pop();
                    if (op.equals("*")) v = vals.pop() * v;
                    else if (op.equals("/")) v = vals.pop() / v;
                    else if (op.equals("sqrt")) v = Math.sqrt(v);
                    else {
                        ops.push(op);
                        break;
                    }
                }
                vals.push(v);
            }
        }
        StdOut.println(vals.pop());
    }
}
