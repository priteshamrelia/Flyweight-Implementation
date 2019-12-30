from abc import abstractmethod
import tkinter as tk
import math
import re


"""
GUI with Observer and Memento implementation
SpreadSheet is primary class which stores list of Cell objects
"""
class Cell():
  # Class variable representing regular expression to detect cell names from expression
  # To keep common regular expression to all cells, it is created as class variable
  cellre = re.compile(r'\b[A-Z]\b')
    # Resonsible to create editable cell in GUI using tkinter Entry layout
  # It is bound calculate method to evaluate cell's expression when cell is focused out
  # Moving to neighbour cells is supported using nevigation arrow keys
  def __init__(self, i, siblings, parent, mem_care_taker):
    self.row = i
    self.siblings = siblings
    self.mem_care_taker = mem_care_taker
    self.name = self.cellname(i)
    self.equation = '0.0'
    self.value = 0.0
      # Set of observers - must be updated if content of this cell changes
    self.observes = set()
    
    # Set of subjects - values required for this cell to calculate
    self.subjects = set()
      # Code snippet to create editable cell using tkinter's Entry layout
    self.var = tk.StringVar()
    entry = self.widget = tk.Entry(parent,
                                  textvariable=self.var,
                                  justify='right')
      # Method binding on various keyboard input
    entry.bind('<FocusIn>', self.edit)
    entry.bind('<FocusOut>', self.calculate)
    entry.bind('<Up>', self.move(-1))
    entry.bind('<Down>', self.move(+1))
    entry.bind('<Return>', self.move(+1))
    self.update_text()

  # Each cell is assigned name to access it in O(1) time from dictionary
  def cellname(self, i):
    return f'{chr(ord("A")+i)}'

  # Method move focus of pointer to cell given by value of rowadvance
  def move(self, rowadvance):
    targetrow = (self.row + rowadvance) % Nrows
    def focus(event):
      targetwidget = self.siblings[self.cellname(targetrow)].widget
      targetwidget.focus()
    return focus

  # Move pointer to given cell and select entire text - makes easy modification
  def edit(self, event):
    self.widget.select_range(0, tk.END)

  # On clicking enter key, calcuate method is executed
  # Current state is stored in memento and then expression is evaluated
  # After evaluation, all observers are notified to update themselved
  def calculate(self, event):
    self.mem_care_taker.save( [self, self.value, self.equation] )
    self.equation = self.var.get()
 
    if not self.valid_input():
      return
    # Evaluate itself and notify all observers
    if self.update():
      self.notify()

  # First it decodes list of observers from cell expression and then update itself
  # by evaluating expression using implementation interpreter if no cyclic dependancy
  # found
  def update(self):
    # Find list of observer cell using regular expression
    if not self.valid_input():
      return
  
    currentdeps = set(self.cellre.findall(self.equation))
    
    # check for cyclic dependency
    # If presents, mark all cell including its observers with text "Error"
    # Also return false indicating update failed for this cell
    self.update_deps(currentdeps)
    if self.is_cyclic_dependent(currentdeps):
      self.equation = "Error"
      self.update_text()
      for req in self.subjects:
        self.siblings[req].update()
      return False
  
    # Look up the values of subject cells
    reqvalues = {str(r): self.siblings[r].value for r in currentdeps}
      # Replace subject cells by its name
    equation = self.parse_equation(reqvalues)
      # Call parser to parse value using interpreter and set text of cell
    self.value = Parser(equation).evaluate()
    self.update_text()
  
    return True

  # Notify all observers to update themselves
  def notify(self):
    for d in self.observes:
      self.siblings[d].update()
      self.siblings[d].notify()

  # if user keeps cell empty, consider it as a valid input with value 0.0
  # In case of cyclic dependancy, do not process cell with "Error" textvalue
  def valid_input(self):
    if self.equation == "Error":
      return False
  
    if self.equation is "":
      self.equation = '0.0'
    
    return True

 
  # as per state, cell's content is adjusted to either value of expression
  def update_text(self):
    SpreadSheet.state.update_cell(self)

  # If expression of cell contains name of other cell, it is replaced by value of that
  # cell. At the end, expression is converted in numerical postfix expression
  def parse_equation(self, reqvalues):
    equation = self.equation
    for elem in equation:
      if(elem in reqvalues.keys()):
        equation = equation.replace(elem, str(reqvalues[elem]))
    return equation

  # Update list of observer each time cell value is updated
  # Extra observer which were not present previously are added
  # set substraction identifies whether to add or remove observers
  def update_deps(self, currentdeps):
      
    # Add this cell to the new requirement's observors
    for r in currentdeps - self.subjects:
      self.siblings[r].observes.add(self.name)
      
    # Remove this cell from dependents no longer referenced
    for r in self.subjects - currentdeps:
      self.siblings[r].observes.remove(self.name)

    self.subjects = currentdeps

  # Detect cyclic dependancy within cells and displays Error in such cell
  def is_cyclic_dependent(self, currentdeps):
    visited = {}
    for sibling in self.siblings.keys():
      visited[sibling] = False
  
    deps_list = list(currentdeps)
    src_cellname = self.name
  
    # check all cell's dependancy and if it mathces with current cell
    # that means current cell is pointing to itself through some cells
    # Mark all such cells as cyclic dependent cell
    while len(deps_list) != 0:
      cell = deps_list.pop(0)
      if not visited[cell]:
        deps_cell = self.siblings[cell].subjects
        for deps in deps_cell:
          if not visited[deps]:
            if deps == src_cellname:
              return True
            deps_list.append(deps)
        visited[cell] = True
  
    return False

