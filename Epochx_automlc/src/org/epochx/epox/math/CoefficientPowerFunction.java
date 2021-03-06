/*
 * Copyright 2007-2011 Tom Castle & Lawrence Beadle
 * Licensed under GNU Lesser General Public License
 * 
 * This file is part of EpochX: genetic programming software for research
 * 
 * EpochX is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * EpochX is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with EpochX. If not, see <http://www.gnu.org/licenses/>.
 * 
 * The latest version is available from: http://www.epochx.org
 */
package org.epochx.epox.math;

import org.epochx.epox.Node;
import org.epochx.tools.util.*;

/**
 * The CoefficientPowerFunction is equivalent to a <code>PowerFunction</code>
 * combined with a <code>MultiplyFunction</code>. It allows a succinct way of
 * representing a variable with an exponent and a coefficient.
 * 
 * An example:
 * 3x^2, which is equivalent to 3*(x^2)
 * CVP 3 x 2, which is equivalent to MUL(POW x 2)
 */
public class CoefficientPowerFunction extends Node {

	/**
	 * Constructs a CoefficientPowerFunction with three <code>null</code>
	 * children.
	 */
	public CoefficientPowerFunction() {
		this(null, null, null);
	}

	/**
	 * Constructs a CoefficientPowerFunction with three numerical child nodes.
	 * 
	 * @param coefficient will be multiplied by the result of the term raised to
	 *        the exponent.
	 * @param term will be raised to the power of the exponent and multiplied by
	 *        the coefficient.
	 * @param exponent the power the term will be raised to.
	 */
	public CoefficientPowerFunction(final Node coefficient, final Node term, final Node exponent) {
		super(coefficient, term, exponent);
	}

	/**
	 * Evaluates this function. The child nodes are evaluated, the
	 * result of which must be a numeric type (one of Double, Float, Long,
	 * Integer). The result becomes the second child's value raised to the
	 * power of the third, then multiplied by the first. The result is returned
	 * as a double.
	 */
	@Override
	public Double evaluate() {
		final Object c1 = getChild(0).evaluate();
		final Object c2 = getChild(1).evaluate();
		final Object c3 = getChild(2).evaluate();
		
		final Class<?> returnType = TypeUtils.getNumericType(c1.getClass(), c2.getClass(), c3.getClass());
		
		if (returnType != null) {
			final double d1 = NumericUtils.asDouble(getChild(0).evaluate());
			final double d2 = NumericUtils.asDouble(getChild(1).evaluate());
			final double d3 = NumericUtils.asDouble(getChild(2).evaluate());

			return d1 * (Math.pow(d2, d3));
		}

		return null;
	}

	/**
	 * Returns the identifier of this function which is CVP.
	 */
	@Override
	public String getIdentifier() {
		return "CVP";
	}

	/**
	 * Returns this function node's return type for the given child input types.
	 * If there are three input types that are all numeric then the return type
	 * will be Double. In all other cases this method will return
	 * <code>null</code> to indicate that the inputs are invalid.
	 * 
	 * @return the Double class or null if the input type is invalid.
	 */
	@Override
	public Class<?> getReturnType(final Class<?> ... inputTypes) {
		if (inputTypes.length == 3 && TypeUtils.isAllNumericType(inputTypes)) {
			return Double.class;
		} else {
			return null;
		}
	}
}
