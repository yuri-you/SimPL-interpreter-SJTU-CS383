package simpl.parser.ast;

import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Seq extends BinaryExpr {

    public Seq(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " ; " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.l.typecheck(E),right=this.r.typecheck(E);
        Substitution S=left.s.compose(right.s).compose(left.t.unify(Type.UNIT));
        return TypeResult.of(S, S.apply(right.t));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        l.eval(s);
        return r.eval(s);
    }
}
