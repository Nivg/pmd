package net.sourceforge.pmd.lang.apex.rule.performance;

import net.sourceforge.pmd.lang.apex.ast.ASTMethodCallExpression;
import net.sourceforge.pmd.lang.apex.rule.AbstractApexRule;
import net.sourceforge.pmd.lang.apex.ast.ASTDoLoopStatement;
import net.sourceforge.pmd.lang.apex.ast.ASTWhileLoopStatement;
import net.sourceforge.pmd.lang.apex.ast.ASTForLoopStatement;
import net.sourceforge.pmd.lang.apex.ast.ASTForEachStatement;
import net.sourceforge.pmd.lang.ast.AbstractNode;
import net.sourceforge.pmd.lang.ast.Node;

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
