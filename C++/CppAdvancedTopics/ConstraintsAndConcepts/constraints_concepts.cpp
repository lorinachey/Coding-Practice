/**
 * This file will be used to work with constraints and concepts, new for C++ 20.
 * 
 * This code demonstrates the use of contrainst and concepts to prevent a function
 * that performs an addition operation from being able to produce erroneous results by
 * modifying types it should not be able to modify. For example, adding to a pointer would
 * cause the pointer to be pointing to a different memory address than intended.
 * 
 * We'll constrain our methods to accept integers and floating point types only.
 * 
 * The original file was created by Bill Weinman on updated 2022-08-23. This version
 * has been modified by Lorin Achey.
*/
#include <format>
#include <concepts>

// format-style print()
constexpr void print(const std::string_view str_fmt, auto&&... args) {
    fputs(std::vformat(str_fmt, std::make_format_args(args...)).c_str(), stdout);
}


// Demonstrate the use of concepts to create a constraint with the "requires" keyword
template<typename T>
requires std::integral<T> || std::floating_point<T>
T arg42(const T& arg) {
    return arg + 42;
}


// Alternatively, create a concept and use that in the template
template<typename T>
concept Numeric = std::integral<T> || std::floating_point<T>;

auto arg52(const Numeric auto& arg) {
    return arg + 52;
}


// We can also define our own concepts rather than using the concepts library
template<typename T>
concept NumericTypeTwo = requires(T a) {
    a + 1;
    a * 1;
};

auto arg62(const NumericTypeTwo auto& arg) {
    return arg + 62;
}


int main() {
    // With the requires constraint, this will no longer compile.
    // auto n = "7.9";

    // With the requires constraint, we can only pass an int or float into arg42.
    auto n = 7.9;
    print("The answer is [{}]\n", arg42(n));

    // With the concept we defined, this will not compile.
    // auto p = "8.9";

    // Use the concept we defined with the concepts library
    auto p = 8.9;
    print("The answer is [{}]\n", arg52(p));

    // With the concept we degined, this will not compile.
    // auto q = "9.9";

    // Use the concept we defined without using the concepts library
    auto q = 9.9;
    print("The answer is [{}]\n", arg62(q));
}
