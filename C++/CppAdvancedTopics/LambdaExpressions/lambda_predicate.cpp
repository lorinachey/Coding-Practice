/**
 * This file is for practice with lambda expressions.
 * 
 * The code has been modified by Lorin Achey.
 * 
 * The original version is from Bill Weinman's LinkedIn Learning Course:
 * lambda-predicate.cpp by Bill Weinman [bw.org]
 * updated 2022-09-09
*/
#include <format>
#include <algorithm>
#include <vector>
#include <string>

// format-style print()
constexpr void print(const std::string_view str_fmt, auto&&... args) {
    fputs(std::vformat(str_fmt, std::make_format_args(args...)).c_str(), stdout);
}

void print_vector_contents(const auto& vector) {
    if (!vector.size()) return;
    for (auto val : vector) {
        print("{} ", val);
    }
    print("\n");
}

int main() {
    std::vector<int> v1 {1, 7, 4, 9, 4, 8, 12, 10, 20 };

    // Create a predicate
    auto is_div4 = [](int i) { return i % 4 == 0; };

    // Count the numbers divisible by 4
    auto count = std::count_if(v1.begin(), v1.end(), is_div4);
    print("Count is: {}\n", count);

    // Alternatively, pass the predicate directly into the function
    auto count_alt = std::count_if(v1.begin(), v1.end(), [](int i) { return i % 4 == 0; });
    print("Alternatively, count is: {}\n", count_alt);

    // Copy the numbers divisble by 4 into a new vector
    std::vector<int> v2 {};
    std::copy_if(v1.begin(), v1.end(), std::back_inserter(v2), is_div4);
    print_vector_contents(v2);

    auto char_upper_operator = [](char c) -> char {
        if (c >= 'a' && c <= 'z') {
            return c - ('a' - 'A');
        } else {
            return c;
        }
    };

    std::string s1 {"begin doing what you want to do now"};
    std::string s2 {};
    print("\ns1: {}\n", s1);

    // Copy s1 to s2 but use the character upper transform
    std::transform(s1.begin(), s1.end(), std::back_inserter(s2), char_upper_operator);
    print("\ns2: {}\n", s2);
}
