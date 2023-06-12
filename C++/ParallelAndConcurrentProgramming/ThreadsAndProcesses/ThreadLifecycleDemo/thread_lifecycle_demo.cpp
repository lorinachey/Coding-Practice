/**
 * This demo shows the main thread spawning a child thread which executes
 * some work and then terminates, rejoining the main thread.
*/
#include <thread>
#include <chrono>

void chef_olivia() {
    printf("Olivia is starting work...\n");
    std::this_thread::sleep_for(std::chrono::seconds(2));
    printf("Olivia is done.\n");
}

int main() {
    printf("Barron is cooking and requests Chef Olivia's help!\n");
    std::thread olivia(chef_olivia);
    printf("\tOlivia is joinable? %s\n", olivia.joinable() ? "true" : "false");
    
    printf("Barron continues cooking soup.\n");
    std::this_thread::sleep_for(std::chrono::seconds(1));
    printf("\tOlivia is joinable? %s\n", olivia.joinable() ? "true" : "false");

    printf("Barron patiently waits for Olivia to finish and join...\n");
    olivia.join();
    printf("\tOlivia is joinable? %s\n", olivia.joinable() ? "true" : "false");

    printf("Chef Barron and Chef Olivia are both done!\n");
}