"""
Spreadsheet class contains two buttons and few cells.
Both buttons are configured with multiple key event listeners to do operations
on created cells
"""
class SpreadSheet(tk.Frame):
  # Initial state of SpreadSheet.
  # It is declared as class variable so that other class can access it
  # without object creation
  state = EquationView()
    # Spreadsheet creates members to hold property values of spreadsheets
  # It also call create widgets which creates GUI containers.
  def __init__(self, rows, master=None):
    super().__init__(master)
    self.rows = rows
    self.cells = {}
    self.pack()
    self.mem_care_taker = MemCareTaker()
    self.create_widgets()

  # create_widget creates GUI frame first and within frame, it creates two button
  # named Undo and View
  # Below Button bar, nine rows are generated with two columns. First column has
  # label of cell and second column has editable textfiled of cell
  def create_widgets(self):
      # Frame for all the cells
    self.cellframe = tk.Frame(self)
    self.cellframe.pack(side='top')
      # Button to reverse changes in first row and first column
    self.undo_btn = tk.Button(self.cellframe, text="Undo", command=self.undo)
    self.undo_btn.grid(row=0, column=0)
      # Button for switching between views in first row and second column
    self.change_view_btn = tk.Button(self.cellframe, text="Value View", command=self.toggle_view)
    self.change_view_btn.grid(row=0, column=1)
      # Create label and editable cell in each row
    for i in range(self.rows):
      self.create_label(i, 0)
      self.create_cell(i, 1)

  # Creates label in given row and col
  # Cell label is given alphabatically as per row value
  # 'A', 'B', 'C'... represents 0th, 1st, 2nd... row respectively
  def create_label(self, row, col):
    rowlabel = tk.Label(self.cellframe, text=chr(ord('A')+row))
    rowlabel.grid(row=row+1, column=col)

  # Creates editable cell in given row and col
  # Created cell is mapped to its label using dictionary
  def create_cell(self, row, col):
    cell = Cell(row, self.cells, self.cellframe, self.mem_care_taker)
    cell.widget.grid(row=row+1, column=col)
    self.cells[cell.name] = cell

  # View button on click listener implementation
  def toggle_view(self):
    SpreadSheet.state.change_state()
    self.update_text()
    for cell in self.cells.values():
      cell.update()

  # Text of button depends on current state
  # Function helps to identify current state and set value to button accordingly
  def update_text(self):
    btn_text = "Equation View"
    if isinstance(SpreadSheet.state, EquationView):
      btn_text = "Value View"
    self.change_view_btn.config(text=btn_text)

  # Undo button on click listener implementation
  # Using memento design pattern, it maintains stack of previous states.
  # It restores last spreadsheet state and set value and equation using restored
  # cell object
  def undo(self):
    cell_info = self.mem_care_taker.restore()
    if cell_info is None:
      return

    # Restore last updated cell's value and expression
    cell = self.cells[cell_info[0].name]
    cell.equation = cell_info[2]
    cell.value = cell_info[1]
    cell.update_text()

    # Set focus to last updated cell
    cell.edit(None)
    cell.notify()
