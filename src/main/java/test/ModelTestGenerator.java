package test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;

public class ModelTestGenerator {

    public static void main(String[] args) throws IOException {

        String packageName = "com.yirendai.dataengine.tbm.vo";

        File rootFile = new File("D:\\workspace\\yirendai-dataengine\\dataengine-tbm-module\\src\\main\\java\\com\\yirendai\\dataengine\\tbm\\vo");
        for (File file : rootFile.listFiles()) {


            String className = file.getName().substring(0, file.getName().lastIndexOf("."));
            String classAllName = packageName + "." + className;
            if (!className.endsWith("Example")) {

                try {
                    Class clazz = Class.forName(classAllName);
                    Method[] methods = clazz.getDeclaredMethods();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(className+"Test.java")));

                    bufferedWriter.write("package " +  packageName + ";");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                    bufferedWriter.write("import " +  classAllName + ";");
                    bufferedWriter.newLine();
                    bufferedWriter.write("import static org.junit.Assert.assertEquals;");
                    bufferedWriter.newLine();
                    bufferedWriter.write("import static org.junit.Assert.assertNotNull;");
                    bufferedWriter.newLine();
                    bufferedWriter.write("import org.junit.Test;");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                    bufferedWriter.write("public class " +  className+"Test" + " {");
                    bufferedWriter.newLine();

                    for (Method method : methods) {
                        if (method.getName().startsWith("get")) {
                            generateGetMethodTest(method,bufferedWriter, className,classAllName);
                        } else if (method.getName().startsWith("toString")) {
                            generateToStringMethodTest(method, bufferedWriter, className, classAllName);
                        }
                        bufferedWriter.newLine();
                    }
                    bufferedWriter.write("}");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println(className);
            }
        }

    }

        private static void generateGetMethodTest(Method method, BufferedWriter bufferedWriter, String className, String classAllName) throws IOException {
        String methodSuffix = method.getName().substring(3);
        bufferedWriter.write("    @Test");
        bufferedWriter.newLine();
        bufferedWriter.write("    public void testGet" + methodSuffix + "() {");
        bufferedWriter.newLine();
        String instanceName = className.substring(0,1).toLowerCase() +  className.substring(1);
        bufferedWriter.write("        " + className + " " + instanceName + " = new " + classAllName  + "();");
        bufferedWriter.newLine();
        String value = getObject(method);
        bufferedWriter.write("        " + instanceName +".set" + methodSuffix + "(" + value +");");
        bufferedWriter.newLine();
        if (method.getReturnType().isAssignableFrom(Long.class)) {
            bufferedWriter.write("        assertEquals(" + value + "," + instanceName + ".get" + methodSuffix + "(" + ").longValue());");
        } else if (method.getReturnType().isAssignableFrom(Integer.class)) {
            bufferedWriter.write("        assertEquals(" + value + "," + instanceName + ".get" + methodSuffix + "(" + ").intValue());");
        } else {
            bufferedWriter.write("        assertEquals(" + value + "," + instanceName + ".get" + methodSuffix + "(" + "));");
        }
        bufferedWriter.newLine();
        bufferedWriter.write("    }");
        bufferedWriter.newLine();
    }
    private static void generateToStringMethodTest(Method method, BufferedWriter bufferedWriter, String className, String classAllName) throws IOException {
        bufferedWriter.write("    @Test");
        bufferedWriter.newLine();
        bufferedWriter.write("    public void testToString() {");
        bufferedWriter.newLine();
        String instanceName = className.substring(0,1).toLowerCase() +  className.substring(1);
        bufferedWriter.write("        " + className + " " + instanceName + " = new " + classAllName  + "();");
        bufferedWriter.newLine();
        bufferedWriter.write("        assertNotNull("+ instanceName + ".toString(" + "));");
        bufferedWriter.newLine();
        bufferedWriter.write("    }");
        bufferedWriter.newLine();
    }

    private static String getObject(Method method) {
        Class  returnType = method.getReturnType();
        if (returnType.isAssignableFrom(Long.TYPE) || returnType.isAssignableFrom(Long.class) ) {
            return "1L";
        }
        if (returnType.isAssignableFrom(Integer.TYPE)|| returnType.isAssignableFrom(Integer.class) ) {
            return "1";
        }
        if (returnType.isAssignableFrom(String.class)) {
            return "\"abc\"";
        }
        return null;
    }
}
