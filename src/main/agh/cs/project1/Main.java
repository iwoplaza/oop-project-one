package agh.cs.project1;

import java.util.Random;

public class Main
{
    public static void main(String[] args)
    {
        Random rand = new Random();
        for (int i = 0; i < 200; ++i)
        {
            Genome g = Genome.createRandomized(15 + rand.nextInt(15));
            System.out.println("\"" + g + "\",");
        }
    }
}
