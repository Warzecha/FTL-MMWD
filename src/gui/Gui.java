package gui;

import example.Xor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Gui extends Application {

    private long startNanoTime = System.currentTimeMillis();
    private GuiPanel panel = new GuiPanel();
    private GuiController controller;
    private Group group = new Group();
    private Xor xor = new Xor();
    private boolean running = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        controller = new GuiController(this);
        panel.setXor(xor);
        Scene scene = new Scene(group, 600, 400);
        stage.setTitle("Drawing a Rectangle");
        stage.setScene(scene);
        controller.setUp(stage, group);
        stage.show();
    }

    public void startAlgorithm(Stage stage) {
        System.out.println("START!");
        setUpAlgorithm();
        startTimer(stage);
        panel.connectToGroup(group);
        setRunning(true);
    }

    private void startTimer(Stage stage) {
        new AnimationTimer()
        {
            double lastIteration = 0.0;
            double step = 0.015;
            public void handle(long currentNanoTime)
            {
                double t = (currentNanoTime - startNanoTime) / 1000000000.0;

                if(t - lastIteration > step) {
                    lastIteration = t;
                    loop(t);
                    render(t, stage);
                }
            }
        }.start();
    }

    public void setUpAlgorithm() {
        xor.initPopulation();
    }

    private void loop(double time) {
        if (running) {
            xor.iterate();
            if (xor.generationNumber >= xor.maxGenerations) {
                running = false;
            }
        }
    }

    private void render(double time, Stage stage) {
        panel.updatePanel();
    }

    public Xor getXor() {
        return xor;
    }

    public GuiPanel getPanel() {
        return panel;
    }

    public Group getGroup() {
        return group;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}