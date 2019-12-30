from abc import ABC, abstractmethod

"""
Strategy implementation to support different criteria 
to insert new node in BST.
Supported Strategy: Lexicographic and Reverse Lexicographic
 
Base class Strategy declares methods to compare new value with 
existing value to decide whether it should go to left node or 
right node.
"""
class Strategy(ABC):
    def __init__(self):
        pass
 
    """
    This method behaves differently in subclasses which extends 
    Strategy class.
    """
    @abstractmethod
    def is_greater(self, val1, val2):
        pass
 
 
"""
This class is responsible to insert node into BST in 
lexicographically.
"""
class Lexicographic():
    def __init__(self):
        pass
 
    """
    New string is compared normally with existing one 
    resulted in inserting new node lexicographically.
    """
    def is_greater(self, val1, val2):
        if val1 > val2:
            return True
        else:
            return False
 
 
"""
This class is responsible to insert node into BST in 
reverse lexicographically.
It means first both strings are reversed and then checked 
lexicographically to determine bigger string.
"""
class ReverseLexicographic():
    def __init__(self):
        pass
 
    """
    New string reversed and existing string is also reversed
    Then both strings are compared and accordingly new string 
    is inserted in left/right node.
    """
    def is_greater(self, val1, val2):
        if val1[::-1] > val2[::-1]:
            return True
        else:
            return False
 
