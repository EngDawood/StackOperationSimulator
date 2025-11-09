# Stack Operation Simulator - Manual Test Plan

## Overview
This document provides a comprehensive manual testing guide for the Stack Operation Simulator JavaFX application. These tests complement the automated unit tests and verify the user interface functionality, visual elements, and user interactions.

## Test Environment Setup
- **Java Version**: JDK 11 or higher
- **JavaFX**: Version 13
- **IDE**: NetBeans (or run via Maven)
- **Stack Capacity**: 12 (as defined in App.java)

## How to Run the Application
```bash
mvn clean javafx:run
```

---

## Test Categories

### 1. Push Operation Tests

#### Test 1.1: Push Single Element
**Steps:**
1. Launch the application
2. Enter `42` in the input field
3. Click "Push" button

**Expected Results:**
- Status label shows "Pushed: 42" in green
- Stack visualization displays one blue box with value `42`
- Label "← TOP" appears next to the element
- Input field is cleared
- Size becomes 1
- Push button remains enabled
- Pop and Peek buttons become enabled

**Status:** ☐ Pass ☐ Fail

---

#### Test 1.2: Push Multiple Elements Sequentially
**Steps:**
1. Launch the application
2. Push the following values in sequence: `10`, `20`, `30`, `40`, `50`
3. Observe the stack visualization after each push

**Expected Results:**
- Each push displays success message in green
- Stack elements appear stacked vertically with most recent on top
- TOP indicator moves to the newest element each time
- Size increases with each push (1, 2, 3, 4, 5)
- Elements displayed in order from bottom to top: 10, 20, 30, 40, 50

**Status:** ☐ Pass ☐ Fail

---

#### Test 1.3: Push Until Stack is Full
**Steps:**
1. Launch the application
2. Push 12 values consecutively (e.g., 1 through 12)
3. Observe the Push button state

**Expected Results:**
- All 12 elements are successfully pushed
- Stack visualization shows all 12 elements
- No empty slots (dashed boxes) are visible
- Push button becomes disabled when stack is full
- Status shows capacity reached indication
- Size displays 12

**Status:** ☐ Pass ☐ Fail

---

#### Test 1.4: Attempt to Push When Stack is Full (Overflow)
**Steps:**
1. Fill the stack with 12 elements
2. Verify Push button is disabled
3. Note that attempting to push is prevented by disabled button

**Expected Results:**
- Push button is disabled (grayed out)
- Clicking has no effect
- Input field can still accept text but push action is prevented
- No error occurs because the UI prevents the invalid action

**Status:** ☐ Pass ☐ Fail

---

#### Test 1.5: Push Negative Numbers
**Steps:**
1. Launch the application
2. Enter and push `-10`
3. Enter and push `-25`
4. Enter and push `-100`

**Expected Results:**
- All negative values are successfully pushed
- Status shows "Pushed: [value]" in green for each
- Stack visualization displays negative values with red background (as per styling)
- Values are displayed correctly with minus sign
- TOP indicator points to -100

**Status:** ☐ Pass ☐ Fail

---

#### Test 1.6: Push Zero
**Steps:**
1. Launch the application
2. Push `5`
3. Push `0`
4. Push `10`
5. Push `0`

**Expected Results:**
- Zero values are successfully pushed
- Stack displays zeros with blue background (as per styling for zero values)
- Zero values appear correctly in the stack
- Size increases normally

**Status:** ☐ Pass ☐ Fail

---

#### Test 1.7: Push Large Numbers
**Steps:**
1. Launch the application
2. Push `2147483647` (Integer.MAX_VALUE)
3. Push `1000000`
4. Push `-2147483648` (Integer.MIN_VALUE)

**Expected Results:**
- Large numbers are successfully pushed
- Values display correctly without truncation
- No overflow errors occur
- Visual elements may adjust to accommodate large numbers

**Status:** ☐ Pass ☐ Fail

---

### 2. Pop Operation Tests

#### Test 2.1: Pop from Stack with Multiple Elements
**Steps:**
1. Launch the application
2. Push `10`, `20`, `30`
3. Click "Pop" button

