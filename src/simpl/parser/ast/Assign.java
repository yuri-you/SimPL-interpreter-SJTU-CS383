package simpl.parser.ast;

// import javax.swing.text.DefaultStyledDocument.ElementSpec;

import simpl.interpreter.RefValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class Assign extends BinaryExpr {

    public Assign(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return l + " := " + r;
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.l.typecheck(E);
        TypeResult right=this.r.typecheck(E);
        Substitution S=left.s.compose(right.s);
        Type typeleft=S.apply(left.t),typeright=S.apply(right.t);
        if(typeleft instanceof RefType){
            Type ans=((RefType) typeleft).t;
            S=S.compose(typeright.unify(ans));
            return TypeResult.of(S,Type.UNIT);
        }
        else{
            if(typeleft instanceof  TypeVar){
                TypeVar newvar=new TypeVar(true);
                S=S.compose(typeleft.unify(new RefType(newvar)));
                Type ans=S.apply(newvar);
                S=S.compose(typeright.unify(ans));
                return TypeResult.of(S,Type.UNIT);
            }
            else throw new TypeMismatchError();
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left=l.eval(s);
        if(left instanceof RefValue){
            Value right=r.eval(s);
            s.M.put(((RefValue) left).p,right);
            return Value.UNIT;
        }
        else throw new RuntimeError("Assign left input not a Ref");
    }
}
