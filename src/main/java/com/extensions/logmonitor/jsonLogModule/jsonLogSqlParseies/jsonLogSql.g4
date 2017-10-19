grammar jsonLogSql ;

fragment A_
	: 'a'
	| 'A' ;

fragment B_
	: 'b'
	| 'B' ;

fragment C_
	: 'c'
	| 'C' ;

fragment D_
	: 'd'
	| 'D' ;

fragment E_
	: 'e'
	| 'E' ;

fragment F_
	: 'f'
	| 'F' ;

fragment G_
	: 'g'
	| 'G' ;

fragment H_
	: 'h'
	| 'H' ;

fragment I_
	: 'i'
	| 'I' ;

fragment J_
	: 'j'
	| 'J' ;

fragment K_
	: 'k'
	| 'K' ;

fragment L_
	: 'l'
	| 'L' ;

fragment M_
	: 'm'
	| 'M' ;

fragment N_
	: 'n'
	| 'N' ;

fragment O_
	: 'o'
	| 'O' ;

fragment P_
	: 'p'
	| 'P' ;

fragment Q_
	: 'q'
	| 'Q' ;

fragment R_
	: 'r'
	| 'R' ;

fragment S_
	: 's'
	| 'S' ;

fragment T_
	: 't'
	| 'T' ;

fragment U_
	: 'u'
	| 'U' ;

fragment V_
	: 'v'
	| 'V' ;

fragment W_
	: 'w'
	| 'W' ;

fragment X_
	: 'x'
	| 'X' ;

fragment Y_
	: 'y'
	| 'Y' ;

fragment Z_
	: 'z'
	| 'Z' ;

keyword
	: TRUE
	| FALSE
	| ALL
	| NOT
	| LIKE
	| IF
	| EXISTS
	| ASC
	| DESC
	| ORDER
	| GROUP
	| BY
	| HAVING
	| WHERE
	| FROM
	| AS
	| SELECT
	| DISTINCT
	| IS
	| NULL
	| ADD
	| BETWEEN
	| AVG
	| COUNT
	| MAX
	| MIN
	| SUM
	| DIVIDE
	| MOD
	| OR
	| AND
	| XOR
	| EQ
	| NOT_EQ
	| LET
	| GET
	| SET_VAR
	| SHIFT_LEFT
	| SHIFT_RIGHT ;

TRUE
	: T_ R_ U_ E_ ;

FALSE
	: F_ A_ L_ S_ E_ ;

ALL
	: A_ L_ L_ ;

NOT
	: N_ O_ T_
	| '!' ;

LIKE
	: L_ I_ K_ E_ ;

IF
	: I_ F_ ;

EXISTS
	: E_ X_ I_ S_ T_ S_ ;

ASC
	: A_ S_ C_ ;

DESC
	: D_ E_ S_ C_ ;

ORDER
	: O_ R_ D_ E_ R_ ;

GROUP
	: G_ R_ O_ U_ P_ ;

BY
	: B_ Y_ ;

HAVING
	: H_ A_ V_ I_ N_ G_ ;

WHERE
	: W_ H_ E_ R_ E_ ;

FROM
	: F_ R_ O_ M_ ;

AS
	: A_ S_ ;

SELECT
	: S_ E_ L_ E_ C_ T_ ;

DISTINCT
	: D_ I_ S_ T_ I_ N_ C_ T_ ;

IS
	: I_ S_ ;

NULL
	: N_ U_ L_ L_ ;

CAST
	: C_ A_ S_ T_ ;

ADD
	: A_ D_ D_ ;

BETWEEN
	: B_ E_ T_ W_ E_ E_ N_ ;

RLIKE
	: R_ L_ I_ K_ E_ ;

REGEXP
	: R_ E_ G_ E_ X_ P_ ;

LIMIT
	: L_ I_ M_ I_ T_ ;

IN
	: I_ N_ ;

DAY
	: D_ A_ Y_ ;

HOUR
	: H_ O_ U_ R_ ;

MINUTE
	: M_ I_ N_ U_ T_ E_ ;

MONTH
	: M_ O_ N_ T_ H_ ;

SECOND
	: S_ E_ C_ O_ N_ D_ ;