**Expected Results:**
- Status shows "Popped: 30" in green
- Element with value 30 disappears from visualization
- TOP indicator moves to element 20
- Size becomes 2
- One empty slot appears at the top

**Status:** ☐ Pass ☐ Fail

---

#### Test 2.2: Pop Until Stack is Empty
**Steps:**
1. Push `10`, `20`, `30`
2. Click "Pop" three times

**Expected Results:**
- First pop shows "Popped: 30"
- Second pop shows "Popped: 20"
- Third pop shows "Popped: 10"
- After final pop, all slots are empty (dashed boxes)
- Pop and Peek buttons become disabled
- Push button becomes enabled (if it was disabled)
- Status shows isEmpty returns true

**Status:** ☐ Pass ☐ Fail

---

#### Test 2.3: Attempt to Pop from Empty Stack (Underflow)
**Steps:**
1. Launch the application (empty stack)
2. Observe Pop button state
3. Note that Pop button is disabled

**Expected Results:**
- Pop button is disabled on startup
- Cannot click the button
- UI prevents underflow error through button state management

**Status:** ☐ Pass ☐ Fail

---

#### Test 2.4: Verify LIFO Order
**Steps:**
1. Push values in order: `1`, `2`, `3`, `4`, `5`
2. Pop all five values
3. Record the order of popped values

**Expected Results:**
- Pop sequence returns: 5, 4, 3, 2, 1 (reverse order)
- Status messages confirm: "Popped: 5", "Popped: 4", etc.
- Visual demonstration of LIFO principle
- Each pop removes from the top of the stack

**Status:** ☐ Pass ☐ Fail

---

### 3. Peek Operation Tests

#### Test 3.1: Peek at Stack with Elements (Verify No Removal)
**Steps:**
1. Push `100`, `200`, `300`
2. Click "Peek" button
3. Observe the stack visualization
4. Check the size

**Expected Results:**
- Status shows "Top element: 300" in blue
- Stack visualization remains unchanged
- Element 300 still at the top with TOP indicator
- Size remains 3
- No elements are removed

**Status:** ☐ Pass ☐ Fail

---

#### Test 3.2: Peek at Empty Stack
**Steps:**
1. Launch application with empty stack
2. Observe Peek button state

**Expected Results:**
- Peek button is disabled
- Cannot perform peek operation on empty stack
- UI prevents error through button management

**Status:** ☐ Pass ☐ Fail

---

#### Test 3.3: Peek Multiple Times
**Steps:**
1. Push `42`
2. Click "Peek" three times
3. Check size and visualization after each peek

**Expected Results:**
- Each peek shows "Top element: 42" in blue
- Stack remains unchanged after all peeks
- Size stays at 1
- Element still visible with TOP indicator
- No visual changes to stack

**Status:** ☐ Pass ☐ Fail

---

### 4. Size and IsEmpty Tests

#### Test 4.1: Check Size After Each Push/Pop
**Steps:**
1. Launch application
2. Click "Size" button (should show 0)
3. Push `10`, click "Size" (should show 1)
4. Push `20`, click "Size" (should show 2)
5. Pop once, click "Size" (should show 1)
6. Pop once, click "Size" (should show 0)

**Expected Results:**
- Size button always shows current accurate count
- Status displays "Stack size: [number]" in blue
- Size updates correctly after each operation

**Status:** ☐ Pass ☐ Fail

---

#### Test 4.2: Verify IsEmpty Returns True for New Stack
**Steps:**
1. Launch application
2. Click "Is Empty" button immediately

**Expected Results:**
- Status shows "Stack is empty: true" in blue
- Confirms new stack starts empty

**Status:** ☐ Pass ☐ Fail

---

#### Test 4.3: Verify IsEmpty Returns False After Push
**Steps:**
1. Launch application
2. Push `42`
3. Click "Is Empty"

**Expected Results:**
- Status shows "Stack is empty: false" in blue
- Confirms stack has elements

**Status:** ☐ Pass ☐ Fail

---

#### Test 4.4: Verify IsEmpty Returns True After Popping All Elements
**Steps:**
1. Push `10`, `20`, `30`
2. Pop all three elements
3. Click "Is Empty"

