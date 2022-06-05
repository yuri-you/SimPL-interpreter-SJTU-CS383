package simpl.parser.ast;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
// import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Deref extends UnaryExpr {

    public Deref(Expr e) {
        super(e);
    }

    public String toString() {
        return "!" + e;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.e.typecheck(E);
        if(left.t instanceof RefType){
            return TypeResult.of(left.s,((RefType)left.t).t);
        }
        else{
            if(left.t instanceof TypeVar){
                Substitution S=left.s;
                TypeVar newvar=new TypeVar(true);
                S=S.compose(left.t.unify(new RefType(newvar)));
                return TypeResult.of(S,S.apply(newvar));
            }
            else throw new TypeMismatchError();
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left=this.e.eval(s);
        if(left instanceof RefValue){
            return s.M.get(((RefValue) left).p);
        }
        else throw new RuntimeError("Deref input not a Ref");
    }
}
