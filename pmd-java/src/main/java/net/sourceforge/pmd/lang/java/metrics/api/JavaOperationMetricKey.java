/**
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.java.metrics.api;

import net.sourceforge.pmd.lang.java.ast.ASTMethodOrConstructorDeclaration;
import net.sourceforge.pmd.lang.java.ast.MethodLikeNode;
import net.sourceforge.pmd.lang.java.metrics.internal.AtfdMetric;
import net.sourceforge.pmd.lang.java.metrics.internal.AtfdMetric.AtfdOperationMetric;
import net.sourceforge.pmd.lang.java.metrics.internal.CycloMetric;
import net.sourceforge.pmd.lang.java.metrics.internal.LocMetric;
import net.sourceforge.pmd.lang.java.metrics.internal.LocMetric.LocOperationMetric;
import net.sourceforge.pmd.lang.java.metrics.internal.NcssMetric;
import net.sourceforge.pmd.lang.java.metrics.internal.NcssMetric.NcssOperationMetric;
import net.sourceforge.pmd.lang.java.metrics.internal.NpathMetric;
import net.sourceforge.pmd.lang.metrics.MetricKey;


/**
 * Keys identifying standard operation metrics.
 */
public enum JavaOperationMetricKey implements MetricKey<MethodLikeNode> {

    /**
     * Access to Foreign Data.
     *
     * @see AtfdMetric
     */
    ATFD(new AtfdOperationMetric()),

    /**
     * Cyclomatic complexity.
     *
     * @see CycloMetric
     */
    CYCLO(new CycloMetric()),

    /**
     * Non Commenting Source Statements.
     *
     * @see NcssMetric
     */
    NCSS(new NcssOperationMetric()),

    /**
     * Lines of Code.
     *
     * @see LocMetric
     */
    LOC(new LocOperationMetric()),


    /**
     * N-path complexity.
     *
     * @see NpathMetric
     */
    NPATH(new NpathMetric());


    private final JavaOperationMetric calculator;


    JavaOperationMetricKey(JavaOperationMetric m) {
        calculator = m;
    }


    @Override
    public JavaOperationMetric getCalculator() {
        return calculator;
    }


    @Override
    public boolean supports(MethodLikeNode node) {
        return calculator.supports(node);
    }


    /**
     * @see #supports(MethodLikeNode)
     * @deprecated Provided here for backwards binary compatibility with {@link #supports(MethodLikeNode)}.
     *             Please explicitly link your code to that method and recompile your code. Will be remove with 7.0.0
     */
    @Deprecated
    public boolean supports(ASTMethodOrConstructorDeclaration node) {
        return this.supports((MethodLikeNode) node);
    }
}
