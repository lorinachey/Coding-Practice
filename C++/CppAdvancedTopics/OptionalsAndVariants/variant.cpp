/**
 * The original version of this file was written by:
 *  variant.cpp by Bill Weinman [bw.org]
 *  updated 2022-10-28
 * 
 * This version has been modified by Lorin Achey.
*/
#include <format>
#include <string_view>
#include <vector>
#include <variant>

using std::string_view;

// format-style print
constexpr void print(const std::string_view str_fmt, auto&&... args) {
    fputs(std::vformat(str_fmt, std::make_format_args(args...)).c_str(), stdout);
}

constexpr auto print_newline = []{ print("\n"); };

// Define the Animal base class that all the animal types will inherit from
class Animal {
    string_view _name {};
    string_view _sound {};
 public:
    // Prevent instances of the Animal class being created with this constructor
    Animal() = delete;

    Animal(string_view name, string_view sound) : _name{ name }, _sound{ sound } {};

    void speak() const {
        print("\t{} makes the sounds {}\n", _name, _sound);
    }
};

class Cat : public Animal {
 public:
    Cat(string_view name) : Animal(name, "meow") {}
};

class Dog : public Animal {
 public:
    Dog(string_view name) : Animal(name, "woof") {}
};

class Wookie : public Animal {
 public:
    Wookie(string_view name) : Animal(name, "roarrrrr") {}
};

// This will be used to demonstrate the use of std::visit
struct animal_speaks {
    void operator()(const Cat& cat) const {cat.speak();}
    void operator()(const Dog& dog) const {dog.speak();}
    void operator()(const Wookie& wookie) const {wookie.speak();}
};

int main() {
    print_newline();

    // Create an alias to use for our vector of animals
    using v_animal = std::variant<Cat, Dog, Wookie>;
    std::vector<v_animal> pets { Cat{"Mavis"}, Dog{"Beacon"}, Wookie{"Teaches"}, Dog{"Typing"} };

    print("Using the get method...\n");
    for (const v_animal& animal : pets) {
        auto index = animal.index();
        if (index == 0) get<Cat>(animal).speak();
        if (index == 1) get<Dog>(animal).speak();
        if (index == 2) get<Wookie>(animal).speak();
    }
    print_newline();

    print("Using the get_if method...\n");
    for (const v_animal& animal : pets) {
        if (const auto& object = get_if<Cat>(&animal); object) object->speak();
        else if (const auto& object = get_if<Dog>(&animal); object) object->speak();
        else if (const auto& object = get_if<Wookie>(&animal); object) object->speak();
    }
    print_newline();

    print("Using the visit method...\n");
    for (const v_animal& animal : pets) {
        std::visit(animal_speaks{}, animal);
    }
}

