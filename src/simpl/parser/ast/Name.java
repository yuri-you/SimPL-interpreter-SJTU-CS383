package simpl.parser.ast;

import simpl.interpreter.RecValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;

public class Name extends Expr {

    public Symbol x;

    public Name(Symbol x) {
        this.x = x;
    }

    public String toString() {
        return "" + x;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        Type v=E.get(this.x);
        if(v!=null){
            return TypeResult.of(v);
        }
        else{
            throw new TypeMismatchError();
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value v=s.E.get(x);
        if(v!=null){
            if(v instanceof RecValue){
                Rec ans=new Rec(x,((RecValue)v).e);
                return ans.eval(State.of(((RecValue)v).E, s.M, s.p));
            }
            else return v;
        }
        else throw new RuntimeError("No such Name");
    }
}
