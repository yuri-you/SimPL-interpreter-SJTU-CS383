package simpl.interpreter.lib;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.parser.ast.Expr;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class fst extends FunValue {

    public fst() {
        super(Env.empty, Symbol.symbol("x"), new Expr() {
            @Override public TypeResult typecheck(TypeEnv E) throws TypeError{
                //it will be never used, but the abstract class requires to define such function
                return null;
            }
            @Override public Value eval(State s) throws RuntimeError {
                Symbol x = Symbol.symbol("x");
                Value pair = s.E.get(x);
                if (pair instanceof PairValue) {
                    return ((PairValue) pair).v1;
                }
                throw new RuntimeError("fst input not a pair");
            }
        });
    }
}
