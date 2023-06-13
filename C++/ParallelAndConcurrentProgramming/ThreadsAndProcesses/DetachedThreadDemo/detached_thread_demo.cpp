/**
 * Use a detached thread for Olivia's garbage collection
 * process so that Barron isn't stuck waiting until she
 * finishes.
*/
#include <thread>
#include <chrono>

void kitchen_garbage_collection() {
    while (true) {
        printf("Olivia is cleaning the kitchen...\n");
        std::this_thread::sleep_for(std::chrono::seconds(2));
    }
}

int main() {
    std::thread olivia(kitchen_garbage_collection);
    olivia.detach();

    for (int i=0; i < 4; i++) {
        printf("Barron is cooking...\n");
        std::this_thread::sleep_for(std::chrono::milliseconds(300));
    }

    printf("Barron is done!\n");
}
