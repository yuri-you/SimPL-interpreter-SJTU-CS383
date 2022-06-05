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

public class AndAlso extends BinaryExpr {

    public AndAlso(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " andalso " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.l.typecheck(E);
        TypeResult right=this.r.typecheck(E);
        Substitution S=left.s.compose(right.s);
        Type typeleft=S.apply(left.t);
        Type typeright=S.apply(right.t);
        S=S.compose(typeleft.unify(Type.BOOL));
        S=S.compose(typeright.unify(Type.BOOL));
        return TypeResult.of(S,Type.BOOL);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left=this.l.eval(s),right=this.r.eval(s);
        if((left instanceof BoolValue)&&(right instanceof BoolValue)){
            return new BoolValue(((BoolValue)left).b&&((BoolValue)right).b);
        }
        else{
            throw new RuntimeError("AndAlso Input not Bool");
        }
    }
}
