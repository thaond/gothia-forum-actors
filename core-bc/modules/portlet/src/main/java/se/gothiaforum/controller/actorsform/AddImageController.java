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

package se.gothiaforum.controller.actorsform;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.journal.model.JournalArticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import se.gothiaforum.actorsarticle.util.ActorsServiceUtil;
import se.gothiaforum.validator.actorsform.ImageValidator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * The controller that handles the logo (image) related functionality.
 * 
 * @author simgo3
 */
@Controller
@RequestMapping(value = "VIEW")
public class AddImageController {
    private static final Logger LOG = LoggerFactory.getLogger(AddImageController.class);

    @Autowired
    private ActorsService actorsService;

    @Autowired
    private ImageValidator validator;

    /**
     * Renders image form view.
     * 
     * @param model
     *            the model
     * @param request
     *            the request
     * @return the string
     */
    @RenderMapping(params = "view=showImageError")
    public String showImageErrorView(Model model, RenderRequest request) {

        return "blocks/imageErrorView";
    }

    /**
     * Renders the image form view.
     * 
     * @param model
     *            the model
     * @param request
     *            the request
     * @return the string
     */
    @RenderMapping(params = "view=showImageActorsForm")
    public String showImageFormView(Model model, RenderRequest request) {

        // Workaround to get the errors form-validation from action request
        Errors errors = (Errors) model.asMap().get("errors");
        if (errors != null) {
            model.addAttribute("org.springframework.validation.BindingResult.actorArticle", errors);
            return "blocks/formView";
        }

        return "blocks/imageView";
    }

    /**
     * Adds the logo to the image gallery for the actors organization.
     * 
     * @param request
     *            the request
     * @param response
     *            the response
     * @throws Exception
     *             the exception
     */
    @RequestMapping(params = "action=addActorImage")
    public void addActorImage(ActionRequest request, ActionResponse response, Model model) throws Exception {

        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        ActorArticle actorArticle = actorsService.getActorsArticle(themeDisplay);
        long actorGroupId = actorArticle.getGroupId();
        long userId = themeDisplay.getUserId();

        List<String> errors = new ArrayList<String>();

        if (request instanceof MultipartActionRequest) {
            MultipartActionRequest multipartRequest = (MultipartActionRequest) request;
            MultipartFile multipartFile = multipartRequest.getFile("file");

            if (multipartFile.getSize() == 0) {

                ServiceContext serviceContext =
                        ServiceContextFactory.getInstance(JournalArticle.class.getName(), request);

                String imageURL =
                        actorsService.getImageURL((ThemeDisplay) request
                                .getAttribute(WebKeys.THEME_DISPLAY));

                actorArticle.setImageUuid(imageURL);

                JournalArticle article =
                        actorsService.updateActors(actorArticle, userId, serviceContext,
                                actorArticle.getTagsStr(), themeDisplay.getScopeGroupId());

                Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class.getName());
                indexer.reindex(article);

            } else {

                String originalFileName = multipartFile.getOriginalFilename();
                String mimeType = multipartFile.getContentType();

                validator.validate(multipartFile, errors);

                if (!errors.isEmpty()) {
                    model.addAttribute("errorList", errors);
                    response.setRenderParameter("view", "showImageActorsForm");
                } else {

                    byte[] logoInByte = multipartFile.getBytes();

                    DLFileEntry image =
                            actorsService.addImage(userId, actorGroupId, originalFileName, logoInByte,
                                    mimeType, themeDisplay.getPortletDisplay().getRootPortletId());

                    ServiceContext serviceContext =
                            ServiceContextFactory.getInstance(JournalArticle.class.getName(), request);

                    // Assume there is no parent folder. Otherwise we would need to concatenate a like "{parentFolderId}/{image.getFolderId}
                    String imageUrl = ActorsServiceUtil.getImageUrl(image);

                    actorArticle.setImageUuid(imageUrl);

                    JournalArticle article =
                            actorsService.updateActors(actorArticle, userId, serviceContext,
                                    actorArticle.getTagsStr(), themeDisplay.getScopeGroupId());

                    Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class.getName());
                    indexer.reindex(article);

                }
            }
        }
    }

    protected void initBinder(PortletRequest request, PortletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
    }

    /**
     * Gets the image url.
     * 
     * @param request
     *            the request
     * @return the image url.
     */
    @ModelAttribute(value = "actorLogo")
    public String getActorsLogo(PortletRequest request) {
        return actorsService.getImageURL((ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY));
    }

    public void setActorsService(ActorsService actorsService) {
        this.actorsService = actorsService;
    }

    public void setValidator(ImageValidator validator) {
        this.validator = validator;
    }

}
