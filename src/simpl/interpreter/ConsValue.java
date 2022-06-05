package simpl.interpreter;

// import simpl.parser.ast.Nil;

public class ConsValue extends Value {

    public final Value v1, v2;

    public ConsValue(Value v1, Value v2) {
        this.v1 = v1;
        this.v2 = v2;
    }

    public String toString() {
        return "@list"+this.length();//recursive count the length
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof ConsValue)return (this.v1.equals(((ConsValue) other).v1))&&(this.v2.equals(((ConsValue) other).v2));
        else return false;
    }

    private int length(){
        if(v2 instanceof NilValue)return 1;
        else return ((ConsValue) v2).length();
    }
}
