package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;
/**
 * 代理类实例测试, 这个测试使用代理对象跟踪一个二分查找.
 */
public class ProxyTest{
    public static void main(String[] args) {
        var elements = new Object[1000];

        // 填充代理元素
        for(int i = 0; i < elements.length; i++){
            Integer value = i + 1;
            // 构造 value 的包装器
            var handler = new TraceHandler(value);
            /*
             * newProxyInstance 参数解释:
             * ClassLoader.getSystemClassLoader(): 系统类加载器
             * new Class[]{Comparable.class}: 要代理的接口, 当执行接口定义方法时, 会由 handler 代理执行
             * handler: 调用处理器, 当检测到 Class 数组中的接口方法时, 由 handler 中的 invoke 代理执行
             */
            Object proxy = Proxy.newProxyInstance(
                ClassLoader.getSystemClassLoader(),
                new Class[]{Comparable.class},
                handler
            );
            // 把代理器包装的值封装到数组中
            elements[i] = proxy;
        }

        // 生成一个随机数
        Integer key = new Random().nextInt(elements.length) + 1;

        // 按照随机数查找数组中对应的元素
        int result = Arrays.binarySearch(elements, key);

        // 如果查到就输出匹配结果
        if(result >= 0) System.out.println(elements[result]);
    }
}

class TraceHandler implements InvocationHandler{

    private Object target;

    /**
     * 调用处理器的构造器
     * @param t 调用代理对象的值
     */
    public TraceHandler(Object t){
        target = t;
    }

    /**
     * 代理器处理方法, 这里代理的是 Comparable 接口中的 compareTo 方法,
     * 在执行 compareTo 方法前, 打印调用对象/调用方法和方法参数,
     * 最后为目标对象执行原本的方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 打印执行过程
        System.out.print(target);
        System.out.print("." + method.getName() + "(");
        if(args != null){
            for (int i = 0; i < args.length; i++) {
                System.out.print(args[i]);
                if(i < args.length - 1) System.out.print(", ");
            }
        }
        System.out.println(")");
        // 执行方法
        return method.invoke(target, args);
    }

}