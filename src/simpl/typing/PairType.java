package simpl.typing;

public final class PairType extends Type {

    public Type t1, t2;

    public PairType(Type t1, Type t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    @Override
    public boolean isEqualityType() {
        return this.t1.isEqualityType()&&this.t2.isEqualityType();
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if(t instanceof TypeVar){
            return t.unify(this);
        }
        else{
            if(t instanceof PairType){
            Substitution left=this.t1.unify(((PairType) t).t1),right=this.t2.unify(((PairType) t).t2);
            return left.compose(right);
            }
            else throw new TypeMismatchError();
        }
    }

    @Override
    public boolean contains(TypeVar tv) {
        return this.t1.contains(tv)||this.t2.contains(tv);
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return new PairType(this.t1.replace(a, t), this.t2.replace(a, t));
    }

    public String toString() {
        return "(" + t1 + " * " + t2 + ")";
    }
}
