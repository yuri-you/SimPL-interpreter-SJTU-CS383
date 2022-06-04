package simpl.interpreter;

public class BoolValue extends Value {

    public final boolean b;

    public BoolValue(boolean b) {
        this.b = b;
    }

    public String toString() {
        return "" + b;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof BoolValue) return this.b==((BoolValue)other).b;
        else return false;
    }
}
