package gui;

import example.Xor;
import javafx.scene.Group;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GuiPanel {

    private ArrayList<FilledRectangle> rectangles = new ArrayList<>();
    private ArrayList<Text> texts = new ArrayList<>();
    private Xor xor;
    private final List<String> TEXTS = Arrays.asList(
            "Generation Number:    ",
                "Top Genome Fitness:   ",
                "Population Size:           ",
                "Biggest Genome Size:  ");

    public GuiPanel() {
        for(int i = 0; i < 4; i++) {
            rectangles.add(new FilledRectangle(210, 100+ i*60, 200, 50));
            texts.add(new Text(10, 125 + i*60, TEXTS.get(i)));
        }
    }

    public void setXor(Xor xor) {
        this.xor = xor;
    }

    public void updatePanel() {
        try {
            rectangles.get(0).setPercentageFill(100*xor.generationNumber/xor.maxGenerations);
        } catch (ArithmeticException e) {
            // ignore
        }
        texts.get(0).setText(TEXTS.get(0) + xor.generationNumber);
        rectangles.get(1).setPercentageFill(xor.topGenome.getFitness()*25);
        texts.get(1).setText(TEXTS.get(1) + Math.round(xor.topGenome.getFitness() * 100.0) / 100.0);
        rectangles.get(2).setPercentageFill(xor.population.getSize());
        texts.get(2).setText(TEXTS.get(2) + xor.population.getSize());
        rectangles.get(3).setPercentageFill(xor.population.getBiggestGenomeSize()*20);
        texts.get(3).setText(TEXTS.get(3) + xor.population.getBiggestGenomeSize());
    }

    public void connectToGroup(Group group) {
        for(FilledRectangle fr : rectangles) {
            fr.connectToGroup(group);
        }
        for(Text t : texts) {
            group.getChildren().add(t);
        }
    }

    public ArrayList<FilledRectangle> getRectangles() {
        return rectangles;
    }

}
