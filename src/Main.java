/**
 * The main logic:
 * for the four numbers:
 * <p>
 * <p>
 * Created by edward.gao on 16/11/2017.
 */
public class Main {


    /**
     * @param args
     */
    public static void main(String[] args) {
        int[] numbers = {2, 5, 6, 7, 8};
        KIND[] kinds = {KIND.MULTIPLY2, KIND.DEFAULT, KIND.DEFAULT, KIND.DEFAULT, KIND.DEFAULT};
        int target = 1;
        System.out.println(solve(target, numbers, kinds));

    }


    public static boolean isTarget(int target, String[] exps, int[] numbers, KIND[] kinds, boolean[] used, int n) {

        if (n == 1) {
            return numbers[0] == target;
        }
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int a, b;
                String expa, expb;
                a = numbers[i];
                b = numbers[j];
                used[i] = used[j] = true;
                numbers[j] = numbers[n - 1];
                expa = exps[i];
                expb = exps[j];
                exps[j] = exps[n - 1];


                if (kinds[i] == KIND.DEFAULT && kinds[j] == KIND.DEFAULT) {
                    exps[i] = "(" + expa + "+" + expb + ")";

                    numbers[i] = a + b;
                    if (isTarget(target, exps, numbers, kinds, used, n - 1)) {
                        return true;
                    }


                    exps[i] = "(" + expa + "-" + expb + ")";

                    numbers[i] = a - b;
                    if (a > b && isTarget(target, exps, numbers, kinds, used, n - 1)) {
                        return true;
                    }

                }

                if (kinds[i] == KIND.DEFAULT && kinds[j] == KIND.MULTIPLY2) {
                    exps[i] = "(" + expa + "*" + expb + ")";

                    numbers[i] = a * b;
                    if (isTarget(target, exps, numbers, kinds, used, n - 1)) {
                        return true;
                    }

                    exps[i] = "(" + expa + "/" + expb + ")";

                    numbers[i] = a / b;
                    if (a % b == 0 & isTarget(target, exps, numbers, kinds, used, n - 1)) {
                        return true;
                    }
                }


                if (kinds[i] == KIND.MULTIPLY2 && kinds[j] == KIND.DEFAULT) {
                    exps[i] = "(" + expa + "*" + expb + ")";

                    numbers[i] = a * b;
                    if (isTarget(target, exps, numbers, kinds, used, n - 1)) {
                        return true;
                    }

                    exps[i] = "(" + expb + "/" + expa + ")";


                    if (a > 0 && b % a == 0) {

                        numbers[i] = b / a;
                        if (isTarget(target, exps, numbers, kinds, used, n - 1)) {
                            return true;
                        }
                    }
                }

                numbers[i] = a;
                numbers[j] = b;
                exps[i] = expa;
                exps[j] = expb;
                used[i] = used[j] = false;
            }
        }
        return false;
    }


    public static Result solve(int target, int[] numbers, KIND[] kinds) {
        Result r = new Result();
        boolean marked[] = new boolean[numbers.length];


        String[] exps = new String[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            exps[i] = String.valueOf(numbers[i]);
            if (i == 0) {
                marked[0] = true;
            }
            else {
                marked[i] = false;
            }
        }
        boolean isTrue = isTarget(target, exps, numbers, kinds, marked, numbers.length);
        if (isTrue) {
            r.matched = true;
            r.expression = exps[0];
        }
        return r;
    }


    enum KIND {
        DEFAULT,
        MULTIPLY2
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
