package se.gothiaforum.test;

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

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import se.gothiaforum.actorsarticle.util.ActorsArticleUtil;
import se.gothiaforum.util.FriendlyURLNormalizer;
import se.gothiaforum.util.NormalizerWrapper;

@RunWith(MockitoJUnitRunner.class)
public class TestActorsArticleUtil {

    private final NormalizerWrapper normalizerWrapper = null;

    @Before
    public void before() {

        NormalizerWrapperMock normalizerWrapperMock = new NormalizerWrapperMock();
        FriendlyURLNormalizer.setNormalizerWrapper(normalizerWrapperMock);

    }

    @Test
    public void test1() throws Exception {

        long articelId = 1;
        String title = "test";

        ActorsArticleUtil AAU = new ActorsArticleUtil();

        String result = AAU.generateUrlTitle(articelId, title);

        assertEquals(title, result);
    }

    @Test
    public void test2() throws Exception {

        long articelId = 1;
        String title = "222";

        ActorsArticleUtil AAU = new ActorsArticleUtil();

        String result = AAU.generateUrlTitle(articelId, title);

        assertEquals(String.valueOf(articelId), result);
    }

    @Test
    public void test3() throws Exception {

        long articelId = 1;
        String title = null;

        ActorsArticleUtil AAU = new ActorsArticleUtil();

        String result = AAU.generateUrlTitle(articelId, title);

        assertEquals(String.valueOf(articelId), result);
    }

    @Test
    public void test4() throws Exception {

        long articelId = 1;
        String title = "";

        ActorsArticleUtil AAU = new ActorsArticleUtil();

        String result = AAU.generateUrlTitle(articelId, title);

        assertEquals(String.valueOf(articelId), result);
    }

}
