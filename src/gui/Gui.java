package gui;

import evoNeat.Environment;
import example.Ftl;
import example.Xor;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Gui extends Application {

    private long startNanoTime = 0;//System.currentTimeMillis();
    private GuiPanel panel = new GuiPanel();
    private GuiController controller;
    private Group group = new Group();


    private Environment model = new Ftl(14, 12, 50, 200);
//    private Environment model = new Xor(400, 1000);

    private boolean running = false;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        controller = new GuiController(this);

        panel.setEnv(model);
        Scene scene = new Scene(group, 550, 650);
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

        model.initPopulation();

    }

    private void loop(double time) {
        if (running) {
            model.iterate();
            if (model.generationNumber >= model.maxGenerations) {
                running = false;
            }
        }
    }

    private void render(double time, Stage stage) {
        panel.updatePanel();
    }


    public Environment getModel() {
        return model;

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