grammar TestCase;

ID  : [a-zA-Z_][a-zA-Z_0-9]*;
WS  : [ \t\r\n]+ -> skip;
INT : [0-9]+;

fragment EXPONENT : [eE] [+-]? [0-9]+;

NUM : [0-9]+ '.' [0-9]* EXPONENT?
    | '.' [0-9]+ EXPONENT?
    | [0-9]+ EXPONENT
    ;

COMMENT : '//' ~[\r\n]* -> skip;

PLUS  : '+';
MINUS : '-';
TIMES : '*';
DIV   : '/';

EQ : '==';
NE : '!=';
LT : '<';
GT : '>';
LE : '<=';
GE : '>=';

test
  : ID ':' '(' params ')' '->' type ';'
    'spec' branch (',' branch)* ';'
    'seeds' (typedExpr (',' typedExpr)*)? ';'
    'inputs' (input (',' input)*)? ';'
    'grammar' exprG=rules ';'
    'boolGrammar' inherit='inherit'? boolG=rules ';'
    'correctness' factor=NUM ';';

rules : (prod (',' prod)*)?;

typedExpr : '(' type ')' expr;

params : (ID ':' type (',' ID ':' type)* ','?)?;

type
  : 'int' #intType
  | 'bool' #boolType
  | type '[' ']' #arrayType
  ;

expr
  : value #constant
  | ID #variable
  | '(' expr ')' #paren
  | MINUS expr #unaryMinus
  | base=expr '[' index=expr ']' #index
  | lhs=expr op=(TIMES | DIV) rhs=expr #binary
  | lhs=expr op=(PLUS | MINUS) rhs=expr #binary
  | lhs=expr op=(EQ | NE | LT | GT | LE | GE) rhs=expr #rel
  | func=ID '(' (expr (',' expr)*)? ')' #funcCall
  ;

branch : cond '->' expr;

cond : expr ('&&' expr)*;

value
  : INT #scalar
  | 'true' #true
  | 'false' #false
  | 'infinity' #infinity
  | '[' (value (',' value)*)? ']' #list
  ;

input : '(' (value (',' value)*)? ')' ;

indexOp : '[' ']';

prod
  : op=(TIMES | DIV | PLUS | MINUS) cost=INT #binaryRule
  | op=(EQ | NE | LT | GT | LE | GE) cost=INT #compareRule
  | indexOp+ type cost=INT #indexRule
  | func=ID cost=INT #funcRule
  ;
