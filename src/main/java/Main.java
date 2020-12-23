import init.ServerConfiguration;
import web.WebServer;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, IOException {
        // Exercise 0 get the class object
        Class<String> stringClass = String.class;
        Map<String, Integer> mapObject = new HashMap();
        Class<?> hashMapClass = mapObject.getClass();
        Class<?> squareClass = Class.forName("Main$Square");
        // Exercise 1: print class info
        printClassInfo(stringClass, hashMapClass, squareClass);

        // Exercise 1: print class info
        Drawable circleObject = new Drawable(){
            @Override
            public int getNumberOfCorners(){return 0;}
        };
        printClassInfo(Collection.class, boolean.class, int[][].class, Color.class, circleObject.getClass());

        // Exercise 2: create popup type info from class
        // For example:
        // This is a JDK type
        // Name: List
        // Type: Interface
        // Inherits from: Collection

        // This is a custom type
        // Name: product
        // Type: Class
        // Inherits from: Object
        for(Class<?> clazz: new Class<?>[]{Collection.class, boolean.class, int[][].class, Color.class, circleObject.getClass()})
            System.out.println(Exercise.createPopupTypeInfoFromClass(clazz));
        System.out.println();

        // Exercise 3 recursion find all implemented Interface
        for(Class<?> clazz: new Class<?>[]{Collection.class, boolean.class, int[][].class, Color.class, circleObject.getClass()})
            Exercise2.findAllImplementedInterfaces(clazz);
        System.out.println();

        // access private
        initConfiguration();
        WebServer webServer = new WebServer();
        webServer.startServer();

    }

    public static void initConfiguration() throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
        Constructor<ServerConfiguration> constructor = ServerConfiguration.class.getDeclaredConstructor(int.class, String.class);
        // access an otherwise unaccessible class
        constructor.setAccessible(true);
        constructor.newInstance(8000, "Good Day!");
    }

    private static void printClassInfo(Class<?> ...classes){
        for(Class<?> clazz:classes){
            System.out.println(String.format("class name: %s, class package: %s", clazz.getSimpleName(), clazz.getPackage()));
            Class<?>[] implementedInterfaces = clazz.getInterfaces();
            for(Class<?> implementedInterface: implementedInterfaces){
                System.out.println(String.format("class %s implements : %s", clazz.getSimpleName(), implementedInterface.getSimpleName()));
            }

            System.out.println("Is array : "+clazz.isArray());
            System.out.println("Is primitive : "+ clazz.isPrimitive());
            System.out.println("Is enum: "+clazz.isEnum());
            System.out.println("Is interface: "+clazz.isInterface());
            System.out.println("Is anonymous: "+clazz.isAnonymousClass());
            System.out.println();
        }

    }

    private static class Square implements Drawable{
        @Override
        public int getNumberOfCorners(){
            return 4;
        }
    }
    private static interface Drawable{
        int getNumberOfCorners();
    }
    private enum Color{
        BLUE,RED,GREEN
    }
}
