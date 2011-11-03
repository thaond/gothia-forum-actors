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

import se.gothiaforum.actorsarticle.domain.model.ActorArticle;

/**
 * This class takes an XML representing an ActorArticle and converts it to an ActorArticle object.
 * 
 * @author Hans Gyllensten, vgrid=hangy2
 */
public interface ActorsArticleConverterService {

    /**
     * Converts xml to a actors article.
     * 
     * @param actorsArticleAsXML
     *            the actors article as xml
     * @return the actors article
     */
    ActorArticle getActorsArticle(String actorsArticleAsXML);

}
