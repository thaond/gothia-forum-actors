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

import javax.portlet.RenderRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

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
 * The controller class for the tag cloud portlet.
 * 
 * @author simgo3
 */
@Controller
@RequestMapping(value = "VIEW")
public class TagCloudController {

    /**
     * Renders the view for the tag cloud.
     * 
     * @param request
     *            the request
     * @param model
     *            the model
     * @return a view
     */
    @RenderMapping
    public String renderView(RenderRequest request, Model model) {

        try {

            List<JournalArticle> articles = JournalArticleLocalServiceUtil.getJournalArticles(0,
                    JournalArticleLocalServiceUtil.getJournalArticlesCount());

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

                final int number8 = 8;
                final int number7 = 7;
                final int number6 = 6;
                final int number5 = 5;
                final int number4 = 4;
                final int number3 = 3;
                final int number2 = 2;

                if (entry.getCount() > number8) {
                    cssClass = "tag-weight-10";
                } else if (entry.getCount() > number7) {
                    cssClass = "tag-weight-9";
                } else if (entry.getCount() > number6) {
                    cssClass = "tag-weight-8";
                } else if (entry.getCount() > number5) {
                    cssClass = "tag-weight-7";
                } else if (entry.getCount() > number4) {
                    cssClass = "tag-weight-6";
                } else if (entry.getCount() > number3) {
                    cssClass = "tag-weight-5";
                } else if (entry.getCount() > number2) {
                    cssClass = "tag-weight-4";
                } else {
                    cssClass = "tag-weight-2";
                }

                TagVO tagVO = new TagVO(cssClass, entry.getElement().getName(),
                        ActorsConstants.SEARCH_REDIRECT_URL + entry.getElement().getName(), entry.getElement()
                                .getTagId());

                tagVO.setCount(entry.getCount());

                tagVOList.add(tagVO);

            }

            final int size = 20;

            if (tagVOList.size() > size) {

                for (int i = 1; tagVOList.size() > size; i++) {

                    List<TagVO> removeList = new ArrayList<TagVO>();

                    for (TagVO tagVO : tagVOList) {

                        if (tagVO.getCount() == i) {
                            removeList.add(tagVO);
                        }
                    }
                    tagVOList.removeAll(removeList);
                }
            }

            Collections.shuffle(tagVOList);

            model.addAttribute("tagVOList", tagVOList);

        } catch (SystemException e) {
            e.printStackTrace();
        } catch (PortalException e) {
            e.printStackTrace();
        }

        return "tagCloudView";
    }
}
