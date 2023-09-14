/**
 * The original version of this file was written by:
 *  any.cpp by Bill Weinman [bw.org]
 *  updated 2022-10-28
 * 
 * This version has been modified by Lorin Achey.
*/
#include <any>
#include <format>
#include <string>
#include <vector>


// format-style print()
constexpr void print(const std::string_view str_fmt, auto&&... args) {
    fputs(std::vformat(str_fmt, std::make_format_args(args...)).c_str(), stdout);
}

void print_any(const std::any& object) {
    if (!object.has_value()) {
        print("None\n");
    } else if (object.type() == typeid(int)) {
        auto x = std::any_cast<int>(object);
        print("int {}\n", x);
    } else if (object.type() == typeid(std::string)) {
        auto x = std::any_cast<const std::string&>(object);
        print("string {}\n", x);
    } else if (object.type() == typeid(std::vector<int>)) {
        auto v = std::any_cast<const std::vector<int>&>(object);
        print("vector of int, {} elements: ", v.size());
        for (const auto& e : v) print("{} ", e);
        print("\n");
    } else {
        print("I don't handle this type (compiler type string: {})\n", object.type().name());
    }
}

int main() {
    print_any(42);
    print_any(std::string{"47"});
    print_any(std::vector{1, 2, 3, 4, 5});
    print_any(std::vector{1.0, 2.0, 3.0, 4.0, 5.0});
}