YEAR
	: Y_ E_ A_ R_ ;

AVG
	: A_ V_ G_ ;

COUNT
	: C_ O_ U_ N_ T_ ;

MAX
	: M_ A_ X_ ;

MIN
	: M_ I_ N_ ;

SUM
	: S_ U_ M_ ;

// basic token definition ------------------------------------------------------------

DIVIDE
	: (  D_ I_ V_ )
	| '/' ;

MOD
	: (  M_ O_ D_ )
	| '%' ;

OR
	: (  O_ R_ )
	| '||' ;

AND
	: (  A_ N_ D_ )
	| '&&' ;

XOR
	: ( X_ O_ R_ )
	| '^' ;

EQ
	: '='
	| '<=>' ;

NOT_EQ
	: '<>'
	| '!='
	| '~='
	| '^=' ;

LET
	: '<=' ;

GET
	: '>=' ;

SET_VAR
	: ':=' ;

SHIFT_LEFT
	: '<<' ;

SHIFT_RIGHT
	: '>>' ;

SEMI
	: ';' ;

COLON
	: ':' ;

DOT
	: '.' ;

COMMA
	: ',' ;

ASTERISK
	: '*' ;

RPAREN
	: ')' ;

LPAREN
	: '(' ;

RBRACK
	: ']' ;

LBRACK
	: '[' ;

PLUS
	: '+' ;

MINUS
	: '-' ;

NEGATION
	: '~' ;

VERTBAR
	: '|' ;

BITAND
	: '&' ;

POWER_OP
	: '^' ;

GTH
	: '>' ;

LTH
	: '<' ;

Double_Quote
	: '"'
	| '\'' ;

INTEGER_NUM
	: ('0'..'9')+ ;

VARCHAR_NUM
	: ('0'..'9')+ ;

BINARY_NUM
	: ('0'..'9')+ ;

fragment HEX_DIGIT_FRAGMENT
	: ( 'a'..'f' | 'A'..'F' | '0'..'9' ) ;

HEX_DIGIT
	: (  '0x'     (HEX_DIGIT_FRAGMENT)+  )
	| (  'X' '\'' (HEX_DIGIT_FRAGMENT)+ '\''  ) ;

BIT_NUM
	: (  '0b'    ('0'|'1')+  )
	| (  B_ '\'' ('0'|'1')+ '\''  ) ;

REAL_NUMBER
	: (  INTEGER_NUM DOT INTEGER_NUM | INTEGER_NUM DOT | DOT INTEGER_NUM | INTEGER_NUM  )
	(  ('E'|'e') ( PLUS | MINUS )? INTEGER_NUM  )? ;

TEXT_STRING
	: ( N_ | ('_' U_ T_ F_ '8') )?
	(
		(  '\'' ( ('\\' '\\') | ('\'' '\'') | ('\\' '\'') | ~('\'') )* '\''  )
		|
		(  '\"' ( ('\\' '\\') | ('\"' '\"') | ('\\' '\"') | ~('\"') )* '\"'  )
		|
		( ':' ID )
	) ;

ID
	: ( 'A'..'Z' | 'a'..'z' | '_' | '$' | '0'..'9' )+ ;

LINE_COMMENT
	: '//' ~[\r\n]* -> skip ;

BLOCKCOMMENT
	: '/*' .*? '*/' -> skip ;

WHITE_SPACE
	: ( ' '|'\r'|'\t'|'\n' ) -> skip ;

// http://dev.mysql.com/doc/refman/5.6/en/comments.html
SL_COMMENT
	: ( ('--'|'#') ~('\n'|'\r')* '\r'? '\n' ) -> skip ;

Regex_Escaped_Unicode
	: ' ' .. '['
	| ']' .. '~'
	| '\u00A0' .. '\uFFFF' ;

delimited_statement
	: Regex_Escaped_Unicode ;

//-----------------------------------------------------------------Parser part----------------------------------------------------------------------

// basic type definition -----------------------------------------------------------------------
relational_op
	: EQ
	| LTH
	| GTH
	| NOT_EQ
	| LET
	| GET ;

interval_unit
	: SECOND
	| MINUTE
	| HOUR
	| DAY
	| MONTH
	| YEAR ;

