from abc import abstractmethod

"""
State implementation for View type
Supported Views: Value and Eqaution
Base class View declares methods to change the state and update cell
content according to state
"""
class View():
  @abstractmethod
  def change_state(self):
    pass
  @abstractmethod  
  def update_cell(self, cell):
    pass

"""
Concrete state class for Value View
Upon request it changes current state to Equation View
Set value in content of cell
"""
class ValueView(View):
  def change_state(self):
    SpreadSheet.state = EquationView()
  def update_cell(self, cell):
    cell.var.set(cell.value)

"""
Concrete state class for Equation View
Upon request it changes current state to Value View
Set expression in content of cell
"""
class EquationView(View):
  def change_state(self):
    SpreadSheet.state = ValueView()
  def update_cell(self, cell):
    cell.var.set(cell.equation)
