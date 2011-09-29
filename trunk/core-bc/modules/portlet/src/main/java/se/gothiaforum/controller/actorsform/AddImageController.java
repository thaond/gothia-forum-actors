package se.gothiaforum.controller.actorsform;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.portlet.bind.PortletRequestDataBinder;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.multipart.MultipartActionRequest;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.service.ActorsService;
import se.gothiaforum.validator.actorsform.ImageValidator;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;

/**
 * @author simgo3
 * 
 */

@Controller
@RequestMapping(value = "VIEW")
public class AddImageController {
    private static final Log log = LogFactory.getLog(AddImageController.class);

    @Autowired
    private ActorsService actorsService;

    @Autowired
    private ImageValidator validator;

    @RenderMapping(params = "view=showImageActorsForm")
    public String showImageFormView(Model model, RenderRequest request) {

        // Workaround to get the errors form-validation from actionrequest
        Errors errors = (Errors) model.asMap().get("errors");
        if (errors != null) {
            model.addAttribute("org.springframework.validation.BindingResult.actorArticle", errors);
            return "blocks/formView";
        }

        return "blocks/imageView";
    }

    @RequestMapping(params = "action=addActorImage")
    public void addActorImage(ActionRequest request, ActionResponse response) throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        ActorArticle actorArticle = actorsService.getActorsArticle(themeDisplay);
        long actorGroupId = actorArticle.getGroupId();
        long userId = themeDisplay.getUserId();

        if (request instanceof MultipartActionRequest) {
            MultipartActionRequest multipartRequest = (MultipartActionRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("file");

            if (multipartFile.getSize() != 0) {

                System.out.println("Addimage - multipartFile.getSize() != 0");

                String originalFileName = multipartFile.getOriginalFilename();
                String mime_type = multipartFile.getContentType();

                if (validator.isValidate(multipartFile)) {

                    byte[] logoInByte = multipartFile.getBytes();

                    IGImage image = actorsService.addImage(userId, actorGroupId, originalFileName, logoInByte,
                            mime_type);

                    ServiceContext serviceContext = ServiceContextFactory.getInstance(
                            JournalArticle.class.getName(), request);

                    String imageURL = "/image/image_gallery?uuid=" + image.getUuid() + "&groupId="
                            + actorArticle.getGroupId();

                    actorArticle.setImageUuid(imageURL);

                    JournalArticle article = actorsService.updateActors(actorArticle, userId, serviceContext,
                            actorArticle.getTagsStr(), themeDisplay.getScopeGroupId());

                    Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class.getName());
                    indexer.reindex(article);
                }
            } else {

                ServiceContext serviceContext = ServiceContextFactory.getInstance(JournalArticle.class.getName(),
                        request);

                String imageURL = actorsService.getIGImageURL((ThemeDisplay) request
                        .getAttribute(WebKeys.THEME_DISPLAY));

                actorArticle.setImageUuid(imageURL);

                JournalArticle article = actorsService.updateActors(actorArticle, userId, serviceContext,
                        actorArticle.getTagsStr(), themeDisplay.getScopeGroupId());

                Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class.getName());
                indexer.reindex(article);

            }
        }
    }

    protected void initBinder(PortletRequest request, PortletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        //binder.registerCustomEditor(String.class, new StringMultipartFileEditor());
    }

    @ModelAttribute(value = "actorLogo")
    public String getActors(PortletRequest request) {
        return actorsService.getIGImageURL((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY));
    }

    public void setActorsService(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    public void setValidator(ImageValidator validator) {
        this.validator = validator;
    }

}
