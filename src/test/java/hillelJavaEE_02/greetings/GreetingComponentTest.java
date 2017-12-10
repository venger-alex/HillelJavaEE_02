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

        GreetingComponent greetingComponent = new GreetingComponent();
        Map<String, Integer> distributionMap = new HashMap<>();

        for (int i = 0; i < 1_000_000; i++) {
            distributionMap.merge(greetingComponent.getRandomGreeting(), 1, (a, b) -> a + b);
        }
        Collection<Integer> realFrequencies = distributionMap.values();

        Double m1 = ((double)1 / realFrequencies.size()) * realFrequencies.stream().mapToInt((m) -> m).sum();

        // helpstat.ru/statisticheskie-tablitsyi/kriticheskie-tochki-2-raspredeleniya/
        // k =2 (size - 1), a = 0.1, -> 4.60517
        assertThat(realFrequencies.stream().mapToDouble((m) -> m)
                                        .map((m) -> pow(m - m1, 2) / m1)
                                        .sum(),
                    lessThan(4.60517));
    }
}