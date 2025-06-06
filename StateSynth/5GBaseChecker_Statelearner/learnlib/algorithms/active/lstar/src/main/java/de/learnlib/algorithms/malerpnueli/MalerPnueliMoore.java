/* Copyright (C) 2013-2022 TU Dortmund
 * This file is part of LearnLib, http://www.learnlib.de/.
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
package de.learnlib.algorithms.malerpnueli;

import java.util.Collections;
import java.util.List;

import com.github.misberner.buildergen.annotations.GenerateBuilder;
import de.learnlib.algorithms.lstar.ce.ObservationTableCEXHandlers;
import de.learnlib.algorithms.lstar.closing.ClosingStrategies;
import de.learnlib.algorithms.lstar.closing.ClosingStrategy;
import de.learnlib.algorithms.lstar.moore.ExtensibleLStarMoore;
import de.learnlib.api.oracle.MembershipOracle;
import net.automatalib.words.Alphabet;
import net.automatalib.words.Word;

public class MalerPnueliMoore<I, O> extends ExtensibleLStarMoore<I, O> {

    public MalerPnueliMoore(Alphabet<I> alphabet, MembershipOracle<I, Word<O>> oracle) {
        this(alphabet, oracle, Collections.emptyList(), ClosingStrategies.CLOSE_FIRST);
    }

    @GenerateBuilder(defaults = BuilderDefaults.class)
    public MalerPnueliMoore(Alphabet<I> alphabet,
                            MembershipOracle<I, Word<O>> oracle,
                            List<Word<I>> initialSuffixes,
                            ClosingStrategy<? super I, ? super Word<O>> closingStrategy) {
        super(alphabet, oracle, initialSuffixes, ObservationTableCEXHandlers.MALER_PNUELI, closingStrategy);
    }

}
