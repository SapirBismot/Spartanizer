package il.org.spartan.refactoring.wring;

import static il.org.spartan.idiomatic.*;
import static il.org.spartan.refactoring.utils.extract.*;
import static org.eclipse.jdt.core.dom.InfixExpression.Operator.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.refactoring.preferences.PluginPreferencesResources.*;
import il.org.spartan.refactoring.utils.*;

/** Replace <code>(double)X</code> by <code>1.*X</code>
 * @author Alex Kopzon
 * @author Dan Greenstein
 * @since 2016 */
public final class CastToDouble2Multiply1 extends Wring.ReplaceCurrentNode<CastExpression> {
  @Override String description(final CastExpression e) {
    return "Use 1.*" + expression(e) + " instead of (double)" + expression(e);
  }
  @Override ASTNode replacement(final CastExpression e) {
    return eval(//
        () -> replacement(expression(e))//
    ).when(//
        type(e).isPrimitiveType() && "double".equals("" + type(e)) //
    );
  }
  private static InfixExpression replacement(final Expression $) {
    return subject.pair(literal($), $).to(TIMES);
  }
  private static NumberLiteral literal(final Expression e) {
    final NumberLiteral $ = e.getAST().newNumberLiteral();
    $.setToken("1.");
    return $;
  }
  @Override WringGroup wringGroup() {
    return WringGroup.REORDER_EXPRESSIONS;
  }
}