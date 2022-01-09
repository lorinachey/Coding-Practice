"""
The UnionFind classes. A UnionFind is also known as a Disjoint Set.

Space Complexity -> O(N) space to store the array of size N.
"""

"""
This class represents the QuickFind method which has the following time complexities:
    find() -> O(1)
    union() -> O(N)
    connected() -> O(1)
"""
class QuickFind:

    def __init__(self, size):
        self.root = [i for i in range(size)]

    def find(self, x):
        return self.root[x]

    def union(self, x, y):
        root_x = self.find(x)
        root_y = self.find(y)

        if root_x != root_y:
            for i in range(len(self.root)):
                # Update the root node
                if self.root[i] == root_y:
                    self.root[i] = root_x

    def connected(self, x, y):
        return self.find(x) == self.find(y)


"""
This class represents the QuickUnion method which has the following time complexities:
    find() -> O(N)
    union() -> O(N)
    connected() -> O(N)
    But these are the worse case, and the time complexities can actually be <= O(N) in many cases.
"""
class QuickUnion:

    def __init__(self, size):
        self.root = [i for i in range(size)]

    def find(self, x):
        while x != self.root[x]:
            x = self.root[x]
        return x

    def union(self, x, y):
        root_x = self.find(x)
        root_y = self.find(y)
        if root_x != root_y:
            self.root[root_y] = root_x

    def connected(self, x, y):
        return self.find(x) == self.find(y)