**Expected Results:**
- Status shows "Stack is empty: true" in blue
- Confirms stack is empty after removing all elements

**Status:** ☐ Pass ☐ Fail

---

### 5. Input Validation Tests

#### Test 5.1: Enter Non-Numeric Characters
**Steps:**
1. Enter `abc` in the input field
2. Click "Push"

**Expected Results:**
- Warning alert dialog appears with title "Invalid Input"
- Message states "Please enter a valid integer value."
- Status label shows "Invalid input. Enter integers only." in red
- No element is added to stack
- Size remains unchanged

**Status:** ☐ Pass ☐ Fail

---

#### Test 5.2: Enter Decimal Numbers
**Steps:**
1. Enter `3.14` in the input field
2. Click "Push"

**Expected Results:**
- Warning alert appears
- Error message indicates invalid input
- Status shows error in red
- No element added to stack

**Status:** ☐ Pass ☐ Fail

---

#### Test 5.3: Enter Empty Input
**Steps:**
1. Leave input field empty
2. Click "Push"

**Expected Results:**
- Warning alert appears with title "Invalid Input"
- Message states "Input field is empty. Please enter an integer value."
- Status shows "Invalid input. Enter integers only." in red
- No changes to stack

**Status:** ☐ Pass ☐ Fail

---

#### Test 5.4: Enter Whitespace Only
**Steps:**
1. Enter only spaces (e.g., "   ") in input field
2. Click "Push"

**Expected Results:**
- Treated as empty input
- Warning alert appears
- Error message displayed
- No element added

**Status:** ☐ Pass ☐ Fail

---

#### Test 5.5: Enter Very Large Numbers (Beyond Integer Range)
**Steps:**
1. Enter `99999999999999999999` (exceeds Integer.MAX_VALUE)
2. Click "Push"

**Expected Results:**
- NumberFormatException is caught
- Warning alert appears
- Status shows invalid input error in red
- No element added to stack

**Status:** ☐ Pass ☐ Fail

---

### 6. Clear Operation Tests

#### Test 6.1: Clear Stack with Elements
**Steps:**
1. Push `10`, `20`, `30`, `40`, `50`
2. Click "Clear Stack"

**Expected Results:**
- Status shows "Stack cleared" in orange
- All elements disappear from visualization
- All slots show as empty (dashed boxes)
- Size becomes 0
- Pop and Peek buttons become disabled
- Push button becomes enabled

**Status:** ☐ Pass ☐ Fail

---

#### Test 6.2: Clear Empty Stack
**Steps:**
1. Launch application (empty stack)
2. Click "Clear Stack"

**Expected Results:**
- Status shows "Stack cleared" in orange
- Stack remains empty (no errors)
- Size remains 0
- Button states remain appropriate

**Status:** ☐ Pass ☐ Fail

---

#### Test 6.3: Push After Clearing
**Steps:**
1. Push several elements
2. Click "Clear Stack"
3. Push `100`, `200`

**Expected Results:**
- After clear, stack is empty
- New elements successfully pushed
- Stack operates normally
- Elements displayed correctly
- Size reflects new elements only

**Status:** ☐ Pass ☐ Fail

---

### 7. Visual and Styling Tests

#### Test 7.1: Stack Element Color Coding
**Steps:**
1. Push positive number (e.g., `50`)
2. Push negative number (e.g., `-50`)
3. Push zero (e.g., `0`)
4. Observe the colors

**Expected Results:**
- Positive numbers have green background
- Negative numbers have red background
- Zero has blue background
- Colors are clearly distinguishable
- Text is readable on all backgrounds

**Status:** ☐ Pass ☐ Fail

---

#### Test 7.2: Button Hover Effects
**Steps:**
1. Hover mouse over each button
2. Observe visual feedback

**Expected Results:**
- Buttons show hover effect (color change, shadow, etc.)
- Hover provides clear visual feedback
- Disabled buttons do not show hover effects

**Status:** ☐ Pass ☐ Fail

---

#### Test 7.3: Status Label Color Coding
**Steps:**
1. Perform successful push (green)
2. Attempt invalid input (red)
3. Click Size or IsEmpty (blue)
4. Click Clear (orange)