// basic const data definition ---------------------------------------------------------------
string_literal
	: TEXT_STRING ;

number_literal
	: (PLUS | MINUS)? (INTEGER_NUM | REAL_NUMBER) ;

hex_literal
	: HEX_DIGIT ;

boolean_literal
	: TRUE
	| FALSE ;

bit_literal
	: BIT_NUM ;

literal_value
	: ( string_literal | number_literal | hex_literal | boolean_literal | bit_literal | NULL ) ;

functionList
	: time_functions ;

time_functions
	: DAY
	| HOUR
	| MINUTE
	| MONTH
	| SECOND
	| YEAR ;

group_functions
	: AVG
	| COUNT
	| MAX
	| MIN
	| SUM ;

table_name
	: any_name ;

column_name
	: any_name ;

alias
	: ( AS )? any_name_exclude_keyword ;

any_name
	: ID
	| keyword
	| string_literal ;

any_name_exclude_keyword
	: ID
	| string_literal ;

expression
	: exp_factor1 ( OR exp_factor1 )* 	#whereOr ;

exp_factor1
	: exp_factor2 ( XOR exp_factor2 )*	#whereXOR ;

exp_factor2
	: predicate ( AND predicate )*		#whereAnd ;

predicate
	: ( simple_expr EQ simple_expr )				#condEq
	| ( simple_expr NOT_EQ simple_expr )			#condNotEq
	| ( simple_expr LTH simple_expr )				#condLt
	| ( simple_expr GTH simple_expr )				#condGt
	| ( simple_expr LET simple_expr )				#condLet
	| ( simple_expr GET simple_expr )				#condGet
	| ( simple_expr (NOT)? IN LPAREN literal_value (COMMA literal_value)* RPAREN )	#condInOrNot
	| ( simple_expr (NOT)? BETWEEN literal_value AND literal_value )	#condBetweenOrNot
	| ( simple_expr (NOT)? LIKE simple_expr  )		#condLikeOrNot
	| ( simple_expr (NOT)? REGEXP simple_expr )		#condRegexpOrNot
	| ( simple_expr ( IS (NOT)? (boolean_literal|NULL)))	#condIsTFNOrNot
	| ( expression_list )							#condSubConds ;

simple_expr
	: literal_value
	| column_spec
	| function_call
	| expression_list ;

function_call
	: (  functionList ( LPAREN (expression (COMMA expression)*)? RPAREN ) ?  )
	| (  group_functions LPAREN ( ASTERISK | ALL | DISTINCT )? (simple_expr|'*') RPAREN  ) ;

column_spec
	: (table_name DOT )* (column_name) ;

expression_list
	: LPAREN expression ( COMMA expression )* RPAREN ;

table_references
	: table_name ( COMMA table_name )* ;

select_statement
	: select_expression (  (ALL | DISTINCT)?  select_expression )* ;

select_expression
	: select_list
	(
		FROM table_references
		( where_clause )?
		( groupby_clause )?
	) ?
	( orderby_clause )?
	( limit_clause )? ';'? ;

where_clause
	: WHERE expression ;

groupby_clause
	: GROUP BY groupby_item (COMMA groupby_item)* ( having_clause )? ;

groupby_item
	: column_spec
	| INTEGER_NUM
	| simple_expr ;

having_clause
	: HAVING expression ;

orderby_clause
	: ORDER BY orderby_item (COMMA orderby_item)*	#orderBy ;

orderby_item
	: groupby_item (ASC | DESC)? ;

limit_clause
	: LIMIT ((offset COMMA)? row_count)	#limit ;

offset
	: INTEGER_NUM ;

row_count
	: INTEGER_NUM ;

select_list
	: SELECT
	( ALL | DISTINCT )? displayed_column ( COMMA displayed_column )* ;

//-----------displayed_column------------------------

displayed_column
	: ASTERISK							#selectAll
	| column_spec DOT ASTERISK			#selectTableDotAll
	| ( column_spec (alias)? )			#selectTableColumn
	| ( function_call (alias)? ) 		#selectFun ;

length
	: INTEGER_NUM ;

varchar_length
	: INTEGER_NUM ;

binary_length
	: INTEGER_NUM ;

