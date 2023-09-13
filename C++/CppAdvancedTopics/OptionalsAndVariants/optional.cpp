/**
 * The original version of this file was written by:
 *  optional.cpp by Bill Weinman [bw.org]
 *  updated 2022-08-30
 * 
 * This version has been modified by Lorin Achey.
 * 
*/
#include <format>
#include <optional>

// format-style print()
constexpr void print(const std::string_view str_fmt, auto&&... args) {
    fputs(std::vformat(str_fmt, std::make_format_args(args...)).c_str(), stdout);
}


///////////
// Before using Optional
///////////
/**
struct return_status {
    long value;
    bool have_value;
};

return_status factor(long n) {
    return_status rs {0, false};
    for (long i = 2; i <= n / 2; ++i) {
        if (n % i == 0) {
            rs.value = i;
            rs.have_value = true;
            return rs;
        }
    }
    return rs;
}
*/




///////////
// After Using Optional
///////////
std::optional<long> factor(long n) {
    for (long i = 2; i <= n / 2; ++i) {
        if (n % i == 0) {
            return i;
        }
    }
    return {};
}

int main() {
    /**
     * Before using Optional:
    long a {42};
    auto x = factor(a);

    if (x.have_value) print("lowest factor of {} is {}\n", a, x.value);
    else print("{} is prime\n", a);
    */

    long a {42};
    auto x = factor(a);

    if (x.has_value()) print("lowest factor of {} is {}\n", a, x.value());
    else print("{} is prime\n", a);

    // The if statement can be simplified even further:
    // if (x) print("lowest factor of {} is {}\n", a, *x);
}
