package com.mycompany.stackoperationsimulator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Random;

/**
 * Stack Simulator Launcher - Configuration Screen
 *
 * <p>This application provides a launcher/configuration screen that allows users
 * to configure stack parameters before starting the main simulator.</p>
 *
 * <p>Features include:
 * - Configurable maximum stack size (5-20)
 * - Option to start with empty stack or random data
 * - Configurable number of random elements (1-10)
 * </p>
 */
public class StackLauncher extends Application {

    // UI Components
    private Spinner<Integer> stackSizeSpinner;
    private RadioButton emptyStackRadio;
    private RadioButton randomDataRadio;
    private Spinner<Integer> randomCountSpinner;
    private ToggleGroup dataOptionsGroup;

    @Override
    public void start(Stage stage) {
        // Stage configuration
        stage.setTitle("Stack Simulator Launcher");
        stage.setWidth(500);
        stage.setHeight(450);
        stage.setResizable(false);

        // Create root layout
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);

        // Add all sections
        root.getChildren().addAll(
            createTitleSection(),
            createDescriptionSection(),
            createConfigurationSection(),
            createButtonPanel()
        );

        // Create and set scene
        Scene scene = new Scene(root, 500, 450);

        // Load external CSS stylesheet
        String css = getClass().getResource("styles.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    /**
     * Creates the title section with application name.
     *
     * @return Label containing the title
     */
    private Label createTitleSection() {
        Label titleLabel = new Label("Stack Operation Simulator");
        titleLabel.setFont(Font.font("System", FontWeight.BOLD, 24));
        titleLabel.setAlignment(Pos.CENTER);
        titleLabel.getStyleClass().add("launcher-title");

        return titleLabel;
    }

    /**
     * Creates the description section with application description.
     *
     * @return TextArea containing the description
     */
    private TextArea createDescriptionSection() {
        TextArea descriptionArea = new TextArea(
            "This application simulates the operations of a Stack data structure."
        );
        descriptionArea.setEditable(false);
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefHeight(80);
        descriptionArea.setFocusTraversable(false);
        descriptionArea.getStyleClass().add("launcher-description");
        descriptionArea.setStyle("-fx-background-color: #f5f5f5;");

        return descriptionArea;
    }

    /**
     * Creates the configuration section with all configuration controls.
     *
     * @return VBox containing the configuration panel
     */
    private VBox createConfigurationSection() {
        VBox configSection = new VBox(10);

        // Configuration title
        Label configLabel = new Label("Configuration");
        configLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        configLabel.getStyleClass().add("config-title");

        // Create GridPane for configuration controls
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(15);

        // Maximum Stack Size Control (Row 0)
        Label stackSizeLabel = new Label("Maximum Stack Size:");
        stackSizeLabel.getStyleClass().add("config-label");
        GridPane.setConstraints(stackSizeLabel, 0, 0);

        stackSizeSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> stackSizeFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(5, 20, 10);
        stackSizeSpinner.setValueFactory(stackSizeFactory);
        stackSizeSpinner.setEditable(true);
        stackSizeSpinner.setPrefWidth(80);
        GridPane.setConstraints(stackSizeSpinner, 1, 0);

        // Initial Data Options (Row 1)
        Label initialDataLabel = new Label("Initial Data:");
        initialDataLabel.getStyleClass().add("config-label");
        GridPane.setConstraints(initialDataLabel, 0, 1);

        // Create radio buttons with toggle group
        dataOptionsGroup = new ToggleGroup();

        emptyStackRadio = new RadioButton("Empty Stack");
        emptyStackRadio.setToggleGroup(dataOptionsGroup);
        emptyStackRadio.setSelected(true);

        randomDataRadio = new RadioButton("Random Data");
        randomDataRadio.setToggleGroup(dataOptionsGroup);

        HBox radioBox = new HBox(10);
        radioBox.getChildren().addAll(emptyStackRadio, randomDataRadio);
        GridPane.setConstraints(radioBox, 1, 1);

        // Random Data Count (Row 2)
        Label randomCountLabel = new Label("Number of Elements:");
        randomCountLabel.getStyleClass().add("config-label");
        GridPane.setConstraints(randomCountLabel, 0, 2);

        randomCountSpinner = new Spinner<>();
        SpinnerValueFactory<Integer> randomCountFactory =
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 5);
        randomCountSpinner.setValueFactory(randomCountFactory);
        randomCountSpinner.setEditable(true);
        randomCountSpinner.setPrefWidth(80);
        randomCountSpinner.setDisable(true); // Initially disabled
        GridPane.setConstraints(randomCountSpinner, 1, 2);

        // Add listener to enable/disable random count spinner
        randomDataRadio.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            randomCountSpinner.setDisable(!isSelected);
        });

        // Add all controls to GridPane
        gridPane.getChildren().addAll(
            stackSizeLabel, stackSizeSpinner,
            initialDataLabel, radioBox,
            randomCountLabel, randomCountSpinner
        );

        configSection.getChildren().addAll(configLabel, gridPane);

        return configSection;
    }

    /**
     * Creates the button panel with Start Simulator and Exit buttons.
     *
     * @return HBox containing the action buttons
     */
    private HBox createButtonPanel() {
        HBox buttonPanel = new HBox(20);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.setPadding(new Insets(30, 0, 0, 0));

        // Start Simulator Button
        Button startButton = new Button("Start Simulator");
        startButton.setPrefWidth(150);
        startButton.setPrefHeight(40);
        startButton.getStyleClass().add("launcher-start-button");
        startButton.setStyle(
            "-fx-background-color: #4CAF50; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 5px;"
        );
        startButton.setOnAction(e -> handleStartSimulator());

        // Exit Button
        Button exitButton = new Button("Exit");
        exitButton.setPrefWidth(150);
        exitButton.setPrefHeight(40);
        exitButton.getStyleClass().add("launcher-exit-button");
        exitButton.setStyle(
            "-fx-background-color: #757575; " +
            "-fx-text-fill: white; " +
            "-fx-font-size: 14px; " +
            "-fx-font-weight: bold; " +
            "-fx-padding: 10px 20px; " +
            "-fx-background-radius: 5px;"
        );
        exitButton.setOnAction(e -> handleExit());

        buttonPanel.getChildren().addAll(startButton, exitButton);

        return buttonPanel;
    }

    /**
     * Handles the Start Simulator button action.
     * Creates and launches the main simulator with configured parameters.
     */
    private void handleStartSimulator() {
        // Get selected stack size
        int stackSize = stackSizeSpinner.getValue();

        // Create new stage for main simulator
        Stage simulatorStage = new Stage();

        // Create simulator with capacity using constructor
        App simulator = new App(stackSize);

        // Check which radio button is selected
        if (randomDataRadio.isSelected()) {
            // Random data selected
            int numberOfElements = randomCountSpinner.getValue();

            // Validate number of elements does not exceed stack size
            if (numberOfElements > stackSize) {
                numberOfElements = stackSize;
            }

            // Generate random integers between -99 and 99
            Random random = new Random();
            int[] initialData = new int[numberOfElements];
            for (int i = 0; i < numberOfElements; i++) {
                initialData[i] = random.nextInt(199) - 99; // Range: -99 to 99
            }

            // Set initial data
            simulator.setInitialData(initialData);
        }

        // Start simulator
        simulator.start(simulatorStage);

        // Close launcher stage
        Stage launcherStage = (Stage) stackSizeSpinner.getScene().getWindow();
        launcherStage.close();
    }

    /**
     * Handles the Exit button action.
     * Closes the application.
     */
    private void handleExit() {
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
