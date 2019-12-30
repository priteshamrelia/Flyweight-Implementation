from abc import ABC, abstractmethod

 
"""
Binary Search Tree implementation (BST)
Node Data - String
"""
class Node(ABC):
    """
    Declares an abstract class that is common to all 
    nodes in the binary search tree.
    """
    def __init__(self, strategy):
        pass
 
    """
    Implement an insertion operation for new node into BST.
    """
    @abstractmethod
    def insert(self, elem):
        pass
 
    """
    Implement an accept operation to support tree traversal.
    """
    @abstractmethod
    def accept(self, order):
        pass
 
 
"""
Null Node defines operations for empty nodes in 
Binary Search Tree.
"""
class NullNode(Node):
    def __init__(self, strategy):
        self.strategy = strategy
 
    """
    Implement an insertion operation for Null Node.
    Existing Null Node is converted to TreeNode.
    """
    def insert(self, elem):
        return BST(elem, self.strategy)
 
    """
    Implement an accept operation for Null Node.
    Actually it does nothing. TreeNode handles accepting visitor.
    """
    def accept(self, order):
        pass
 
 
"""
Tree Node defines operations for actual nodes who holds data in 
Binary Search Tree.
"""
class BST(Node):
    """
    TreeNode creates two empty nodes (left and right) along with 
    string data and also holds a object of strategy object which 
    decides insertion order.
    """
    def __init__(self, elem, strategy):
        self.strategy = strategy
        self.elem = elem
        self.left = NullNode(strategy)
        self.right = NullNode(strategy)
 
    """
    Implements an insertion operation for Null Node.
    Existing Null Node is converted to TreeNode.
    """
    def insert(self, elem):
        if self.strategy.is_greater(elem, self.elem):
            self.right = self.right.insert(elem)
        else:
            self.left = self.left.insert(elem)
        return self
 
    """
    Implements an accept operation for Null Node.
    Actually it does nothing. TreeNode handles accepting visitor.
    """
    def accept(self, order):
        return order.visit(self)
 
