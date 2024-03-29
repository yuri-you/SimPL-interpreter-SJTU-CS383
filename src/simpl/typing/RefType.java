package simpl.typing;

// import java.sql.Ref;

public final class RefType extends Type {

    public Type t;

    public RefType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        return this.t.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if(t instanceof TypeVar){
            return t.unify(this);
        }
        else{
            if(t instanceof RefType){
            return this.t.unify(((RefType)t).t);
            }
            else throw new TypeMismatchError();
        }
    }

    @Override
    public boolean contains(TypeVar tv) {
        return this.t.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return new RefType(this.t.replace(a, t));
    }

    public String toString() {
        return t + " ref";
    }
}
