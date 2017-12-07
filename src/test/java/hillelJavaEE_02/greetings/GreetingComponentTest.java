package hillelJavaEE_02.greetings;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.pow;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class GreetingComponentTest {

    @Test
    public void getRandomGreeting() {
        // math.semestr.ru/group/uniform-distribution.php

        GreetingComponent gC = new GreetingComponent();
        ArrayList<String> gList = (ArrayList<String>) gC.getGreetingsList();
        List<Integer> randomInts = new LinkedList<>();

        for (int i = 0; i < 30; i++) {
            randomInts.add(gList.indexOf(gC.getRandomGreeting()) + 1);
        }

        Double m1 = ((double)1 / randomInts.size()) * randomInts.stream().mapToInt((m) -> m).sum();

        assertThat(randomInts.stream().mapToDouble((m) -> m)
                                        .map((m) -> pow(m - m1, 2) / m1)
                                        .sum(),
                    lessThan(39.09));
    }
}