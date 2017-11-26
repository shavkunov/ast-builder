grammar Fun;

file
    :   (function)* block
    ;

function
    : 'fun' IDENTIFIER '(' parameterNames ')' blockWithBraces
    ;

block
    :   (statement)*
    ;

blockWithBraces
    :   '{' block '}'
    ;

statement
    :   functionCall
    |   writeCall
    |   assignment
    |   whileCycle
    |   ifStatement
;

functionCall
    : IDENTIFIER '(' arguments ')' SEMICOLON
    ;

writeCall
    : 'write' '(' expression ')' SEMICOLON
    ;

parameterNames
    : (IDENTIFIER (',' IDENTIFIER)*)?
    ;

whileCycle
    : 'while' '(' expression ')' blockWithBraces
    ;

ifStatement
    : 'if' '(' expression ')' 'then' blockWithBraces ('else' blockWithBraces)?
    ;

assignment
    : IDENTIFIER ':=' expression SEMICOLON
    ;


expression
    : IDENTIFIER
    | LITERAL
    | innerExpression
    | readExpression

    | leftOp = expression operation = ('*' | '/' | '%') rightOp = expression
    | leftOp = expression operation = ('+' | '-') rightOp = expression
    | leftOp = expression operation = ('<' | '>' | '<=' | '>=') rightOp = expression
    | leftOp = expression operation = ('==' | '!=') rightOp = expression
    | leftOp = expression operation = '&&' rightOp = expression
    | leftOp = expression operation = '||' rightOp = expression
    ;

readExpression
    : 'read' '(' ')'
    ;

innerExpression
    : '(' expression ')'
    ;

arguments
    : (expression (',' expression)*)?
    ;

SEMICOLON : ';';

IDENTIFIER
    : ([a-zA-Z] | '_') ([a-zA-Z] | '_' | [0-9])*
    ;

LITERAL
    :   '0'
    |   '-'? [1-9] [0-9]*
    ;

COMMENTARY
    : '//' ~[\r\n]* -> skip
    ;

WHITESPACE
    : (' ' | '\t' | '\r'| '\n') -> skip
    ;