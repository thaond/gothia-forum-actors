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

package se.gothiaforum.actorsarticle.service;

import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.journal.model.JournalArticle;
import se.gothiaforum.actorsarticle.domain.model.ActorArticle;

import java.util.List;

/**
 * The Interface for the ActorsService.
 * 
 * @author simgo3
 * 
 *         Manage actors article.
 */
public interface ActorsService {

    /**
     * Adds the actor.
     * 
     * @param actorArticle
     *            the actor article
     * @param userId
     *            the user id
     * @param defaultUserId
     *            the default user id
     * @param companyId
     *            the company id
     * @param serviceContext
     *            the service context
     * @param tagsEntries
     *            the tags entries
     * @param groupId
     *            the group id
     */
    void addActor(ActorArticle actorArticle, long userId, long defaultUserId, long companyId,
            ServiceContext serviceContext, String tagsEntries, long groupId);

    /**
     * Update the actor.
     * 
     * @param actorArticle
     *            the actor article
     * @param userId
     *            the user id
     * @param serviceContext
     *            the service context
     * @param tagsEntries
     *            the tags entries
     * @param groupId
     *            the group id
     * @return the journal article
     */
    JournalArticle updateActors(ActorArticle actorArticle, long userId, ServiceContext serviceContext,
            String tagsEntries, long groupId);

    /**
     * Returns the actors article for the current user.
     * 
     * @param themeDisplay
     *            the theme display
     * @return the actors article
     */
    ActorArticle getActorsArticle(ThemeDisplay themeDisplay);

    /**
     * Send actor presentation to the workflow engine for approval.
     * 
     * @param userId
     *            the user id
     * @param actorArticle
     *            the actor article
     * @param groupId
     *            the group id
     * @param serviceContext
     *            the service context
     */
    void sendActors(long userId, JournalArticle actorArticle, long groupId, ServiceContext serviceContext);

    /**
     * Gets all organizations of the type actor.
     * 
     * @param companyId
     *            the company id
     * @return all organizations of the type actor.
     */
    List<Organization> getOrganizations(long companyId);

    /**
     * Gets all the users that are a member of the actor organization that the current user is a member of.
     * 
     * @param themeDisplay
     *            the theme display
     * @return the members of the actor organization.
     */
    List<User> getUsers(ThemeDisplay themeDisplay);

    /**
     * Sends a social request. That requests to be a member of an actor organization.
     * 
     * @param organizationId
     *            the organization id
     * @param userId
     *            the user id
     * @param companyId
     *            the company id
     */
    void sendConnectRequest(long organizationId, long userId, long companyId);

    /**
     * Gets the URL to the image for the current actor.
     * 
     * @param themeDisplay
     *            the theme display
     * @return the iG image url
     */
    String getImageURL(ThemeDisplay themeDisplay);

    /**
     * Adds the image to the image gallery and adds it to the actor article.
     * 
     * @param userId
     *            the user id
     * @param actorGroupId
     *            the actor group id
     * @param originalFileName
     *            the original file name
     * @param logoInByte
     *            the logo in byte
     * @param mimeType
     *            the mime type
     * @param portletId
     * @return the iG image
     */
    DLFileEntry addImage(long userId, long actorGroupId, String originalFileName, byte[] logoInByte, String mimeType, String portletId);

}