**Expected Results:**
- Success messages in green (#2E7D32)
- Error messages in red (#C62828)
- Info messages in blue (#1565C0)
- Warning messages in orange (#EF6C00)
- Colors are clearly visible and appropriate

**Status:** ☐ Pass ☐ Fail

---

#### Test 7.4: TOP Indicator Visibility
**Steps:**
1. Push one element
2. Push additional elements
3. Pop elements
4. Observe TOP indicator

**Expected Results:**
- TOP indicator (← TOP) appears next to topmost element
- Indicator is in red color
- Indicator moves with stack operations
- Only one TOP indicator visible at a time
- No TOP indicator when stack is empty

**Status:** ☐ Pass ☐ Fail

---

#### Test 7.5: Capacity Indicator Display
**Steps:**
1. Launch application
2. Locate capacity indicator

**Expected Results:**
- "Capacity: 12" label visible at top of visualization
- Label remains visible throughout all operations
- Font size and color appropriate (gray, size 12)

**Status:** ☐ Pass ☐ Fail

---

#### Test 7.6: Empty Slots Visualization
**Steps:**
1. Push 3 elements
2. Observe empty slots

**Expected Results:**
- 9 empty slots visible (12 - 3)
- Empty slots show dashed border
- Empty slots contain "---" text
- White background for empty slots
- Clear distinction from filled slots

**Status:** ☐ Pass ☐ Fail

---

### 8. Button State Management Tests

#### Test 8.1: Dynamic Button Enable/Disable
**Steps:**
1. Start with empty stack - verify Pop and Peek disabled
2. Push one element - verify all buttons enabled
3. Fill stack completely - verify Push disabled
4. Pop one element - verify Push re-enabled
5. Pop all elements - verify Pop and Peek disabled again

**Expected Results:**
- Buttons correctly disabled/enabled based on stack state
- UI prevents invalid operations
- Clear visual indication of button states
- All operations maintain proper button states

**Status:** ☐ Pass ☐ Fail

---

### 9. Integration and Edge Case Tests

#### Test 9.1: Rapid Sequential Operations
**Steps:**
1. Quickly push 5 elements
2. Quickly pop 3 elements
3. Quickly push 2 more
4. Click Size, IsEmpty, Peek in quick succession

**Expected Results:**
- All operations complete correctly
- No visual glitches
- Correct final state
- Status updates appropriately

**Status:** ☐ Pass ☐ Fail

---

#### Test 9.2: Window Resize Behavior
**Steps:**
1. Push several elements
2. Resize application window (larger and smaller)
3. Observe layout behavior

**Expected Results:**
- Layout adjusts appropriately
- Elements remain visible and properly formatted
- No overlap or layout breaks
- Scrolling works if needed

**Status:** ☐ Pass ☐ Fail

---

#### Test 9.3: Complete Stack Lifecycle
**Steps:**
1. Start with empty stack
2. Fill completely (12 elements)
3. Empty completely
4. Refill to 6 elements
5. Clear stack
6. Add 3 elements

**Expected Results:**
- All operations work correctly
- Proper state maintained throughout
- Visualization accurate at each step
- No errors or inconsistencies

**Status:** ☐ Pass ☐ Fail

---

## Test Summary

**Total Tests:** 42
**Tests Passed:** _____
**Tests Failed:** _____
**Pass Rate:** _____%

## Notes and Issues

Record any bugs, observations, or suggestions here:

```
[Space for notes]
```

---

## Test Sign-Off

**Tester Name:** ___________________
**Date:** ___________________
**Signature:** ___________________

---

## Appendix: Common Issues and Troubleshooting

### Issue: Application won't start
**Solution:** Ensure JavaFX is properly configured and Java 11+ is installed

### Issue: Buttons not responding
**Solution:** Check if stack state allows the operation (buttons may be disabled)

### Issue: Visual elements not displaying correctly
**Solution:** Check if CSS file is loaded properly and styles are applied

### Issue: Numbers display incorrectly
**Solution:** Verify input validation is working and only integers are accepted
