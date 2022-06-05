package simpl.interpreter.pcf;

import simpl.interpreter.BoolValue;
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

public class iszero extends FunValue {

    public iszero() {
        super(Env.empty, Symbol.symbol("x"), new Expr() {
            @Override public TypeResult typecheck(TypeEnv E) throws TypeError{
                //it will be never used, but the abstract class requires to define such function
                return null;
            }
            @Override public Value eval(State s) throws RuntimeError {
                Symbol x = Symbol.symbol("x");
                Value integer = s.E.get(x);
                if (integer instanceof IntValue) {
                    return new BoolValue(((IntValue) integer).equals(new IntValue(0)));
                }
                throw new RuntimeError("iszero input not a integer");
            }
        });
    }
}
