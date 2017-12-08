package hillelJavaEE_02.greetings;

import org.junit.Test;

import java.util.*;

import static java.lang.Math.pow;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

public class GreetingComponentTest {

    @Test
    public void getRandomGreeting() {
        // math.semestr.ru/group/uniform-distribution.php

        GreetingComponent gC = new GreetingComponent();
        List<String> gList = gC.getGreetingsList();
        Map<Integer, Integer> distribution = new HashMap<>();

        for (int i = 0; i < 1_000_000; i++) {
            Integer randomValue = gList.indexOf(gC.getRandomGreeting()) + 1;
            if(distribution.get(randomValue) == null) {
                distribution.put(randomValue, 1);
            } else {
                distribution.put(randomValue, distribution.get(randomValue) + 1);
            }
        }
        Collection<Integer> realFrequencies = distribution.values();

        Double m1 = ((double)1 / realFrequencies.size()) * realFrequencies.stream().mapToInt((m) -> m).sum();

        // helpstat.ru/statisticheskie-tablitsyi/kriticheskie-tochki-2-raspredeleniya/
        // k =2 (size - 1), a = 0.1, -> 4.60517
        assertThat(realFrequencies.stream().mapToDouble((m) -> m)
                                        .map((m) -> pow(m - m1, 2) / m1)
                                        .sum(),
                    lessThan(4.60517));
    }
}