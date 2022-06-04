package simpl.interpreter;

import simpl.parser.Symbol;

public class Env {

    private final Env E;
    private final Symbol x;
    private final Value v;

    private Env() {
        E = null;
        x = null;
        v = null;
    }

    public static Env empty = new Env() {
        public Value get(Symbol y) {
            return null;
        }

        public Env clone() {
            return this;
        }
    };

    public Env(Env E, Symbol x, Value v) {
        this.E = E;
        this.x = x;
        this.v = v;
    }

    public Value get(Symbol y)throws RuntimeError  {
        //标志在该环境
        if(this.x.toString().equals(y.toString()))return this.v;
        else{
            //标志不在该环境
            if(v==null)throw new RuntimeError("Free Variable");
            return this.E.get(y);
        }
    }

    public Env clone() {
        return new Env(E,x,v);
    }
    public Env add(Symbol x,Value v){
        return new Env(this,x,v);
    }
}
