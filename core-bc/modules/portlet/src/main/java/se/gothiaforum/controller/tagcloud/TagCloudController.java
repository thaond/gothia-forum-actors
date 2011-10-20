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

package se.gothiaforum.controller.tagcloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import se.gothiaforum.actorsarticle.util.ActorsConstants;
import se.gothiaforum.model.tagcloud.TagVO;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetTagLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

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
 * @author simgo3
 * 
 *         This Controller class is used for the tag cloud.
 * 
 */

@Controller
@RequestMapping(value = "VIEW")
public class TagCloudController {

	@RenderMapping
	public String renderView(RenderRequest request, Model model) {

		System.out.println("render");

		try {

			// AssetEntryUtil.getAssetTagsSize(0);

			List<JournalArticle> articles = JournalArticleLocalServiceUtil.getJournalArticles(0,
			        JournalArticleLocalServiceUtil.getJournalArticlesCount());
			// List<AssetEntry> entrys = new ArrayList<AssetEntry>();

			Set<AssetEntry> entrys = new HashSet<AssetEntry>();

			for (JournalArticle ja : articles) {
				if (ja.getType().equals(ActorsConstants.TYPE_ACTOR)) {
					entrys.add(AssetEntryLocalServiceUtil.getEntry(JournalArticle.class.getName(),
					        ja.getResourcePrimKey()));
				}
			}

			Multiset<AssetTag> tagMultiSet = HashMultiset.create();

			for (AssetEntry entry : entrys) {

				List<AssetTag> tags = AssetTagLocalServiceUtil.getEntryTags(entry.getEntryId());

				for (AssetTag tag : tags) {
					tagMultiSet.add(tag);
				}
			}

			List<TagVO> tagVOList = new ArrayList<TagVO>();

			for (Entry<AssetTag> entry : tagMultiSet.entrySet()) {

				String cssClass;

				if (entry.getCount() > 8) {
					cssClass = "tag-weight-10";
				} else if (entry.getCount() > 7) {
					cssClass = "tag-weight-9";
				} else if (entry.getCount() > 6) {
					cssClass = "tag-weight-8";
				} else if (entry.getCount() > 5) {
					cssClass = "tag-weight-7";
				} else if (entry.getCount() > 4) {
					cssClass = "tag-weight-6";
				} else if (entry.getCount() > 3) {
					cssClass = "tag-weight-5";
				} else if (entry.getCount() > 2) {
					cssClass = "tag-weight-4";
				} else {
					cssClass = "tag-weight-2";
				}

				TagVO tagVO = new TagVO(cssClass, entry.getElement().getName(),
				        ActorsConstants.SEARCH_RIDERECT_URL + entry.getElement().getName(), entry.getElement()
				                .getTagId());

				tagVO.setCount(entry.getCount());

				tagVOList.add(tagVO);

			}

			if (tagVOList.size() > 12) {

				for (int i = 1; tagVOList.size() > 12; i++) {

					List<TagVO> removeList = new ArrayList<TagVO>();

					for (TagVO tagVO : tagVOList) {

						if (tagVO.getCount() == i) {
							removeList.add(tagVO);
						}
					}
					tagVOList.removeAll(removeList);
				}
			}

			// List<AssetTag> assetTagList = new
			// ArrayList<AssetTag>(Collections.unmodifiableCollection(assetTags));

			Collections.shuffle(tagVOList);

			model.addAttribute("tagVOList", tagVOList);

		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "tagCloudView";
	}

	@ActionMapping("action")
	public void action(ActionRequest request, ActionResponse response) {

		System.out.println("action");

	}

	@ResourceMapping
	public void resource(ResourceRequest request, ResourceResponse response) {

		System.out.println("resource");

	}

}
