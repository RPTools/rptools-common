tree grammar MTScriptTreeParser;

options {
  language = Java;
  tokenVocab = MTScript;
  ASTLabelType = CommonTree;
}

@header {
    package net.rptools.parser.tree;
    
    import net.rptools.parser.symboltable.SymbolTable;
    
}

@members {
    private SymbolTable symbolTable;
    
    private net.rptools.parser.tree.ScriptNode scriptNode;
    
    
    public void setSymbolTable(SymbolTable stable) {
        symbolTable = stable;
    }
}

evaluator   returns [net.rptools.parser.tree.ScriptTreeNode node] 
  : { scriptNode = new net.rptools.parser.tree.ScriptNode(); }
    statement+ { node = scriptNode; }
  ;

  
statement
  : (asNode = assignment) { 
      ((net.rptools.parser.tree.ScriptNode)scriptNode).addStatement(asNode);
    }
  | (expNode = expression) { 
      ((net.rptools.parser.tree.ScriptNode)scriptNode).addStatement(expNode);
    }
  ;
  

assignment returns [net.rptools.parser.tree.ScriptTreeNode node] 
  : ^(ASSIGNMENT VARIABLE id=Identifier n=expression) { 
        node = new net.rptools.parser.tree.AssignVariableNode(id.toString(), n);
    }
  | ^(ASSIGNMENT PROPERTY id=Identifier n=expression) {
        node = new net.rptools.parser.tree.AssignPropertyNode(id.toString(), n);
    }
  ;
  
