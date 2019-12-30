
import view

#############################################
# Memento implementation
#############################################

class Memento():
  def __init__(self, cell_info):
    self.cell_info = cell_info
  def get_cell_info(self):
    return self.cell_info

class MemCareTaker():
  def __init__(self):
    self.memento_stack = []
  def save(self, cell_info):
    self.memento_stack.append(Memento(cell_info))
  def restore(self):
    if (len(self.memento_stack) == 0):
      return None
    last_state = self.memento_stack.pop()
    return last_state.get_cell_info()
 