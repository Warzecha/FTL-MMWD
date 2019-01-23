package gui;

import evoNeat.Environment;
import example.Ftl;
import javafx.scene.Group;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiPanel {

    private ArrayList<FilledRectangle> rectangles = new ArrayList<>();
    private LineChart<Number, Number> fitnessChart;
    public NumberAxis fitnessAxis;
    private XYChart.Series fitnessSeries;
    private XYChart.Series sizeSeries;
    private Rectangle pointer = new Rectangle(0, 0);
    private ArrayList<Text> texts = new ArrayList<>();

    private int updateCounter = 0;

    private Environment model;
    private final List<String> TEXTS = Arrays.asList(
            "Generation Number:    ",
                "Top Genome Fitness:   ",
                "Population Size:           ",
                "Biggest Genome Size:  ");

    public GuiPanel() {
        for(int i = 0; i < 4; i++) {
            texts.add(new Text(60, 125 + i*30, TEXTS.get(i)));
        }
        rectangles.add(new FilledRectangle(260, 110, 200, 20));

        fitnessAxis = new NumberAxis(0, 200, 10);
        fitnessChart = new LineChart<>(fitnessAxis, new NumberAxis());
        pointer.setVisible(false);
        fitnessSeries = new XYChart.Series();
        fitnessSeries.setName("Best fitness");
        sizeSeries = new XYChart.Series();
        sizeSeries.setName("Biggest genome size");
        fitnessChart.getData().add(fitnessSeries);
        fitnessChart.getData().add(sizeSeries);
        fitnessChart.setLayoutY(220);
    }

    public void setEnv(Environment model) {
        this.model = model;
    }

    public void updatePanel() {

        if (model.generationNumber > updateCounter) {
            updateCounter = model.generationNumber;

            try {
                rectangles.get(0).setPercentageFill(100*model.generationNumber/model.maxGenerations);
            } catch (ArithmeticException e) {
                // ignore
            }
            texts.get(0).setText(TEXTS.get(0) + model.generationNumber);
            texts.get(1).setText(TEXTS.get(1) + model.topGenome.getPoints());
            texts.get(2).setText(TEXTS.get(2) + model.population.getSize());
            texts.get(3).setText(TEXTS.get(3) + model.population.getBiggestGenomeSize());

            if (model.generationNumber%4 == 1) {
                int newX = model.generationNumber;

                XYChart.Data fitnessData = new XYChart.Data(newX, model.topGenome.getPoints());
//            XYChart.Data sizeData = new XYChart.Data(newX, model.population.getBiggestGenomeSize());
                fitnessData.setNode(pointer);
//            sizeData.setNode(pointer);
                fitnessSeries.getData().add(fitnessData);
//            sizeSeries.getData().add(sizeData);
            }

        }

    }

    public void connectToGroup(Group group) {
        for(FilledRectangle fr : rectangles) {
            fr.connectToGroup(group);
        }
        for(Text t : texts) {
            group.getChildren().add(t);
        }
        group.getChildren().add(fitnessChart);
    }

    public ArrayList<FilledRectangle> getRectangles() {
        return rectangles;
    }

}
