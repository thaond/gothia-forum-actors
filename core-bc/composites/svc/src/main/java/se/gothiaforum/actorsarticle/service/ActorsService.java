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

import java.util.List;

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;

import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;

// TODO: Auto-generated Javadoc
/**
 * The Interface ActorsService.
 * 
 * @author simgo3
 * 
 *         Manage actors article.
 */
public interface ActorsService {

    public void addActors(ActorArticle actorArticle, long userId, long defaultUserId, long companyId,
            ServiceContext serviceContext, String tagsEntries, long groupId);

    public JournalArticle updateActors(ActorArticle actorArticle, long userId, ServiceContext serviceContext,
            String tagsEntries, long groupId);

    public ActorArticle getActorsArticle(ThemeDisplay themeDisplay);

    public void sendActors(long userId, JournalArticle actorArticle, long groupId, ServiceContext serviceContext);

    public List<Organization> getOrganizations(long companyId);

    public List<User> getUsers(ThemeDisplay themeDisplay);

    public void sendConnectRequest(long organizationId, long userId, long companyId);

    public String getIGImageURL(ThemeDisplay themeDisplay);

    public IGImage addImage(long userId, long actorGroupId, String originalFileName, byte[] logoInByte,
            String mime_type);

}