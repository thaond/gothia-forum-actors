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
public class ValidatorTest {
    private static final Log LOG = LogFactory.getLog(ValidatorTest.class);

    ActorArticle actorArticle;

    @Before
    public void before() {

        actorArticle = new ActorArticle();

        actorArticle.setAddress("Cheesecake sweet street 24 Marshmallow City");
        actorArticle.setArticleId("10003");
        actorArticle.setArticleStatus(1);
        actorArticle.setCompanyName("Tiramisu caramels");
        actorArticle.setContactWeb("Candy canes bonbon");
        actorArticle.setDetailedDescription("Cake pie sugar plum liquorice gingerbread marshmallow."
                + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
                + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
                + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake");
        actorArticle.setExternalHomepage("www.fruitcake.com");
        actorArticle.setFax("+4631 345 64 24");
        actorArticle.setMail("sweet@fruitcake.com");
        actorArticle.setMobilePhone("+4631 345 64 25");
        actorArticle.setName("Mr Cake Jelly");
        actorArticle.setOrganizationName("Applicake pie");
        actorArticle.setPhone("+4631 345 64 25");
        actorArticle.setTagsStr("chups,carrot,cake,soufflé");
        actorArticle.setTitle("Powder chocolate cake pie");

    }

    @Test
    public void test1() throws Exception {

        ActorArticleValidator actorArticleValidator = new ActorArticleValidator();

        Errors errors = Mockito.mock(Errors.class);
        actorArticleValidator.validate(actorArticle, errors);
        Mockito.verify(errors, Mockito.never()).rejectValue(anyString(), anyString(), anyString());

    }

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

    @Test
    public void test3() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (1 * 1024 * 1024));
        Mockito.when(multipartFile.getOriginalFilename()).thenReturn("my-image.pizza");
        Mockito.when(multipartFile.getContentType()).thenReturn("image/pizza");

        imageValidator.validate(multipartFile, errors);

        assertEquals(2, errors.size());

    }

    @Test
    public void test4() throws Exception {

        ImageValidator imageValidator = new ImageValidator();

        List<String> errors = new ArrayList<String>();
        MultipartFile multipartFile = Mockito.mock(MultipartFile.class);

        Mockito.when(multipartFile.getSize()).thenReturn((long) (8 * 1024 * 1024));

        imageValidator.validate(multipartFile, errors);

        assertEquals(1, errors.size());

    }
}
