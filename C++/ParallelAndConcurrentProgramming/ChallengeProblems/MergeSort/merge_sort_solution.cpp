/**
 * Challenge: Sort an array of random integers with merge sort.
 * 
 * This file contains both a sequential merge_sort implementation and a
 * parallel merge_sort implementation. The Speedup is calculated as part of
 * the execution.
 */
#include <thread>
#include <cmath>


/* declaration of merge helper function */
void merge(int * array, unsigned int left, unsigned int mid, unsigned int right);

/* sequential implementation of merge sort */
void sequential_merge_sort(int * array, unsigned int left, unsigned int right) {
    if (left < right) {
        unsigned int mid = (left + right) / 2;       // find the middle point
        sequential_merge_sort(array, left, mid);     // sort the left half
        sequential_merge_sort(array, mid+1, right);  // sort the right half
        merge(array, left, mid, right);              // merge the two sorted halves
    }
}

/* parallel implementation of merge sort */
void parallel_merge_sort(int * array, unsigned int left, unsigned int right, unsigned int depth=0) {
    if (depth >= std::log(std::thread::hardware_concurrency())) {
        // TODO: uncomment the following print statement if you want to check the base case values
        // printf("Base case reached - depth: %u and hw concurrency: %u \n", depth, std::thread::hardware_concurrency());
        sequential_merge_sort(array, left, right);
    } else {
        // Use another thread to perform the subarray breakdown on the left subarray
        unsigned int mid = (left + right) / 2;
        std::thread left_thread = std::thread(parallel_merge_sort, array, left, mid, depth + 1);
        parallel_merge_sort(array, mid + 1, right, depth + 1);
        left_thread.join();
        merge(array, left, mid, right);
    }
}

/* helper function to merge two sorted subarrays
   array[l..m] and array[m+1..r] into array */
void merge(int * array, unsigned int left, unsigned int mid, unsigned int right) {
    // Get the number of elements in the left and the right subarrays
    unsigned int num_left = (mid + 1) - left;
    unsigned int num_right = right - mid;

    // Copy the data into temporary left and right subarrays that will be merged
    int array_left[num_left];
    int array_right[num_right];
    std::copy(&array[left], &array[mid+1], array_left);
    std::copy(&array[mid + 1], &array[right + 1], array_right);

    // Initialize starting indeces
    unsigned int lt_index = 0;
    unsigned int rt_index = 0;
    unsigned int insert_index = left;

    while ((lt_index < num_left) || (rt_index < num_right)) {
        if ((lt_index < num_left) && (rt_index < num_right)) {
            if (array_left[lt_index] <= array_right[rt_index]) {
                array[insert_index] = array_left[lt_index];
                lt_index++;
            } else {
                array[insert_index] = array_right[rt_index];
                rt_index++;
            }
        } else if (lt_index < num_left) {
            array[insert_index] = array_left[lt_index];
            lt_index++;
        } else if (rt_index < num_right) {
            array[insert_index] = array_right[rt_index];
            rt_index++;
        }
        insert_index++;
    }
}

int main() {
    const int NUM_EVAL_RUNS = 100;
    const int N = 100000;  // number of elements to sort

    int original_array[N], sequential_result[N], parallel_result[N];
    for (int i=0; i < N; i++) {
        original_array[i] = rand();
    }

    printf("Evaluating Sequential Implementation...\n");
    std::chrono::duration<double> sequential_time(0);
    std::copy(&original_array[0], &original_array[N-1], sequential_result);
    sequential_merge_sort(sequential_result, 0, N-1);  // "warm up"
    for (int i=0; i < NUM_EVAL_RUNS; i++) {
        std::copy(&original_array[0], &original_array[N-1], sequential_result);  // reset result array
        auto start_time = std::chrono::high_resolution_clock::now();
        sequential_merge_sort(sequential_result, 0, N-1);
        sequential_time += std::chrono::high_resolution_clock::now() - start_time;
    }
    sequential_time /= NUM_EVAL_RUNS;

    printf("Evaluating Parallel Implementation...\n");
    std::chrono::duration<double> parallel_time(0);
    std::copy(&original_array[0], &original_array[N-1], parallel_result);
    parallel_merge_sort(parallel_result, 0, N-1);  // "warm up"
    for (int i=0; i < NUM_EVAL_RUNS; i++) {
        std::copy(&original_array[0], &original_array[N - 1], parallel_result);  // reset result array
        auto start_time = std::chrono::high_resolution_clock::now();
        parallel_merge_sort(parallel_result, 0, N-1);
        parallel_time += std::chrono::high_resolution_clock::now() - start_time;
    }
    parallel_time /= NUM_EVAL_RUNS;

    // verify sequential and parallel results are same
    for (int i=0; i < N; i++) {
        if (sequential_result[i] != parallel_result[i]) {
            printf("ERROR: Result mismatch at index %d!\n", i);
        }
    }
    printf("Average Sequential Time: %.2f ms\n", sequential_time.count()*1000);
    printf("  Average Parallel Time: %.2f ms\n", parallel_time.count()*1000);
    printf("Speedup: %.2f\n", sequential_time/parallel_time);
    printf("Efficiency %.2f%%\n", 100*(sequential_time/parallel_time)/std::thread::hardware_concurrency());
}