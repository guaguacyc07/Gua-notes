#include<stdio.h>

void insert(int *arr, int size, int index, int value);

void main() {
    // 1. 初始化数组(int类型)
    int arr1[5] = {1, 2, 3, 4, 5};
    int arr2[5] = {0}; // 初始化为{0, 0, 0, 0, 0}

    // 2. 访问元素(O(1)时间复杂度)
    printf("arr1[0] = %d\n", arr1[0]); // 输出第一个元素
    printf("arr1[4] = %d\n", arr1[4]); // 输出最后一个元素

    // 3. 输出原始数组
    printf("Original arr1: ");
    for (int i = 0; i < 5; i++) {   
        printf("%d ", arr1[i]);
    }
    printf("\n");

    // 3. 插入元素(O(n)时间复杂度)
    insert(arr1, 5, 2, 99); // 在索引2插入99

    // 4. 输出插入后的数组
    printf("After insertion: ");
    for (int i = 0; i < 6; i++) {
        printf("%d ", arr1[i]);
    }

}

void insert(int arr[], int size, int index, int value) {
    // 3. 插入元素(O(n)时间复杂度)
    if (index < 0 || index > size) {
        printf("Index out of bounds\n");
        return;
    }
    for (int i = size; i > index; i--) {
        arr[i] = arr[i - 1]; // 将元素向后移动
    }
    arr[index] = value; // 插入新元素
}