package antlr.tour;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.FileInputStream;
import java.io.InputStream;

public class Calc {

    public static void main(String[] args) throws Exception {
        ANTLRInputStream input = new ANTLRInputStream("1 * 2 + 6 / 2 + 3 * 2\n"); // create a lexer that feeds off of input CharStream

        LabeledExprLexer lexer = new LabeledExprLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LabeledExprParser parser = new LabeledExprParser(tokens);
        ParseTree tree = parser.prog(); // parse

        EvalVisitor eval = new EvalVisitor();
        int value = eval.visit(tree);
        System.out.println(value);
    }
}
