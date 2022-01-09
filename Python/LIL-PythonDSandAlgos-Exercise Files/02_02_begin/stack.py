"""
Python Data Structures - A Game-Based Approach
Stack class
Robin Andrews - https://compucademy.net/
"""


class Stack:
    def __init__(self):
        self.items = []

    def is_empty(self):
        # Can also do: return not self.items
        return len(self.items) == 0

    def push(self, item):
        self.items.append(item)

    def pop(self):
        return self.items.pop()

    def peek(self):
        # Return the last item in the list
        return self.items[-1]

    def size(self):
        return len(self.items)

    def __str__(self):
        # Allows us to inspect the items
        return str(self.items)


if __name__ == "__main__":
    s = Stack()
    s.push(2)
    s.push(3)
    print(s)
