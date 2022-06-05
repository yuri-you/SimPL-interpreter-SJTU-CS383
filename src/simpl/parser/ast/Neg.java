package simpl.parser.ast;

import simpl.interpreter.IntValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Neg extends UnaryExpr {

    public Neg(Expr e) {
        super(e);
    }

    public String toString() {
        return "~" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult ans=this.e.typecheck(E);
        Substitution S=ans.s.compose(ans.t.unify(Type.INT));
        return TypeResult.of(S, Type.INT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value ans=this.e.eval(s);
        if(ans instanceof  IntValue){
            return new IntValue(-((IntValue)ans).n);
        }
        else throw new RuntimeError("Neg input not a int");
    }
}
