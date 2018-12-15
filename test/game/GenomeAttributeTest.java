package game;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class GenomeAttributeTest {

    @Test
    public void mutatesInRange() {
        GenomeAttribute attribute = new GenomeAttribute(0, 100, 50);
        for (int i = 0; i < 100; i++) {
            attribute.setValue(50);
            attribute.mutate(5);
            assertTrue(attribute.getValue() >= 45 && attribute.getValue() <= 55);
        }
    }

}
