package agh.cs.project1;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main
{
    public static void main(String[] args)
    {
//        for (int i = 0; i < 50; ++i)
//        {
//            Genome g = Genome.createRandomized(32);
//            System.out.println("\"" + g + "\",");
//        }

        int[] singleParent = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int[] doubleParent = new int[] { 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 };

        int firstSlice = 4;
        int secondSlice = 8;

        System.out.println(Arrays.toString(IntStream.concat(
                Arrays.stream(doubleParent).limit(firstSlice),
                IntStream.concat(
                        Arrays.stream(singleParent).limit(secondSlice).skip(firstSlice),
                        Arrays.stream(doubleParent).skip(secondSlice)
                )
        ).toArray()));
    }
}
