package model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.*;

public class ScoresTest {

    @Test
    void testSaveData() {
        ScoreList List = new ScoreList();

        String name1 = "Bob";
        int score1 = 500;

        String name2 = "Sally";
        int score2 = 360;

        String name3 = "Joe";
        int score3 = 920;

        Scores s1 = new Scores(name1, score1);
        Scores s2 = new Scores(name2, score2);
        Scores s3 = new Scores(name3, score3);

        // List.add(s1);
        // List.add(s2);
        // List.add(s3);

        assertEquals(List.get(0).getName(), "Bob");
        assertEquals(List.get(0).getName(), "500");

        assertEquals(List.get(1).getName(), "Sally");
        assertEquals(List.get(1).getName(), "360");

        assertEquals(List.get(2).getName(), "Joe");
        assertEquals(List.get(2).getName(), "920");

    }

    @Test
    void testSort_byUsing_getScores() throws IOException {
        ScoreList List = new ScoreList();

        String name1 = "Bob";
        int score1 = 500;

        String name2 = "Sally";
        int score2 = 360;

        String name3 = "Joe";
        int score3 = 920;

        Scores s1 = new Scores(name1, score1);
        Scores s2 = new Scores(name2, score2);
        Scores s3 = new Scores(name3, score3);

        // List.add(s1);
        // List.add(s2);
        // List.add(s3);

        List.loadData();

        assertEquals(List.get(0).getName(), "Joe");
        assertEquals(List.get(0).getName(), "920");

        assertEquals(List.get(1).getName(), "Bob");
        assertEquals(List.get(1).getName(), "500");

        assertEquals(List.get(0).getName(), "Sally");
        assertEquals(List.get(0).getName(), "360");
    }

}
