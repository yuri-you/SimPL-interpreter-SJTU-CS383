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

public class Loop extends Expr {

    public Expr e1, e2;

    public Loop(Expr e1, Expr e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public String toString() {
        return "(while " + e1 + " do " + e2 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.e1.typecheck(E),right=this.e2.typecheck(E);
        Substitution S=left.s.compose(right.s);
        S=S.compose(left.t.unify(Type.BOOL)).compose(right.t.unify(Type.UNIT));
        return TypeResult.of(S,Type.UNIT);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left=this.e1.eval(s);
        if(left instanceof BoolValue){
            if(((BoolValue)left).b){
                return new Seq(this.e2,this).eval(s);
            }
            else{
                return Value.UNIT;
            }
        }
        else throw new RuntimeError("Loop left input not a bool");
    }
}
