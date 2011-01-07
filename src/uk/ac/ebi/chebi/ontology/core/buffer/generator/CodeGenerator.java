package uk.ac.ebi.chebi.ontology.core.buffer.generator;

import java.lang.reflect.Method;

public class CodeGenerator {
    public static void main(String[] args) {
        String[] clasesToGen=new String[]{"org.openscience.cdk.Atom"};
        try {
            Class c = Class.forName(clasesToGen[0]);
            Method[] methods=c.getMethods();
            for(Method method:methods){
                if(method.getName().startsWith("set")){
                    System.out.println(method.getName()+"\t"+method.getParameterTypes()[0]);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
