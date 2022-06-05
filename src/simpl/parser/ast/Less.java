package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Less extends RelExpr {

    public Less(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " < " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left=this.l.eval(s),right=this.r.eval(s);
        if((left instanceof IntValue)&&(right instanceof IntValue)){
            boolean b=((IntValue) left).n<((IntValue) right).n;
            return new BoolValue(b);
        }
        else throw new RuntimeError("Less input not int");
    }
}
