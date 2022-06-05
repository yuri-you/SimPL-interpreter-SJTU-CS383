package simpl.interpreter.pcf;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class succ extends FunValue {

    public succ() {
        super(Env.empty, Symbol.symbol("x"), new Expr() {
            @Override public TypeResult typecheck(TypeEnv E) throws TypeError{
                //it will be never used, but the abstract class requires to define such function
                return null;
            }
            @Override public Value eval(State s) throws RuntimeError {
                Symbol x = Symbol.symbol("x");
                Value integer = s.E.get(x);
                if (integer instanceof IntValue) {
                    return new IntValue(((IntValue) integer).n+1);
                }
                throw new RuntimeError("succ input not a integer");
            }
        });
    }
}
