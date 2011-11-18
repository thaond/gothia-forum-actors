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

package se.gothiaforum.actorsarticle.service.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.service.ActorsArticleConverterService;
import se.gothiaforum.actorsarticle.service.ActorsService;
import se.gothiaforum.actorsarticle.util.ActorAssetEntryUtil;
import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.actorsarticle.util.ActorsServiceUtil;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.asset.service.AssetTagLocalService;
import com.liferay.portlet.asset.service.AssetTagPropertyLocalService;
import com.liferay.portlet.imagegallery.NoSuchFolderException;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGFolderLocalService;
import com.liferay.portlet.imagegallery.service.IGImageLocalService;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.social.service.SocialRequestLocalService;

/**
 * The Class ActorsArticleService.
 * 
 * @author simgo3
 * 
 */
public class ActorsServiceImpl implements ActorsService {

    private static final Log LOG = LogFactory.getLog(ActorsServiceImpl.class);
    private final ActorsArticleConverterService actorsArticleConverterService;
    private final ActorsServiceUtil actorsServiceUtil;
    private final AssetEntryLocalService assetEntryService;
    private final AssetTagLocalService assetTagService;
    private final AssetTagPropertyLocalService assetTagPropertyService;
    private final CounterLocalService counterService;
    private final IGImageLocalService iGImageService;
    private final IGFolderLocalService iGFolderService;
    private final JournalArticleLocalService articleService;
    private final JournalArticleResourceLocalService articleResourceService;
    private final OrganizationLocalService organizationService;
    private final SocialRequestLocalService socialRequestService;
    private final RoleLocalService roleService;
    private final UserLocalService userService;
    private final MBMessageLocalService mBMessageLocalService;
    private final ActorAssetEntryUtil actorAssetEntryUtil;

    private static final int ADD_MEMBER = 1;
    private static final int FILE_SUFIX_LENGHT = 3;

