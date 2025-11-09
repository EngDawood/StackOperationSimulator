package com.mycompany.stackoperationsimulator;

/**
 * Array-based implementation of a stack data structure.
 *
 * <p>This class provides a fixed-capacity stack that follows the LIFO
 * (Last-In-First-Out) principle. Elements are stored in an internal array,
 * and a top pointer tracks the index of the most recently added element.</p>
 *
 * <p>The stack has a fixed capacity set at construction time. Attempting to
 * push beyond this capacity will result in a stack overflow exception.</p>
 *
 * <p><strong>Key characteristics:</strong></p>
 * <ul>
 *   <li>All operations (push, pop, peek) execute in O(1) constant time</li>
 *   <li>Fixed capacity - cannot grow beyond initial size</li>
 *   <li>LIFO behavior - last element pushed is first element popped</li>
 *   <li>Proper handling of overflow and underflow conditions</li>
 * </ul>
 *
 * @author StackOperationSimulator
 * @version 1.0
 */
public class StackDemo {

    /**
     * Internal array to store stack elements.
     */
    private double[] array;

    /**
     * Index of the top element in the stack.
     * A value of -1 indicates an empty stack.
     */
    private int top = -1;

    /**
     * Maximum number of elements the stack can hold.
     */
    private int capacity;

    /**
     * Constructs a new StackDemo with the specified capacity.
     *
     * <p>The stack is initialized as empty with top set to -1.
     * The internal array is created with the specified capacity.</p>
     *
     * @param capacity the maximum number of elements this stack can hold
     * @throws IllegalArgumentException if capacity is less than or equal to zero
     */
    public StackDemo(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive");
        }
        this.capacity = capacity;
        this.array = new double[capacity];
        this.top = -1;
    }

    /**
     * Checks if the stack is empty.
     *
     * <p>A stack is considered empty when the top pointer equals -1,
     * which is its initial state and the state after all elements are popped.</p>
     *
     * @return true if the stack contains no elements, false otherwise
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * Checks if the stack is full.
     *
     * <p>A stack is considered full when the top pointer equals capacity minus 1,
     * meaning all available positions in the internal array are occupied.</p>
     *
     * @return true if the stack has reached its maximum capacity, false otherwise
     */
    public boolean isFull() {
        return top == capacity - 1;
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * <p>The top pointer is incremented first, then the element is placed
     * at the new top position. This ensures proper LIFO ordering.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     * StackDemo stack = new StackDemo(5);
     * stack.push(10);  // Stack: [10]
     * stack.push(20);  // Stack: [10, 20]
     * stack.push(30);  // Stack: [10, 20, 30]
     * </pre>
     *
     * @param element the double value to push onto the stack
     * @throws IllegalStateException if the stack is full (overflow condition)
     */
    public void push(double element) {
        if (isFull()) {
            throw new IllegalStateException("Stack Overflow");
        }
        // Increment top first, then assign element
        // This maintains proper indexing where top always points to the last element
        top++;
        array[top] = element;
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * <p>The element at the current top position is retrieved and stored,
     * then the top pointer is decremented to point to the previous element.
     * This implements the LIFO removal behavior.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     * StackDemo stack = new StackDemo(5);
     * stack.push(10);
     * stack.push(20);
     * stack.push(30);
     * int value = stack.pop();  // Returns 30, Stack: [10, 20]
     * value = stack.pop();       // Returns 20, Stack: [10]
     * </pre>
     *
     * @return the element at the top of the stack
     * @throws IllegalStateException if the stack is empty (underflow condition)
     */
    public double pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack Underflow");
        }
        // Store the element before decrementing top
        // This ensures we return the correct value
        double element = array[top];
        top--;
        return element;
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * <p>This is a non-destructive operation that allows viewing the top
     * element while maintaining the current stack state. The top pointer
     * remains unchanged.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     * StackDemo stack = new StackDemo(5);
     * stack.push(10);
     * stack.push(20);
     * int value = stack.peek();  // Returns 20, Stack unchanged: [10, 20]
     * value = stack.peek();       // Returns 20 again, Stack: [10, 20]
     * </pre>
     *
     * @return the element at the top of the stack
     * @throws IllegalStateException if the stack is empty
     */
    public double peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty");
        }
        return array[top];
    }

    /**
     * Returns the current number of elements in the stack.
     *
     * <p>Since top is -1 for an empty stack and increases by 1 for each push,
     * the size is always top + 1. This provides O(1) size calculation.</p>
     *
     * <p><strong>Boundary cases:</strong></p>
     * <ul>
     *   <li>Empty stack (top = -1): returns 0</li>
     *   <li>One element (top = 0): returns 1</li>
     *   <li>Full stack (top = capacity - 1): returns capacity</li>
     * </ul>
     *
     * @return the number of elements currently in the stack
     */
    public int size() {
        return top + 1;
    }

    /**
     * Returns the maximum capacity of the stack.
     *
     * @return the maximum number of elements the stack can hold
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Returns a copy of all elements currently in the stack.
     *
     * <p>Creates a new array containing only the valid elements (from index 0 to top).
     * This method is primarily used for visualization purposes, allowing external
     * components to display the stack contents without direct access to the
     * internal array.</p>
     *
     * <p>The returned array has elements in bottom-to-top order, where index 0
     * represents the bottom of the stack and the last index represents the top.</p>
     *
     * <p><strong>Example:</strong></p>
     * <pre>
     * StackDemo stack = new StackDemo(5);
     * stack.push(10);
     * stack.push(20);
     * stack.push(30);
     * int[] elements = stack.getElements();  // Returns [10, 20, 30]
     * </pre>
     *
     * @return a new array containing all current stack elements from bottom to top
     */
    public double[] getElements() {
        // Create array of exact size needed (current stack size)
        double[] elements = new double[size()];

        // Copy elements from index 0 to top
        // This preserves the bottom-to-top ordering
        for (int i = 0; i <= top; i++) {
            elements[i] = array[i];
        }

        return elements;
    }
}
