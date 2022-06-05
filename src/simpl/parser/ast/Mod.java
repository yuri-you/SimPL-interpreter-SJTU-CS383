package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;

public class Mod extends ArithExpr {

    public Mod(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " % " + r + ")";
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v1=l.eval(s),v2=r.eval(s);
        //From rule, we need to eval both side of the Add operator first

        if((v1 instanceof IntValue)&&(v2 instanceof IntValue)){
            if(((IntValue) v2).n!=0){
                return new IntValue (((IntValue) v1).n%((IntValue) v2).n);
            }
            else throw new RuntimeError("Mod by zero");
        }
        else throw new RuntimeError("One side of Mod operator is not an integer");
    }
}
