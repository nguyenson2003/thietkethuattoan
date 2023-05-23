 

public class Division implements Expression{

    private final Expression leftExpression;
    private final Expression rightExpression;

    public Division(Expression leftExpression,Expression rightExpression ){
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }
    @Override
    public double interpret() {
        return leftExpression.interpret()*1.0 / rightExpression.interpret();
    }
}
