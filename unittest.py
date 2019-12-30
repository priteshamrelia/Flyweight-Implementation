import unittest

"""
Unite Testing
"""

# Testcases for Parser which is part of interpreter implementation
class TestExpression(unittest.TestCase):
    def test_expression(self):
        context = [15, 5]
        exp = ExpressionPlus()
        exp.interpret(context)
        self.assertEqual(context.pop(), 20.0)

        context = [15, 5]
        exp = ExpressionMinus()
        exp.interpret(context)
        self.assertEqual(context.pop(), 10.0)

        context = [15, 5]
        exp = ExpressionMultiply()
        exp.interpret(context)
        self.assertEqual(context.pop(), 75.0)

        context = [15, 5]
        exp = ExpressionDivide()
        exp.interpret(context)
        self.assertEqual(context.pop(), 3.0)

        context = [15]
        exp = ExpressionSin()
        exp.interpret(context)
        self.assertEqual(context.pop(), 0.6502)

        context = [15]
        exp = ExpressionLog()
        exp.interpret(context)
        self.assertEqual(context.pop(), 2.708)

# Testcases for Parser which is part of interpreter implementation
class TestParser(unittest.TestCase):
    def test_parser(self):
        parser = None
        
        parser = Parser("91 65 + 40 - ")
        self.assertEqual(parser.evaluate(), 116.0)
        
        parser = Parser("3 9 6 * + sin")
        self.assertEqual(parser.evaluate(), 0.43616)
        
        parser = Parser("45 sin lg")
        self.assertEqual(parser_obj1.evaluate(), -0.1614)

# Testcases for SpreadSheet which implements GUI design
# 
class TestSpreadSheet(unittest.TestCase):

    # One cell perform some arithmatic expression based on value of other cell
    def test_calculate_cell_input(self):
      frame = tk.Tk()
      spreadsheet = SpreadSheet(9, frame)
      spreadsheet.cells['A'].equation = "10"
      spreadsheet.cells['A'].calculate(None)
      spreadsheet.cells['B'].equation = "A 2 *"    # Makes B = 20
      spreadsheet.cells['B'].calculate(None)
      spreadsheet.cells['C'].equation = "A B *"    # Makes C = 200
      spreadsheet.cells['C'].calculate(None)
      
      self.assertEqual(spreadsheet.cells['B'].equation, "A 2 *")
      self.assertEqual(spreadsheet.cells['B'].value, "20")
      self.assertEqual(spreadsheet.cells['C'].equation, "A B *")
      self.assertEqual(spreadsheet.cells['C'].value, "200")

    # Cells are dependent on each other directly or indirectly
    def test_cyclic_dependency(self):
      frame = tk.Tk()
      spreadsheet = SpreadSheet(9, frame)
      spreadsheet.cells['A'].equation = "B 2 +"    # A depends on B
      spreadsheet.cells['A'].calculate(None)
      spreadsheet.cells['B'].equation = "C 1 -"    # B depends on C
      spreadsheet.cells['B'].calculate(None)
      spreadsheet.cells['C'].equation = "A 12 *"    # C depends on A and creates a cycle
      spreadsheet.cells['C'].calculate(None)

      self.assertEqual(spreadsheet.cells['A'].value, "Error")
      self.assertEqual(spreadsheet.cells['B'].value, "Error")
      self.assertEqual(spreadsheet.cells['C'].value, "Error")

    # Changing value of cell recalculate value of dependent cell
    def test_cell_dependency(self):
      frame = tk.Tk()
      spreadsheet = SpreadSheet(9, frame)
      spreadsheet.cells['A'].equation = "15"    # A = 15
      spreadsheet.cells['A'].calculate(None)
      spreadsheet.cells['B'].equation = "4"    # B = 4
      spreadsheet.cells['B'].calculate(None)
      spreadsheet.cells['C'].equation = "A B *"    # C = A*B = 60
      spreadsheet.cells['C'].calculate(None)

      self.assertEqual(spreadsheet.cells['C'].value, "60.0")

      spreadsheet.cells['B'].equation = "6"    # B = 6
      spreadsheet.cells['B'].calculate(None)
      self.assertEqual(spreadsheet.cells['C'].value, "90.0") # C = A*B = 90

    # Undo mechanism, restoring last state of spreadsheet
    def test_undo(self):
      frame = tk.Tk()
      spreadsheet = SpreadSheet(9, frame)
      spreadsheet.cells['A'].equation = "200"    # A = 200
      spreadsheet.cells['A'].calculate(None)
      spreadsheet.cells['B'].equation = "40"    # B = 40
      spreadsheet.cells['B'].calculate(None)
      spreadsheet.cells['C'].equation = "A B /"    # C = A/B = 5
      spreadsheet.cells['C'].calculate(None)

      spreadsheet.cells['B'].equation = "20"    # B = 20 and makes C = 10
      spreadsheet.cells['B'].calculate(None)

      spreadsheet.cells['A'].equation = "150"    # A 150 and makes C = 7.5
      spreadsheet.cells['A'].calculate(None)

      self.assertEqual(spreadsheet.cells['A'].value, "150")
      self.assertEqual(spreadsheet.cells['B'].value, "20")
      self.assertEqual(spreadsheet.cells['C'].value, "7.5")

      spreadsheet.undo()    # A's value is reverted back to 200
      self.assertEqual(spreadsheet.cells['A'].value, "200")
      self.assertEqual(spreadsheet.cells['B'].value, "20")
      self.assertEqual(spreadsheet.cells['C'].value, "10")

      spreadsheet.undo()    # B's value is reverted back to 40
      self.assertEqual(spreadsheet.cells['A'].value, "200")
      self.assertEqual(spreadsheet.cells['B'].value, "40")
      self.assertEqual(spreadsheet.cells['C'].value, "5")

    # By rewriting equation by value, cell is no more dependent on any other cell
    def test_equation_overwrite(self):
      frame = tk.Tk()
      spreadsheet = SpreadSheet(9, frame)
      spreadsheet.cells['A'].equation = "10"
      spreadsheet.cells['A'].calculate(None)
      spreadsheet.cells['B'].equation = "A 1 +"    # Makes B = 11
      spreadsheet.cells['B'].calculate(None)

      self.assertEqual(spreadsheet.cells['B'].value, "11")
      self.assertEqual(spreadsheet.cells['B'].equation, "A 1 +")

      spreadsheet.cells['B'].equation = "1"    # Makes B = 1 overwriting equation

      self.assertEqual(spreadsheet.cells['B'].value, "1")
      self.assertEqual(spreadsheet.cells['B'].equation, "1")

#############################################
# Test Program
#
def main():
  # GUI Frame with SpreadSheet Creation
  root = tk.Tk()
  Nrows = 9
  app = SpreadSheet(Nrows, master=root)
  app.mainloop()

  # UnitTest start
  unittest.main()


if __name__ == "__main__":
  main()

