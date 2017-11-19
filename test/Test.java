import org.junit.Assert;

/**
 * test our functions.
 * Created by edward.gao on 19/11/2017.
 */
public class Test {

    @org.junit.Test
    public void test1(){
        int nums [] = {8,6,5,3};
        int target = 2;
        Main.KIND [] kinds = {Main.KIND.DEFAULT,Main.KIND.DEFAULT,Main.KIND.DEFAULT,Main.KIND.DEFAULT};
        Main.Result r = Main.solve(target, nums, kinds);
        System.out.println("Actually - " + r);
        Assert.assertTrue("should got (8-6)= (5-3) = 2", r.matched);
    }
}