expression returns [net.rptools.parser.tree.ScriptTreeNode node]
    : ^('+' op1=expression op2=expression) { 
        node = net.rptools.parser.tree.BinaryMathOpNode.getAddNode(op1, op2);
      }
    | ^('-' op1=expression op2=expression) { 
        node = net.rptools.parser.tree.BinaryMathOpNode.getSubtractNode(op1, op2);
      }
    | ^('*' op1=expression op2=expression) { 
        node = net.rptools.parser.tree.BinaryMathOpNode.getMultiplyNode(op1, op2);
      }
    | ^('/' op1=expression op2=expression) {
        node = net.rptools.parser.tree.BinaryMathOpNode.getDivideNode(op1, op2);
      }
    | ^('%' op1=expression op2=expression) { 
        node = net.rptools.parser.tree.BinaryMathOpNode.getRemainderNode(op1, op2);
      }   
    | ^('^' op1=expression op2=expression) {
        node = net.rptools.parser.tree.BinaryMathOpNode.getPowerNode(op1, op2);
      }   
    | ^(UNARY_MINUS n=expression) {
        node = new net.rptools.parser.tree.NegateNode(n);
      }
    | i=Integer {
        node = new net.rptools.parser.tree.ConstantNode(java.lang.Integer.parseInt(i.toString())); 
      } 
    | Number { 
        node = new net.rptools.parser.tree.ConstantNode(Double.parseDouble($Number.toString())); 
      }
    | ^(l=Label expr=expression) { 
        node = new net.rptools.parser.tree.LabelNode(l.toString(), expr);
      }
    | ^(REPEAT_SUM_GROUP i=Integer? expr=expression) {
        int times;
        if (i == null) {
            times = 1;
        } else {
            times = java.lang.Integer.parseInt(i.toString());
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getRepeatSumNode(times, expr);
      }
      | ^(REPEAT_SUM_GROUP VARIABLE Identifier expr=expression) {
        int times;
        if (i == null) {
            times = 1;
        } else {
            times = java.lang.Integer.parseInt(i.toString());
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getVariableRepeatSumNode($Identifier.getText(), expr);
       }
     | ^(REPEAT_SUM_GROUP PROPERTY Identifier expr=expression) {
        int times;
        if (i == null) {
            times = 1;
        } else {
            times = java.lang.Integer.parseInt(i.toString());
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getPropertyRepeatSumNode($Identifier.getText(), expr);
       }  
      | ^(REPEAT_SUM_GROUP PROMPT StringLiteral? expr=expression) {
        String prompt;
        if ($StringLiteral == null) {
            prompt = null;
        } else {
            prompt = $StringLiteral.getText();
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getPromptRepeatSumNode(prompt, expr);
       }  
    | ^(REPEAT_GROUP i=Integer? expr=expression) {
        int times;
        if (i == null) {
            times = 1;
        } else {
            times = java.lang.Integer.parseInt(i.toString());
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getRepeatNode(times, expr);
      }    
     | ^(REPEAT_GROUP VARIABLE Identifier expr=expression) {
        int times;
        if (i == null) {
            times = 1;
        } else {
            times = java.lang.Integer.parseInt(i.toString());
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getVariableRepeatNode($Identifier.getText(), expr);
       }
     | ^(REPEAT_GROUP PROPERTY Identifier expr=expression) {
        int times;
        if (i == null) {
            times = 1;
        } else {
            times = java.lang.Integer.parseInt(i.toString());
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getPropertyRepeatNode($Identifier.getText(), expr);
       }  
     | ^(REPEAT_GROUP PROMPT StringLiteral? expr=expression) {
        String prompt;
        if ($StringLiteral == null) {
            prompt = null;
        } else {
            prompt = $StringLiteral.getText();
        }
        node = net.rptools.parser.tree.RepeatGroupNode.getPromptRepeatNode(prompt, expr);
       }  
    | VARIABLE Identifier  {
        node = new net.rptools.parser.tree.VariableNode($Identifier.toString());
      }
    | PROPERTY Identifier {
        node = new net.rptools.parser.tree.PropertyNode($Identifier.toString());
      }
    | ROLL { 
        node = new net.rptools.parser.tree.RollNode($ROLL.toString());
      }
    | ^('&' op1=expression op2=expression) {
        node = new net.rptools.parser.tree.ListConcatNode(op1, op2);
      }
    | ^(PROMPT Identifier StringLiteral?) {
        String prompt;
        if ($StringLiteral == null) {
            prompt = null;
        } else {
            prompt = $StringLiteral.getText();
        }
        node = new net.rptools.parser.tree.PromptVariableNode($Identifier.toString(), prompt);
    }
    | s=StringLiteral { 
        node = new net.rptools.parser.tree.ConstantNode(s.toString()); 
      }
    | ^(FUNCTION_CALL func=Identifier alist=functionArguments?) {
        if (alist == null) {
          alist = new net.rptools.parser.tree.FunctionArgumentList();
        }

        node = new net.rptools.parser.tree.FunctionCallNode(func.getText(), alist);
    }
   ;

functionArguments returns [net.rptools.parser.tree.FunctionArgumentList args]
  : el=expressionList nel=namedExpressionList? {
      args = new net.rptools.parser.tree.FunctionArgumentList();

      for (net.rptools.parser.tree.ScriptFunctionArgument a : el) {
          args.addArgument(a);
      }
      
      if (nel != null) {
        for (net.rptools.parser.tree.ScriptFunctionArgument a : nel) {
            args.addArgument(a);
        }
      }
  }
  | nel=namedExpressionList {
      args = new net.rptools.parser.tree.FunctionArgumentList();
      
     for (net.rptools.parser.tree.ScriptFunctionArgument a : nel) {
         args.addArgument(a);
      }
  }
  ;

  
expressionList returns [List<net.rptools.parser.tree.ScriptFunctionArgument> argList]
  @init {
      argList = new ArrayList<net.rptools.parser.tree.ScriptFunctionArgument>();
  }
  : (e=expression {
      net.rptools.parser.tree.ScriptFunctionArgument a = new net.rptools.parser.tree.ScriptFunctionArgument(e);
      argList.add(a);
  })+
  ;

namedExpressionList returns [List<net.rptools.parser.tree.ScriptFunctionArgument> argList]
  @init {
      argList = new ArrayList<net.rptools.parser.tree.ScriptFunctionArgument>();
  }
  : (a=namedExpression {
      argList.add(a);
  })+
  ;
  

namedExpression returns [net.rptools.parser.tree.ScriptFunctionArgument arg]
  : ^(NAMED_EXPRESSION name=Identifier e=expression) {
      arg = new net.rptools.parser.tree.ScriptFunctionArgument(name.getText(), e);
  }
  ;