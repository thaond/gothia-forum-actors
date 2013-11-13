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

/**
 * 
 */
package com.gothiaforum.validator.actorsform;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.validator.actorsform.ActorArticleValidator;
import se.gothiaforum.validator.actorsform.ImageValidator;

/**
 * @author simongoransson
 * 
 */
public class ImageValidatorTest {
    private static final Log LOG = LogFactory.getLog(ImageValidatorTest.class);

    /**
     * Test watch should work for jpg file
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test2() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (1 * 1024 * 1024));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("my-image.jpg");
        Mockito.when(multipartFile.getContentType()).thenReturn("image/jpeg");

        imageValidator.validate(multipartFile, errors);

        assertEquals(0, errors.size());

    }

    /**
     * Test watch should work for png file.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test3() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (1 * 1024 * 1024));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("my-image.png");
        Mockito.when(multipartFile.getContentType()).thenReturn("image/png");

        imageValidator.validate(multipartFile, errors);

        assertEquals(0, errors.size());

    }

    /**
     * Test watch should work for gif file.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test4() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (1 * 1024 * 1024));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("my-image.gif");
        Mockito.when(multipartFile.getContentType()).thenReturn("image/gif");

        imageValidator.validate(multipartFile, errors);

        assertEquals(0, errors.size());

    }

    /**
     * Test watch should work for x-png file
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test5() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (1 * 1024 * 1024));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("my-image.png");
        Mockito.when(multipartFile.getContentType()).thenReturn("image/x-png");

        imageValidator.validate(multipartFile, errors);

        assertEquals(0, errors.size());

    }

    /**
     * Test with wrong filename and wrong content type.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test6() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (1 * 1024 * 1024));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("my-image.pizza");
        Mockito.when(multipartFile.getContentType()).thenReturn("image/pizza");

        imageValidator.validate(multipartFile, errors);

        assertEquals(2, errors.size());

    }

    /**
     * Test whit to large file
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test7() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (8 * 1024 * 1024));

        imageValidator.validate(multipartFile, errors);

        assertEquals(1, errors.size());

    }

    /**
     * Test whit to big file
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test8() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (-10));

        imageValidator.validate(multipartFile, errors);

        assertEquals(1, errors.size());

    }
}
