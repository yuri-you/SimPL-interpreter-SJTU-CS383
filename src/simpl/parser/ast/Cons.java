package simpl.parser.ast;

import simpl.interpreter.ConsValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.Type;
import simpl.typing.ListType;
import simpl.typing.Substitution;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeResult;

public class Cons extends BinaryExpr {

    public Cons(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " :: " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.l.typecheck(E);
        TypeResult right=this.r.typecheck(E);
        Substitution S=left.s.compose(right.s);
        Type typeleft=S.apply(left.t),typeright=S.apply(right.t);
        S=S.compose((new ListType(typeleft)).unify(typeright));
        return TypeResult.of(S,S.apply(typeright));
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left=l.eval(s),right=r.eval(s);
        if(right instanceof ConsValue){
            return new ConsValue(left, right);
        }
        else throw new RuntimeError("Cons right input not a list");
    }
}
