package genericReflection;

import java.lang.reflect.*;
import java.util.*;

public class GenericReflectionTest{
    public static void main(String[] args) {
        // read class name from command line args or user input
        String name;
        if (args.length > 0) name = args[0];
        else{
            try(var in = new Scanner(System.in))
            {
                System.out.println("Enter class name (e.g., java.util.Collections):");
                name = in.next();
            }
        }

        try{
            // print generic info for class and public methods
            Class<?> cl = Class.forName(name);
            printClass(cl);
            for(Method m : cl.getDeclaredMethods())
                printMethod(m);
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static void printMethod(Method m) {
        String name = m.getName();
        System.out.print(Modifier.toString(m.getModifiers()));
        System.out.print(" ");
        // getTypeParameters：如果这个方法被声明为一个泛型方法，则获得泛型类型变量，否则返回长度为0的数组。
        printType(m.getTypeParameters(), "<", ", ", ">", true);

        // getGenericReturnType：获得这个方法声明的泛型返回类型
        printType(m.getGenericReturnType(), false);
        System.out.print(" ");
        System.out.print(name);
        System.out.print("(");
        // getGenericParameterTypes：获得这个方法声明的泛型参数类型。如果这个方法没有参数，返回长度为0的数组。
        printType(m.getGenericParameterTypes(), "", ", ", "", false);
        System.out.println(")");
    }

    private static void printClass(Class<?> cl) {
        System.out.print(cl);
        // getTypeParameters: 如果这个类型被声明为泛型类型，则获得泛型类型变量，否则获得一个长度为0的数组。
        printType(cl.getTypeParameters(), "<", ", ", ">", true);
        // getGenericSuperclass: 获得这个类型所声明超类的泛型类型；如果这个类型是Object或者不是类类型（class type），则返回null.
        Type sc = cl.getGenericSuperclass();
        if(sc != null)
        {
            System.out.print(" extends ");
            printType(sc, false);
        }
        // getGenericInterfaces：获得这个类型所声明接口的泛型类型（按照声明的次序），否则，如果这个类型没有实现接口，则返回长度为0的数组。
        printType(cl.getGenericInterfaces(), " implements ", ", ", "", false);
        System.out.println();
    }

    private static void printType(Type[] types, String pre, String seq, String suf, boolean isDefinition){
        if(pre.equals(" extends ") && Arrays.equals(types, new Type[]{Object.class}))
            return ;
        if(types.length > 0) System.out.print(pre);
        for(int i = 0; i < types.length; i++)
        {
            if(i > 0) System.out.print(seq);
            printType(types[i], isDefinition);
        }
        if(types.length > 0) System.out.print(suf);
    }

    private static void printType(Type type, boolean isDefinition){
        // Class 类，描述具体类型
        if(type instanceof Class)
        {
            var t = (Class<?>) type;
            System.out.print(t.getName());
        }
        // TypeVariable 接口，描述类型变量
        else if(type instanceof TypeVariable)
        {
            var t = (TypeVariable<?>) type;
            System.out.print(t.getName()); // 获得这个类型变量的名字
            if(isDefinition)
            // getBounds：获得这个类型变量的子类限定，否则，如果该变量无限定，则返回长度为0的数组。
                printType(t.getBounds(), " extends ", " & ", "", false);
        }
        // WilecardType 接口，描述通配符
        else if(type instanceof WildcardType)
        {
            var t = (WildcardType) type;
            System.out.print("?");
            // getUpperBounds：获得这个类型变量的子类（extends）限定，否则，如果没有子类限定，则返回长度为0的数组。
            printType(t.getUpperBounds(), " extends ", " & ", "", false);
            // getLowerBounds：获得这个类型变量的超类（super）限定，否则，如果没有子类限定，则返回长度为0的数组。
            printType(t.getLowerBounds(), " super ", " & ", "", false);
        }
        // ParameterizedType 接口，描述泛型类或接口类型
        else if(type instanceof ParameterizedType)
        {
            var t = (ParameterizedType) type;
            // getOwnerType：如果是内部类，则返回其外部类类型；如果是一个顶级类型，则返回null
            Type owner = t.getOwnerType(); 
            if(owner != null)
            {
                printType(owner, false);
                System.out.print(".");
            }
            // getRawType：获得这个参数化类型的原始类型。
            printType(t.getRawType(), false);
            // getActualTypeArguments：获得这个参数化类型声明的类型参数。
            printType(t.getActualTypeArguments(), "<", ", ", ">", false);
        }
        // GenericArrayType 接口，描述泛型数组
        else if(type instanceof GenericArrayType)
        {
            var t = (GenericArrayType) type;
            System.out.print("");
            // getGenericComponentType：获得这个数组类型声明的泛型元素类型。
            printType(t.getGenericComponentType(), isDefinition);
            System.out.print("[]");
        }
    }
}