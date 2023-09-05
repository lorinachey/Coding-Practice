/**
 * The original version of this file was written by:
 *  container.cpp by Bill Weinman [bw.org]
 *  updated 2022-10-26
 * 
 * This version has been modified by Lorin Achey.
 * 
 * This file contains a class called "container" which is a simple wrapper
 * class that will be used to demonstrate the use of different constructors.
*/
#include <format>
#include <string>
#include <vector>
#include <initializer_list>
#include <utility>

using std::string;

// Use a constexpr to indicate this can be computed at compile-time rather than runtime
constexpr void print(const std::string_view str_fmt, auto&&... args) {
    fputs(std::vformat(str_fmt, std::make_format_args(args...)).c_str(), stdout);
}

template<typename T>
class container {
    std::vector<T> contained_items {};
public:
    container() { print("Default constructor\n"); }
    container(std::initializer_list<T> initial_list);
    container(const container& rhs);  // Original copy constructor

    // Move constructor - && makes this the rvalue reference. We need the noexcept to prevent
    // the rvalue from being in an unusable state
    container(container&& rhs) noexcept;

    ~container();
    void reset();
    container<T>& operator = (const container& rhs);
    string str() const;
};

// Initializer list constructor
template<typename T>
container<T>::container(std::initializer_list<T> initial_list) : contained_items {initial_list.begin(), initial_list.end()} {
    print("Initializer list constructor\n");
}

// Copy constructor
template<typename T>
container<T>::container(const container& rhs) : contained_items {rhs.contained_items} {
    print("Copy constructor\n");
}

// Move constructor
template<typename T>
container<T>::container(container&& rhs) noexcept : contained_items {std::move(rhs.contained_items)} {
    print("Move constructor\n");
}

// Copy assignment operator
template<typename T>
container<T>& container<T>::operator= (const container& rhs) {
    print("Copy assignment operator\n");
    if (this != &rhs) {
        contained_items = rhs.contained_items;
    }
    return *this;
}

// Destructor
template<typename T>
container<T>::~container() {
    print("Destructor\n");
}

// Reset/Empty the list
template<typename T>
void container<T>::reset() {
    contained_items.clear();
}

// Get a string representation of the list
template<typename T>
string container<T>::str() const {
    string out {};
    if (contained_items.empty()) {
        return "[EMPTY]";
    }
    for (auto item : contained_items) {
        if (out.size()) {
            out += ":";
        }
        out += format("{}", item);
    }
    return out;
}

int main() {
    container<string> a {"one", "two", "three", "four", "five"};
    container<string> b {"five", "six", "seven"};

    print("a: {}\n", a.str());
    print("b: {}\n", b.str());

    container c(std::move(a));
    print("a: {}\n", a.str());
    print("c: {}\n", c.str());
}
