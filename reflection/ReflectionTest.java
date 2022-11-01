package reflection;

import java.util.*;
import java.lang.reflect.*;
/**
 * 这个反射类测试类将通过标准输入获取一个类名, 然后将类的所有信息输出到标准输出.
 * @version 1.1 2022-10-30
 * @author lrvu
 */
public class ReflectionTest{
    public static void main(String[] args)
        throws ClassNotFoundException
    {
        // 声明类名变量
        String name;
        // 通过 args 或者 System.in 获取类名
        if(args.length > 0) name = args[0];
        else{
            Scanner in = new Scanner(System.in);
            System.out.println("请输入一个类名(例如 java.util.Date):");
            name = in.next();
        }

        // 获取对应的类
        Class cl = Class.forName(name);
        // 获取对应的超类
        Class supercl = cl.getSuperclass();
        // 获取对应的权限修饰
        String modifiers = Modifier.toString(cl.getModifiers());
        // 如果有修饰就输出类名修饰符
        if(modifiers.length() > 0) 
            System.out.print(modifiers + " ");
        // 输出类名
        System.out.print("class "+ cl.getName());
        // 如果有父类就输出父类
        if(supercl != null && supercl != Object.class) 
            System.out.print("extends " + supercl.getName());
        System.out.print("\n{\n");
        printConstructors(cl);
        System.out.println();
        printMethods(cl);
        System.out.println();
        printFields(cl);
        System.out.println("}");
        
    }

    /**
     * 打印出 class 引用对象的所有字段
     * @param cl 一个 class 类
     */
    private static void printFields(Class cl) {
         // 获取字段数组
         Field[] fields = cl.getDeclaredFields();
         // 遍历数组
         for(Field f : fields){
            System.out.print("    ");
             // 获取字段修饰符
             String modifier = Modifier.toString(f.getModifiers());
             if(modifier.length() > 0) System.out.print(modifier + " ");
             // 获取字段类型和字段名
             String type = f.getType().getName();
             System.out.print(type + " " + f.getName());
             System.out.println(";");
         }
    }

    /**
     * 打印出 class 引用对象的所有方法
     * @param cl 一个 class 类
     */
    private static void printMethods(Class cl) {
        // 获取方法数组
        Method[] methods = cl.getDeclaredMethods();
        // 遍历数组
        for(Method m : methods){
            System.out.print("    ");
            // 获取方法修饰符
            String modifier = Modifier.toString(m.getModifiers());
            if(modifier.length() > 0) System.out.print(modifier + " ");
            // 获取方法返回类型和方法名
            String ret = m.getReturnType().getName();
            System.out.print(ret + " " + m.getName() + "(");
            // 获取方法参数
            Class[] params = m.getParameterTypes();
            for (int i = 0; i < params.length; i++) {
                System.out.print(params[i].getName());
                if(i!=params.length - 1) System.out.print(", ");
            }
            System.out.println(");");
        }
    }

    /**
     * 打印出 class 引用对象的所有构造器
     * @param cl 一个 class 类
     */
    private static void printConstructors(Class cl) {
        // 获取构造器数组
        Constructor[] constructors = cl.getDeclaredConstructors();
        // 遍历数组
        for(Constructor cons : constructors){
            System.out.print("    ");
            // 获取构造器修饰符
            String modifier = Modifier.toString(cons.getModifiers());
            if(modifier.length() > 0) System.out.print(modifier + " ");
            // 获取构造器名称
            System.out.print(cons.getName() + "(");
            // 获取构造器参数
            Class[] params =  cons.getParameterTypes();
            for (int i = 0; i < params.length; i++) {
                System.out.print(params[i].getName());
                if(i!=params.length - 1) System.out.print(", ");
            }
            System.out.println(");");
            
        }
    }
}