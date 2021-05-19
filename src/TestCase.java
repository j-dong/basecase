import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import parser.TestCaseLexer;
import parser.TestCaseVisitor;
import parser.TestCaseParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntConsumer;

public class TestCase extends AbstractParseTreeVisitor<Object> implements TestCaseVisitor<Object> {
    private String testName = null;
    private List<Type> parameterTypes = null;
    private List<String> parameterNames = null;
    private Map<String, Variable> parameterNameToVariable = null;
    private Type expectedType = null; // used for parsing values
    private Type outputType = null;
    private Synthesizer.Result spec = null;
    private List<Expression> seeds = null;
    private List<List<Value>> inputs = null;
    private Grammar exprGrammar = null;
    private Grammar boolGrammar = null;
    private Grammar curGrammar = null;
    private double correctnessFactor = 5.0;

    public String getTestName() {
        return testName;
    }

    public List<Type> getParameterTypes() {
        return parameterTypes;
    }

    public List<String> getParameterNames() {
        return parameterNames;
    }

    public Type getOutputType() {
        return outputType;
    }

    public Synthesizer.Result getSpec() {
        return spec;
    }

    public List<Expression> getSeeds() {
        return seeds;
    }

    public List<List<Value>> getInputs() {
        return inputs;
    }

    public Grammar getExprGrammar() {
        return exprGrammar;
    }

    public Grammar getBoolGrammar() {
        return boolGrammar;
    }

    public Double getCorrectnessFactor() { return correctnessFactor; }

    public static TestCase parseFile(String filename) throws IOException {
        TestCaseLexer lexer = new TestCaseLexer(CharStreams.fromFileName(filename));
        TestCaseParser parser = new TestCaseParser(new CommonTokenStream(lexer));
        return new TestCase().visitTest(parser.test());
    }

    private RuntimeException error(String message) {
        return new RuntimeException(message);
    }

    @Override
    public TestCase visitTest(TestCaseParser.TestContext ctx) {
        if (ctx.exception != null) {
            throw error("parse error: " + ctx.exception);
        }
        testName = ctx.ID().getText();
        visitParams(ctx.params());
        outputType = visitType(ctx.type());
        List<Synthesizer.Branch> branches = new ArrayList<>();
        for (TestCaseParser.BranchContext br : ctx.branch()) {
            branches.add(visitBranch(br));
        }
        spec = new Synthesizer.Result(branches);
        seeds = new ArrayList<>();
        for (TestCaseParser.TypedExprContext ex : ctx.typedExpr()) {
            seeds.add(visitTypedExpr(ex));
        }
        inputs = new ArrayList<>();
        for (TestCaseParser.InputContext in : ctx.input()) {
            inputs.add(visitInput(in));
        }
        exprGrammar = visitRules(ctx.exprG);
        if (ctx.inherit != null) {
            boolGrammar = visitRules(ctx.boolG, exprGrammar.clone());
        } else {
            boolGrammar = visitRules(ctx.boolG);
        }
        try {
            correctnessFactor = Double.parseDouble(ctx.factor.getText());
        } catch (NumberFormatException e) {
            throw error("invalid floating-point number: " + ctx.factor.getText());
        }
        return this;
    }

    @Override
    public Grammar visitRules(TestCaseParser.RulesContext ctx) {
        return visitRules(ctx, new Grammar());
    }

    public Grammar visitRules(TestCaseParser.RulesContext ctx, Grammar grammar) {
        curGrammar = grammar;
        for (TestCaseParser.ProdContext prod : ctx.prod()) {
            visitProd(prod);
        }
        return curGrammar;
    }

    @Override
    public Expression visitTypedExpr(TestCaseParser.TypedExprContext ctx) {
        expectedType = visitType(ctx.type());
        return visitExpr(ctx.expr());
    }

    @Override
    public Void visitParams(TestCaseParser.ParamsContext ctx) {
        assert ctx.ID().size() == ctx.type().size();
        parameterNames = new ArrayList<>();
        parameterTypes = new ArrayList<>();
        parameterNameToVariable = new HashMap<>();
        for (int i = 0; i < ctx.ID().size(); i++) {
            String name = ctx.ID(i).getText();
            Type type = visitType(ctx.type(i));
            parameterNames.add(name);
            parameterTypes.add(type);
            if (parameterNameToVariable.containsKey(name)) {
                throw error("duplicate parameter: " + name);
            }
            parameterNameToVariable.put(name, new Variable(i, name, type));
        }
        return null;
    }

