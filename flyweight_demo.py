import sys
import unittest



'''
This is Flyweight factory that given a character returns the Flyweight
character object for the character.
Each class implements __sizeof__ method in order to calculate how much memory
its data member holds.
'''
class CharacterFactory():

    def __init__(self):
        self.char_array = {}

    def __sizeof__(self):
        size = 0
        for key in self.char_array:
            size += sys.getsizeof(key)
            size += sys.getsizeof(self.char_array[key])
        return size

    '''
    If character is built before, it will reuse it instead of
    creating new one.
    '''
    def get_character(self, char):
        character = None

        if char in self.char_array:
            character = self.char_array[char]
        else:
            character = Character(char)
            self.char_array[char] = character

        return character

    def get_character_array(self):
        return self.char_array


'''
This class stores char as an object. It is used as part of flyweight pattern.
'''
class Character():
    def __init__(self, char):
        self.char = char

    def __sizeof__(self):
        size = 0
        size += sys.getsizeof(self.char)
        return size

    def set_char(self, char):
        self.char = char

    def get_char(self):
        return self.char




'''
This class stores char as an object. It also hold font attribute for
each object. This class is used to compare result with flyweight pattern.
'''
class StyledCharacter():
    def __init__(self, char, font):
        self.char = char
        self.font = font

    def __sizeof__(self):
        size = 0
        size += sys.getsizeof(self.char)
        size += sys.getsizeof(self.font)
        return size

    def set_char(self, char):
        self.char = char

    def get_char(self):
        return self.char




'''
This is Flyweight factory that given attributes for fonts returns the Flyweight
character object for the font.
'''
class Font():
    def __init__(self, name, size, style):
        self.name = name
        self.size = size
        self.style = style

    def __sizeof__(self):
        size = 0
        size += sys.getsizeof(self.name)
        size += sys.getsizeof(self.size)
        size += sys.getsizeof(self.style)
        return size

    def get_name(self):
        return self.name

    def set_name(self, name):
        self.name = name

    def get_size(self):
        return self.size

    def set_size(self, size):
        self.size = size

    def get_style(self):
        return self.style

    def set_style(self, style):
        self.style = style




'''
Runarray stores font objects for each indices.
Supports adding, appending, deleting array of fonts.
'''
class RunArray():
    def __init__(self):
        self.fonts = {}

    def __sizeof__(self):
        size = 0
        for key in self.fonts:
            size += sys.getsizeof(key)
        return size

    '''
    Following functionality supports modification to created font array
    by providing functions to add, append and delete array.
    '''
    def add(self, start_index, count, font):
        end_index = start_index + count
        self.delete_existing(start_index, end_index)
        self.add_index(start_index, end_index, font)

    def append(self, count, font):
        start_index = 0
        for font in self.fonts:
            for indices in self.fonts[font]:
                if indices[1] > start_index:
                    start_index = indices[1]
        self.add_index(start_index, start_index + count, font)

    def add_index(self, start_index, end_index, font):
        if font not in self.fonts:
            self.fonts[font] = [[start_index, end_index]]
        else:
            self.fonts[font].append([start_index, end_index])

    def delete_existing(self, start_index, end_index):
        count = 0

        for font in self.fonts:
            for indices in self.fonts[font]:
                if indices[0] <= start_index or indices[1] >= end_index:
                    if indices[1] < end_index:
                        count = start_index - indices[0]
                        if count > 0:
                            self.add_index(indices[0], start_index - 1, font)
                    elif indices[0] > start_index:
                        count = indices[1] - end_index
                        if count > 0:
                            self.add_index(end_index + 1, indices[1], font)
                    else:
                        count = start_index - indices[0]
                        if count > 0:
                            self.add_index(indices[0], start_index - 1, font)
                self.fonts[font].remove(indices)

    '''
    Returns font object for character at given index
    '''
    def get_font(self, index):
        for font in self.fonts:
            for indices in self.fonts[font]:
                if indices[0] <= index and indices[1] >= index:
                    return font
        return None




'''
This class is responsible to test implementation and purpose of
flyweight design pattern.
Same input string is built using flyweight and non-flyweight pattern.
Test indicates that without flyweight it takes much more memory to store same information.
'''
class TestFlyWeight(unittest.TestCase):

    def compare_memory_size(self):
        input = "CS 635 Advanced Object-Oriented Design & Programming\n" + "Fall Semester, 2018\n" + "Doc 17 Mediator, Flyweight, Facade, Demeter, Active Object\n" + "Nov 19, 2019\n" + "Copyright ©, All rights reserved. 2019 SDSU & Roger Whitney, 5500 Campanile Drive, San" + "Diego, CA 92182-7700 USA. OpenContent (http://www.opencontent.org/opl.shtml) license defines the copyright on this document."

        '''
        This section stores string using flyweight pattern and memory of object is 
        calculated.
        '''
        runs = RunArray()
        runs.add(0, 144, Font("Times", 13, "bold"))
        runs.add(145, 355, Font("Verdana", 10, "italian"))
        runs.append(356, Font("Times", 12, ""))

        char_factory = CharacterFactory()
        chars = []
        flyweight_size = 0
        for index in range(len(input)):
            chars.append( char_factory.get_character(input[index]) )
            flyweight_size += sys.getsizeof(chars[index])


        '''
        This section stores string without using flyweight pattern and memory of object 
        is calculated.
        '''
        chars = []
        non_flyweight_size = 0
        for index in range(len(input)):
            char = Character(input[index])
            font = None
            if index < 145:
                font = Font("Times", 13, "bold")
            elif index < 356:
                font = Font("Verdana", 10, "italian")
            else:
                font = Font("Times", 12, "")
            chars.append( StyledCharacter(input[index], font) )
            non_flyweight_size += sys.getsizeof(chars[index])

        self.assertLess(flyweight_size, non_flyweight_size)




"""
Test Program Starts
"""
def main():
    # UnitTest start
    unittest.main()


if __name__ == "__main__":
    main()

