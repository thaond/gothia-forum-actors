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

import com.liferay.counter.service.CounterLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
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
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.social.service.SocialRequestLocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.actorsarticle.service.ActorsArticleConverterService;
import se.gothiaforum.actorsarticle.service.ActorsService;
import se.gothiaforum.actorsarticle.util.ActorAssetEntryUtil;
import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.actorsarticle.util.ActorsServiceUtil;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The Class ActorsArticleService.
 * 
 * @author simgo3
 * 
 */
public class ActorsServiceImpl implements ActorsService {

    private static final Logger LOG = LoggerFactory.getLogger(ActorsServiceImpl.class);
    private final ActorAssetEntryUtil actorAssetEntryUtil;
    private final ActorsArticleConverterService actorsArticleConverterService;
    private final ActorsServiceUtil actorsServiceUtil;
    private final AssetEntryLocalService assetEntryService;
    private final AssetTagLocalService assetTagService;
    private final AssetTagPropertyLocalService assetTagPropertyService;
    private final CounterLocalService counterService;
    private final DLFolderLocalService dlFolderLocalService;
    private final JournalArticleLocalService articleService;
    private final JournalArticleResourceLocalService articleResourceService;
    private final MBMessageLocalService mBMessageLocalService;
    private final OrganizationLocalService organizationService;
    private final SocialRequestLocalService socialRequestService;
    private final RoleLocalService roleService;
    private final UserLocalService userService;

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
     * @param assetTagService
     *            the asset tag service
     * @param assetTagPropertyService
     *            the asset tag property service
     * @param counterService
     *            the counter service
     * @param dlFolderLocalService
     *            the i g folder service
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
     * @param mBMessageLocalService
     *            the m b message local service
     * @param actorAssetEntryUtil
     *            the actor asset entry util
     */
    public ActorsServiceImpl(ActorsArticleConverterService actorsArticleConverterService,
            ActorsServiceUtil actorsServiceUtil, AssetEntryLocalService assetEntryService,
            AssetTagLocalService assetTagService, AssetTagPropertyLocalService assetTagPropertyService,
            CounterLocalService counterService,
            DLFolderLocalService dlFolderLocalService, JournalArticleLocalService articleService,
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
        this.dlFolderLocalService = dlFolderLocalService;
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
                throw new RuntimeException("Unable to get actor's article.", e);
            } catch (SystemException e) {
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
                    regionId, countryId, statusId, comments, false, serviceContext);

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
            User user = userService.getUserById(companyId, userId);

            socialRequestService.addRequest(userId, 0, Organization.class.getName(), organizationId,
                    ADD_MEMBER, StringPool.BLANK, user.getUserId());

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
    public DLFileEntry addImage(long userId, long actorGroupId, String originalFileName, byte[] logoInByte,
                                String mimeType, String portletId) {

        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setScopeGroupId(actorGroupId);
        DLFolder logoFolder = null;

        try {
            logoFolder = dlFolderLocalService.getFolder(actorGroupId, 0, ActorsConstants.LOGO_FOLDER_NAME);
        } catch (NoSuchFolderException nsfe) {
            LOG.warn("Unable to add a image folder for an actor, reason no such folder");
        } catch (PortalException e) {
            throw new RuntimeException("Unable to add a image folder for an actor", e);
        } catch (SystemException e) {
            throw new RuntimeException("Unable to add a image folder for an actor", e);
        }

        try {
            if (logoFolder != null) {
                dlFolderLocalService.deleteFolder(logoFolder.getFolderId());
            }

            logoFolder = dlFolderLocalService.addFolder(userId, actorGroupId, actorGroupId, false, 0, ActorsConstants.LOGO_FOLDER_NAME,
                    "", false, serviceContext);

            DLFileEntry addedImage = DLFileEntryLocalServiceUtil.addFileEntry( // todo inject dependency instead
                    userId,
                    actorGroupId,
                    actorGroupId,
                    logoFolder.getFolderId(),
                    originalFileName,
                    mimeType,
                    originalFileName,
                    "",
                    "",
                    DLFileEntryTypeLocalServiceUtil.getDLFileEntryType(46801).getFileEntryTypeId(), // IMAGE_GALLERY_IMAGE// todo inject dependency instead
                    Collections.EMPTY_MAP,
                    null,
                    new ByteArrayInputStream(logoInByte),
                    logoInByte.length,
                    serviceContext
            );

            return addedImage;

        } catch (PortalException e) {
            throw new RuntimeException("Unable to add a image on an actor", e);
        } catch (SystemException e) {
            throw new RuntimeException("Unable to add a image on an actor", e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see se.gothiaforum.actorsarticle.service.ActorsService#getImageURL(com.liferay.portal.theme.ThemeDisplay)
     */
    @Override
    public String getImageURL(ThemeDisplay themeDisplay) {

        long groupId = getActorOrganization(themeDisplay.getUser());

        DLFolder logoFolder;
        try {
            logoFolder = dlFolderLocalService.getFolder(groupId, 0, ActorsConstants.LOGO_FOLDER_NAME);

            List<DLFileEntry> fileEntries = DLFileEntryLocalServiceUtil.getFileEntries(groupId, logoFolder.getFolderId());// todo inject dependency instead

            if (fileEntries.size() > 1) {
                throw new IllegalStateException("Only one image file is expected in an actor logo folder.");
            } else if (fileEntries.size() < 1) {
                throw new RuntimeException("Couldn't find the logo image.");
            } else {
                return ActorsServiceUtil.getImageUrl(fileEntries.get(0));
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
