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

package se.gothiaforum.actorsarticle.util;

import java.io.File;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;
import se.gothiaforum.util.ArticleContentXml;
import se.gothiaforum.util.Constants;

import com.liferay.counter.service.CounterLocalService;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.ListType;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.ListTypeService;
import com.liferay.portal.service.OrganizationLocalService;
import com.liferay.portal.service.RoleLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserGroupRoleLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalStructureLocalService;
import com.liferay.portlet.journal.service.JournalTemplateLocalService;

/**
 * This is a util class that provide help methods for the actor article service.
 * 
 * @author simgo3
 */
public class ActorsServiceUtil {

    private static final Log LOG = LogFactory.getLog(ActorsServiceUtil.class);
    private final AssetEntryLocalService assetEntryService;
    private final ClassNameLocalService classNameService;
    private final CounterLocalService counterService;
    private final JournalStructureLocalService structureService;
    private final JournalTemplateLocalService templateService;
    private final ListTypeService listTypeService;
    private final OrganizationLocalService organizationService;
    private final RoleLocalService roleService;
    private final UserGroupRoleLocalService userGroupRoleService;
    private final UserLocalService userService;

    /**
     * Instantiates a new actors service util.
     * 
     * @param assetEntryService
     *            the asset entry service
     * @param classNameService
     *            the class name service
     * @param counterService
     *            the counter service
     * @param structureService
     *            the structure service
     * @param templateService
     *            the template service
     * @param listTypeService
     *            the list type service
     * @param organizationService
     *            the organization service
     * @param roleService
     *            the role service
     * @param userGroupRoleService
     *            the user group role service
     * @param userService
     *            the user service
     */
    public ActorsServiceUtil(AssetEntryLocalService assetEntryService, ClassNameLocalService classNameService,
            CounterLocalService counterService, JournalStructureLocalService structureService,
            JournalTemplateLocalService templateService, ListTypeService listTypeService,
            OrganizationLocalService organizationService, RoleLocalService roleService,
            UserGroupRoleLocalService userGroupRoleService, UserLocalService userService) {
        super();
        this.assetEntryService = assetEntryService;
        this.classNameService = classNameService;
        this.counterService = counterService;
        this.structureService = structureService;
        this.templateService = templateService;
        this.listTypeService = listTypeService;
        this.organizationService = organizationService;
        this.roleService = roleService;
        this.userGroupRoleService = userGroupRoleService;
        this.userService = userService;
    }