    @Override
    public ArrayType visitArrayType(TestCaseParser.ArrayTypeContext ctx) {
        return new ArrayType(visitType(ctx.type()));
    }

    @Override
    public IntType visitIntType(TestCaseParser.IntTypeContext ctx) {
        return IntType.INSTANCE;
    }

    @Override
    public BoolType visitBoolType(TestCaseParser.BoolTypeContext ctx) {
        return BoolType.INSTANCE;
    }

    @Override
    public Expression visitParen(TestCaseParser.ParenContext ctx) {
        return visitExpr(ctx.expr());
    }

    @Override
    public Constant visitConstant(TestCaseParser.ConstantContext ctx) {
        return new Constant(visitValue(ctx.value()), 0);
    }

    @Override
    public BinaryExpr visitBinary(TestCaseParser.BinaryContext ctx) {
        expectedType = IntType.INSTANCE;
        Expression lhs = this.visitExpr(ctx.lhs);
        expectedType = IntType.INSTANCE;
        Expression rhs = this.visitExpr(ctx.rhs);
        BinaryOperator op = binaryOperator(ctx.op);
        return new BinaryExpr(op, 0, lhs, rhs);
    }

    @Override
    public Variable visitVariable(TestCaseParser.VariableContext ctx) {
        String name = ctx.ID().getText();
        if (!parameterNameToVariable.containsKey(name)) {
            throw error("invalid variable: " + name);
        }
        return parameterNameToVariable.get(name);
    }

    @Override
    public BinaryExpr visitUnaryMinus(TestCaseParser.UnaryMinusContext ctx) {
        return new BinaryExpr(BinaryOperator.MINUS, 0, new Constant(new IntValue(0), 0), visitExpr(ctx.expr()));
    }

    @Override
    public CompareExpr visitRel(TestCaseParser.RelContext ctx) {
        expectedType = IntType.INSTANCE;
        Expression lhs = this.visitExpr(ctx.lhs);
        expectedType = IntType.INSTANCE;
        Expression rhs = this.visitExpr(ctx.rhs);
        CompareOperator op = compareOperator(ctx.op);
        return new CompareExpr(op, 0, lhs, rhs);
    }

    @Override
    public IndexExpr visitIndex(TestCaseParser.IndexContext ctx) {
        assert expectedType != null;
        expectedType = new ArrayType(expectedType);
        Expression base = visitExpr(ctx.base);
        expectedType = IntType.INSTANCE;
        Expression index = visitExpr(ctx.index);
        return new IndexExpr(base, index, 0);
    }

    @Override
    public Value visitInfinity(TestCaseParser.InfinityContext ctx) {
        return new InfiniteValue();
    }

    @Override
    public Expression visitFuncCall(TestCaseParser.FuncCallContext ctx) {
        List<Expression> args = new ArrayList<>();
        List<Type> signature;
        assert this.testName != null;
        Builtin builtin = null;
        String funcName = ctx.func.getText();
        if (funcName.equals(this.testName)) {
            signature = parameterTypes;
        } else {
            builtin = BuiltinKt.getBUILTINS().get(funcName);
            if (builtin == null) {
                throw error("invalid function name '" + funcName + "'");
            }
            signature = builtin.getSignature();
        }
        if (signature.size() != ctx.expr().size()) {
            throw error("wrong number of arguments to '" + funcName + "'; expected " + signature.size() + ", got " + ctx.expr().size());
        }
        for (int i = 0; i < ctx.expr().size(); i++) {
            expectedType = signature.get(i);
            args.add(this.visitExpr(ctx.expr(i)));
        }
        if (funcName.equals(this.testName)) {
            return new RecursiveExpr(testName, outputType, 0, args);
        }
        assert builtin != null;
        return builtin.production(0).getFactory().build(args);
    }

    @Override
    public Synthesizer.Branch visitBranch(TestCaseParser.BranchContext ctx) {
        List<Expression> cond = this.visitCond(ctx.cond());
        expectedType = outputType;
        Expression expr = this.visitExpr(ctx.expr());
        return new Synthesizer.Branch(expr, cond);
    }

