package simpl.typing;

import simpl.parser.Symbol;

public class DefaultTypeEnv extends TypeEnv {

    private TypeEnv E;

    public DefaultTypeEnv() {
        TypeVar env1 = new TypeVar(true), env2 = new TypeVar(true), env3 = new TypeVar(true), env4 = new TypeVar(true), 
            env5 = new TypeVar(true), env6 = new TypeVar(true);
        E = TypeEnv.empty;
        E = TypeEnv.of(E, Symbol.symbol("iszero"), new ArrowType(Type.INT, Type.BOOL));
        E = TypeEnv.of(E, Symbol.symbol("pred"), new ArrowType(Type.INT, Type.INT));
        E = TypeEnv.of(E, Symbol.symbol("succ"), new ArrowType(Type.INT, Type.INT));
        E = TypeEnv.of(E, Symbol.symbol("fst"), new ArrowType(new PairType(env1,env2),env1));
        E = TypeEnv.of(E, Symbol.symbol("snd"), new ArrowType(new PairType(env3,env4),env4));
        E = TypeEnv.of(E, Symbol.symbol("hd"), new ArrowType(new ListType(env5),env5));
        E = TypeEnv.of(E, Symbol.symbol("tl"), new ArrowType(new ListType(env6),new ListType(env6)));
    }

    @Override
    public Type get(Symbol x) {
        return E.get(x);
    }
}
