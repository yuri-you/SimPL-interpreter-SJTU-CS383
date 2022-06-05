package simpl.parser.ast;

// import java.util.concurrent.Flow.Subscriber;

import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Fn extends Expr {

    public Symbol x;
    public Expr e;

    public Fn(Symbol x, Expr e) {
        this.x = x;
        this.e = e;
    }

    public String toString() {
        return "(fn " + x + "." + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeVar newvar=new TypeVar(true);
        TypeResult right=this.e.typecheck(TypeEnv.of(E,x,newvar));
        Substitution S=right.s;
        Type left=S.apply(newvar);
        return TypeResult.of(S,new ArrowType(left, right.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new FunValue(s.E,this.x,this.e);
    }
}
