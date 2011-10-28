/**
 * Copyright 2010 Västra Götalandsregionen
 *
 *   This library is free software; you can redistribute it and/or modify
 *   it under the terms of version 2.1 of the GNU Lesser General Public
 *   License as published by the Free Software Foundation.
 *
 *   This library is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Lesser General Public License for more details.
 *
 *   You should have received a copy of the GNU Lesser General Public
 *   License along with this library; if not, write to the
 *   Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 *   Boston, MA 02111-1307  USA
 *
 */

package com.gothiaforum.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

public class testHashMultiSet {

	@Test
	public void test1() throws Exception {

		String a1 = "a";
		String a2 = "a";

		assertEquals(a1, a2);

	}

	@Test
	public void test2() throws Exception {

		Multiset<String> tagMultiSet = HashMultiset.create();

		String a = "a";
		String b = "b";

		tagMultiSet.add(a);
		tagMultiSet.add(b);
		int curSize = tagMultiSet.size();

		tagMultiSet.add(a);

		for (String s : tagMultiSet) {
			System.out.println(s);

		}

		for (Entry<String> e : tagMultiSet.entrySet()) {
			System.out.println(e.getCount());
			System.out.println(e.getElement());

		}

		assertEquals(tagMultiSet.entrySet().size(), curSize);

	}

	@Test
	public void test3() throws Exception {

		Multiset<String> tagMultiSet = HashMultiset.create();

		String a1 = "a";
		String a2 = "a";

		tagMultiSet.add(a1);

		assertEquals(tagMultiSet.contains(a2), true);

	}

}
