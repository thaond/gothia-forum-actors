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
package se.gothiaforum.actorarticle.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetTag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.gothiaforum.actorsarticle.util.ActorAssetEntryUtil;

/**
 * @author simongoransson
 * 
 */
public class ActorAssetEntryUtilMock extends ActorAssetEntryUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ActorAssetEntryUtilMock.class);

    @Override
    public void clearAssetTags(long pk) throws SystemException {

    }

    @Override
    public void addAssetTag(long pk, AssetTag assetTag) throws SystemException {

    }

}
