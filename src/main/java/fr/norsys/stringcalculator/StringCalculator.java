package fr.norsys.stringcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {



    public int add(String numbers) {
        if (numbers.isEmpty())
            return 0;
        String delimiters = "[,\n]";
        if (numbers.startsWith("//")) {
            // create a pattern to extract the delimiters
            Pattern pattern = Pattern.compile("\\[([^\\[\\]]*)\\]");
            Matcher matcher = pattern.matcher(numbers);
            if (numbers.charAt(2) == '[') {
                int delimitersLength = 0;
                StringBuilder stringBuilder = new StringBuilder();
                while (matcher.find()) {
                    // quote() escape the delimiter and group() get the delimiter that matches the regex
                    stringBuilder.append(Pattern.quote(matcher.group(1)) + "|");
                    delimitersLength += matcher.group(1).length() + 2;
                }
                delimiters = stringBuilder.substring(0, stringBuilder.length() - 1);
                numbers = numbers.substring(delimitersLength + 3);
            } else {
                delimiters = String.valueOf(numbers.charAt(2));
                numbers = numbers.substring(4);
            }
        }
        String[] numberArr = numbers.split(delimiters);
        int length = numberArr.length;
        List<String> negatives = new ArrayList<>();
        for (int j = 0; j < length; j++)
            if (numberArr[j].startsWith("-"))
                negatives.add(numberArr[j]);
        if (!negatives.isEmpty())
            throw new RuntimeException("negatives not allowed " + negatives);
        int res = 0;
        for (int i = 0; i < length; i++) {
            int num = Integer.parseInt(numberArr[i]);
            if (num < 1000)
                res += num;
        }
        return res;
    }

}
