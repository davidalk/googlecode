package uk.co.alkanani;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;

public class PancakeFlipper {

    public static void main(String... args) throws IOException {
        String fileName = "A-large";

        Path file = new File("src/main/resources/flipper/" + fileName + ".in.txt").toPath();

        List<String> inputFile = readAllLines(file, StandardCharsets.UTF_8);

        int noTests = Integer.parseInt(inputFile.get(0));

        List<String> outputList = new ArrayList<>();
        for (int i = 1; i <= noTests; i++) {
            String line[] = inputFile.get(i).split("\\s");
            boolean[] pancakes = createPancakeArray(line[0]);
            int flipper = Integer.valueOf(line[1]);
            int result = flip(pancakes, flipper);
            String output = "Case #" + i + ": " + (result < 0 ? "IMPOSSIBLE" : result);
            System.out.println(output);
            outputList.add(output);
        }

        Path outFile = new File("src/main/resources/flipper/" + fileName + ".out.txt").toPath();
        write(outFile, outputList, StandardCharsets.UTF_8);

    }

    private static int flip(boolean[] pancakes, int flipper) {
        int count = 0;
        int nextNegativePosLeft;
        int nextNegativePosRight;

        do {
            nextNegativePosLeft = getNextNegativePosFromLeft(pancakes);
            if (nextNegativePosLeft == -1) return count;
            if (nextNegativePosLeft + flipper > pancakes.length) return -1;
            flipSegment(pancakes, nextNegativePosLeft, flipper, false);
            count++;
            nextNegativePosRight = getNextNegativePosFromRight(pancakes);
            if (nextNegativePosRight == -1) return count;
            if (nextNegativePosRight - flipper < 0) return -1;
            flipSegment(pancakes, nextNegativePosRight, flipper, true);
            count++;

            if (getNextNegativePosFromLeft(pancakes) == -1 || getNextNegativePosFromRight(pancakes)== -1) return count;

        } while (nextNegativePosLeft + flipper < nextNegativePosRight);

        return -1;
    }

    private static void flipSegment(boolean[] pancakes, int pos, int flipper, boolean right) {
        for (int i = 0; i < flipper; i++) {
            int arrayPos;
            if (right) {
                arrayPos = pos - i;
            } else {
                arrayPos = pos + i;
            }
            if (arrayPos < 0 || arrayPos > pancakes.length - 1) throw new RuntimeException("Shouldn't get here");
            pancakes[arrayPos] = !pancakes[arrayPos];
        }

    }


    private static int getNextNegativePosFromLeft(boolean[] pancakes) {
        for (int i = 0; i < pancakes.length; i++) {
            if (!pancakes[i]) return i;
        }
        return -1;
    }

    private static int getNextNegativePosFromRight(boolean[] pancakes) {
        for (int i = pancakes.length - 1; i >= 0; i--) {
            if (!pancakes[i]) return i;
        }
        return -1;
    }

    private static boolean[] createPancakeArray(String input) {
        boolean[] pancakeArray = new boolean[input.length()];
        for (int i = 0; i < input.length(); i++) {
            char pancake = input.charAt(i);
            if (pancake == '+') {
                pancakeArray[i] = true;
            } else if (pancake == '-') {
                pancakeArray[i] = false;
            } else {
                throw new RuntimeException("Shouldn't get here!");
            }
        }

        return pancakeArray;
    }
}
