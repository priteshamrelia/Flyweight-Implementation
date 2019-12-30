from abc import abstractmethod

"""
Interpreter implementation
   Defines a grammer representation of given language context
   Here, stack is used as context to evaluate postfix expression
"""
class Expression():
  """
  Declares an abstract class that is common to all nodes in the abstact syntax tree.
  """
  @abstractmethod
  def interpret(self, context):
    pass

class ExpressionNumber(Expression):
  """
  Implement an interpret operation for number symbols in the grammer.
  """
  def __init__(self, number):
    self.number = number
  def interpret(self, context):
    context.append(self.number)

"""
Implement an interpret operation for operators in the grammer.
Each class uses number of operands as needed from context
"""
class ExpressionPlus(Expression):
  def __init__(self):
    pass
  def interpret(self, context):
    context.append(context.pop() + context.pop())

class ExpressionMinus(Expression):
  def __init__(self):
    pass
  def interpret(self, context):
    context.append(-context.pop() + context.pop())

class ExpressionMultiply(Expression):
  def __init__(self):
    pass
  def interpret(self, context):
    context.append(context.pop() * context.pop())

class ExpressionDivide(Expression):
  def __init__(self):
    pass
  def interpret(self, context):
    context.append( 1.0/context.pop() * (float)(context.pop()) )

class ExpressionLog(Expression):
  def __init__(self):
    pass
  def interpret(self, context):
    context.append(math.log(context.pop()))

class ExpressionSin(Expression):
  def __init__(self):
    pass
  def interpret(self, context):
    context.append(math.sin(context.pop()))

"""
Parser class creates abstract syntax tree and evaluate it using interpreter
described above
"""
class Parser():
  def __init__(self, expression):
    self.parseTree = []
      # Family of expression object corresponds to numerical operators
    self.expressionDict = {
      '+'  : ExpressionPlus(),
      '-'  : ExpressionMinus(),
      '*'  : ExpressionMultiply(),
      '/'  : ExpressionDivide(),
      'lg' : ExpressionLog(),
      'sin': ExpressionSin()
    }
      # token parsing from given expression appropriately token added to
    # syntax tree
    for token in expression.split():
      expr = self.expressionDict.get(token)
      if expr is None:
        self.parseTree.append(ExpressionNumber(float(token)))
      else:
        self.parseTree.append(expr)

  # Each expression from parse tree is interpreted with reference of context
  def evaluate(self):
    context = []
    for exp in self.parseTree:
      exp.interpret(context)
    return context.pop()

