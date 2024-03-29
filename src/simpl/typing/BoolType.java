package simpl.typing;

final class BoolType extends Type {

    protected BoolType() {
    }

    @Override
    public boolean isEqualityType() {
        return true;
    }

    @Override
    public Substitution unify(Type t) throws TypeError {
        if(t instanceof BoolType){
            return Substitution.IDENTITY;
        }
        else{
            if(t instanceof TypeVar){
                return t.unify(this);
            }
            else throw new TypeMismatchError();
        }
    }

    @Override
    public boolean contains(TypeVar tv) {
        return false;
    }

    @Override
    public Type replace(TypeVar a, Type t) {
        return this;
    }

    public String toString() {
        return "bool";
    }
}
