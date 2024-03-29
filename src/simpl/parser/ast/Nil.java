package simpl.parser.ast;

// import java.lang.ProcessBuilder.Redirect.Type;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.ListType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Nil extends Expr {

    public String toString() {
        return "nil";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        return TypeResult.of(new ListType(new TypeVar(true)));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return Value.NIL;
    }
}
