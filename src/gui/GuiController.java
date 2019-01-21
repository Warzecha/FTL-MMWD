package gui;

import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GuiController {

    private final Gui parentGui;

    public GuiController(Gui parentGui) {
        this.parentGui = parentGui;
    }

    public void setUp(Stage stage, Group group) {
        setUpButton(stage, group);
        Slider populationSlider = createSlider(
                group, 0, 500, 50, 240, 30, "Population size"
        );
        populationSlider.setOnMouseDragged(event -> {
            parentGui.getFtl().populationSize = (int)populationSlider.getValue();
            System.out.println("Changing population size to: " + parentGui.getFtl().populationSize);
        });
        Slider generationsSlider = createSlider(
                group, 0, 5000, 500, 400, 30, "Generations number"
        );
        generationsSlider.setOnMouseDragged(event -> {
            parentGui.getFtl().maxGenerations = (int)generationsSlider.getValue();
            System.out.println("Changing generations max number to: " + parentGui.getFtl().maxGenerations);
        });
    }

    private void setUpButton(Stage stage, Group group) {
        Button button = new Button();
        button.setMinWidth(200);
        button.setMinHeight(50);
        button.setLayoutX(10);
        button.setLayoutY(10);
        setStartButton(button, stage);
        group.getChildren().add(button);
    }

    private Slider createSlider(Group group, int min, int max, int value, int x, int y, String title) {
        Slider slider = new Slider();
        slider.setMin(min);
        slider.setMax(max);
        slider.setLayoutX(x);
        slider.setLayoutY(y);
        slider.setValue(value);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(max/10);
        slider.setMinorTickCount(max/100);
        slider.setBlockIncrement(max/10);
        setUpSliderTitle(group, title, x, y);
        group.getChildren().add(slider);
        return slider;
    }

    private void setUpSliderTitle(Group group, String title, int x, int y) {
        Text text = new Text(title);
        text.setLayoutX(x);
        text.setLayoutY(y);
        group.getChildren().add(text);
    }

    private void setStartButton(Button button, Stage stage) {
        button.setText("Start Algorithm");
        button.setOnAction(event -> {
            parentGui.startAlgorithm(stage);
            setPauseButton(button);
        });
    }

    private void setPauseButton(Button button) {
        button.setText("Pause Algorithm");
        button.setOnAction(event -> {
            System.out.println("PAUSE!");
            parentGui.setRunning(false);
            setResumeButton(button);
        });
    }

    private void setResumeButton(Button button) {
        button.setText("Resume Algorithm");
        button.setOnAction(event -> {
            System.out.println("RESUME!");
            parentGui.setRunning(true);
            setPauseButton(button);
        });
    }


}
