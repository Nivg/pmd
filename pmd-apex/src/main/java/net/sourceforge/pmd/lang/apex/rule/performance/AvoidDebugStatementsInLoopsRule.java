package net.sourceforge.pmd.lang.apex.rule.performance;

import net.sourceforge.pmd.lang.apex.ast.*;
import net.sourceforge.pmd.lang.apex.rule.AbstractApexRule;
import net.sourceforge.pmd.lang.ast.AbstractNode;
import net.sourceforge.pmd.lang.ast.Node;

/**
 * Created by N.G on 19/04/2019.
 */
public class AvoidDebugStatementsInLoopsRule extends AbstractApexRule {

    private static final String SYSTEM_DEBUG = "System.debug";

    public AvoidDebugStatementsInLoopsRule() {

        setProperty(CODECLIMATE_CATEGORIES, new String[]{ "Performance" });
        setProperty(CODECLIMATE_REMEDIATION_MULTIPLIER, 150);
    }

    @Override
    public Object visit(ASTMethodCallExpression node, Object data) {
        String methodName = node.getFullMethodName();

        if (SYSTEM_DEBUG.equalsIgnoreCase(methodName) && node.jjtGetNumChildren() == 2 && insideLoop(node)) {
            addViolation(data, node);
        }
        return data;
    }

    private boolean insideLoop(AbstractNode node) {
        Node n = node.jjtGetParent();

        while (n != null) {
            if (n instanceof ASTDoLoopStatement || n instanceof ASTWhileLoopStatement
                    || n instanceof ASTForLoopStatement || n instanceof ASTForEachStatement) {
                return true;
            }
            n = n.jjtGetParent();
        }

        return false;
    }
}
