package org.kehrbusch.procedure;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.isA;
import static org.hamcrest.MatcherAssert.assertThat;

public class LevenshteinImplTest {
    @Test
    public void testThatCalculateWorks(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("hello");
        levenshtein.addChar("h", false);
        levenshtein.addChar("a", false);
        levenshtein.addChar("l", false);
        levenshtein.addChar("l", false);
        levenshtein.addChar("o", true);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 5);

        assertThat(result.getDistance(), is(1));
    }

    @Test
    public void testThatCalculateWorksWhenTwoWrong(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("helli");
        levenshtein.addChar("h", false);
        levenshtein.addChar("a", false);
        levenshtein.addChar("l", false);
        levenshtein.addChar("l", false);
        levenshtein.addChar("o", true);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 5);

        assertThat(result.getDistance(), is(2));
    }

    @Test
    public void testThatCalculateWorksWithDeletion(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("hallo");
        levenshtein.addChar("h", false);
        levenshtein.addChar("a", false);
        levenshtein.addChar("i", false);
        levenshtein.addChar("e", false);
        levenshtein.addChar("l", false);
        levenshtein.addChar("l", false);
        levenshtein.addChar("o", false);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 5);

        assertThat(result.getDistance(), is(2));
    }

    @Test
    public void testThatCalculateWorksWithInsertion(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("hallo");
        levenshtein.addChar("h", false);
        levenshtein.addChar("l", false);
        levenshtein.addChar("o", true);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 5);

        assertThat(result.getDistance(), is(2));
    }

    @Test
    public void testThatCancelConditionWorks(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("hallo");
        levenshtein.addChar("h", true);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 3);

        assertThat(result.isCancelled(), is(true));
    }

    @Test
    public void testThatCancelConditionKicksInAfterThirdWrong(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("hallo");
        levenshtein.addChar("x", false);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 2);
        assertThat(result.isCancelled(), is(false));

        levenshtein.addChar("a", false);
        result = levenshtein.calculate(d -> d > 2);
        assertThat(result.isCancelled(), is(false));

        levenshtein.addChar("i", false);
        result = levenshtein.calculate(d -> d > 2);
        assertThat(result.isCancelled(), is(false));

        levenshtein.addChar("j", false);
        result = levenshtein.calculate(d -> d > 2);
        assertThat(result.isCancelled(), is(true));
    }

    @Test
    public void testThatReplacingAndInsertionWorksInOne(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("hall");
        levenshtein.addChar("x", false);
        levenshtein.addChar("a", false);
        levenshtein.addChar("i", false);
        levenshtein.addChar("j", true);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 3);
        assertThat(result.isCancelled(), is(false));
    }

    @Test
    public void testThatResultReturnsCorrectSequenceOfDistances(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("hallo");
        levenshtein.addChar("h", false);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 2);
        assertThat(result.getDistance(), is(0));

        levenshtein.addChar("a", false);
        result = levenshtein.calculate(d -> d > 2);
        assertThat(result.getDistance(), is(0));

        levenshtein.addChar("l", false);
        result = levenshtein.calculate(d -> d > 2);
        assertThat(result.getDistance(), is(0));

        levenshtein.addChar("l", true);
        result = levenshtein.calculate(d -> d > 2);
        assertThat(result.getDistance(), is(1));
        assertThat(result.getFinalString(), is("hall"));
    }

    @Test
    public void testThatIdenticalMatchWorks(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("43032 Strada Cascina");
        levenshtein.addChar("4", false);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("3", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("0", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("3", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("2", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar(" ", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("S", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("t", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("r", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("a", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("d", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("a", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar(" ", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("C", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("a", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("s", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("c", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("i", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("n", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
        levenshtein.addChar("a", false);
        result = levenshtein.calculate(d -> d > 10);
        assertThat(result.getDistance(), is(0));
    }

    @Test
    public void testThatLevenshteinCopyConstructor(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("Hello");
        levenshtein.addChar("H", false);
        levenshtein.addChar("e", false);
        levenshtein.addChar("l", false);

        LevenshteinImpl levenshtein1 = new LevenshteinImpl(levenshtein);

        levenshtein.addChar("i", true);
        levenshtein.calculate(d -> d > 5);

        levenshtein1.addChar("l", false);
        levenshtein1.addChar("i", true);

        LevenshteinImpl.Result result = levenshtein1.calculate(d -> d > 5);
        assertThat(result.getDistance(), is(1));
    }

    @Test
    public void testThatSimpleTestWorks(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("123 Test");
        levenshtein.addChar("1", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("2", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("3", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar(" ", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("T", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("e", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("s", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("t", false);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 5);

        assertThat(result.getDistance(), is(0));
    }

    @Test
    public void testThatIncompleteSearchWorks(){
        LevenshteinImpl levenshtein = new LevenshteinImpl("123 Tes");
        levenshtein.addChar("1", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("2", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("3", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar(" ", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("T", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("e", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("s", false);
        LevenshteinImpl.Result result = levenshtein.calculate(d -> d > 5);

        assertThat(result.getDistance(), is(0));
        assertThat(result.hasReachedValidatableLength(), is(true));
        assertThat(result.getFinalString(), is("123 Tes"));
        assertThat(result.isCancelled(), is(false));

        levenshtein.addChar("t", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("i", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("n", false);
        levenshtein.calculate(d -> d > 5);
        levenshtein.addChar("g", false);
    }
}
