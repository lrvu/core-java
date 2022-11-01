package objectAnalyzer;

import java.util.ArrayList;

public class ObjectAnalyzerTest {
    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException {
        var squares = new ArrayList<Integer>();
        for(int i = 0; i <= 5; i++)
            squares.add(i * i);
        System.out.println(new ObjectAnalyzer().toString(squares));
    }
}
