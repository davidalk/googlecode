package uk.co.alkanani;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.readAllLines;
import static java.nio.file.Files.write;

public class TidyNumbers {

    public static void main(String... args) throws IOException {

        String fileName = "B-large";

        Path file = new File("src/main/resources/tidy/" + fileName+ ".in.txt").toPath();

        List<String> inputFile = readAllLines(file, StandardCharsets.UTF_8);

        int noTests = Integer.parseInt(inputFile.get(0));

        List<String> outputList = new ArrayList<>();
        for (int i = 1; i<=noTests; i++) {
            List<Integer> list = stringToArray(inputFile.get(i));
            tidyUp(list, findTidyBoundary(list));
            String out = "Case #" + i + ": " + arrayToString(list);
            outputList.add(out);
            System.out.println(out);

        }


        Path outFile = new File("src/main/resources/tidy/" + fileName+ ".out.txt").toPath();
        write(outFile, outputList, StandardCharsets.UTF_8);
    }


    private static List<Integer> stringToArray(String input) {
        List<Integer> list = new ArrayList<>(input.length());

        for (int i=0; i<input.length(); i++) {
            list.add(Character.getNumericValue(input.charAt(i)));
        }
        return list;
    }

    private static int findTidyBoundary(List<Integer> input) {
        for (int i=0; i<input.size(); i++) {
            int next = i+1;
            if (next == input.size()) {
                return i;
            } else {
                if (input.get(next) < input.get(i)) {
                   return i;
                }
            }
        }
        throw new RuntimeException("Shouldn't get here!");
    }

    private static void tidyUp(List<Integer> input, int tidyBoundary) {
        for (int i=tidyBoundary; i >= 0; i--) {
            int right = i+1;
            int left = i>0 ? i-1 : i;
            if (right < input.size() && input.get(i) > input.get(right)) {
                input.set(i, input.get(i) - 1);

                for (int y=right; y<input.size(); y++) {
                    input.set(y, 9);
                }
            }

        }
    }

    private static String arrayToString(List<Integer> input) {
        final boolean[] firstDigitHasOutput = {false};
        StringBuilder builder = new StringBuilder();
        input.forEach(i -> {
            if (!firstDigitHasOutput[0] && i == 0) {
                return;
            }

            if (!firstDigitHasOutput[0]) {
                builder.append(i);
                firstDigitHasOutput[0] = true;
            } else {
                builder.append(i);
            }
        });
        return builder.toString();
    }
}
