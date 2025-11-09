package com.mycompany.stackoperationsimulator;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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

    // Information panel components
    private Label maxSizeLabel;
    private Label currentSizeLabel;
    private Label isEmptyLabel;
    private Label isFullLabel;

    // Status bar components
    private Label lastOperationLabel;

    // Capacity display components
    private Label capacityDisplayLabel;
    private ProgressBar capacityProgressBar;

    // Stack capacity (configurable)
    private int stackCapacity = 12;

    // Initial data for stack (optional)
    private double[] initialData = null;

    /**
     * Default constructor - initializes with default capacity of 12.
     */
    public App() {
        this.stackCapacity = 12;
    }

    /**
     * Constructor that accepts capacity parameter.
     *
     * @param capacity the maximum number of elements the stack can hold
     */
    public App(int capacity) {
        this.stackCapacity = capacity;
    }

    @Override
    public void start(Stage stage) {
        // Initialize stack
        stack = new StackDemo(stackCapacity);

        // Push initial data if provided
        if (initialData != null) {
            for (double value : initialData) {
                try {
                    stack.push(value);
                } catch (IllegalStateException e) {
                    // Stop if stack becomes full
                    break;
                }
            }
        }

        // Initialize UI
        initializeUI(stage);
    }

    /**
     * Initializes all UI components and displays the stage.
     *
     * @param stage the primary stage for this application
     */
    private void initializeUI(Stage stage) {
        // Stage configuration
        stage.setTitle("Stack Operation Simulator");
        stage.setMinWidth(900);
        stage.setMinHeight(600);

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

        // Create and configure right side (information panel)
        VBox infoPanel = createInformationPanel();
        root.setRight(infoPanel);

        // Create and configure bottom (status bar)
        HBox statusBar = createStatusBar();
        root.setBottom(statusBar);

        // Initial display update
        updateStackDisplay();
        updateButtonStates();
        updateInformationPanel();

        // Create and set scene
        Scene scene = new Scene(root, 1000, 650);

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
        inputField.setPromptText("Enter number");
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
     * Creates the information panel for the right side of the screen.
     *
     * @return VBox containing stack information and operation descriptions
     */
    private VBox createInformationPanel() {
        VBox infoPanel = new VBox(15);
        infoPanel.setPadding(new Insets(20));
        infoPanel.setPrefWidth(250);
        infoPanel.getStyleClass().add("info-panel");

        // Stack Information Section
        Label infoHeader = new Label("Stack Information");
        infoHeader.getStyleClass().add("info-header");

        Separator separator1 = new Separator();

        maxSizeLabel = new Label("Maximum Size: " + stackCapacity);
        maxSizeLabel.getStyleClass().add("info-label");

        currentSizeLabel = new Label("Current Size: 0");
        currentSizeLabel.getStyleClass().add("info-label");

        isEmptyLabel = new Label("Is Empty: Yes");
        isEmptyLabel.getStyleClass().add("info-label");

        isFullLabel = new Label("Is Full: No");
        isFullLabel.getStyleClass().add("info-label");

        // Operation Descriptions Section
        Label opsHeader = new Label("Stack Operations:");
        opsHeader.getStyleClass().add("info-header");

        Separator separator2 = new Separator();

        TextArea opsDescription = new TextArea(
            "Push: Adds an element to the top of the stack\n\n" +
            "Pop: Removes and returns the element at the top of the stack\n\n" +
            "Peek: Returns the top element without removing it\n\n" +
            "LIFO Principle:\n" +
            "Last-In-First-Out"
        );
        opsDescription.setEditable(false);
        opsDescription.setWrapText(true);
        opsDescription.setPrefHeight(300);
        opsDescription.getStyleClass().add("ops-description");

        infoPanel.getChildren().addAll(
            infoHeader,
            separator1,
            maxSizeLabel,
            currentSizeLabel,
            isEmptyLabel,
            isFullLabel,
            opsHeader,
            separator2,
            opsDescription
        );

        return infoPanel;
    }

    /**
     * Creates the status bar at the bottom of the screen.
     *
     * @return HBox containing status bar components
     */
    private HBox createStatusBar() {
        HBox statusBar = new HBox(20);
        statusBar.setPadding(new Insets(10));
        statusBar.setAlignment(Pos.CENTER_LEFT);
        statusBar.getStyleClass().add("status-bar");

        // Add separator line above status bar
        VBox statusContainer = new VBox();
        Separator separator = new Separator();

        Label lastOpLabel = new Label("Last Operation:");
        lastOpLabel.getStyleClass().add("status-bar-label");

        lastOperationLabel = new Label("");
        lastOperationLabel.getStyleClass().add("status-bar-value");

        // Create and initialize statusLabel
        statusLabel = createStatusLabel();

        // Add a spacer region to push status label to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        statusBar.getChildren().addAll(lastOpLabel, lastOperationLabel, spacer, statusLabel);

        statusContainer.getChildren().addAll(separator, statusBar);

        // Return the container with separator and status bar
        return statusBar;
    }

    /**
     * Updates the information panel with current stack state.
     */
    private void updateInformationPanel() {
        int size = stack.size();
        boolean isEmpty = stack.isEmpty();
        boolean isFull = stack.isFull();

        currentSizeLabel.setText("Current Size: " + size);
        isEmptyLabel.setText("Is Empty: " + (isEmpty ? "Yes" : "No"));
        isFullLabel.setText("Is Full: " + (isFull ? "Yes" : "No"));
    }

    /**
     * Handles the Push button action.
     * Validates input, pushes value to stack, and updates display.
     */
    private void handlePush() {
        String input = inputField.getText();

        // Validate input is not empty or whitespace only
        if (input == null || input.trim().isEmpty()) {
            showAlert(AlertType.WARNING, "Invalid Input", "Input field is empty. Please enter a numeric value.");
            setStatusText("Invalid input. Enter numbers only.", "red");
            return;
        }

        try {
            // Parse input to double
            double value = Double.parseDouble(input.trim());

            // Push to stack
            stack.push(value);

            // Update status and display
            setStatusText("Pushed: " + value, "green");
            lastOperationLabel.setText("Pushed: " + value + " | Stack Size: " + stack.size());
            lastOperationLabel.getStyleClass().removeAll("status-success", "status-error", "status-info");
            lastOperationLabel.getStyleClass().add("status-success");

            inputField.clear();

            // Animate the update
            PauseTransition pause = new PauseTransition(Duration.millis(100));
            pause.setOnFinished(e -> {
                updateStackDisplay();
                updateButtonStates();
                updateInformationPanel();
            });
            pause.play();

        } catch (NumberFormatException e) {
            showAlert(AlertType.WARNING, "Invalid Input", "Please enter a valid numeric value.");
            setStatusText("Invalid input. Enter numbers only.", "red");
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
            double value = stack.pop();
            setStatusText("Popped: " + value, "green");
            lastOperationLabel.setText("Popped: " + value + " | Stack Size: " + stack.size());
            lastOperationLabel.getStyleClass().removeAll("status-success", "status-error", "status-info");
            lastOperationLabel.getStyleClass().add("status-success");

            // Animate the fade out effect
            PauseTransition pause = new PauseTransition(Duration.millis(300));
            pause.setOnFinished(e -> {
                updateStackDisplay();
                updateButtonStates();
                updateInformationPanel();
            });
            pause.play();
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
            double value = stack.peek();
            setStatusText("Top element: " + value, "blue");
            lastOperationLabel.setText("Peek: " + value + " | No change");
            lastOperationLabel.getStyleClass().removeAll("status-success", "status-error", "status-info");
            lastOperationLabel.getStyleClass().add("status-info");
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
        lastOperationLabel.setText("Size: " + size + " | No change");
        lastOperationLabel.getStyleClass().removeAll("status-success", "status-error", "status-info", "status-warning");
        lastOperationLabel.getStyleClass().add("status-info");
    }

    /**
     * Handles the Is Empty button action.
     * Checks and displays if stack is empty.
     */
    private void handleIsEmpty() {
        boolean empty = stack.isEmpty();
        setStatusText("Stack is empty: " + empty, "blue");
        lastOperationLabel.setText("Is Empty: " + (empty ? "Yes" : "No") + " | No change");
        lastOperationLabel.getStyleClass().removeAll("status-success", "status-error", "status-info", "status-warning");
        lastOperationLabel.getStyleClass().add("status-info");
    }

    /**
     * Handles the Clear button action.
     * Reinitializes stack and updates display.
     */
    private void handleClear() {
        stack = new StackDemo(stackCapacity);
        setStatusText("Stack cleared", "orange");
        lastOperationLabel.setText("Stack cleared | Stack Size: 0");
        lastOperationLabel.getStyleClass().removeAll("status-success", "status-error", "status-info");
        lastOperationLabel.getStyleClass().add("status-warning");
        updateStackDisplay();
        updateButtonStates();
        updateInformationPanel();
    }

    /**
     * Updates the visual representation of the stack.
     * Displays current elements, empty slots, and capacity indicator.
     * Uses value-based color coding: green for positive, red for negative, blue for zero.
     */
    private void updateStackDisplay() {
        // Clear current display
        stackVisualization.getChildren().clear();

        // Add capacity indicator with progress bar
        VBox capacitySection = new VBox(5);
        capacitySection.setAlignment(Pos.CENTER);

        int currentSize = stack.size();
        double fillPercentage = (double) currentSize / stackCapacity;

        capacityDisplayLabel = new Label("Capacity: " + stackCapacity + " | Used: " + currentSize);
        capacityDisplayLabel.getStyleClass().add("capacity-display-label");

        capacityProgressBar = new ProgressBar(fillPercentage);
        capacityProgressBar.setPrefWidth(250);
        capacityProgressBar.setPrefHeight(20);

        // Set progress bar color based on fill percentage
        if (fillPercentage < 0.7) {
            capacityProgressBar.setStyle("-fx-accent: #66BB6A;"); // Green
        } else if (fillPercentage < 0.9) {
            capacityProgressBar.setStyle("-fx-accent: #FDD835;"); // Yellow
        } else {
            capacityProgressBar.setStyle("-fx-accent: #EF5350;"); // Red
        }

        capacitySection.getChildren().addAll(capacityDisplayLabel, capacityProgressBar);
        stackVisualization.getChildren().add(capacitySection);

        // Get current stack elements
        double[] elements = stack.getElements();

        // Display stack elements (from top to bottom visually)
        for (int i = elements.length - 1; i >= 0; i--) {
            HBox rowBox = new HBox(10);
            rowBox.setAlignment(Pos.CENTER);

            // Add index label on the left
            Label indexLabel = new Label("[" + i + "]");
            indexLabel.getStyleClass().add("index-label");
            indexLabel.setPrefWidth(40);

            // Create element box
            HBox elementBox = new HBox();
            elementBox.setAlignment(Pos.CENTER);
            elementBox.setPadding(new Insets(15));
            elementBox.setPrefHeight(60);
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

            // Add TOP indicator for topmost element on the right
            Label topIndicator = new Label("");
            topIndicator.setPrefWidth(60);
            if (i == elements.length - 1) {
                topIndicator.setText("← TOP");
                topIndicator.getStyleClass().add("top-indicator");
            }

            rowBox.getChildren().addAll(indexLabel, elementBox, topIndicator);
            stackVisualization.getChildren().add(rowBox);
        }

        // Add empty slots visualization
        int emptySlots = stackCapacity - currentSize;
        for (int i = 0; i < emptySlots; i++) {
            HBox rowBox = new HBox(10);
            rowBox.setAlignment(Pos.CENTER);

            // Empty index label
            Label emptyIndexLabel = new Label("");
            emptyIndexLabel.setPrefWidth(40);

            // Create empty slot box
            VBox emptyBox = new VBox(2);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(15));
            emptyBox.setPrefHeight(60);
            emptyBox.setPrefWidth(200);
            emptyBox.getStyleClass().add("empty-slot");

            Label emptyLabel = new Label("---");
            emptyLabel.getStyleClass().add("empty-slot-label");

            Label emptyTextLabel = new Label("(empty)");
            emptyTextLabel.getStyleClass().add("empty-text-label");

            emptyBox.getChildren().addAll(emptyLabel, emptyTextLabel);

            // Empty space on right
            Label emptySpace = new Label("");
            emptySpace.setPrefWidth(60);

            rowBox.getChildren().addAll(emptyIndexLabel, emptyBox, emptySpace);
            stackVisualization.getChildren().add(rowBox);
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

    /**
     * Sets the stack capacity.
     * Must be called before start() method.
     *
     * @param capacity the maximum number of elements the stack can hold
     */
    public void setStackCapacity(int capacity) {
        this.stackCapacity = capacity;
    }

    /**
     * Sets the initial data to be pushed to the stack.
     * Must be called before start() method.
     *
     * @param data array of doubles to push to the stack initially
     */
    public void setInitialData(double[] data) {
        this.initialData = data;
    }

    /**
     * Gets the stack instance for direct manipulation.
     * Allows external classes (like launcher) to access the stack.
     *
     * @return the StackDemo instance
     */
    public StackDemo getStack() {
        return stack;
    }

    public static void main(String[] args) {
        launch();
    }
}
