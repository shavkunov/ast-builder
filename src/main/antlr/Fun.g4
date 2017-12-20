grammar Fun;

file
    :   (function)* block EOF
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
    :   functionCall SEMICOLON
    |   readCall SEMICOLON
    |   writeCall SEMICOLON
    |   assignment SEMICOLON
    |   whileCycle
    |   ifStatement
;

returnStatement
    : 'return' expression
    ;

writeCall
    : 'write' '(' expression ')'
    ;

readCall
    : 'read' '(' IDENTIFIER ')'
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
    : IDENTIFIER ':=' expression
    ;

functionCall
    : IDENTIFIER '(' arguments ')'
    ;

expression
    : IDENTIFIER
        # variableExpression

    |   functionCall
        # functionCallExpression

    | LITERAL
        # literalExpression

    | '(' expression ')'
        # innerExpression

    | leftOp = expression operation = ('*' | '/' | '%') rightOp = expression
        # binaryExpression

    | leftOp = expression operation = ('+' | '-') rightOp = expression
        # binaryExpression

    | leftOp = expression operation = ('<' | '>' | '<=' | '>=') rightOp = expression
        # binaryExpression

    | leftOp = expression operation = ('==' | '!=') rightOp = expression
        # binaryExpression

    | leftOp = expression operation = '&&' rightOp = expression
        # binaryExpression

    | leftOp = expression operation = '||' rightOp = expression
        # binaryExpression
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