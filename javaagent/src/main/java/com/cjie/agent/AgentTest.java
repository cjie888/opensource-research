package com.cjie.agent;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class AgentTest {


    public static void agentmain(String args, Instrumentation instr) throws UnmodifiableClassException {
        System.out.println("Args is " + args);
        System.out.println("==Begin to retransform classes==");
        try {
            //JavaassitClassFileTransformer 自定义实现ClassFileTransformer的类
            instr.addTransformer(new JavassitClassFileTransformer(), true);
            Class[] classes = instr.getAllLoadedClasses();
            for (Class clazz : classes) {
                //if (matchClazz(clazz)) {
                System.out.println("clazz matched" + clazz.getName());
                instr.retransformClasses(clazz);
                //}
            }
        } catch (UnmodifiableClassException e) {
            e.printStackTrace();
        }
        System.out.println("==End to retransformClasses==");
    }

    public static void main(String[] args) {

    }
}