    @Override
    public List<Expression> visitCond(TestCaseParser.CondContext ctx) {
        List<Expression> ret = new ArrayList<>();
        for (TestCaseParser.ExprContext child : ctx.expr()) {
            expectedType = BoolType.INSTANCE;
            ret.add(this.visitExpr(child));
        }
        return ret;
    }

    @Override
    public IntValue visitScalar(TestCaseParser.ScalarContext ctx) {
        try {
            return new IntValue(Integer.parseInt(ctx.INT().getText()));
        } catch (Throwable e) {
            throw error("invalid integer value");
        }
    }

    @Override
    public BoolValue visitTrue(TestCaseParser.TrueContext ctx) {
        return new BoolValue(true);
    }

    @Override
    public BoolValue visitFalse(TestCaseParser.FalseContext ctx) {
        return new BoolValue(false);
    }

    @Override
    public ArrayValue visitList(TestCaseParser.ListContext ctx) {
        if (expectedType == null || !(expectedType instanceof ArrayType)) {
            throw error("expected value of type " + expectedType + "; got " + ctx.toString());
        }
        Type childType = ((ArrayType) expectedType).getMemberType();
        Type type = expectedType;
        List<Value> ret = new ArrayList<>();
        for (TestCaseParser.ValueContext child : ctx.value()) {
            expectedType = childType;
            ret.add(this.visitValue(child));
        }
        return new ArrayValue(ret, type);
    }

    @Override
    public List<Value> visitInput(TestCaseParser.InputContext ctx) {
        List<Value> ret = new ArrayList<>();
        assert parameterTypes != null;
        for (int i = 0; i < ctx.value().size(); i++) {
            expectedType = parameterTypes.get(i);
            ret.add(this.visitValue(ctx.value(i)));
        }
        expectedType = null;
        return ret;
    }

    @Override
    public Void visitIndexOp(TestCaseParser.IndexOpContext ctx) {
        return null;
    }

    @Override
    public Void visitBinaryRule(TestCaseParser.BinaryRuleContext ctx) {
        long cost;
        try {
            cost = Long.parseLong(ctx.cost.getText());
        } catch (NumberFormatException e) {
            throw error("invalid long value " + ctx.cost.getText());
        }
        curGrammar.binary(binaryOperator(ctx.op), cost);
        return null;
    }

    @Override
    public Void visitCompareRule(TestCaseParser.CompareRuleContext ctx) {
        long cost;
        try {
            cost = Long.parseLong(ctx.cost.getText());
        } catch (NumberFormatException e) {
            throw error("invalid long value " + ctx.cost.getText());
        }
        curGrammar.compare(compareOperator(ctx.op), cost);
        return null;
    }

    @Override
    public Void visitIndexRule(TestCaseParser.IndexRuleContext ctx) {
        long cost;
        try {
            cost = Long.parseLong(ctx.cost.getText());
        } catch (NumberFormatException e) {
            throw error("invalid long value " + ctx.cost.getText());
        }
        Type type = visitType(ctx.type());
        curGrammar.index(type, ctx.indexOp().size(), cost);
        return null;
    }

    @Override
    public Production visitFuncRule(TestCaseParser.FuncRuleContext ctx) {
        long cost;
        try {
            cost = Long.parseLong(ctx.cost.getText());
        } catch (NumberFormatException e) {
            throw error("invalid long value " + ctx.cost.getText());
        }
        Builtin builtin = BuiltinKt.getBUILTINS().get(ctx.func.getText());
        if (builtin == null) {
            throw error("invalid function '" + ctx.func.getText() + "'");
        }
        curGrammar.addProduction(builtin.production(cost));
        return null;
    }

    public void visitProd(TestCaseParser.ProdContext ctx) {
        ctx.accept(this);
    }

    public Value visitValue(TestCaseParser.ValueContext ctx) {
        Type expected = expectedType;
        Value v = (Value) ctx.accept(this);
        if (!v.getType().equals(expected)) {
            throw error("type mismatch; expected " + expected + ", got " + v.getType() + " (value: " + v + ")");
        }
        return v;
    }

    public Expression visitExpr(TestCaseParser.ExprContext ctx) {
        Type expected = expectedType;
        Expression e = (Expression) ctx.accept(this);
        if (!e.getType().equals(expected)) {
            throw error("type mismatch; expected " + expected + ", got " + e.getType() + " (expr: " + e + ")");
        }
        return e;
    }

