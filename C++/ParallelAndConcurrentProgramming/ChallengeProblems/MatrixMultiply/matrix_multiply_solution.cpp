/**
 * Challenge: Multiply two matrices and compare the sequential algorithm to the
 * parallel implementation using speedup for the evaluation.
 */
#include <cmath>
#include <cstdint>
#include <thread>

const int NUM_WORKER_THREADS = 4;

// TODO - remove later if unable to get this to compile. The error is coming from
// having a non-constant value used to set array length for worker threads
unsigned int num_threads = std::thread::hardware_concurrency();

void sequential_matrix_multiply(int64_t ** A, size_t num_rows_a, size_t num_cols_a,
                                int64_t ** B, size_t num_rows_b, size_t num_cols_b,
                                int64_t ** C) {
    for (size_t i=0; i < num_rows_a; i++) {
        for (size_t j=0; j < num_cols_b; j++) {
            C[i][j] = 0;  // initialize result cell to zero
            for (size_t k=0; k < num_cols_a; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
}

void parallel_worker(int64_t ** A, size_t num_rows_a, size_t num_cols_a,
                     int64_t ** B, size_t num_rows_b, size_t num_cols_b,
                     int64_t ** C, size_t start_row_c, size_t end_row_c) {
    // Iterate through the subset of rows in A
    for (size_t i = start_row_c; i < end_row_c; i++) {
        for (size_t j = 0; j < num_cols_b; j++) {
            // Initialize the array
            C[i][j] = 0;
            for (size_t k = 0; k < num_cols_a; k++) {
                // Recall that the row of A is multiplied by a column in B
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
}

void parallel_matrix_multiply(int64_t ** A, size_t num_rows_a, size_t num_cols_a,
                              int64_t ** B, size_t num_rows_b, size_t num_cols_b,
                              int64_t ** C) {
    // We'll use agglomeration so that we don't have too many independent tasks if we're computing a large matrix.
    // This means breaking the matrix computation into chunks of rows based on the number of worker threads available.
    size_t chunk_size = ceil(static_cast<float>(num_rows_a/NUM_WORKER_THREADS));
    std::thread worker_threads[NUM_WORKER_THREADS];

    for (size_t i = 0; i < NUM_WORKER_THREADS; i++) {
        size_t start_row_c = std::min(i * chunk_size, num_rows_a);
        size_t end_row_c = std::min((i + 1) * chunk_size, num_rows_a);
        worker_threads[i] = std::thread(parallel_worker, A, num_rows_a, num_cols_a,
                                                         B, num_rows_b, num_cols_b,
                                                         C, start_row_c, end_row_c);
    }
    for (auto &worker : worker_threads) {
        worker.join();
    }
}

int main() {
    const int NUM_EVAL_RUNS = 3;
    const size_t NUM_ROWS_A = 1000;
    const size_t NUM_COLS_A = 1000;
    const size_t NUM_ROWS_B = NUM_COLS_A;
    const size_t NUM_COLS_B = 1000;

    // intialize A with values in range 1 to 100
    int64_t ** A = (int64_t **)malloc(NUM_ROWS_A * sizeof(int64_t *));
    if (A == NULL) {
        exit(2);
    }
    for (size_t i=0; i < NUM_ROWS_A; i++) {
        A[i] = (int64_t *)malloc(NUM_COLS_A * sizeof(int64_t));
        if (A[i] == NULL) {
            exit(2);
        }
        for (size_t j=0; j < NUM_COLS_A; j++) {
            A[i][j] = rand() % 100 + 1;
        }
    }

    // intialize B with values in range 1 to 100   
    int64_t ** B = (int64_t **)malloc(NUM_ROWS_B * sizeof(int64_t *));
    if (B == NULL) {
        exit(2);
    }
    for (size_t i=0; i < NUM_ROWS_B; i++) {
        B[i] = (int64_t *)malloc(NUM_COLS_B * sizeof(int64_t));
        if (B[i] == NULL) {
            exit(2);
        }
        for (size_t j=0; j < NUM_COLS_B; j++) {
            B[i][j] = rand() % 100 + 1;
        }
    }

    // allocate arrays for sequential and parallel results
    int64_t ** sequential_result = (int64_t **)malloc(NUM_ROWS_A * sizeof(int64_t *));
    int64_t ** parallel_result = (int64_t **)malloc(NUM_ROWS_A * sizeof(int64_t *));
    if ((sequential_result == NULL) || (parallel_result == NULL)) {
        exit(2);
    }
    for (size_t i=0; i < NUM_ROWS_A; i++) {
        sequential_result[i] = (int64_t *)malloc(NUM_COLS_B * sizeof(int64_t));
        parallel_result[i] = (int64_t *)malloc(NUM_COLS_B * sizeof(int64_t));
        if ((sequential_result[i] == NULL) || (parallel_result[i] == NULL)) {
            exit(2);
        }
    }

    printf("Evaluating Sequential Implementation...\n");
    std::chrono::duration<double> sequential_time(0);
    sequential_matrix_multiply(A, NUM_ROWS_A, NUM_COLS_A, B, NUM_ROWS_B, NUM_COLS_B, sequential_result);  // "warm up"
    for (int i=0; i < NUM_EVAL_RUNS; i++) {
        auto startTime = std::chrono::high_resolution_clock::now();
        sequential_matrix_multiply(A, NUM_ROWS_A, NUM_COLS_A, B, NUM_ROWS_B, NUM_COLS_B, sequential_result);
        sequential_time += std::chrono::high_resolution_clock::now() - startTime;
    }
    sequential_time /= NUM_EVAL_RUNS;

    printf("Evaluating Parallel Implementation...\n");
    std::chrono::duration<double> parallel_time(0);
    parallel_matrix_multiply(A, NUM_ROWS_A, NUM_COLS_A, B, NUM_ROWS_B, NUM_COLS_B, parallel_result);  // "warm up"
    for (int i=0; i < NUM_EVAL_RUNS; i++) {
        auto startTime = std::chrono::high_resolution_clock::now();
        parallel_matrix_multiply(A, NUM_ROWS_A, NUM_COLS_A, B, NUM_ROWS_B, NUM_COLS_B, parallel_result);
        parallel_time += std::chrono::high_resolution_clock::now() - startTime;
    }
    parallel_time /= NUM_EVAL_RUNS;

    // verify sequential and parallel results
    for (size_t i=0; i < NUM_ROWS_A; i++) {
        for (size_t j=0; j < NUM_COLS_B; j++) {
            if (sequential_result[i][j] != parallel_result[i][j]) {
                printf("ERROR: Result mismatch at row %ld, col %ld!\n", i, j);
            }
        }
    }
    printf("Average Sequential Time: %.2f ms\n", sequential_time.count()*1000);
    printf("  Average Parallel Time: %.2f ms\n", parallel_time.count()*1000);
    printf("Speedup: %.2f\n", sequential_time/parallel_time);
    printf("Efficiency %.2f%%\n", 100*(sequential_time/parallel_time)/std::thread::hardware_concurrency());
}