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

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Errors;
import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.validator.actorsform.ActorArticleValidator;

import static org.mockito.Matchers.anyString;

/**
 * @author simongoransson
 * 
 */
public class ActorArticleValidatorTest {
    private static final Logger LOG = LoggerFactory.getLogger(ActorArticleValidatorTest.class);

    /** The actor article. */
    ActorArticle actorArticle;

    /**
     * Before.
     */
    @Before
    public void before() {

        actorArticle = new ActorArticle();

    }

    /**
     * This test should work well.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test1() throws Exception {

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
        actorArticle.setIntro("Cake pie sugar plum liquorice gingerbread marshmallow."
                + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit ");

        ActorArticleValidator actorArticleValidator = new ActorArticleValidator();

        Errors errors = Mockito.mock(Errors.class);
        actorArticleValidator.validate(actorArticle, errors);
        Mockito.verify(errors, Mockito.never()).rejectValue(anyString(), anyString(), anyString());

    }

    /**
     * Test1.
     * 
     * @throws Exception
     *             the exception
     */
    @Test
    public void test2() throws Exception {

        ActorArticle actorArticle2 = new ActorArticle();

        actorArticle2.setAddress(TO_LONG_TEXT);
        actorArticle2.setArticleId("10003");
        actorArticle2.setArticleStatus(1);
        actorArticle2.setCompanyName(TO_LONG_TEXT);
        actorArticle2.setContactWeb("Candy canes bonbon");
        actorArticle2.setDetailedDescription(TO_LONG_TEXT);
        actorArticle2.setExternalHomepage(TO_LONG_TEXT);
        actorArticle2.setFax("465");
        actorArticle2.setMail("sweetNO_AT_HEREfruitcake.com");
        actorArticle2.setMobilePhone("+4631 345 64 2558349589430859340859340");
        actorArticle2.setName(TO_LONG_TEXT);
        actorArticle2.setOrganizationName(TO_LONG_TEXT);
        actorArticle2.setPhone("+4631 345 64 25hhhhhhhh");
        actorArticle2.setTagsStr("chups,carrot,cake,soufflé");
        actorArticle2.setTitle(TO_LONG_TEXT);
        actorArticle2.setIntro(TO_LONG_TEXT);

        ActorArticleValidator actorArticleValidator = new ActorArticleValidator();

        Errors errors = Mockito.mock(Errors.class);

        actorArticleValidator.validate(actorArticle2, errors);

        Mockito.verify(errors).rejectValue("companyName", "code.to.long", "to-long-companyName");
        Mockito.verify(errors).rejectValue("companyName", "code.contains.dot", "contains-dot");
        Mockito.verify(errors).rejectValue("organizationName", "code.to.long", "to-long-organizationName");
        Mockito.verify(errors).rejectValue("intro", "code.to.long", "to-long-intro");
        Mockito.verify(errors).rejectValue("detailedDescription", "code.to.long", "to-long-detailedDescription");
        Mockito.verify(errors).rejectValue("title", "code.to.long", "to-long-title");
        Mockito.verify(errors).rejectValue("address", "code.to.long", "to-long-address");
        Mockito.verify(errors).rejectValue("name", "code.to.long", "to-long-name");
        Mockito.verify(errors).rejectValue("mail", "code.invalid.email.format", "invalid-mail");
        Mockito.verify(errors).rejectValue("phone", "code.invalid.phone.format", "invalid-phone-number");
        Mockito.verify(errors).rejectValue("mobilePhone", "code.invalid.mobilePhone.format",
                "invalid-mobile-phone-number");
        Mockito.verify(errors).rejectValue("fax", "code.invalid.fax.format", "invalid-fax-number");
        Mockito.verify(errors).rejectValue("externalHomepage", "code.to.long", "to-long-externalHomepage");

    }

    private static final String TO_LONG_TEXT = "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake"
            + "Cake pie sugar plum liquorice gingerbread marshmallow."
            + " Chocolate cake cake cheesecake halvah fruitcake gingerbread topping. Biscuit "
            + "jelly beans applicake. Jelly beans soufflé sweet candy croissant ice cream "
            + "candy. Sweet biscuit chupa chups jujubes cotton candy cake chocolate cake";

}
