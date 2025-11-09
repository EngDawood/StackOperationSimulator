package com.mycompany.stackoperationsimulator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive test suite for StackDemo class.
 *
 * This test class covers all scenarios outlined in Step 7 of the testing requirements:
 * - Push operations (single, multiple, overflow, negative, zero, large numbers)
 * - Pop operations (single, multiple, underflow, LIFO verification)
 * - Peek operations (with elements, empty stack, multiple peeks)
 * - Size and isEmpty operations
 * - Constructor validation
 */
class StackDemoTest {

    private StackDemo stack;
    private static final int TEST_CAPACITY = 5;

    @BeforeEach
    void setUp() {
        stack = new StackDemo(TEST_CAPACITY);
    }

    // ============================================================
    // Constructor Tests
    // ============================================================

    @Test
    @DisplayName("Constructor should create stack with valid capacity")
    void testConstructorWithValidCapacity() {
        StackDemo testStack = new StackDemo(10);
        assertTrue(testStack.isEmpty());
        assertEquals(0, testStack.size());
    }

    @Test
    @DisplayName("Constructor should throw exception for zero capacity")
    void testConstructorWithZeroCapacity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StackDemo(0);
        });
        assertEquals("Capacity must be positive", exception.getMessage());
    }

    @Test
    @DisplayName("Constructor should throw exception for negative capacity")
    void testConstructorWithNegativeCapacity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new StackDemo(-5);
        });
        assertEquals("Capacity must be positive", exception.getMessage());
    }

    // ============================================================
    // Push Operation Tests
    // ============================================================

    @Test
    @DisplayName("Push single element and verify")
    void testPushSingleElement() {
        stack.push(42);
        assertFalse(stack.isEmpty());
        assertEquals(1, stack.size());
        assertEquals(42, stack.peek());
    }

    @Test
    @DisplayName("Push multiple elements sequentially")
    void testPushMultipleElements() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(3, stack.size());
        assertEquals(30, stack.peek());

        int[] elements = stack.getElements();
        assertArrayEquals(new int[]{10, 20, 30}, elements);
    }

    @Test
    @DisplayName("Push until stack is full")
    void testPushUntilFull() {
        for (int i = 1; i <= TEST_CAPACITY; i++) {
            stack.push(i * 10);
        }

        assertTrue(stack.isFull());
        assertEquals(TEST_CAPACITY, stack.size());
        assertEquals(50, stack.peek()); // Last element pushed
    }

    @Test
    @DisplayName("Attempt to push when stack is full throws overflow exception")
    void testPushOverflow() {
        // Fill the stack
        for (int i = 0; i < TEST_CAPACITY; i++) {
            stack.push(i);
        }

        // Attempt to push one more
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            stack.push(999);
        });

        assertEquals("Stack Overflow", exception.getMessage());
        assertEquals(TEST_CAPACITY, stack.size()); // Size unchanged
    }

    @Test
    @DisplayName("Push negative numbers")
    void testPushNegativeNumbers() {
        stack.push(-10);
        stack.push(-25);
        stack.push(-100);

        assertEquals(3, stack.size());
        assertEquals(-100, stack.peek());

        int[] elements = stack.getElements();
        assertArrayEquals(new int[]{-10, -25, -100}, elements);
    }

    @Test
    @DisplayName("Push zero")
    void testPushZero() {
        stack.push(0);
        stack.push(5);
        stack.push(0);

        assertEquals(3, stack.size());
        assertEquals(0, stack.peek());

        int[] elements = stack.getElements();
        assertArrayEquals(new int[]{0, 5, 0}, elements);
    }

    @Test
    @DisplayName("Push large numbers")
    void testPushLargeNumbers() {
        stack.push(Integer.MAX_VALUE);
        stack.push(1000000);
        stack.push(Integer.MIN_VALUE);

        assertEquals(3, stack.size());
        assertEquals(Integer.MIN_VALUE, stack.peek());
    }

    @Test
    @DisplayName("Push mixed positive, negative, and zero values")
    void testPushMixedValues() {
        stack.push(100);
        stack.push(-50);
        stack.push(0);
        stack.push(75);
        stack.push(-25);

        assertEquals(5, stack.size());
        int[] elements = stack.getElements();
        assertArrayEquals(new int[]{100, -50, 0, 75, -25}, elements);
    }

    // ============================================================
    // Pop Operation Tests
    // ============================================================

    @Test
    @DisplayName("Pop from stack with multiple elements")
    void testPopWithMultipleElements() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        int poppedValue = stack.pop();

        assertEquals(30, poppedValue);
        assertEquals(2, stack.size());
        assertEquals(20, stack.peek());
    }

    @Test
    @DisplayName("Pop until stack is empty")
    void testPopUntilEmpty() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());

        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Attempt to pop from empty stack throws underflow exception")
    void testPopUnderflow() {
        assertTrue(stack.isEmpty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            stack.pop();
        });

        assertEquals("Stack Underflow", exception.getMessage());
    }

    @Test
    @DisplayName("Verify LIFO order - last pushed is first popped")
    void testLIFOOrder() {
        // Push elements in sequence
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        // Pop elements and verify reverse order
        assertEquals(5, stack.pop());
        assertEquals(4, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());

        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Pop after filling and verify LIFO with specific values")
    void testPopAfterFilling() {
        int[] values = {100, 200, 300, 400, 500};

        // Push all values
        for (int value : values) {
            stack.push(value);
        }

        // Pop and verify in reverse order
        for (int i = values.length - 1; i >= 0; i--) {
            assertEquals(values[i], stack.pop());
        }
    }

    // ============================================================
    // Peek Operation Tests
    // ============================================================

    @Test
    @DisplayName("Peek at stack with elements - verify no removal")
    void testPeekWithElements() {
        stack.push(100);
        stack.push(200);

        int peekedValue = stack.peek();

        assertEquals(200, peekedValue);
        assertEquals(2, stack.size()); // Size should remain unchanged
        assertEquals(200, stack.peek()); // Should still be the same value
    }

    @Test
    @DisplayName("Peek at empty stack throws exception")
    void testPeekEmptyStack() {
        assertTrue(stack.isEmpty());

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            stack.peek();
        });

        assertEquals("Stack is empty", exception.getMessage());
    }

    @Test
    @DisplayName("Peek multiple times returns same value")
    void testPeekMultipleTimes() {
        stack.push(42);

        assertEquals(42, stack.peek());
        assertEquals(42, stack.peek());
        assertEquals(42, stack.peek());

        // Verify size hasn't changed
        assertEquals(1, stack.size());
    }

    @Test
    @DisplayName("Peek after push and pop operations")
    void testPeekAfterPushPop() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertEquals(30, stack.peek());

        stack.pop();
        assertEquals(20, stack.peek());

        stack.pop();
        assertEquals(10, stack.peek());
    }

    // ============================================================
    // Size Operation Tests
    // ============================================================

    @Test
    @DisplayName("Size after each push operation")
    void testSizeAfterPush() {
        assertEquals(0, stack.size());

        stack.push(10);
        assertEquals(1, stack.size());

        stack.push(20);
        assertEquals(2, stack.size());

        stack.push(30);
        assertEquals(3, stack.size());
    }

    @Test
    @DisplayName("Size after each pop operation")
    void testSizeAfterPop() {
        stack.push(10);
        stack.push(20);
        stack.push(30);
        assertEquals(3, stack.size());

        stack.pop();
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());

        stack.pop();
        assertEquals(0, stack.size());
    }

    @Test
    @DisplayName("Size after mixed push and pop operations")
    void testSizeAfterMixedOperations() {
        assertEquals(0, stack.size());

        stack.push(1);
        stack.push(2);
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());

        stack.push(3);
        stack.push(4);
        assertEquals(3, stack.size());

        stack.pop();
        stack.pop();
        assertEquals(1, stack.size());
    }

    // ============================================================
    // isEmpty Operation Tests
    // ============================================================

    @Test
    @DisplayName("isEmpty returns true for new stack")
    void testIsEmptyForNewStack() {
        StackDemo newStack = new StackDemo(10);
        assertTrue(newStack.isEmpty());
    }

    @Test
    @DisplayName("isEmpty returns false after push")
    void testIsEmptyAfterPush() {
        assertTrue(stack.isEmpty());

        stack.push(42);

        assertFalse(stack.isEmpty());
    }

    @Test
    @DisplayName("isEmpty returns true after popping all elements")
    void testIsEmptyAfterPoppingAll() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        assertFalse(stack.isEmpty());

        stack.pop();
        stack.pop();
        stack.pop();

        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("isEmpty after various operations")
    void testIsEmptyAfterVariousOperations() {
        assertTrue(stack.isEmpty());

        stack.push(1);
        assertFalse(stack.isEmpty());

        stack.pop();
        assertTrue(stack.isEmpty());

        stack.push(2);
        stack.push(3);
        assertFalse(stack.isEmpty());
    }

    // ============================================================
    // isFull Operation Tests
    // ============================================================

    @Test
    @DisplayName("isFull returns false for new stack")
    void testIsFullForNewStack() {
        assertFalse(stack.isFull());
    }

    @Test
    @DisplayName("isFull returns true when stack reaches capacity")
    void testIsFullWhenAtCapacity() {
        for (int i = 0; i < TEST_CAPACITY; i++) {
            assertFalse(stack.isFull());
            stack.push(i);
        }

        assertTrue(stack.isFull());
    }

    @Test
    @DisplayName("isFull returns false after popping from full stack")
    void testIsFullAfterPop() {
        // Fill the stack
        for (int i = 0; i < TEST_CAPACITY; i++) {
            stack.push(i);
        }

        assertTrue(stack.isFull());

        stack.pop();

        assertFalse(stack.isFull());
    }

    // ============================================================
    // getElements Operation Tests
    // ============================================================

    @Test
    @DisplayName("getElements returns empty array for empty stack")
    void testGetElementsEmptyStack() {
        int[] elements = stack.getElements();

        assertNotNull(elements);
        assertEquals(0, elements.length);
    }

    @Test
    @DisplayName("getElements returns correct array for stack with elements")
    void testGetElementsWithElements() {
        stack.push(10);
        stack.push(20);
        stack.push(30);

        int[] elements = stack.getElements();

        assertArrayEquals(new int[]{10, 20, 30}, elements);
    }

    @Test
    @DisplayName("getElements returns independent copy of stack contents")
    void testGetElementsReturnsIndependentCopy() {
        stack.push(100);
        stack.push(200);

        int[] elements1 = stack.getElements();
        int[] elements2 = stack.getElements();

        // Verify they have the same values
        assertArrayEquals(elements1, elements2);

        // Verify they are different array instances
        assertNotSame(elements1, elements2);

        // Modify one array
        elements1[0] = 999;

        // Verify the modification doesn't affect the stack
        int[] elements3 = stack.getElements();
        assertEquals(100, elements3[0]);
    }

    @Test
    @DisplayName("getElements after full stack operations")
    void testGetElementsAfterFullStackOperations() {
        // Fill the stack
        for (int i = 1; i <= TEST_CAPACITY; i++) {
            stack.push(i * 10);
        }

        int[] elements = stack.getElements();

        assertEquals(TEST_CAPACITY, elements.length);
        assertArrayEquals(new int[]{10, 20, 30, 40, 50}, elements);
    }

    // ============================================================
    // Integration Tests - Complex Scenarios
    // ============================================================

    @Test
    @DisplayName("Complex scenario: Push, pop, peek, and size operations")
    void testComplexScenario1() {
        // Start with empty stack
        assertTrue(stack.isEmpty());
        assertEquals(0, stack.size());

        // Push some elements
        stack.push(5);
        stack.push(10);
        stack.push(15);
        assertEquals(3, stack.size());
        assertEquals(15, stack.peek());

        // Pop one element
        assertEquals(15, stack.pop());
        assertEquals(2, stack.size());

        // Push more elements
        stack.push(20);
        stack.push(25);
        assertEquals(4, stack.size());

        // Verify LIFO order
        assertEquals(25, stack.pop());
        assertEquals(20, stack.pop());
        assertEquals(10, stack.pop());
        assertEquals(5, stack.pop());

        // Stack should be empty
        assertTrue(stack.isEmpty());
    }

    @Test
    @DisplayName("Complex scenario: Fill, empty, and refill stack")
    void testComplexScenario2() {
        // Fill the stack
        for (int i = 1; i <= TEST_CAPACITY; i++) {
            stack.push(i);
        }
        assertTrue(stack.isFull());

        // Empty the stack
        for (int i = 0; i < TEST_CAPACITY; i++) {
            stack.pop();
        }
        assertTrue(stack.isEmpty());

        // Refill with different values
        for (int i = 10; i <= 10 + TEST_CAPACITY - 1; i++) {
            stack.push(i);
        }

        assertTrue(stack.isFull());
        assertEquals(14, stack.peek());
    }

    @Test
    @DisplayName("Complex scenario: Alternating push and pop operations")
    void testComplexScenario3() {
        stack.push(1);
        assertEquals(1, stack.size());

        stack.push(2);
        assertEquals(2, stack.size());

        assertEquals(2, stack.pop());
        assertEquals(1, stack.size());

        stack.push(3);
        stack.push(4);
        assertEquals(3, stack.size());

        assertEquals(4, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(1, stack.size());

        assertFalse(stack.isEmpty());
        assertEquals(1, stack.peek());
    }

    @Test
    @DisplayName("Boundary test: Stack with capacity of 1")
    void testStackWithCapacityOne() {
        StackDemo tinyStack = new StackDemo(1);

        assertTrue(tinyStack.isEmpty());
        assertFalse(tinyStack.isFull());

        tinyStack.push(42);

        assertFalse(tinyStack.isEmpty());
        assertTrue(tinyStack.isFull());
        assertEquals(42, tinyStack.peek());

        // Cannot push another
        assertThrows(IllegalStateException.class, () -> {
            tinyStack.push(99);
        });

        assertEquals(42, tinyStack.pop());
        assertTrue(tinyStack.isEmpty());
    }

    @Test
    @DisplayName("Boundary test: Large capacity stack")
    void testLargeCapacityStack() {
        StackDemo largeStack = new StackDemo(1000);

        // Push 1000 elements
        for (int i = 0; i < 1000; i++) {
            largeStack.push(i);
        }

        assertTrue(largeStack.isFull());
        assertEquals(1000, largeStack.size());

        // Pop all elements and verify LIFO
        for (int i = 999; i >= 0; i--) {
            assertEquals(i, largeStack.pop());
        }

        assertTrue(largeStack.isEmpty());
    }
}
