package com.mycompany.stackoperationsimulator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Stack Operation Simulator - JavaFX Application
 *
 * <p>This application provides a visual, interactive demonstration of stack
 * data structure operations following LIFO (Last-In-First-Out) principles.</p>
 *
 * <p>Features include push, pop, peek, size, isEmpty operations with proper
 * overflow and underflow handling, all visualized in real-time.</p>
 */
public class App extends Application {

    // Stack instance
    private StackDemo stack;

    // UI Components
    private TextField inputField;
    private VBox stackVisualization;
    private Label statusLabel;

    // Buttons for dynamic enable/disable
    private Button pushButton;
    private Button popButton;
    private Button peekButton;

    // Stack capacity
    private static final int STACK_CAPACITY = 12;

    @Override
    public void start(Stage stage) {
        // Initialize stack
        stack = new StackDemo(STACK_CAPACITY);

        // Stage configuration
        stage.setTitle("Stack Operation Simulator");
        stage.setMinWidth(600);
        stage.setMinHeight(500);

        // Create root layout
        BorderPane root = new BorderPane();

        // Create and configure left side (controls)
        VBox controls = createControlPanel();
        root.setLeft(controls);

        // Create separator between controls and visualization
        Region separator = new Region();
        separator.getStyleClass().add("separator");
        separator.setPrefWidth(2);
        root.setCenter(createCenterLayout());

        // Create and configure bottom (status label)
        statusLabel = createStatusLabel();
        root.setBottom(statusLabel);

        // Initial display update
        updateStackDisplay();
        updateButtonStates();

        // Create and set scene
        Scene scene = new Scene(root, 800, 600);

        // Load external CSS stylesheet
        String css = getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Creates the control panel with input field and operation buttons.
     *
     * @return VBox containing all control components
     */
    private VBox createControlPanel() {
        VBox controls = new VBox(10);
        controls.setPadding(new Insets(20));
        controls.setAlignment(Pos.TOP_CENTER);
        controls.getStyleClass().add("control-panel");

        // Label for input
        Label inputLabel = new Label("Enter Value:");
        inputLabel.getStyleClass().add("input-label");

        // TextField for numeric input
        inputField = new TextField();
        inputField.setPromptText("Enter integer");
        inputField.setPrefWidth(120);

        // Create buttons with CSS styling
        pushButton = new Button("Push");
        pushButton.setPrefWidth(120);
        pushButton.getStyleClass().add("primary-button");
        pushButton.setOnAction(e -> handlePush());

        popButton = new Button("Pop");
        popButton.setPrefWidth(120);
        popButton.getStyleClass().add("primary-button");
        popButton.setOnAction(e -> handlePop());

        peekButton = new Button("Peek");
        peekButton.setPrefWidth(120);
        peekButton.getStyleClass().add("primary-button");
        peekButton.setOnAction(e -> handlePeek());

        Button sizeButton = new Button("Size");
        sizeButton.setPrefWidth(120);
        sizeButton.getStyleClass().add("secondary-button");
        sizeButton.setOnAction(e -> handleSize());

        Button isEmptyButton = new Button("Is Empty");
        isEmptyButton.setPrefWidth(120);
        isEmptyButton.getStyleClass().add("secondary-button");
        isEmptyButton.setOnAction(e -> handleIsEmpty());

        Button clearButton = new Button("Clear Stack");
        clearButton.setPrefWidth(120);
        clearButton.getStyleClass().add("clear-button");
        clearButton.setOnAction(e -> handleClear());

        // Add all components to controls VBox
        controls.getChildren().addAll(
            inputLabel,
            inputField,
            pushButton,
            popButton,
            peekButton,
            sizeButton,
            isEmptyButton,
            clearButton
        );

        return controls;
    }

    /**
     * Creates the center layout with separator and visualization panel.
     *
     * @return HBox containing separator and visualization
     */
    private HBox createCenterLayout() {
        HBox centerLayout = new HBox();

        // Create separator
        Region separator = new Region();
        separator.getStyleClass().add("separator");
        separator.setPrefWidth(2);

        // Create visualization panel
        stackVisualization = createVisualizationPanel();

        centerLayout.getChildren().addAll(separator, stackVisualization);
        HBox.setHgrow(stackVisualization, Priority.ALWAYS);

        return centerLayout;
    }

    /**
     * Creates the visualization panel for displaying stack elements.
     *
     * @return VBox configured for stack visualization
     */
    private VBox createVisualizationPanel() {
        VBox visualization = new VBox(5);
        visualization.setAlignment(Pos.BOTTOM_CENTER);
        visualization.setPadding(new Insets(20));
        visualization.getStyleClass().add("visualization-panel");

        return visualization;
    }

    /**
     * Creates the status label for displaying operation messages.
     *
     * @return Label configured for status messages
     */
    private Label createStatusLabel() {
        Label label = new Label("Ready");
        label.getStyleClass().add("status-label");
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);

        return label;
    }

