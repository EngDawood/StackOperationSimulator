# StackOperationSimulator
# Stack Operation Simulator

A JavaFX application that provides a visual, interactive demonstration of stack data structure operations following LIFO (Last-In-First-Out) principles.

## Project Overview

This project implements a graphical stack simulator that helps users understand stack operations through visual feedback and interactive controls. The application demonstrates push, pop, peek, size, and isEmpty operations with proper overflow and underflow handling.

## Prerequisites

- Java Development Kit (JDK) 11 or higher
- NetBeans IDE
- JavaFX SDK configured in your environment

## Features

- Push elements onto the stack
- Pop elements from the stack
- View current stack contents
- Display stack size
- Check if stack is empty
- Handle stack overflow and underflow conditions
- Visual representation of stack operations with real-time updates

## TODO List: Step-by-Step Implementation

### Step 1: Core Stack Implementation

Create a `StackDemo` class that implements the following:

**Instance Variables:**
```java
private int[] array;
private int top = -1;
private int capacity;
```

**Constructor:**
- Accept capacity as parameter
- Initialize array with given capacity
- Set top to -1 (empty stack indicator)

**Methods to implement:**

1. `public boolean isEmpty()`
   - Return true when top equals -1
   - Return false otherwise

2. `public boolean isFull()`
   - Return true when top equals capacity minus 1
   - Return false otherwise

3. `public void push(int element)`
   - Check if stack is full using isFull()
   - If full, throw IllegalStateException with message "Stack Overflow"
   - If not full, increment top first, then assign element to array[top]

4. `public int pop()`
   - Check if stack is empty using isEmpty()
   - If empty, throw IllegalStateException with message "Stack Underflow"
   - If not empty, store array[top] in temporary variable, decrement top, return temporary variable

5. `public int peek()`
   - Check if stack is empty
   - If empty, throw IllegalStateException with message "Stack is empty"
   - Return array[top] without modifying top

6. `public int size()`
   - Return top plus 1

7. `public int[] getElements()`
   - Create new array of size equal to current stack size
   - Copy elements from index 0 to top into new array
   - Return new array

### Step 2: JavaFX Application Setup

Create main application class extending `Application`:

**Stage Configuration:**
- Set title to "Stack Operation Simulator"
- Set minimum width to 600 and minimum height to 500

**Layout Structure:**
- Use BorderPane as root layout
- Create VBox for left side (controls)
- Create VBox for center (stack visualization)
- Create Label at bottom for status messages

**Control Components (Left Side):**
- Label with text "Enter Value:"
- TextField for numeric input (set prompt text to "Enter integer")
- Button "Push" with preferred width 120
- Button "Pop" with preferred width 120
- Button "Peek" with preferred width 120
- Button "Size" with preferred width 120
- Button "Is Empty" with preferred width 120
- Button "Clear Stack" with preferred width 120
- Add spacing of 10 between components
- Set padding of 20 for the VBox

