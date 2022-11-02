package stackTrace;

import java.util.Scanner;

/**
 * 输出一个递归阶乘函数的堆栈轨迹
 */
public class StackTraceTest {
    
    /**
     * 使用递归计算一个整数的阶乘
     * @param n 一个非负整数
     * @return n! = 1 * 2 * 3 * ... * n
     */
    public static int factorial(int n)
    {
        System.out.println("factorial(" + n + ")");
        var walker = StackWalker.getInstance();
        walker.forEach(System.out::println);
        int r;
        if(n <= 1) r = 1;
        else r = n * factorial(n - 1);
        System.out.println("return " + r);
        return r;
    }

    public static void main(String[] args) {
        try(var in = new Scanner(System.in))
        {
            System.out.print("Enter n: ");
            int n = in.nextInt();
            factorial(n);
        }
    }
}
