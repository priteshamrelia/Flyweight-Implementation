from abc import ABC, abstractmethod

"""
Order implements different type of tree traversal.
Supported traversal: PreOrder
 
Base class Order declares methods to traverse tree 
based on type of node.
"""
class Order(ABC):
    def __init__(self):
        pass
 
    """
    Implements a traversal algorithm to traverse tree in different fashion.
    """
    @abstractmethod
    def visit(self, bst):
        pass
 
 
"""
PreOrder implements traversal of BST in pre-order fashion
"""
class PreOrder(Order):
    def __init__(self):
        pass
 
    """
    Implements a traversal algorithm to traverse tree in pre-order fashion.
    It means root comes first, then left node and finally right node is visited.
    """
    def visit(self, bst):
        if isinstance(bst, NullNode):
            return "()"
        else:
            return "(" + bst.elem + self.visit(bst.left) \
                + self.visit(bst.right) + ")"
 
