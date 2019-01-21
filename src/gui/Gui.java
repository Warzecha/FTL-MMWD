package gui;

import example.Ftl;
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
    private Ftl ftl = new Ftl(14, 12, 200, 100);
    private boolean running = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        controller = new GuiController(this);
        panel.setFtl(ftl);
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
        ftl.initPopulation();
    }

    private void loop(double time) {
        if (running) {
            ftl.iterate();
            if (ftl.generationNumber >= ftl.maxGenerations) {
                running = false;
            }
        }
    }

    private void render(double time, Stage stage) {
        panel.updatePanel();
    }

    public Ftl getFtl() {
        return ftl;
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