package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Ref extends UnaryExpr {

    public Ref(Expr e) {
        super(e);
    }

    public String toString() {
        return "(ref " + e + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult ans=this.e.typecheck(E);
        return TypeResult.of(ans.s,new RefType(ans.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        int p1 = s.p.get();
        s.p.set(p1+1);
        Value v = e.eval(s);
        s.M.put(p1, v);
        return new RefValue(p1);
    }
}