    /**
     * This method converts the ActroArticle object to an XML string.
     * 
     * @param actorArticle
     *            the actor article
     * @return the XML string
     */
    public String content2XML(ActorArticle actorArticle) {

        ArticleContentXml aC2X = new ArticleContentXml();

        try {
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()),
                    ActorsConstants.ARTICLE_XML_COMPANY_NAME, ActorsConstants.ELEMENT_TYPE_TEXT,
                    actorArticle.getCompanyName());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()),
                    ActorsConstants.ARTICLE_XML_ORGANIZATION_NAME, ActorsConstants.ELEMENT_TYPE_TEXT,
                    actorArticle.getOrganizationName());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_INTRO,
                    ActorsConstants.ELEMENT_TYPE_TEXT, actorArticle.getIntro());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()),
                    ActorsConstants.ARTICLE_XML_DETAILED_DISCRIPTION, ActorsConstants.ELEMENT_TYPE_TEXTAREA,
                    actorArticle.getDetailedDescription());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_HOMEPAGE,
                    ActorsConstants.ELEMENT_TYPE_TEXT, actorArticle.getExternalHomepage());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()),
                    ActorsConstants.ARTICLE_XML_CONTACT_NAME, ActorsConstants.ELEMENT_TYPE_TEXT,
                    actorArticle.getName());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()),
                    ActorsConstants.ARTICLE_XML_CONTACT_TITLE, ActorsConstants.ELEMENT_TYPE_TEXT,
                    actorArticle.getTitle());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_ADDRESS,
                    ActorsConstants.ELEMENT_TYPE_TEXT, actorArticle.getAddress());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_PHONE,
                    ActorsConstants.ELEMENT_TYPE_TEXT, actorArticle.getPhone());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()),
                    ActorsConstants.ARTICLE_XML_MOBILE_PHONE, ActorsConstants.ELEMENT_TYPE_TEXT,
                    actorArticle.getMobilePhone());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_FAX,
                    ActorsConstants.ELEMENT_TYPE_TEXT, actorArticle.getFax());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_MAIL,
                    ActorsConstants.ELEMENT_TYPE_TEXT, actorArticle.getMail());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_LOGO,
                    ActorsConstants.ELEMENT_TYPE_IMAGE_GALLERY, actorArticle.getImageUuid());
            aC2X.dynamicElemnet(String.valueOf(counterService.increment()), ActorsConstants.ARTICLE_XML_IMAGES,
                    ActorsConstants.ELEMENT_TYPE_IMAGE_GALLERY, "");

        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

        return aC2X.end();

    }

    /**
     * Gets the asset entry for an article.
     * 
     * @param userId
     *            the user id
     * @param groupId
     *            the group id
     * @param article
     *            the article
     * @return the asset entry
     */
    public AssetEntry getAssetEntry(long userId, long groupId, JournalArticle article) {

        AssetEntry assetEntry;
        try {
            assetEntry = assetEntryService.createAssetEntry(counterService.increment());

            User user = userService.getUser(userId);

            assetEntry.setClassPK(article.getResourcePrimKey());
            assetEntry.setGroupId(groupId);
            assetEntry.setCompanyId(user.getCompanyId());
            assetEntry.setUserId(userId);
            assetEntry.setUserName(user.getFullName());
            assetEntry.setCreateDate(article.getCreateDate());
            assetEntry.setModifiedDate(article.getCreateDate());
            assetEntry.setClassNameId(classNameService.getClassNameId(JournalArticle.class.getName()));
            assetEntry.setClassUuid(article.getUuid());
            assetEntry.setVisible(Boolean.FALSE);
            assetEntry.setPublishDate(article.getCreateDate());
            assetEntry.setMimeType(ContentTypes.TEXT_HTML);
            assetEntry.setTitle(article.getTitle());
            assetEntry.setSummary("");
            assetEntry.setHeight(0);
            assetEntry.setWidth(0);
            assetEntry.setViewCount(0);

        } catch (PortalException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

        return assetEntry;

    }

    /**
     * Adds a journal structure.
     * 
     * @param userId
     *            the user id
     * @param groupId
     *            the group id
     * @param xsd
     *            the xsd
     * @param structureName
     *            the structure name
     * @param structureDescription
     *            the structure description
     * @return the journal structure
     * @throws Exception
     *             the exception
     */
    public JournalStructure addJournalStructure(long userId, long groupId, String xsd, String structureName,
            String structureDescription) throws Exception {

        String structureId = String.valueOf(counterService.increment());
        boolean autoStructureId = true;
        String parentStructureId = "";
        ServiceContext serviceContext = new ServiceContext();
        serviceContext.setAddCommunityPermissions(true);
        serviceContext.setAddGuestPermissions(true);

        return structureService.addStructure(userId, groupId, structureId, autoStructureId, parentStructureId,
                structureName, structureDescription, xsd, serviceContext);
    }

    /**
     * Adds a journal template.
     * 
     * @param userId
     *            the user id
     * @param groupId
     *            the group id
     * @param xsl
     *            the xsl
     * @param structureId
     *            the structure id
     * @param templateName
     *            the template name
     * @param templateDescription
     *            the template description
     * @return the journal template
     * @throws Exception
     *             the exception
     */
    public JournalTemplate addJournalTemplate(long userId, long groupId, String xsl, String structureId,
            String templateName, String templateDescription) throws Exception {

        String templateId = String.valueOf(CounterLocalServiceUtil.increment());

        boolean autoTemplateId = true;
        boolean formatXsl = true;
        String langType = "vm";
        boolean cacheable = true;
        boolean smallImage = false;
        String smallImageURL = StringPool.BLANK;
        File smallFile = null;

        ServiceContext serviceContext = new ServiceContext();

        serviceContext.setAddCommunityPermissions(true);
        serviceContext.setAddGuestPermissions(true);

        return templateService.addTemplate(userId, groupId, templateId, autoTemplateId, structureId, templateName,
                templateDescription, xsl, formatXsl, langType, cacheable, smallImage, smallImageURL, smallFile,
                serviceContext);
    }

    /**
     * Gets the parent organization, and creates it if there is no one.
     * 
     * @param defaultUserId
     *            the default userId
     * @return the parent organization
     */
    public Organization getParentOrganization(long defaultUserId) {

        Organization org = null;

        try {
            List<Organization> allOrgs = organizationService.getOrganizations(0,
                    organizationService.getOrganizationsCount());

            List<Organization> parentOrgs = new ArrayList<Organization>();
            for (Organization o : allOrgs) {

                if (o.getType().equals(ActorsConstants.ACTOR_PARENT)) {
                    parentOrgs.add(o);
                }
            }

            if (parentOrgs.size() < 1) {

                long parentOrganizationId = 0;
                String name = "Gothia Parent Organization";
                String type = ActorsConstants.ACTOR_PARENT;
                boolean recursable = true;
                long regionId = 0;
                long countryId = 0;
                int statusId = getStatusIdForOrganizationFullMember();
                String comments = "This is the " + name;
                ServiceContext serviceContext = null;

                org = organizationService.addOrganization(defaultUserId, parentOrganizationId, name, type,
                        recursable, regionId, countryId, statusId, comments, serviceContext);

            } else if (parentOrgs.size() == 1) {
                org = parentOrgs.get(0);
            } else {
                org = parentOrgs.get(0);
                throw new RuntimeException("It is to many Organization of type gothia-actor-parent");
            }

        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (PortalException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }
        return org;
    }

    public int getStatusIdForOrganizationFullMember() {

        try {

            List<ListType> list;
            list = listTypeService.getListTypes(ActorsConstants.LISTTYPE_TYPE);
            for (ListType type : list) {

                String typeNameLowerCase = type.getName().toLowerCase(Constants.LOCALE);
                if (typeNameLowerCase.indexOf("full") > -1 && typeNameLowerCase.indexOf("member") > -1) {
                    // most probably we have found the Full Member type. To solve differences between service packs
                    // we get the value like this
                    return type.getListTypeId();
                }
            }
            // we are not supposed to reach this place. If so we didn´t found what we were looking for
            String temp = "Internal error, expected to find status id for organization Full Member";
            throw new RuntimeException(temp);

        } catch (com.liferay.portal.kernel.exception.SystemException e) {
            throw new RuntimeException("Failed to get status id for organization Full Member", e);
        }
    }

    /**
     * Add the article to workflow.
     * 
     * @param article
     * @param userId
     * @param serviceContext
     * @param groupId
     */
    public void addWorkFlow(long userId, JournalArticle article, long groupId, ServiceContext serviceContext) {
        try {

            User user = userService.getUser(userId);
            WorkflowHandler workflowHandler = WorkflowHandlerRegistryUtil.getWorkflowHandler(JournalArticle.class
                    .getName());
            Map<String, Serializable> workflowContext = new HashMap<String, Serializable>();
            workflowContext.put(WorkflowConstants.CONTEXT_COMPANY_ID, String.valueOf(article.getCompanyId()));
            workflowContext.put(WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME,
                    String.valueOf(article.getClass().getName()));
            workflowContext.put(WorkflowConstants.CONTEXT_ENTRY_CLASS_PK, String.valueOf(article.getPrimaryKey()));
            workflowContext.put(WorkflowConstants.CONTEXT_ENTRY_TYPE, String.valueOf(article.getType()));
            workflowContext.put(WorkflowConstants.CONTEXT_GROUP_ID, String.valueOf(article.getGroupId()));
            workflowContext.put(WorkflowConstants.CONTEXT_USER_ID, String.valueOf(article.getUserId()));
            workflowContext.put(WorkflowConstants.CONTEXT_SERVICE_CONTEXT, serviceContext);

            workflowHandler.startWorkflowInstance(article.getCompanyId(), groupId, user.getUserId(),
                    article.getPrimaryKey(), article.getClass().getName(), workflowContext);
        } catch (PortalException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }
    }

    /**
     * Sets fields for the actor article.
     * 
     * @param article
     *            the article
     * @param actorArticle
     *            the actor article
     * @param groupId
     *            the group id
     * @param parentOrgGroupId
     *            the parentOrgGroupId
     * @param userId
     *            the user id
     * @return the journal article with the updated fields
     */
    public JournalArticle setArticleFields(JournalArticle article, ActorArticle actorArticle, long groupId,
            long parentOrgGroupId, long userId) {

        try {
            article.setArticleId(String.valueOf(counterService.increment()) + ActorsConstants.LANG_PREFIX);

            article.setGroupId(groupId);
            article.setType(ActorsConstants.TYPE_ACTOR);
            article.setVersion(1);
            article.setTitle(actorArticle.getCompanyName() + ActorsConstants.LANG_PREFIX);
            article.setIndexable(true);

            // Encode the titel to url safe UTF-8 String.
            article.setUrlTitle(URLEncoder.encode(actorArticle.getCompanyName(), ActorsConstants.UTF_8));
            User user = userService.getUser(userId); // The User who creates the article.

            /**
             * User specific fields
             */
            article.setUserId(user.getUserId());
            article.setUserName(user.getFullName());
            article.setUserUuid(user.getUserUuid());
            article.setStatusByUserName(user.getFullName());
            article.setStatusByUserId(user.getUserId());

            article.setCompanyId(user.getCompanyId());

            /**
             * Date specific fields
             */
            Date now = new Date();
            article.setCreateDate(now);
            article.setModifiedDate(now);
            article.setDisplayDate(now);
            article.setStatusDate(now);

            article.setSmallImageId(counterService.increment());
            article.setSmallImage(false);

            article.setStatus(ActorsConstants.DRAFT); // Sets the status of the article to draft.

        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (PortalException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

        article.setTemplateId(ActorsConstants.GLOBAL_TEMPLATEID);
        article.setStructureId(ActorsConstants.GLOBAL_STRUCTRUEID);

        return article;

    }

    /**
     * Adds the user to a organization.
     * 
     * @param userId
     *            the user id
     * @param organizationId
     *            the organization id
     */
    public void addUserToOrg(long userId, long organizationId) {

        long[] userIds = new long[1];

        userIds[0] = userId;

        try {
            userService.addOrganizationUsers(organizationId, userIds);
        } catch (PortalException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        } catch (SystemException e) {
            throw new RuntimeException("TODO: Handle this exception better", e);
        }

    }

    /**
     * Adds a role to a user.
     * 
     * @param userId
     * @param companyId
     * @param organizationId
     */
    public void addUserRole(long userId, long companyId, long groupId) {
        try {
            long[] userIds = new long[1];
            userIds[0] = userId;
            Role role = roleService.getRole(companyId, ActorsConstants.ORGANIZATION_ADMIN);
            userGroupRoleService.addUserGroupRoles(userIds, groupId, role.getRoleId());
        } catch (PortalException e1) {
            throw new RuntimeException("TODO: Handle this exception better", e1);
        } catch (SystemException e1) {
            throw new RuntimeException("TODO: Handle this exception better", e1);
        }

    }

}
