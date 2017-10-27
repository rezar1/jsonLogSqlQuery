grammar json ;

jsonFile
	: json+ #jsonFileRoot;

json
	: object	#objectPart
	| array		#arrayPart ;

object
	: '{' pair (',' pair)* '}'	#objPair
	| '{' '}' 					#emptyObj ;

array
	: '[' value (',' value)* ']'	#arrayValues
	| '[' ']' 						#emptyArray ;

pair
	: STRING	':'	value	#keyValue ;

value
	: STRING		#stringValue
	| NUMBER		#numberValue
	| object 		#subObject 	//递归调用
	| array			#subArray	//递归调用
	| 'true'		#trueValue
	| 'false'		#falseValue
	| 'null'		#nullValue ;

STRING	
	: '"' (ESC | ~["\\])* '"' ;

NUMBER	
	: '-'? INT '.' INT EXP? //注意这里不是左递归 , 无法匹配类似 1.3.4e5的数字
	| '-'? INT EXP
	| '-'? INT ;

fragment ESC
	: '\\' (["\\/bfnrt] | UNICODE) ;

fragment UNICODE
	: 'u'	HEX HEX HEX HEX ;

fragment HEX
	: [0-9A-FA-F] ;

fragment INT	//除0外的数字不允许以0开头.
	: '0'
	| [1-9] [0-9]* ;

fragment EXP
	: [Ee] [+\-]? INT  //\-是对-的转义,因为在 []里面 -表示的是范围.
	;

WS
	: [ \t\n\r]+ -> skip ;

