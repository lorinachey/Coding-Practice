/**
 * This file is a demo of using threads. In this program,
 * two "choppers" each get their own thread where the number of 
 * vegetables chopped is counted.
*/
#include <thread>
#include <chrono>

bool chopping = true;

void vegetable_chopper(const char* name) {
    unsigned int vegetable_count = 0;
    while (chopping) {
        vegetable_count++;
    }
    printf("%s chopped %u vegetables\n", name, vegetable_count);
}

int main() {
    std::thread olivia(vegetable_chopper, "OLIVIA");
    std::thread barron(vegetable_chopper, "BARRON");

    printf("BARRON and OLIVIA are chopping veggies...\n");
    std::this_thread::sleep_for(std::chrono::seconds(2));
    chopping = false;

    olivia.join();
    barron.join();
}