
import unittest
 
"""
Unit Testing
Testcases for Binary Search Tree
"""
class TestBST(unittest.TestCase):
 
    """
    insertion of new data into BST
    strategy - lexicographically sorted
    """
    def test_insertion(self):
        elements = ["pweo", "qia", "abc", "lkd", "pot"]
 
        strategy = Lexicographic()
 
        # strategy object passed while initializing bst
        # indicates in which order new node needs to be
        # inserted
        bst = BST(elements[0], strategy)
        self.assertEqual(bst.elem, "pweo")
 
        for elem in elements[1:]:
            bst.insert(elem)
 
        self.assertEqual(bst.elem, "pweo")
        self.assertEqual(bst.left.elem, "abc")
        self.assertEqual(bst.right.elem, "qia")
 
 
    """
    insertion of new data into BST
    strategy - reversed string and lexicographically sorted
    """
    def test_strategy(self):
        elements = ["pweo", "qia", "abc", "lkd", "pot"]
 
        strategy = ReverseLexicographic()
 
        # strategy object passed while initializing bst
        # indicates in which order new node needs to be
        # inserted
        bst = BST(elements[0], strategy)
        for elem in elements[1:]:
            bst.insert(elem)
 
        self.assertEqual(bst.elem, "pweo")
        self.assertEqual(bst.left.elem, "qia")
        self.assertEqual(bst.right.elem, "pot")
 
 
    """
    Travesing BST in pre-order fashion
    using visitor
    """
    def test_preorder(self):
        elements = ["pweo", "qia", "abc", "lkd", "pot"]
 
        strategy = Lexicographic()
 
        # strategy object passed while initializing bst
        # indicates in which order new node needs to be
        # inserted
        bst = BST(elements[0], strategy)
 
        # PreOrder object acts as a visitor to existing BST 
        # to traverse BST in pre order fashion
        pre_order = PreOrder()
        self.assertEqual(bst.accept(pre_order), "(pweo()())")
 
        for elem in elements[1:]:
            bst.insert(elem)
 
        self.assertEqual(bst.accept(pre_order), "(pweo(abc()(lkd()(pot()())))(qia()()))")
 
 
 
 
 
"""
Test Program Starts
"""
def main():
    # UnitTest start
    unittest.main()
 
 
if __name__ == "__main__":
    main()
