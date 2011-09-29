package se.gothiaforum.validator.actorsform;

import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.liferay.portal.service.OrganizationLocalService;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.service.ActorsService;

/**
 * @author Hans Gyllensten, vgrid=hangy2 and Simon Göransson vgrid=simgo3
 * 
 *         Validating the ActorArticle object
 * 
 */
public class ActorArticleValidator implements Validator {

    private static final Log log = LogFactory.getLog(ActorArticleValidator.class);
    private final static Pattern EMAIL_PATTERN = Pattern.compile(".+@.+\\.[a-z]+");
    private final static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^?[+]?[0-9]{8,14}");

    private Locale locale = new Locale("sv_SE");
    
    @Override
    public boolean supports(Class clazz) {
        return ActorArticle.class.equals(clazz);

    }
    

    /**
     * @inheritDoc
     */
    @Override
    public void validate(Object target, Errors errors) {

        ActorArticle actorArticle = (ActorArticle) target;
        System.out.println("HAGY IN  VALIDATE");
        ValidatorUtils.removeLeadingAndTrailingWhitespacesOnAllStringFields(actorArticle); // Remove leading and trailing spaces on all String fields

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "companyName", "code.missing", "invalid-companyName");  
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "detailedDescription", "code.missing",
               "invalid-detailedDescription");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "code.missing", "invalid-name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "code.missing", "invalid-address");
        
        if (actorArticle.getCompanyName().length() > ValidationConstants.LENGHT_VALIDATION_DEFAULT) {
            errors.rejectValue("companyName", "code.to.long", "to-long-companyName");
        }
        
        if (actorArticle.getCompanyName().contains(".")) {
            errors.rejectValue("companyName", "code.contains.dot", "contains-dot");
        }

        if (actorArticle.getOrganizationName().length() > ValidationConstants.LENGHT_VALIDATION_DEFAULT) {
            errors.rejectValue("organizationName", "code.to.long", "to-long-organizationName");
        }

        if (actorArticle.getIngress().length() > ValidationConstants.LENGHT_VALIDATION_INTRO) {
            errors.rejectValue("ingress", "code.to.long", "to-long-ingress");
        }

        if (actorArticle.getDetailedDescription().length() > ValidationConstants.LENGHT_VALIDATION_DESCRIPTION) {
            errors.rejectValue("detailedDescription", "code.to.long", "to-long-detailedDescription");
        }

        if (actorArticle.getExternalHomepage().length() > ValidationConstants.LENGHT_VALIDATION_URL) {
            errors.rejectValue("externalHomepage", "code.to.long", "to-long-externalHomepage");
        }

        if (actorArticle.getName().length() > ValidationConstants.LENGHT_VALIDATION_DEFAULT) {
            errors.rejectValue("name", "code.to.long", "to-long-name");
        }

        if (actorArticle.getTitle().length() > ValidationConstants.LENGHT_VALIDATION_DEFAULT) {
            errors.rejectValue("title", "code.to.long", "to-long-title");
        }

        if (actorArticle.getAddress().length() > ValidationConstants.LENGHT_VALIDATION_ADDRESS) {
            errors.rejectValue("address", "code.to.long", "to-long-address");
        }

        if (!isEmail(actorArticle.getMail())) {
            errors.rejectValue("mail", "code.invalid.email.format", "invalid-mail");
        }

        if (!isPhoneNumber(actorArticle.getPhone())) {
            errors.rejectValue("phone", "code.invalid.phone.format", "invalid-phone-number");
        }

        if (!isPhoneNumberEmptyIsOk(actorArticle.getMobilePhone())) {
            errors.rejectValue("mobilePhone", "code.invalid.mobilePhone.format", "invalid-mobile-phone-number");
        }

        if (!isPhoneNumberEmptyIsOk(actorArticle.getFax())) {
            errors.rejectValue("fax", "code.invalid.fax.format", "invalid-fax-number");
        }      
    }

    private boolean isEmail(String value) {
        return EMAIL_PATTERN.matcher(value).matches();
    }

    private boolean isPhoneNumber(String value) {

        String phoneNumber = value.replace('.', ' ').replace('-', ' ').replace('(', ' ').replace(')', ' ')
                .replaceAll("\\W", "");

        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isPhoneNumberEmptyIsOk(String value) {

        if (value.equals("")) {
            return true;
        }

        String phoneNumber = value.replace('.', ' ').replace('-', ' ').replace('(', ' ').replace(')', ' ')
                .replaceAll("\\W", "");

        return PHONE_NUMBER_PATTERN.matcher(phoneNumber).matches();
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}