    /**
     * Instantiates a new actors service impl.
     * 
     * @param actorsArticleConverterService
     *            the actors article converter service
     * @param actorsServiceUtil
     *            the actors service util
     * @param assetEntryService
     *            the asset entry service
     * @param counterService
     *            the counter service
     * @param iGImageService
     *            the iG image service
     * @param iGFolderService
     *            the iG folder service
     * @param articleService
     *            the article service
     * @param articleResourceService
     *            the article resource service
     * @param organizationService
     *            the organization service
     * @param socialRequestService
     *            the social request service
     * @param roleService
     *            the role service
     * @param userService
     *            the user service
     */
    public ActorsServiceImpl(ActorsArticleConverterService actorsArticleConverterService,
            ActorsServiceUtil actorsServiceUtil, AssetEntryLocalService assetEntryService,
            AssetTagLocalService assetTagService, AssetTagPropertyLocalService assetTagPropertyService,
            CounterLocalService counterService, IGImageLocalService iGImageService,
            IGFolderLocalService iGFolderService, JournalArticleLocalService articleService,
            JournalArticleResourceLocalService articleResourceService,
            OrganizationLocalService organizationService, SocialRequestLocalService socialRequestService,
            RoleLocalService roleService, UserLocalService userService,
            MBMessageLocalService mBMessageLocalService, ActorAssetEntryUtil actorAssetEntryUtil) {

        super();

        this.actorsArticleConverterService = actorsArticleConverterService;
        this.actorsServiceUtil = actorsServiceUtil;
        this.assetEntryService = assetEntryService;
        this.assetTagService = assetTagService;
        this.assetTagPropertyService = assetTagPropertyService;
        this.counterService = counterService;
        this.iGImageService = iGImageService;
        this.iGFolderService = iGFolderService;
        this.articleService = articleService;
        this.articleResourceService = articleResourceService;
        this.organizationService = organizationService;
        this.socialRequestService = socialRequestService;
        this.roleService = roleService;
        this.userService = userService;
        this.mBMessageLocalService = mBMessageLocalService;
        this.actorAssetEntryUtil = actorAssetEntryUtil;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * se.gothiaforum.actorsarticle.service.ActorsService#getActorsArticle(com.liferay.portal.theme.ThemeDisplay)
     */
    @Override
    public ActorArticle getActorsArticle(ThemeDisplay themeDisplay) {

        JournalArticle journalArticle = null;
        List<JournalArticle> articles;
        long groupId = getActorOrganization(themeDisplay.getUser());

        try {
            articles = articleService.getArticles(groupId);

            // Find the Journal Article for the current group.
            for (JournalArticle jA : articles) {
                if (jA.getType().equals(ActorsConstants.TYPE_ACTOR)) {
                    journalArticle = articleService.getLatestArticle(jA.getResourcePrimKey());
                }
            }
        } catch (SystemException e) {
            throw new RuntimeException("Unable to get actor's article.", e);
        }
        // Creates a model of actors article by the journal article.
        catch (PortalException e) {
            e.printStackTrace();
        }

        ActorArticle actorArticle = new ActorArticle();

        if (journalArticle != null) {
            actorArticle = actorsArticleConverterService.getActorsArticle(journalArticle.getContent());
            actorArticle.setArticleId(journalArticle.getArticleId());
            actorArticle.setPrimKeyId(journalArticle.getPrimaryKey());
            actorArticle.setResourcePrimKeyId(journalArticle.getResourcePrimKey());
            actorArticle.setArticleStatus(journalArticle.getStatus());
            actorArticle.setGroupId(journalArticle.getGroupId());

            // journalArticle.setTemplateId(ActorsConstants.GLOBAL_TEMPLATEID);
            try {
                actorArticle.setContent(articleService.getArticleContent(journalArticle,
                        ActorsConstants.GLOBAL_TEMPLATEID, null, themeDisplay.getLanguageId(), themeDisplay));
            } catch (PortalException e) {
                // actorArticle.setContent("Fel");
                // return actorArticle;
                throw new RuntimeException("Unable to get actor's article.", e);
            } catch (SystemException e) {
                // actorArticle.setContent("Fel");
                // return actorArticle;
                throw new RuntimeException("Unable to get actor's article.", e);
            }
        }
        return actorArticle;
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#getOrganizations(long)
     */
    @Override
    public List<Organization> getOrganizations(long companyId) {

        List<Organization> orgs = null;

        try {
            Organization parentOrganization = organizationService.getOrganization(companyId,
                    ActorsConstants.PARENT_ORGANIZATION);

            orgs = parentOrganization.getSuborganizations();
        } catch (SystemException e) {
            throw new RuntimeException("Failed to get organizations", e);
        } catch (PortalException e) {
            throw new RuntimeException("Failed to get organizations", e);
        }
        return orgs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#getUsers(com.liferay.portal.theme.ThemeDisplay)
     */
    @Override
    public List<User> getUsers(ThemeDisplay themeDisplay) {
        Organization organization = null;
        List<Organization> organizations;

        try {
            organizations = themeDisplay.getUser().getOrganizations();
        } catch (PortalException e1) {
            throw new RuntimeException("Unable to get users", e1);
        } catch (SystemException e1) {
            throw new RuntimeException("Unable to get users", e1);
        }
        for (Organization org : organizations) {
            if (org.getType().equals(ActorsConstants.ORGNIZATION_ACTOR_TYPE)) {
                organization = org;
            }
        }
        List<User> users = null;
        if (organization != null) {
            try {
                users = userService.getOrganizationUsers(organization.getOrganizationId());
            } catch (SystemException e) {
                throw new RuntimeException("Unable to get users", e);
            }
        }
        return users;
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#addActor(se.gothiaforum.actorsarticle.domain.model.
     * ActorArticle, long, long, long, com.liferay.portal.service.ServiceContext, java.lang.String, long)
     */
    @Override
    public void addActor(ActorArticle actorArticle, long userId, long defaultUserId, long companyId,
            ServiceContext serviceContext, String tagsEntries, long groupId) {
        /**
         * Create a organization and add the user to it.
         */
        Organization parentOrganization = actorsServiceUtil.getParentOrganization(defaultUserId);
        String name = actorArticle.getCompanyName();
        String type = "gothia-actor";
        boolean recursable = true;
        long regionId = 0;
        long countryId = 0;
        int statusId = actorsServiceUtil.getStatusIdForOrganizationFullMember();
        String comments = "This is the organization for " + name;
        Organization org = null;

        long parentOrganizationId = parentOrganization.getOrganizationId();

        try {
            org = organizationService.addOrganization(userId, parentOrganizationId, name, type, recursable,
                    regionId, countryId, statusId, comments, null);

        } catch (PortalException e) {
            throw new RuntimeException("Unable to add a organization", e);
        } catch (SystemException e) {
            throw new RuntimeException("Unable to add a organization", e);
        }

        actorsServiceUtil.addUserToOrg(userId, org.getOrganizationId());
        actorsServiceUtil.addUserRole(userId, companyId, org.getGroup().getGroupId());

        try {
            /**
             * Create a article and setts all the values for the article.
             */
            JournalArticle article = articleService.createJournalArticle(counterService.increment());
            article = actorsServiceUtil.setArticleFields(article, actorArticle, org.getGroup().getGroupId(),
                    parentOrganization.getGroup().getGroupId(), userId);

            /**
             * Create a resource for the article.
             */
            JournalArticleResource articleResource = articleResourceService
                    .createJournalArticleResource(counterService.increment());

            articleResource.setArticleId(article.getArticleId());
            articleResource.setGroupId(org.getGroup().getGroupId());
            article.setResourcePrimKey(articleResource.getPrimaryKey());
            articleResourceService.addJournalArticleResource(articleResource);

            /**
             * Creating the content String from the field of the form.
             */
            article.setContent(actorsServiceUtil.content2XML(actorArticle));
            articleService.addJournalArticle(article); // Add the article.

            /**
             * Add AssetEntry for the article.
             */

            articleService.addArticleResources(article, true, true); // Add the article to the DB.

            AssetEntry assetEntry = actorsServiceUtil.getAssetEntry(userId, org.getGroup().getGroupId(), article);

            assetEntryService.addAssetEntry(assetEntry);

            addArticleTags(userId, groupId, article, tagsEntries, serviceContext, assetEntry);

            // Message Boards
            mBMessageLocalService
                    .addDiscussionMessage(userId, article.getUserName(), org.getGroup().getGroupId(),
                            JournalArticle.class.getName(), article.getResourcePrimKey(),
                            WorkflowConstants.STATUS_PENDING);

        } catch (SystemException e) {
            throw new RuntimeException("Unable to add a actor", e);
        } catch (PortalException e) {
            throw new RuntimeException("Unable to add a actor", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * se.gothiaforum.actorsarticle.service.ActorsService#updateActors(se.gothiaforum.actorsarticle.domain.model
     * .ActorArticle, long, com.liferay.portal.service.ServiceContext, java.lang.String, long)
     */
    @Override
    public JournalArticle updateActors(ActorArticle actorArticle, long userId, ServiceContext serviceContext,
            String tagsEntries, long groupId) {

        JournalArticle article;
        try {
            article = articleService.getArticle(actorArticle.getPrimKeyId());

            article.setContent(actorsServiceUtil.content2XML(actorArticle));

            articleService.updateJournalArticle(article);

            String className = JournalArticle.class.getName();
            long classPK = article.getResourcePrimKey();

            AssetEntry assetEntry = assetEntryService.getEntry(className, classPK);

            addArticleTags(userId, groupId, article, tagsEntries, serviceContext, assetEntry);

        } catch (PortalException e) {
            throw new RuntimeException("Could not update an actor", e);
        } catch (SystemException e) {
            throw new RuntimeException("Could not update an actor", e);
        }
        return article;
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#sendActors(long,
     * com.liferay.portlet.journal.model.JournalArticle, long, com.liferay.portal.service.ServiceContext)
     */
    @Override
    public void sendActors(long userId, JournalArticle article, long groupId, ServiceContext serviceContext) {
        /**
         * Add article to workflow. Sends the article for approving.
         */
        actorsServiceUtil.addWorkFlow(userId, article, groupId, serviceContext);
        article.setStatus(ActorsConstants.PENDING); // Sets the status of the article to pending.

        try {
            articleService.updateJournalArticle(article);
        } catch (SystemException e) {
            throw new RuntimeException("Unable to Update the article. ", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#sendConnectRequest(long, long, long)
     */
    @Override
    public void sendConnectRequest(long organizationId, long userId, long companyId) {
        try {
            Role role = roleService.getRole(companyId, "Organization Administrator");
            long groupId = organizationService.getOrganization(organizationId).getGroup().getGroupId();
            LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();

            long roleId = Long.valueOf(role.getRoleId());
            long longGroupId = Long.valueOf(groupId);
            Long[] userGroupRole = new Long[] { longGroupId, roleId };

            userParams.put("userGroupRole", userGroupRole);
            List<User> users = userService.search(companyId, null, true, userParams, QueryUtil.ALL_POS,
                    QueryUtil.ALL_POS, (OrderByComparator) null);

            for (User user : users) {

                socialRequestService.addRequest(userId, 0, Organization.class.getName(), organizationId,
                        ADD_MEMBER, StringPool.BLANK, user.getUserId());
            }

        } catch (PortalException e) {
            throw new RuntimeException("Unable to send a social request", e);
        } catch (SystemException e) {
            throw new RuntimeException("Unable to send a social request", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#addImage(long, long, java.lang.String, byte[],
     * java.lang.String)
     */
    @Override
    public IGImage addImage(long userId, long actorGroupId, String originalFileName, byte[] logoInByte,
            String mimeType) {

        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setScopeGroupId(actorGroupId);
        IGFolder logoFolder = null;

        try {
            logoFolder = iGFolderService.getFolder(actorGroupId, 0, ActorsConstants.LOGO_FOLDER_NAME);
        } catch (NoSuchFolderException nsfe) {
            // Do nothing
        } catch (PortalException e) {
            throw new RuntimeException("Unable to add a image folder for an actor", e);
        } catch (SystemException e) {
            throw new RuntimeException("Unable to add a image folder for an actor", e);
        }

        try {
            if (logoFolder != null) {
                iGFolderService.deleteFolder(logoFolder.getFolderId());
            }

            logoFolder = iGFolderService
                    .addFolder(userId, 0, ActorsConstants.LOGO_FOLDER_NAME, "", serviceContext);

            String filename = "actor-logo."
                    + originalFileName.substring(originalFileName.length() - FILE_SUFIX_LENGHT,
                            originalFileName.length());

            IGImage addImage = iGImageService.addImage(userId, actorGroupId, logoFolder.getFolderId(),
                    ActorsConstants.ACTOR_LOGO_NAME, filename, filename, logoInByte, mimeType, serviceContext);

            return addImage;

        } catch (PortalException e) {
            throw new RuntimeException("Unable to add a image on an actor", e);
        } catch (SystemException e) {
            throw new RuntimeException("Unable to add a image on an actor", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#getIGImageURL(com.liferay.portal.theme.ThemeDisplay)
     */
    @Override
    public String getIGImageURL(ThemeDisplay themeDisplay) {

        long groupId = getActorOrganization(themeDisplay.getUser());

        IGFolder logoIGFolder;
        try {
            logoIGFolder = iGFolderService.getFolder(groupId, 0, ActorsConstants.LOGO_FOLDER_NAME);
            List<IGImage> images = iGImageService.getImages(groupId, logoIGFolder.getFolderId());
            IGImage actorLogo = null;

            for (IGImage image : images) {
                if (image.getName().equals(ActorsConstants.ACTOR_LOGO_NAME)) {
                    actorLogo = image;
                }
            }
            if (actorLogo != null) {
                String imageURL = themeDisplay.getPathImage() + "/image_gallery?img_id="
                        + actorLogo.getLargeImageId() + ActorsConstants.ACTOR_LOGO_HEIGHT_AND_WIDTH;
                return imageURL;
            }

        } catch (PortalException e) {
            LOG.info("No logotyp was found");
        } catch (SystemException e) {
            LOG.info("No logotyp was found");
        }
        return "noLogo";
    }

    private long getActorOrganization(User user) {

        List<Organization> organizations;
        try {
            organizations = user.getOrganizations();
        } catch (PortalException e1) {
            throw new RuntimeException("Could not find any actor's organization", e1);
        } catch (SystemException e1) {
            throw new RuntimeException("Could not find any actor's organization", e1);
        }

        long groupId = 0;

        for (Organization org : organizations) {
            if (org.getType().equals(ActorsConstants.ORGNIZATION_ACTOR_TYPE)) {
                groupId = org.getGroup().getGroupId();
            }
        }
        return groupId;
    }

    private void addArticleTags(long userId, long groupId, JournalArticle article, String tagsEntries,
            ServiceContext serviceContext, AssetEntry assetEntry) throws SystemException, PortalException {

        /**
         * Add asset tags
         */
        String[] tagsArray = null;
        if (tagsEntries != null && !tagsEntries.equalsIgnoreCase("")) {
            tagsArray = tagsEntries.split(",");
        }

        if (tagsArray != null) {
            actorAssetEntryUtil.clearAssetTags(assetEntry.getPrimaryKey());

            for (String tagName : tagsArray) {

                if (assetTagService.hasTag(groupId, tagName)) { // add an existing tag to the article
                    AssetTag assetTag = assetTagService.getTag(groupId, tagName);
                    actorAssetEntryUtil.addAssetTag(assetEntry.getPrimaryKey(), assetTag);
                } else { // Creating a new tag and adding it to the article
                    AssetTag assetTag = assetTagService.addTag(userId, tagName, null, serviceContext);
                    assetTagPropertyService.addTagProperty(userId, assetTag.getTagId(), "lang", "sv");
                    assetTagPropertyService.addTagProperty(userId, assetTag.getTagId(), "type", "actor");
                    actorAssetEntryUtil.addAssetTag(assetEntry.getPrimaryKey(), assetTag);
                }
            }
        }
    }

}
