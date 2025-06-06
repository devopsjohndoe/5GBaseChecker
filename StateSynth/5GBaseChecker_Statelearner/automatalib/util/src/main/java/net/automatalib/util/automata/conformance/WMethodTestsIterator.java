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
package net.automatalib.util.automata.conformance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.automatalib.automata.UniversalDeterministicAutomaton;
import net.automatalib.commons.util.collections.AbstractThreeLevelIterator;
import net.automatalib.commons.util.collections.CollectionsUtil;
import net.automatalib.commons.util.collections.ReusableIterator;
import net.automatalib.util.automata.cover.Covers;
import net.automatalib.util.automata.equivalence.CharacterizingSets;
import net.automatalib.words.Word;
import net.automatalib.words.WordBuilder;

/**
 * Iterator that returns test words generated by the W method.
 * <p>
 * See "Testing software design modeled by finite-state machines" by Tsun S. Chow.
 *
 * @param <I>
 *         input symbol type
 *
 * @author Malte Isberner
 */
public class WMethodTestsIterator<I> extends AbstractThreeLevelIterator<List<I>, Word<I>, Word<I>, Word<I>> {

    private final Iterable<Word<I>> prefixes;
    private final Iterable<Word<I>> suffixes;

    private final WordBuilder<I> wordBuilder = new WordBuilder<>();

    /**
     * Convenience-constructor for {@link #WMethodTestsIterator(UniversalDeterministicAutomaton, Collection, int)} that
     * selects {@code 0} as {@code maxDepth}.
     *
     * @param automaton
     *         the automaton for which the testing sequences should be generated
     * @param inputs
     *         the input symbols that should be considered for test sequence generation
     */
    public WMethodTestsIterator(UniversalDeterministicAutomaton<?, I, ?, ?, ?> automaton,
                                Collection<? extends I> inputs) {
        this(automaton, inputs, 0);
    }

    /**
     * Constructor.
     *
     * @param automaton
     *         the automaton for which the testing sequences should be generated
     * @param inputs
     *         the input symbols that should be considered for test sequence generation
     * @param maxDepth
     *         the maximum number of symbols that are appended to the transition-cover part of the test sequences
     */
    public WMethodTestsIterator(UniversalDeterministicAutomaton<?, I, ?, ?, ?> automaton,
                                Collection<? extends I> inputs,
                                int maxDepth) {
        super(CollectionsUtil.<I>allTuples(inputs, 0, maxDepth).iterator());
        this.prefixes = new ReusableIterator<>(Covers.transitionCoverIterator(automaton, inputs),
                                               new ArrayList<>(automaton.size() * inputs.size()));

        final Iterator<Word<I>> characterizingSet = CharacterizingSets.characterizingSetIterator(automaton, inputs);

        // Special case: List of characterizing suffixes may be empty,
        // but in this case we still need to iterate over the prefixes!
        if (!characterizingSet.hasNext()) {
            this.suffixes = Collections.singletonList(Word.epsilon());
        } else {
            this.suffixes = new ReusableIterator<>(characterizingSet);
        }
    }

    @Override
    protected Iterator<Word<I>> l2Iterator(List<I> l1Object) {
        return prefixes.iterator();
    }

    @Override
    protected Iterator<Word<I>> l3Iterator(List<I> l1Object, Word<I> l2Object) {
        return suffixes.iterator();
    }

    @Override
    protected Word<I> combine(List<I> middle, Word<I> prefix, Word<I> suffix) {
        wordBuilder.ensureAdditionalCapacity(prefix.size() + middle.size() + suffix.size());
        Word<I> word = wordBuilder.append(prefix).append(middle).append(suffix).toWord();
        wordBuilder.clear();
        return word;
    }
}
