package simpl.parser.ast;

import simpl.interpreter.PairValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Type;
import simpl.typing.PairType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Pair extends BinaryExpr {

    public Pair(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(pair " + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left = this.l.typecheck(E);
        TypeResult right = this.r.typecheck(E);
        Substitution S = left.s.compose(right.s);
        Type typeleft=S.apply(left.t),typeright=S.apply(right.t);
        return TypeResult.of(S, new PairType(typeleft,typeright));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        return new PairValue(this.l.eval(s), this.r.eval(s));
    }
}