    /**
     * Handles the Push button action.
     * Validates input, pushes value to stack, and updates display.
     */
    private void handlePush() {
        String input = inputField.getText();

        // Validate input is not empty or whitespace only
        if (input == null || input.trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Invalid Input", "Input field is empty. Please enter an integer value.");
            setStatusText("Invalid input. Enter integers only.", "red");
            return;
        }

        try {
            // Parse input to integer
            int value = Integer.parseInt(input.trim());

            // Push to stack
            stack.push(value);

            // Update status and display
            setStatusText("Pushed: " + value, "green");
            inputField.clear();
            updateStackDisplay();
            updateButtonStates();

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "Please enter a valid integer value.");
            setStatusText("Invalid input. Enter integers only.", "red");
        } catch (IllegalStateException e) {
            showAlert(AlertType.ERROR, "Stack Overflow", e.getMessage());
            setStatusText(e.getMessage(), "red");
        }
    }

    /**
     * Handles the Pop button action.
     * Removes top element from stack and updates display.
     */
    private void handlePop() {
        try {
            int value = stack.pop();
            setStatusText("Popped: " + value, "green");
            updateStackDisplay();
            updateButtonStates();
        } catch (IllegalStateException e) {
            showAlert(AlertType.ERROR, "Stack Underflow", e.getMessage());
            setStatusText(e.getMessage(), "red");
        }
    }

    /**
     * Handles the Peek button action.
     * Displays top element without removing it.
     */
    private void handlePeek() {
        try {
            int value = stack.peek();
            setStatusText("Top element: " + value, "blue");
        } catch (IllegalStateException e) {
            showAlert(AlertType.ERROR, "Stack Empty", e.getMessage());
            setStatusText(e.getMessage(), "red");
        }
    }

    /**
     * Handles the Size button action.
     * Displays current number of elements in stack.
     */
    private void handleSize() {
        int size = stack.size();
        setStatusText("Stack size: " + size, "blue");
    }

    /**
     * Handles the Is Empty button action.
     * Checks and displays if stack is empty.
     */
    private void handleIsEmpty() {
        boolean empty = stack.isEmpty();
        setStatusText("Stack is empty: " + empty, "blue");
    }

    /**
     * Handles the Clear button action.
     * Reinitializes stack and updates display.
     */
    private void handleClear() {
        stack = new StackDemo(STACK_CAPACITY);
        setStatusText("Stack cleared", "orange");
        updateStackDisplay();
        updateButtonStates();
    }

    /**
     * Updates the visual representation of the stack.
     * Displays current elements, empty slots, and capacity indicator.
     * Uses value-based color coding: green for positive, red for negative, blue for zero.
     */
    private void updateStackDisplay() {
        // Clear current display
        stackVisualization.getChildren().clear();

        // Add capacity indicator
        Label capacityLabel = new Label("Capacity: " + STACK_CAPACITY);
        capacityLabel.getStyleClass().add("capacity-label");
        stackVisualization.getChildren().add(capacityLabel);

        // Get current stack elements
        int[] elements = stack.getElements();
        int currentSize = stack.size();

        // Display stack elements (from top to bottom visually)
        for (int i = elements.length - 1; i >= 0; i--) {
            HBox elementBox = new HBox();
            elementBox.setAlignment(Pos.CENTER);
            elementBox.setPadding(new Insets(10));
            elementBox.setPrefHeight(50);
            elementBox.setPrefWidth(200);

            // Apply CSS class based on value (positive, negative, or zero)
            if (elements[i] > 0) {
                elementBox.getStyleClass().add("stack-element-positive");
            } else if (elements[i] < 0) {
                elementBox.getStyleClass().add("stack-element-negative");
            } else {
                elementBox.getStyleClass().add("stack-element-zero");
            }

            Label valueLabel = new Label(String.valueOf(elements[i]));
            valueLabel.getStyleClass().add("stack-value-label");

            elementBox.getChildren().add(valueLabel);

            // Add TOP indicator for topmost element
            if (i == elements.length - 1) {
                Label topIndicator = new Label(" ← TOP");
                topIndicator.getStyleClass().add("top-indicator");
                elementBox.getChildren().add(topIndicator);
            }

            stackVisualization.getChildren().add(elementBox);
        }

        // Add empty slots visualization
        int emptySlots = STACK_CAPACITY - currentSize;
        for (int i = 0; i < emptySlots; i++) {
            HBox emptyBox = new HBox();
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(10));
            emptyBox.setPrefHeight(50);
            emptyBox.setPrefWidth(200);
            emptyBox.getStyleClass().add("empty-slot");

            Label emptyLabel = new Label("---");
            emptyLabel.getStyleClass().add("empty-slot-label");

            emptyBox.getChildren().add(emptyLabel);
            stackVisualization.getChildren().add(emptyBox);
        }
    }

    /**
     * Updates button enabled/disabled states based on current stack state.
     * Disables Push button when stack is full.
     * Disables Pop and Peek buttons when stack is empty.
     */
    private void updateButtonStates() {
        // Disable Push button when stack is full
        pushButton.setDisable(stack.isFull());

        // Disable Pop and Peek buttons when stack is empty
        boolean isEmpty = stack.isEmpty();
        popButton.setDisable(isEmpty);
        peekButton.setDisable(isEmpty);
    }

    /**
     * Shows an Alert dialog with specified type, title, and message.
     *
     * @param type the type of alert (ERROR, WARNING, INFORMATION)
     * @param title the title of the alert dialog
     * @param message the message content
     */
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Sets the status label text with specified color using CSS classes.
     *
     * @param message the message to display
     * @param color the color name (green, red, blue, orange)
     */
    private void setStatusText(String message, String color) {
        statusLabel.setText(message);

        // Remove all color-related CSS classes
        statusLabel.getStyleClass().removeAll("status-success", "status-error", "status-info", "status-warning");

        // Add appropriate CSS class based on color
        switch (color.toLowerCase()) {
            case "green":
                statusLabel.getStyleClass().add("status-success");
                break;
            case "red":
                statusLabel.getStyleClass().add("status-error");
                break;
            case "blue":
                statusLabel.getStyleClass().add("status-info");
                break;
            case "orange":
                statusLabel.getStyleClass().add("status-warning");
                break;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