    public Type visitType(TestCaseParser.TypeContext ctx) {
        return (Type) ctx.accept(this);
    }

    private BinaryOperator binaryOperator(Token t) {
        BinaryOperator op;
        switch (t.getType()) {
            case TestCaseParser.PLUS:
                op = BinaryOperator.PLUS;
                break;
            case TestCaseParser.MINUS:
                op = BinaryOperator.MINUS;
                break;
            case TestCaseParser.TIMES:
                op = BinaryOperator.TIMES;
                break;
            case TestCaseParser.DIV:
                op = BinaryOperator.DIV;
                break;
            default:
                throw error("invalid binary operator " + t.getText());
        }
        return op;
    }

    private CompareOperator compareOperator(Token t) {
        CompareOperator op;
        switch (t.getType()) {
            case TestCaseParser.EQ:
                op = CompareOperator.EQUALS;
                break;
            case TestCaseParser.NE:
                op = CompareOperator.NOT_EQUALS;
                break;
            case TestCaseParser.LT:
                op = CompareOperator.LESS_THAN;
                break;
            case TestCaseParser.GT:
                op = CompareOperator.GREATER_THAN;
                break;
            case TestCaseParser.LE:
                op = CompareOperator.LESS_EQUALS;
                break;
            case TestCaseParser.GE:
                op = CompareOperator.GREATER_EQUALS;
                break;
            default:
                throw error("invalid comparison operator " + t.getText());
        }
        return op;
    }

    private void appendCommaSeparated(StringBuilder builder, int size, String separator, IntConsumer consumer) {
        if (size == 0) return;
        consumer.accept(0);
        for (int i = 1; i < size; i++) {
            builder.append(separator);
            consumer.accept(i);
        }
    }

    private void appendGrammar(StringBuilder builder, Grammar grammar) {
        appendCommaSeparated(builder, grammar.getProductions().size(), ", ", i -> {
            Production prod = grammar.getProductions().get(i);
            ExpressionFactory fact = prod.getFactory();
            if (fact instanceof BinaryFactory) {
                BinaryFactory bin = (BinaryFactory) fact;
                builder.append(bin.getOp());
                builder.append(" ");
                builder.append(bin.getCost());
            } else if (fact instanceof CompareFactory) {
                CompareFactory bin = (CompareFactory) fact;
                builder.append(bin.getOp());
                builder.append(" ");
                builder.append(bin.getCost());
            } else if (fact instanceof IndexFactory) {
                IndexFactory idx = (IndexFactory) fact;
                builder.append("[] ");
                builder.append(((ArrayType) prod.getSignature().get(0)).getMemberType());
                builder.append(" ");
                builder.append(idx.getCost());
            } else if (fact instanceof FuncCallFactory) {
                FuncCallFactory fc = (FuncCallFactory) fact;
                builder.append(fc.getFuncName());
                builder.append(" ");
                builder.append(fc.getCallCost());
            } else {
                builder.append("<unknown>");
            }
        });
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(testName); builder.append(" : (");
        appendCommaSeparated(builder, parameterNames.size(), ", ", i -> {
            builder.append(parameterNames.get(i));
            builder.append(" : ");
            builder.append(parameterTypes.get(i));
        });
        builder.append(") -> ");
        builder.append(outputType);
        builder.append(";\nspec");
        appendCommaSeparated(builder, spec.getBranches().size(), ",", i -> {
            builder.append("\n  ");
            builder.append(spec.getBranches().get(i));
        });
        builder.append(";\n");
        builder.append("seed ");
        appendCommaSeparated(builder, seeds.size(), ", ", i -> {
            builder.append("(");
            builder.append(seeds.get(i).getType());
            builder.append(") ");
            builder.append(seeds.get(i));
        });
        builder.append(";\n");
        builder.append("inputs ");
        appendCommaSeparated(builder, inputs.size(), ", ", i -> {
            builder.append("(");
            final List<Value> input = inputs.get(i);
            appendCommaSeparated(builder, input.size(), ", ", j -> builder.append(input.get(j)));
            builder.append(")");
        });
        builder.append(";\n");
        builder.append("grammar ");
        appendGrammar(builder, exprGrammar);
        builder.append(";\nboolGrammar ");
        appendGrammar(builder, boolGrammar);
        builder.append(";\ncorrectness ");
        builder.append(correctnessFactor);
        builder.append(";");
        return builder.toString();
    }
}
