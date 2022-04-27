package model;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;

public class ScoresTest {

    @Test
    public void testSaveData_byUsing_addScores() throws IOException {
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

        List.addScore(s1);
        List.addScore(s2);
        List.addScore(s3);

        assertEquals(List.get(0).getName(), "Bob");
        assertEquals(List.get(0).getScore(), 500);

        assertEquals(List.get(1).getName(), "Sally");
        assertEquals(List.get(1).getScore(), 360);

        assertEquals(List.get(2).getName(), "Joe");
        assertEquals(List.get(2).getScore(), 920);

    }
}
