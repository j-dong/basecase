// Generated from src/TestCase.g4 by ANTLR 4.9.2
package parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TestCaseParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, ID=22, WS=23, INT=24, NUM=25, 
		COMMENT=26, PLUS=27, MINUS=28, TIMES=29, DIV=30, EQ=31, NE=32, LT=33, 
		GT=34, LE=35, GE=36;
	public static final int
		RULE_test = 0, RULE_rules = 1, RULE_typedExpr = 2, RULE_params = 3, RULE_type = 4, 
		RULE_expr = 5, RULE_branch = 6, RULE_cond = 7, RULE_value = 8, RULE_input = 9, 
		RULE_indexOp = 10, RULE_prod = 11;
	private static String[] makeRuleNames() {
		return new String[] {
			"test", "rules", "typedExpr", "params", "type", "expr", "branch", "cond", 
			"value", "input", "indexOp", "prod"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'('", "')'", "'->'", "';'", "'spec'", "','", "'seeds'", 
			"'inputs'", "'grammar'", "'boolGrammar'", "'inherit'", "'correctness'", 
			"'int'", "'bool'", "'['", "']'", "'&&'", "'true'", "'false'", "'infinity'", 
			null, null, null, null, null, "'+'", "'-'", "'*'", "'/'", "'=='", "'!='", 
			"'<'", "'>'", "'<='", "'>='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, "ID", "WS", 
			"INT", "NUM", "COMMENT", "PLUS", "MINUS", "TIMES", "DIV", "EQ", "NE", 
			"LT", "GT", "LE", "GE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "TestCase.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TestCaseParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class TestContext extends ParserRuleContext {
		public RulesContext exprG;
		public Token inherit;
		public RulesContext boolG;
		public Token factor;
		public TerminalNode ID() { return getToken(TestCaseParser.ID, 0); }
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<BranchContext> branch() {
			return getRuleContexts(BranchContext.class);
		}
		public BranchContext branch(int i) {
			return getRuleContext(BranchContext.class,i);
		}
		public List<RulesContext> rules() {
			return getRuleContexts(RulesContext.class);
		}
		public RulesContext rules(int i) {
			return getRuleContext(RulesContext.class,i);
		}
		public TerminalNode NUM() { return getToken(TestCaseParser.NUM, 0); }
		public List<TypedExprContext> typedExpr() {
			return getRuleContexts(TypedExprContext.class);
		}
		public TypedExprContext typedExpr(int i) {
			return getRuleContext(TypedExprContext.class,i);
		}
		public List<InputContext> input() {
			return getRuleContexts(InputContext.class);
		}
		public InputContext input(int i) {
			return getRuleContext(InputContext.class,i);
		}
		public TestContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_test; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitTest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TestContext test() throws RecognitionException {
		TestContext _localctx = new TestContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_test);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(ID);
			setState(25);
			match(T__0);
			setState(26);
			match(T__1);
			setState(27);
			params();
			setState(28);
			match(T__2);
			setState(29);
			match(T__3);
			setState(30);
			type(0);
			setState(31);
			match(T__4);
			setState(32);
			match(T__5);
			setState(33);
			branch();
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(34);
				match(T__6);
				setState(35);
				branch();
				}
				}
				setState(40);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(41);
			match(T__4);
			setState(42);
			match(T__7);
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(43);
				typedExpr();
				setState(48);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(44);
					match(T__6);
					setState(45);
					typedExpr();
					}
					}
					setState(50);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(53);
			match(T__4);
			setState(54);
			match(T__8);
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(55);
				input();
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(56);
					match(T__6);
					setState(57);
					input();
					}
					}
					setState(62);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(65);
			match(T__4);
			setState(66);
			match(T__9);
			setState(67);
			((TestContext)_localctx).exprG = rules();
			setState(68);
			match(T__4);
			setState(69);
			match(T__10);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(70);
				((TestContext)_localctx).inherit = match(T__11);
				}
			}

			setState(73);
			((TestContext)_localctx).boolG = rules();
			setState(74);
			match(T__4);
			setState(75);
			match(T__12);
			setState(76);
			((TestContext)_localctx).factor = match(NUM);
			setState(77);
			match(T__4);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RulesContext extends ParserRuleContext {
		public List<ProdContext> prod() {
			return getRuleContexts(ProdContext.class);
		}
		public ProdContext prod(int i) {
			return getRuleContext(ProdContext.class,i);
		}
		public RulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rules; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitRules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesContext rules() throws RecognitionException {
		RulesContext _localctx = new RulesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << ID) | (1L << PLUS) | (1L << MINUS) | (1L << TIMES) | (1L << DIV) | (1L << EQ) | (1L << NE) | (1L << LT) | (1L << GT) | (1L << LE) | (1L << GE))) != 0)) {
				{
				setState(79);
				prod();
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(80);
					match(T__6);
					setState(81);
					prod();
					}
					}
					setState(86);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypedExprContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TypedExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typedExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitTypedExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypedExprContext typedExpr() throws RecognitionException {
		TypedExprContext _localctx = new TypedExprContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_typedExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(T__1);
			setState(90);
			type(0);
			setState(91);
			match(T__2);
			setState(92);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamsContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(TestCaseParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(TestCaseParser.ID, i);
		}
		public List<TypeContext> type() {
			return getRuleContexts(TypeContext.class);
		}
		public TypeContext type(int i) {
			return getRuleContext(TypeContext.class,i);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitParams(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_params);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(94);
				match(ID);
				setState(95);
				match(T__0);
				setState(96);
				type(0);
				setState(103);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(97);
						match(T__6);
						setState(98);
						match(ID);
						setState(99);
						match(T__0);
						setState(100);
						type(0);
						}
						} 
					}
					setState(105);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
				}
				setState(107);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==T__6) {
					{
					setState(106);
					match(T__6);
					}
				}

				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	 
		public TypeContext() { }
		public void copyFrom(TypeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ArrayTypeContext extends TypeContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public ArrayTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitArrayType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IntTypeContext extends TypeContext {
		public IntTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitIntType(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BoolTypeContext extends TypeContext {
		public BoolTypeContext(TypeContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitBoolType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 8;
		enterRecursionRule(_localctx, 8, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__13:
				{
				_localctx = new IntTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(112);
				match(T__13);
				}
				break;
			case T__14:
				{
				_localctx = new BoolTypeContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(113);
				match(T__14);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ArrayTypeContext(new TypeContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(116);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(117);
					match(T__15);
					setState(118);
					match(T__16);
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ParenContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ParenContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitParen(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConstantContext extends ExprContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ConstantContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitConstant(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryContext extends ExprContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode TIMES() { return getToken(TestCaseParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(TestCaseParser.DIV, 0); }
		public TerminalNode PLUS() { return getToken(TestCaseParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(TestCaseParser.MINUS, 0); }
		public BinaryContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitBinary(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableContext extends ExprContext {
		public TerminalNode ID() { return getToken(TestCaseParser.ID, 0); }
		public VariableContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class UnaryMinusContext extends ExprContext {
		public TerminalNode MINUS() { return getToken(TestCaseParser.MINUS, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public UnaryMinusContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitUnaryMinus(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class RelContext extends ExprContext {
		public ExprContext lhs;
		public Token op;
		public ExprContext rhs;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode EQ() { return getToken(TestCaseParser.EQ, 0); }
		public TerminalNode NE() { return getToken(TestCaseParser.NE, 0); }
		public TerminalNode LT() { return getToken(TestCaseParser.LT, 0); }
		public TerminalNode GT() { return getToken(TestCaseParser.GT, 0); }
		public TerminalNode LE() { return getToken(TestCaseParser.LE, 0); }
		public TerminalNode GE() { return getToken(TestCaseParser.GE, 0); }
		public RelContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitRel(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IndexContext extends ExprContext {
		public ExprContext base;
		public ExprContext index;
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public IndexContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitIndex(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncCallContext extends ExprContext {
		public Token func;
		public TerminalNode ID() { return getToken(TestCaseParser.ID, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public FuncCallContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 10;
		enterRecursionRule(_localctx, 10, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				{
				_localctx = new ConstantContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(125);
				value();
				}
				break;
			case 2:
				{
				_localctx = new VariableContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(126);
				match(ID);
				}
				break;
			case 3:
				{
				_localctx = new ParenContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(127);
				match(T__1);
				setState(128);
				expr(0);
				setState(129);
				match(T__2);
				}
				break;
			case 4:
				{
				_localctx = new UnaryMinusContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(131);
				match(MINUS);
				setState(132);
				expr(6);
				}
				break;
			case 5:
				{
				_localctx = new FuncCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(133);
				((FuncCallContext)_localctx).func = match(ID);
				setState(134);
				match(T__1);
				setState(143);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__15) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << ID) | (1L << INT) | (1L << MINUS))) != 0)) {
					{
					setState(135);
					expr(0);
					setState(140);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(136);
						match(T__6);
						setState(137);
						expr(0);
						}
						}
						setState(142);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(145);
				match(T__2);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(164);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(162);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(148);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(149);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==TIMES || _la==DIV) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(150);
						((BinaryContext)_localctx).rhs = expr(5);
						}
						break;
					case 2:
						{
						_localctx = new BinaryContext(new ExprContext(_parentctx, _parentState));
						((BinaryContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(151);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(152);
						((BinaryContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==PLUS || _la==MINUS) ) {
							((BinaryContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(153);
						((BinaryContext)_localctx).rhs = expr(4);
						}
						break;
					case 3:
						{
						_localctx = new RelContext(new ExprContext(_parentctx, _parentState));
						((RelContext)_localctx).lhs = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(154);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(155);
						((RelContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NE) | (1L << LT) | (1L << GT) | (1L << LE) | (1L << GE))) != 0)) ) {
							((RelContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(156);
						((RelContext)_localctx).rhs = expr(3);
						}
						break;
					case 4:
						{
						_localctx = new IndexContext(new ExprContext(_parentctx, _parentState));
						((IndexContext)_localctx).base = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(157);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(158);
						match(T__15);
						setState(159);
						((IndexContext)_localctx).index = expr(0);
						setState(160);
						match(T__16);
						}
						break;
					}
					} 
				}
				setState(166);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class BranchContext extends ParserRuleContext {
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BranchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_branch; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitBranch(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BranchContext branch() throws RecognitionException {
		BranchContext _localctx = new BranchContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_branch);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			cond();
			setState(168);
			match(T__3);
			setState(169);
			expr(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CondContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitCond(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CondContext cond() throws RecognitionException {
		CondContext _localctx = new CondContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_cond);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			expr(0);
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__17) {
				{
				{
				setState(172);
				match(T__17);
				setState(173);
				expr(0);
				}
				}
				setState(178);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
	 
		public ValueContext() { }
		public void copyFrom(ValueContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ScalarContext extends ValueContext {
		public TerminalNode INT() { return getToken(TestCaseParser.INT, 0); }
		public ScalarContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitScalar(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TrueContext extends ValueContext {
		public TrueContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitTrue(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FalseContext extends ValueContext {
		public FalseContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitFalse(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InfinityContext extends ValueContext {
		public InfinityContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitInfinity(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListContext extends ValueContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public ListContext(ValueContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_value);
		int _la;
		try {
			setState(195);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case INT:
				_localctx = new ScalarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(179);
				match(INT);
				}
				break;
			case T__18:
				_localctx = new TrueContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(180);
				match(T__18);
				}
				break;
			case T__19:
				_localctx = new FalseContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(181);
				match(T__19);
				}
				break;
			case T__20:
				_localctx = new InfinityContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(182);
				match(T__20);
				}
				break;
			case T__15:
				_localctx = new ListContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(183);
				match(T__15);
				setState(192);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT))) != 0)) {
					{
					setState(184);
					value();
					setState(189);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==T__6) {
						{
						{
						setState(185);
						match(T__6);
						setState(186);
						value();
						}
						}
						setState(191);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					}
				}

				setState(194);
				match(T__16);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class InputContext extends ParserRuleContext {
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public InputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_input; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final InputContext input() throws RecognitionException {
		InputContext _localctx = new InputContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_input);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			match(T__1);
			setState(206);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__15) | (1L << T__18) | (1L << T__19) | (1L << T__20) | (1L << INT))) != 0)) {
				{
				setState(198);
				value();
				setState(203);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__6) {
					{
					{
					setState(199);
					match(T__6);
					setState(200);
					value();
					}
					}
					setState(205);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(208);
			match(T__2);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IndexOpContext extends ParserRuleContext {
		public IndexOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_indexOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitIndexOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IndexOpContext indexOp() throws RecognitionException {
		IndexOpContext _localctx = new IndexOpContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_indexOp);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(T__15);
			setState(211);
			match(T__16);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ProdContext extends ParserRuleContext {
		public ProdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prod; }
	 
		public ProdContext() { }
		public void copyFrom(ProdContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class FuncRuleContext extends ProdContext {
		public Token func;
		public Token cost;
		public TerminalNode ID() { return getToken(TestCaseParser.ID, 0); }
		public TerminalNode INT() { return getToken(TestCaseParser.INT, 0); }
		public FuncRuleContext(ProdContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitFuncRule(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class IndexRuleContext extends ProdContext {
		public Token cost;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode INT() { return getToken(TestCaseParser.INT, 0); }
		public List<IndexOpContext> indexOp() {
			return getRuleContexts(IndexOpContext.class);
		}
		public IndexOpContext indexOp(int i) {
			return getRuleContext(IndexOpContext.class,i);
		}
		public IndexRuleContext(ProdContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitIndexRule(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class BinaryRuleContext extends ProdContext {
		public Token op;
		public Token cost;
		public TerminalNode INT() { return getToken(TestCaseParser.INT, 0); }
		public TerminalNode TIMES() { return getToken(TestCaseParser.TIMES, 0); }
		public TerminalNode DIV() { return getToken(TestCaseParser.DIV, 0); }
		public TerminalNode PLUS() { return getToken(TestCaseParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(TestCaseParser.MINUS, 0); }
		public BinaryRuleContext(ProdContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitBinaryRule(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CompareRuleContext extends ProdContext {
		public Token op;
		public Token cost;
		public TerminalNode INT() { return getToken(TestCaseParser.INT, 0); }
		public TerminalNode EQ() { return getToken(TestCaseParser.EQ, 0); }
		public TerminalNode NE() { return getToken(TestCaseParser.NE, 0); }
		public TerminalNode LT() { return getToken(TestCaseParser.LT, 0); }
		public TerminalNode GT() { return getToken(TestCaseParser.GT, 0); }
		public TerminalNode LE() { return getToken(TestCaseParser.LE, 0); }
		public TerminalNode GE() { return getToken(TestCaseParser.GE, 0); }
		public CompareRuleContext(ProdContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TestCaseVisitor ) return ((TestCaseVisitor<? extends T>)visitor).visitCompareRule(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProdContext prod() throws RecognitionException {
		ProdContext _localctx = new ProdContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_prod);
		int _la;
		try {
			setState(227);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PLUS:
			case MINUS:
			case TIMES:
			case DIV:
				_localctx = new BinaryRuleContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(213);
				((BinaryRuleContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PLUS) | (1L << MINUS) | (1L << TIMES) | (1L << DIV))) != 0)) ) {
					((BinaryRuleContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(214);
				((BinaryRuleContext)_localctx).cost = match(INT);
				}
				break;
			case EQ:
			case NE:
			case LT:
			case GT:
			case LE:
			case GE:
				_localctx = new CompareRuleContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(215);
				((CompareRuleContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NE) | (1L << LT) | (1L << GT) | (1L << LE) | (1L << GE))) != 0)) ) {
					((CompareRuleContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(216);
				((CompareRuleContext)_localctx).cost = match(INT);
				}
				break;
			case T__15:
				_localctx = new IndexRuleContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(218); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(217);
					indexOp();
					}
					}
					setState(220); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==T__15 );
				setState(222);
				type(0);
				setState(223);
				((IndexRuleContext)_localctx).cost = match(INT);
				}
				break;
			case ID:
				_localctx = new FuncRuleContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(225);
				((FuncRuleContext)_localctx).func = match(ID);
				setState(226);
				((FuncRuleContext)_localctx).cost = match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 4:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 5:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 4);
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 2);
		case 4:
			return precpred(_ctx, 5);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3&\u00e8\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\2\7"+
		"\2\'\n\2\f\2\16\2*\13\2\3\2\3\2\3\2\3\2\3\2\7\2\61\n\2\f\2\16\2\64\13"+
		"\2\5\2\66\n\2\3\2\3\2\3\2\3\2\3\2\7\2=\n\2\f\2\16\2@\13\2\5\2B\n\2\3\2"+
		"\3\2\3\2\3\2\3\2\3\2\5\2J\n\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\7\3"+
		"U\n\3\f\3\16\3X\13\3\5\3Z\n\3\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\7\5h\n\5\f\5\16\5k\13\5\3\5\5\5n\n\5\5\5p\n\5\3\6\3\6\3\6\5\6"+
		"u\n\6\3\6\3\6\3\6\7\6z\n\6\f\6\16\6}\13\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\7\7\7\u008d\n\7\f\7\16\7\u0090\13\7\5\7\u0092"+
		"\n\7\3\7\5\7\u0095\n\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\3\7\3\7\7\7\u00a5\n\7\f\7\16\7\u00a8\13\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\7\t\u00b1\n\t\f\t\16\t\u00b4\13\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\n\7\n"+
		"\u00be\n\n\f\n\16\n\u00c1\13\n\5\n\u00c3\n\n\3\n\5\n\u00c6\n\n\3\13\3"+
		"\13\3\13\3\13\7\13\u00cc\n\13\f\13\16\13\u00cf\13\13\5\13\u00d1\n\13\3"+
		"\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\6\r\u00dd\n\r\r\r\16\r\u00de"+
		"\3\r\3\r\3\r\3\r\3\r\5\r\u00e6\n\r\3\r\2\4\n\f\16\2\4\6\b\n\f\16\20\22"+
		"\24\26\30\2\6\3\2\37 \3\2\35\36\3\2!&\3\2\35 \2\u00ff\2\32\3\2\2\2\4Y"+
		"\3\2\2\2\6[\3\2\2\2\bo\3\2\2\2\nt\3\2\2\2\f\u0094\3\2\2\2\16\u00a9\3\2"+
		"\2\2\20\u00ad\3\2\2\2\22\u00c5\3\2\2\2\24\u00c7\3\2\2\2\26\u00d4\3\2\2"+
		"\2\30\u00e5\3\2\2\2\32\33\7\30\2\2\33\34\7\3\2\2\34\35\7\4\2\2\35\36\5"+
		"\b\5\2\36\37\7\5\2\2\37 \7\6\2\2 !\5\n\6\2!\"\7\7\2\2\"#\7\b\2\2#(\5\16"+
		"\b\2$%\7\t\2\2%\'\5\16\b\2&$\3\2\2\2\'*\3\2\2\2(&\3\2\2\2()\3\2\2\2)+"+
		"\3\2\2\2*(\3\2\2\2+,\7\7\2\2,\65\7\n\2\2-\62\5\6\4\2./\7\t\2\2/\61\5\6"+
		"\4\2\60.\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\62\63\3\2\2\2\63\66\3\2\2"+
		"\2\64\62\3\2\2\2\65-\3\2\2\2\65\66\3\2\2\2\66\67\3\2\2\2\678\7\7\2\28"+
		"A\7\13\2\29>\5\24\13\2:;\7\t\2\2;=\5\24\13\2<:\3\2\2\2=@\3\2\2\2><\3\2"+
		"\2\2>?\3\2\2\2?B\3\2\2\2@>\3\2\2\2A9\3\2\2\2AB\3\2\2\2BC\3\2\2\2CD\7\7"+
		"\2\2DE\7\f\2\2EF\5\4\3\2FG\7\7\2\2GI\7\r\2\2HJ\7\16\2\2IH\3\2\2\2IJ\3"+
		"\2\2\2JK\3\2\2\2KL\5\4\3\2LM\7\7\2\2MN\7\17\2\2NO\7\33\2\2OP\7\7\2\2P"+
		"\3\3\2\2\2QV\5\30\r\2RS\7\t\2\2SU\5\30\r\2TR\3\2\2\2UX\3\2\2\2VT\3\2\2"+
		"\2VW\3\2\2\2WZ\3\2\2\2XV\3\2\2\2YQ\3\2\2\2YZ\3\2\2\2Z\5\3\2\2\2[\\\7\4"+
		"\2\2\\]\5\n\6\2]^\7\5\2\2^_\5\f\7\2_\7\3\2\2\2`a\7\30\2\2ab\7\3\2\2bi"+
		"\5\n\6\2cd\7\t\2\2de\7\30\2\2ef\7\3\2\2fh\5\n\6\2gc\3\2\2\2hk\3\2\2\2"+
		"ig\3\2\2\2ij\3\2\2\2jm\3\2\2\2ki\3\2\2\2ln\7\t\2\2ml\3\2\2\2mn\3\2\2\2"+
		"np\3\2\2\2o`\3\2\2\2op\3\2\2\2p\t\3\2\2\2qr\b\6\1\2ru\7\20\2\2su\7\21"+
		"\2\2tq\3\2\2\2ts\3\2\2\2u{\3\2\2\2vw\f\3\2\2wx\7\22\2\2xz\7\23\2\2yv\3"+
		"\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\13\3\2\2\2}{\3\2\2\2~\177\b\7\1"+
		"\2\177\u0095\5\22\n\2\u0080\u0095\7\30\2\2\u0081\u0082\7\4\2\2\u0082\u0083"+
		"\5\f\7\2\u0083\u0084\7\5\2\2\u0084\u0095\3\2\2\2\u0085\u0086\7\36\2\2"+
		"\u0086\u0095\5\f\7\b\u0087\u0088\7\30\2\2\u0088\u0091\7\4\2\2\u0089\u008e"+
		"\5\f\7\2\u008a\u008b\7\t\2\2\u008b\u008d\5\f\7\2\u008c\u008a\3\2\2\2\u008d"+
		"\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f\3\2\2\2\u008f\u0092\3\2"+
		"\2\2\u0090\u008e\3\2\2\2\u0091\u0089\3\2\2\2\u0091\u0092\3\2\2\2\u0092"+
		"\u0093\3\2\2\2\u0093\u0095\7\5\2\2\u0094~\3\2\2\2\u0094\u0080\3\2\2\2"+
		"\u0094\u0081\3\2\2\2\u0094\u0085\3\2\2\2\u0094\u0087\3\2\2\2\u0095\u00a6"+
		"\3\2\2\2\u0096\u0097\f\6\2\2\u0097\u0098\t\2\2\2\u0098\u00a5\5\f\7\7\u0099"+
		"\u009a\f\5\2\2\u009a\u009b\t\3\2\2\u009b\u00a5\5\f\7\6\u009c\u009d\f\4"+
		"\2\2\u009d\u009e\t\4\2\2\u009e\u00a5\5\f\7\5\u009f\u00a0\f\7\2\2\u00a0"+
		"\u00a1\7\22\2\2\u00a1\u00a2\5\f\7\2\u00a2\u00a3\7\23\2\2\u00a3\u00a5\3"+
		"\2\2\2\u00a4\u0096\3\2\2\2\u00a4\u0099\3\2\2\2\u00a4\u009c\3\2\2\2\u00a4"+
		"\u009f\3\2\2\2\u00a5\u00a8\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a6\u00a7\3\2"+
		"\2\2\u00a7\r\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a9\u00aa\5\20\t\2\u00aa\u00ab"+
		"\7\6\2\2\u00ab\u00ac\5\f\7\2\u00ac\17\3\2\2\2\u00ad\u00b2\5\f\7\2\u00ae"+
		"\u00af\7\24\2\2\u00af\u00b1\5\f\7\2\u00b0\u00ae\3\2\2\2\u00b1\u00b4\3"+
		"\2\2\2\u00b2\u00b0\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\21\3\2\2\2\u00b4"+
		"\u00b2\3\2\2\2\u00b5\u00c6\7\32\2\2\u00b6\u00c6\7\25\2\2\u00b7\u00c6\7"+
		"\26\2\2\u00b8\u00c6\7\27\2\2\u00b9\u00c2\7\22\2\2\u00ba\u00bf\5\22\n\2"+
		"\u00bb\u00bc\7\t\2\2\u00bc\u00be\5\22\n\2\u00bd\u00bb\3\2\2\2\u00be\u00c1"+
		"\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf\u00c0\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1"+
		"\u00bf\3\2\2\2\u00c2\u00ba\3\2\2\2\u00c2\u00c3\3\2\2\2\u00c3\u00c4\3\2"+
		"\2\2\u00c4\u00c6\7\23\2\2\u00c5\u00b5\3\2\2\2\u00c5\u00b6\3\2\2\2\u00c5"+
		"\u00b7\3\2\2\2\u00c5\u00b8\3\2\2\2\u00c5\u00b9\3\2\2\2\u00c6\23\3\2\2"+
		"\2\u00c7\u00d0\7\4\2\2\u00c8\u00cd\5\22\n\2\u00c9\u00ca\7\t\2\2\u00ca"+
		"\u00cc\5\22\n\2\u00cb\u00c9\3\2\2\2\u00cc\u00cf\3\2\2\2\u00cd\u00cb\3"+
		"\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\u00d1\3\2\2\2\u00cf\u00cd\3\2\2\2\u00d0"+
		"\u00c8\3\2\2\2\u00d0\u00d1\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d3\7\5"+
		"\2\2\u00d3\25\3\2\2\2\u00d4\u00d5\7\22\2\2\u00d5\u00d6\7\23\2\2\u00d6"+
		"\27\3\2\2\2\u00d7\u00d8\t\5\2\2\u00d8\u00e6\7\32\2\2\u00d9\u00da\t\4\2"+
		"\2\u00da\u00e6\7\32\2\2\u00db\u00dd\5\26\f\2\u00dc\u00db\3\2\2\2\u00dd"+
		"\u00de\3\2\2\2\u00de\u00dc\3\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e0\3\2"+
		"\2\2\u00e0\u00e1\5\n\6\2\u00e1\u00e2\7\32\2\2\u00e2\u00e6\3\2\2\2\u00e3"+
		"\u00e4\7\30\2\2\u00e4\u00e6\7\32\2\2\u00e5\u00d7\3\2\2\2\u00e5\u00d9\3"+
		"\2\2\2\u00e5\u00dc\3\2\2\2\u00e5\u00e3\3\2\2\2\u00e6\31\3\2\2\2\34(\62"+
		"\65>AIVYimot{\u008e\u0091\u0094\u00a4\u00a6\u00b2\u00bf\u00c2\u00c5\u00cd"+
		"\u00d0\u00de\u00e5";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}