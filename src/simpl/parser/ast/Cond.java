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

public class Cond extends Expr {

    public Expr e1, e2, e3;

    public Cond(Expr e1, Expr e2, Expr e3) {
        this.e1 = e1;
        this.e2 = e2;
        this.e3 = e3;
    }

    public String toString() {
        return "(if " + e1 + " then " + e2 + " else " + e3 + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult _if=this.e1.typecheck(E),_then=this.e2.typecheck(E),_else=this.e3.typecheck(E);
        Substitution S=_if.s.compose(_if.t.unify(Type.BOOL));
        S=S.compose(_then.s).compose(_else.s);
        Type typethen=S.apply(_then.t),typeelse=S.apply(_else.t);
        S=S.compose(typethen.unify(typeelse));
        Type ans=S.apply(typethen);
        return TypeResult.of(S,ans);
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value  _if=this.e1.eval(s);
        if(_if instanceof BoolValue){
            if(((BoolValue)_if).b){
                return this.e2.eval(s);
            }
            else{
                return this.e3.eval(s);
            }
        }
        else throw new RuntimeError("Condition input left not a boolen");
    }
}
