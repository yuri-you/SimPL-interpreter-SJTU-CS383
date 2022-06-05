package simpl.parser.ast;

import simpl.interpreter.BoolValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Not extends UnaryExpr {

    public Not(Expr e) {
        super(e);
    }

    public String toString() {
        return "(not " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult ans=this.e.typecheck(E);
        Substitution S=ans.s.compose(ans.t.unify(Type.BOOL));
        return TypeResult.of(S,Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value ans=this.e.eval(s);
        if(ans instanceof BoolValue){
            return new BoolValue(!((BoolValue)ans).b);
        }
        throw new RuntimeError("Not input not a bool");
    }
}
