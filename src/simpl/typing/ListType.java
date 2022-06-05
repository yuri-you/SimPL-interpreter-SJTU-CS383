package simpl.typing;

public final class ListType extends Type {

    public Type t;

    public ListType(Type t) {
        this.t = t;
    }

    @Override
    public boolean isEqualityType() {
        return t.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if(t instanceof TypeVar){
            return t.unify(this);
        }
        else{
            if(t instanceof ListType){
            return this.t.unify(((ListType) t).t);
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
        this.t=this.t.replace(a, t);
        return this;
    }

    public String toString() {
        return t + " list";
    }
}
