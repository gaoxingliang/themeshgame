import com.google.common.base.Splitter;

import java.io.Console;
import java.util.Arrays;

/**
 *
 * Created by edward.gao on 16/11/2017.
 */
public class Main {
    private static final ThreadLocal<Result> result = new ThreadLocal<>();

    /**
     * a test
     * @param args
     */
    public static void main(String[] args) {

        Console c = System.console();
        while (true) {
            c.printf("input the numbers\n");
            String numbers = c.readLine();
            if (numbers.isEmpty()) {
                c.printf("No numbers");
                continue;
            }
            int targetNum = 0;
            while (true) {
                c.printf("input the target\n");
                String target = c.readLine();
                if (target.isEmpty()) {
                    c.printf("No target");
                    continue;
                }
                targetNum = Integer.valueOf(target);
                break;
            }
            int [] nums = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(numbers).stream().mapToInt(k -> Integer.valueOf(k)).sorted().toArray();
            System.out.println("Number are - " + Arrays.toString(nums));
            c.printf("which numbers has multiple 2? \n");
            String muls = c.readLine();
            KIND [] kinds = new KIND[nums.length];
            int mulsArr [] =new int[0];
            if (muls.length() > 0) {
                mulsArr =  Splitter.on(",").trimResults().omitEmptyStrings().splitToList(muls).stream().mapToInt(k -> Integer.valueOf(k)).toArray();
            }
            for (int i = 0; i < nums.length; i++) {
                KIND kind = Main.KIND.DEFAULT;
                for (int v : mulsArr) {
                    if (v == nums[i]) {
                        kind = KIND.MULTIPLY2;
                        break;
                    }
                }
                kinds[i] = kind;
            }
            System.out.println(solve(targetNum, nums, kinds));

        }



    }


    public static boolean isTarget(int target, String[] exps, int[] numbers, KIND[] kinds, int n) {

        if (n == 1) {
            return numbers[0] == target;
        }
        for (int i = 0; i < n; i++) {

            for (int j = i + 1; j < n; j++) {

                int a, b;
                String expa, expb;
                a = numbers[i];
                b = numbers[j];
                numbers[j] = numbers[n - 1];
                expa = exps[i];
                expb = exps[j];
                exps[j] = exps[n - 1];
                // record previous kind and result
                KIND kinda = kinds[i], kindb = kinds[j];

                if (kinds[i] == KIND.DEFAULT && kinds[j] == KIND.DEFAULT) {
                    exps[i] = "(" + expa + "+" + expb + ")";

                    numbers[i] = a + b;
                    kinds[i] = kinds[j] = KIND.DEFAULT;
                    if (isTarget(target, exps, numbers, kinds, n - 1)) {
                        return true;
                    }

                    if (a > b) {
                        exps[i] = "(" + expa + "-" + expb + ")";
                        numbers[i] = a - b;
                    }
                    else {
                        exps[i] = "(" + expb + "-" + expa + ")";
                        numbers[i] = b - a;
                    }

                    if (isTarget(target, exps, numbers, kinds, n - 1)) {
                        return true;
                    }

                }

                if (kinds[i] == KIND.DEFAULT && kinds[j] == KIND.MULTIPLY2) {
                    exps[i] = "(" + expa + "*" + expb + ")";

                    numbers[i] = a * b;
                    kinds[i] = kinds[j] = KIND.DEFAULT;

                    if (isTarget(target, exps, numbers, kinds, n - 1)) {
                        return true;
                    }

                    exps[i] = "(" + expa + "/" + expb + ")";
                    if (b > 0 && a % b == 0) {
                        numbers[i] = a / b;
                        if (isTarget(target, exps, numbers, kinds, n - 1)) {
                            return true;
                        }
                    }

                }


                if (kinds[i] == KIND.MULTIPLY2 && kinds[j] == KIND.DEFAULT) {
                    exps[i] = "(" + expb + "*" + expa + ")";

                    numbers[i] = a * b;
                    kinds[i] = kinds[j] = KIND.DEFAULT;

                    if (isTarget(target, exps, numbers, kinds, n - 1)) {
                        return true;
                    }

                    exps[i] = "(" + expb + "/" + expa + ")";


                    if (a > 0 && b % a == 0) {

                        numbers[i] = b / a;
                        if (isTarget(target, exps, numbers, kinds, n - 1)) {
                            return true;
                        }
                    }
                }

                numbers[i] = a;
                numbers[j] = b;
                exps[i] = expa;
                exps[j] = expb;
                kinds[i] = kinda;
                kinds[j] = kindb;

                if (isAllEqualTarget(target, n, numbers)) {
                    StringBuilder expsBuilder = new StringBuilder();
                    for (int k = 0; k <= n -1; k++) {
                        expsBuilder.append(exps[k]);
                        if (k != n -1) {
                            expsBuilder.append("=");
                        }
                    }
                    System.out.println(String.format("special case, target=%d,expression=%s", target, expsBuilder.toString()));
                    result.get().expression = expsBuilder.toString();
                    result.get().matched = true;
                    return true;
                }

            }
        }
        return false;
    }


    private static boolean isAllEqualTarget(int target, int n, int [] nums) {
        for (int i = 0; i < n; i++) {
            if (nums[i] != target) {
                return false;
            }
        }
        return true;
    }


    public static Result solve(int target, int[] numbers, KIND[] kinds) {
        Result r = new Result();
        result.set(r);
        String[] exps = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            exps[i] = String.valueOf(numbers[i]);
        }

        boolean isTrue = isTarget(target, exps, numbers, kinds, numbers.length);
        if (isTrue) {
            r.matched = true;
            r.expression = r.expression.equals("unknown") ? exps[0] : r.expression;
        }
        return r;
    }


    /**
     * the number kind
     *
     */
    enum KIND {
        DEFAULT,  // default ,only + - could be used
        MULTIPLY2 // multiple 2, divide 2
    }


    static class Result {
        boolean matched = false;
        String expression = "unknown";


        @Override
        public String toString() {
            return String.format("matched=%s, expression=%s", matched, expression);
        }
    }
}
