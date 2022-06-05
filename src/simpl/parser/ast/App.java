package simpl.parser.ast;

import simpl.interpreter.Env;
import simpl.interpreter.FunValue;
import simpl.interpreter.RuntimeError;
import simpl.interpreter.State;
import simpl.interpreter.Value;
import simpl.parser.Symbol;
import simpl.typing.ArrowType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;
import simpl.typing.TypeVar;

public class App extends BinaryExpr {

    public App(Expr l, Expr r) {
        super(l, r);
    }

    public String toString() {
        return "(" + l + " " + r + ")";
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.l.typecheck(E);
        TypeResult right=this.r.typecheck(E);
        Substitution S=left.s.compose(right.s);
        Type typeleft=S.apply(left.t),typeright=S.apply(right.t);
        if(typeleft instanceof ArrowType){
            S=S.compose(typeright.unify(((ArrowType)typeleft).t1));
            return TypeResult.of(S, S.apply(((ArrowType)typeleft).t2));
        }
        else{
            if(typeleft instanceof TypeVar){
                TypeVar newvar=new TypeVar(true);
                S=S.compose(typeleft.unify(new ArrowType(typeright, newvar)));
                return TypeResult.of(S,S.apply(newvar));
            }
            throw new TypeMismatchError();
        }
    }

    @Override
    public Value eval(State s) throws RuntimeError {
        Value left=this.l.eval(s);
        if(left instanceof FunValue){
            Value right=r.eval(s);
            FunValue function=(FunValue) left;
            return function.e.eval(State.of(new Env(function.E, function.x,right), s.M, s.p));
        }
        else throw new RuntimeError("Apply input left not a function");
    }
}
