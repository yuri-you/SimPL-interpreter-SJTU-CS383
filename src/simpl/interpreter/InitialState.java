package simpl.interpreter;

import static simpl.parser.Symbol.symbol;
import simpl.interpreter.lib.*;
import simpl.interpreter.pcf.*;
// import simpl.interpreter.lib.tl;
// import simpl.interpreter.lib.fst;
// import simpl.interpreter.lib.snd;
// import simpl.interpreter.pcf.iszero;
// import simpl.interpreter.pcf.pred;
// import simpl.interpreter.pcf.succ;

public class InitialState extends State {

    public InitialState() {
        super(initialEnv(Env.empty), new Mem(), new Int(0));
    }

    private static Env initialEnv(Env E) {
        Env ans=new Env(E,symbol("fst"),new fst());
        ans=ans.add(symbol("hd"), new hd());
        ans=ans.add(symbol("snd"), new snd());
        ans=ans.add(symbol("tl"), new tl());
        ans=ans.add(symbol("iszero"), new iszero());
        ans=ans.add(symbol("pred"), new pred());
        ans=ans.add(symbol("succ"), new succ());
        return ans;
    }
}