**Visualization Area (Center):**
- Create VBox to hold stack elements
- Set alignment to BOTTOM_CENTER
- Add padding of 20
- Set background color to light gray (#f0f0f0)

**Status Label (Bottom):**
- Set font size to 14
- Set padding of 10
- Set alignment to center

### Step 3: Controller Logic Integration

**Initialize Stack:**
- Create StackDemo instance with capacity between 10 and 15
- Declare as instance variable in application class

**Push Button Handler:**
- Get text from TextField
- Validate input is not empty
- Parse input to integer using Integer.parseInt() wrapped in try-catch
- Call stack.push() with parsed value inside try-catch
- If successful, update status label with "Pushed: [value]" in green text
- If NumberFormatException, show "Invalid input. Enter integers only." in red
- If IllegalStateException (overflow), show exception message in red
- Clear TextField after operation
- Call method to update visual display

**Pop Button Handler:**
- Call stack.pop() inside try-catch
- If successful, update status label with "Popped: [value]" in green text
- If IllegalStateException (underflow), show exception message in red
- Call method to update visual display

**Peek Button Handler:**
- Call stack.peek() inside try-catch
- If successful, update status label with "Top element: [value]" in blue text
- If IllegalStateException, show exception message in red
- Do not update visual display (non-modifying operation)

**Size Button Handler:**
- Call stack.size()
- Update status label with "Stack size: [size]" in blue text

**IsEmpty Button Handler:**
- Call stack.isEmpty()
- Update status label with "Stack is empty: [true/false]" in blue text

**Clear Button Handler:**
- Reinitialize StackDemo with same capacity
- Update status label with "Stack cleared" in orange text
- Call method to update visual display

### Step 4: Visual Stack Representation

Create method `updateStackDisplay()`:

**Clear Current Display:**
- Get children list from visualization VBox
- Clear all children

**Add Capacity Indicator:**
- Create Label showing "Capacity: [capacity]"
- Style with font size 12 and gray color
- Add to top of VBox

**Display Stack Elements:**
- Get elements array from stack.getElements()
- Loop through array from last index to first (top to bottom visually)
- For each element:
  - Create HBox container
  - Add Label with element value (font size 18, bold)
  - Set background color to light blue (#87CEEB)
  - Set border with color black and width 2
  - Set padding of 10
  - Set preferred height of 50
  - Set alignment to CENTER
  - Add to VBox

**Add Empty Slots Visualization:**
- Calculate empty slots as capacity minus stack.size()
- Loop through empty slots count
- For each empty slot:
  - Create HBox with dashed border
  - Add Label with text "---"
  - Set background color to white
  - Set padding of 10
  - Set preferred height of 50
  - Set alignment to CENTER
  - Add to VBox

**Add Top Pointer Indicator:**
- If stack is not empty:
  - Create Label with text "← TOP" in red color
  - Position next to topmost element using HBox
  - Make font bold and size 14

### Step 5: Enhanced Error Handling

**Add Input Validation:**
- Before parsing input, check TextField is not empty or whitespace only
- Show alert "Input field is empty" if no input provided
- Use trim() on input before validation

**Disable/Enable Buttons Dynamically:**
- After each operation, check stack state
- Disable Push button when stack.isFull() returns true
- Disable Pop and Peek buttons when stack.isEmpty() returns true
- Re-enable buttons when conditions change

**Add Alert Dialogs:**
- For critical errors (overflow/underflow), show Alert dialog
- Set alert type to ERROR for stack exceptions
- Set alert type to WARNING for invalid inputs
- Include clear title and content text

### Step 6: Styling Enhancements

**Button Styling:**
- Set button style with background color (#4CAF50 for primary actions)
- Set text fill to white
- Set font weight to bold
- Add hover effect by changing background color on mouse enter/exit
- Set border radius to 5

**Stack Element Styling:**
- Use different colors for different value ranges (optional):
  - Green for positive values
  - Red for negative values
  - Blue for zero
- Add drop shadow effect to stack elements
- Use gradient background for visual depth

**Status Label Styling:**
- Set different text colors based on message type:
  - Green (#2E7D32) for success operations
  - Red (#C62828) for errors
  - Blue (#1565C0) for informational messages
  - Orange (#EF6C00) for warnings

**Layout Styling:**
- Add background gradient to main BorderPane
- Set consistent spacing between all components
- Add separator line between controls and visualization
- Use consistent font family throughout application

### Step 7: Testing Scenarios

**Test Push Operations:**
- Push single element and verify display updates
- Push multiple elements sequentially
- Push until stack is full
- Attempt to push when stack is full (verify overflow error)
- Push negative numbers
- Push zero
- Push large numbers

**Test Pop Operations:**
- Pop from stack with multiple elements
- Pop until stack is empty
- Attempt to pop from empty stack (verify underflow error)
- Verify LIFO order (last pushed is first popped)

**Test Peek Operations:**
- Peek at stack with elements (verify no removal)
- Peek at empty stack (verify error)
- Peek multiple times (verify same value returned)

**Test Size and IsEmpty:**
- Check size after each push/pop
- Verify isEmpty returns true for new stack
- Verify isEmpty returns false after push
- Verify isEmpty returns true after popping all elements

**Test Input Validation:**
- Enter non-numeric characters
- Enter decimal numbers
- Enter empty input
- Enter whitespace only
- Enter very large numbers

**Test Clear Operation:**
- Clear stack with elements
- Clear empty stack
- Push after clearing (verify works correctly)

### Step 8: Documentation

Add Javadoc comments to all methods in StackDemo class:

**Class-level documentation:**
- Describe purpose of class
- Explain array-based implementation
- Document capacity limitation
- Note LIFO behavior

**Method-level documentation:**
- Document parameters with @param
- Document return values with @return
- Document exceptions with @throws
- Include usage examples where helpful

**Inline comments:**
- Explain index manipulation (why increment before assignment)
- Clarify boundary conditions (top == -1, top == capacity-1)
- Document algorithm for visualization

## How to Run

1. Open project in NetBeans
2. Ensure JavaFX libraries are configured in project properties
3. Build project to compile all classes
4. Run main application class
5. Application window displays with stack interface ready for interaction

## Usage Guide

1. **Push Operation**: Enter integer in input field, click Push to add to stack top
2. **Pop Operation**: Click Pop to remove and display top element
3. **Peek Operation**: Click Peek to view top element without removal
4. **Size**: Click Size to see current number of elements
5. **Check Empty**: Click isEmpty to verify if stack contains elements
6. **Clear Stack**: Click Clear to reset stack to empty state

## Key Concepts Demonstrated

- **LIFO Principle**: Last element pushed is first element popped
- **Array-Based Implementation**: Stack stored in fixed-size array with index tracking
- **Top Pointer**: Integer variable tracking current top position (starts at -1)
- **Overflow Condition**: Attempting push when top equals capacity minus 1
- **Underflow Condition**: Attempting pop or peek when top equals -1
- **Time Complexity**: O(1) for all stack operations

## Progress Checklist

### Core Stack Implementation
- [ ] StackDemo class created with instance variables
- [ ] Constructor implemented with capacity initialization
- [ ] isEmpty() method implemented
- [ ] isFull() method implemented
- [ ] push() method with overflow handling
- [ ] pop() method with underflow handling
- [ ] peek() method implemented
- [ ] size() method implemented
- [ ] getElements() method for visualization

### JavaFX Application
- [ ] Main application class extending Application
- [ ] BorderPane layout configured
- [ ] Input TextField added
- [ ] Push button with event handler
- [ ] Pop button with event handler
- [ ] Peek button with event handler
- [ ] Size button with event handler
- [ ] IsEmpty button with event handler
- [ ] Clear button with event handler
- [ ] Status label for messages

### Visualization
- [ ] VBox for stack element display
- [ ] Method to update visual display
- [ ] Stack elements shown as styled components
- [ ] Empty slots visualization
- [ ] Top pointer indicator
- [ ] Capacity indicator display
- [ ] Visual updates after each operation

### Error Handling
- [ ] Input validation for numeric values
- [ ] NumberFormatException handling
- [ ] Overflow exception handling
- [ ] Underflow exception handling
- [ ] Empty input validation
- [ ] Button disable/enable logic
- [ ] Alert dialogs for errors

### Styling
- [ ] Button styling with colors
- [ ] Hover effects on buttons
- [ ] Stack element background colors
- [ ] Border styling for elements
- [ ] Status label color coding
- [ ] Layout spacing and padding
- [ ] Font styling throughout

### Testing
- [ ] Push operations tested
- [ ] Pop operations tested
- [ ] Peek operations tested
- [ ] Size and isEmpty tested
- [ ] Overflow scenario tested
- [ ] Underflow scenario tested
- [ ] Invalid input tested
- [ ] Clear operation tested
- [ ] LIFO order verified

### Documentation
- [ ] Javadoc comments added
- [ ] Method documentation complete
- [ ] Inline comments for complex logic
- [ ] README usage guide written
- [ ] Code formatted consistently

---

**Project Type**: JavaFX Desktop Application  
**Data Structure**: Array-Based Stack  
**Complexity**: Intermediate  
**Estimated Time**: 4-6 hours