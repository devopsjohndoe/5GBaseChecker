/* Copyright (C) 2013-2022 TU Dortmund
 * This file is part of AutomataLib, http://www.automatalib.net/.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.automatalib.modelcheckers.m3c.formula;

import java.io.IOException;

import net.automatalib.modelcheckers.m3c.formula.visitor.FormulaNodeVisitor;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Java representation of a "{@literal <>}" (sub-)formula.
 *
 * @param <L>
 *         label type
 * @param <AP>
 *         atomic proposition type
 *
 * @author murtovi
 */
public class DiamondNode<L, AP> extends AbstractModalFormulaNode<L, AP> {

    public DiamondNode(FormulaNode<L, AP> node) {
        this(null, node);
    }

    public DiamondNode(@Nullable L action, FormulaNode<L, AP> node) {
        super(action, node);
    }

    @Override
    public void print(Appendable a) throws IOException {
        printMuCalcNode(a, '<', '>');
    }

    @Override
    public <T> T accept(FormulaNodeVisitor<T, L, AP> visitor) {
        return visitor.visit(this);
    }

}
