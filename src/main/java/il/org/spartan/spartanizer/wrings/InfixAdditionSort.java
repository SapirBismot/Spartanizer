package il.org.spartan.spartanizer.wrings;

import static org.eclipse.jdt.core.dom.InfixExpression.Operator.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.InfixExpression.*;

import il.org.spartan.spartanizer.assemble.*;
import il.org.spartan.spartanizer.ast.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.java.*;
import il.org.spartan.spartanizer.wringing.*;

/** sorts the arguments of a {@link Operator#PLUS} expression. Extra care is
 * taken to leave intact the use of {@link Operator#PLUS} for the concatenation
 * of {@link String}s.
 * @author Yossi Gil
 * @since 2015-07-17 */
public final class InfixAdditionSort extends InfixSorting implements Kind.Sorting {
  @Override public boolean canSuggest(final InfixExpression ¢) {
    return stringType.isNot(¢) && super.canSuggest(¢);
  }

  @Override public boolean demandsToSuggestButPerhapsCant(final InfixExpression ¢) {
    return ¢.getOperator() == PLUS;
  }

  @Override public Expression replacement(final InfixExpression x) {
    final List<Expression> operands = extract.allOperands(x);
    return !stringType.isNot(x) || !sort(operands) ? null : subject.operands(operands).to(x.getOperator());
  }

  @Override protected boolean sort(final List<Expression> ¢) {
    return ExpressionComparator.ADDITION.sort(¢);
  }
}