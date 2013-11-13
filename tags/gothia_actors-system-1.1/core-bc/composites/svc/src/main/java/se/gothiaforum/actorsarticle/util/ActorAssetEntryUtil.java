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
package se.gothiaforum.actorsarticle.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.persistence.AssetEntryUtil;

/**
 * @author simongoransson
 * 
 */
public class ActorAssetEntryUtil {
    private static final Log LOG = LogFactory.getLog(ActorAssetEntryUtil.class);

    public void clearAssetTags(long pk) throws SystemException {
        AssetEntryUtil.clearAssetTags(pk);
    }

    /**
     * @param primaryKey
     * @param assetTag
     * @throws SystemException
     */
    public void addAssetTag(long pk, AssetTag assetTag) throws SystemException {
        AssetEntryUtil.addAssetTag(pk, assetTag);
    }

}
