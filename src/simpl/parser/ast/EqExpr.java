package simpl.parser.ast;

// import simpl.typing.ListType;
// import simpl.typing.PairType;
// import simpl.typing.RefType;
import simpl.typing.Substitution;
import simpl.typing.Type;
import simpl.typing.TypeEnv;
import simpl.typing.TypeError;
import simpl.typing.TypeMismatchError;
import simpl.typing.TypeResult;
// import simpl.typing.TypeVar;

public abstract class EqExpr extends BinaryExpr {

    public EqExpr(Expr l, Expr r) {
        super(l, r);
    }

    @Override
    public TypeResult typecheck(TypeEnv E) throws TypeError {
        TypeResult left=this.l.typecheck(E);
        TypeResult right=this.r.typecheck(E);
        Substitution S=left.s.compose(right.s);
        Type typeleft=S.apply(left.t),typeright=S.apply(right.t);
        S=S.compose(typeleft.unify(typeright));
        typeleft=S.apply(typeleft);
        // typeright=S.apply(typeright);
        if(typeleft.isEqualityType()){
            return TypeResult.of(S,Type.BOOL);
        }
        else throw new TypeMismatchError();
    }
}
