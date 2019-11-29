package com.cjie.agent;

import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class JavassitClassFileTransformer implements ClassFileTransformer {

    private ClassPool classPool = ClassPool.getDefault();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("Transforming start" + className);
        CtClass ctClass = null;
        byte[] returnByte = null;
        //String classNameJava = JvmUtils.jvmnametoJavaname(className);
        String classNameJava = className;
//        if (!matchClazz(classNameJava)) {
//            return  null;
//        }
        try {
            ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));
            System.out.println("ctClass.getName() " + ctClass.getName());
            if (!ctClass.isInterface()) {
                for (CtBehavior method : ctClass.getDeclaredBehaviors()) {
                    //if (matchMethod(method.getLongName())) {

                    //}
                }
                returnByte = ctClass.toBytecode();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ctClass.detach();
        }
        return returnByte;
    }
}
