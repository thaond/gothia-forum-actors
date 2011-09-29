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
 * @author Hans Gyllensten, vgrid=hangy2
 *
 * This class takes an XML representing an ActorArticle and converts it to an ActorArticle objekt
 */
public interface ActorsArticleConverterService {
	
	public abstract ActorArticle getActorsArticle(String actorsArticleAsXML);

}
/**
 * Typichal input XML
 * ******************
<?xml version="1.0"?>
<root>
<dynamic-element instance-id="10447" name="subtitle" type="text" index-type="">
	<dynamic-content><![CDATA[ingress]]></dynamic-content>
</dynamic-element>

<dynamic-element instance-id="10448" name="description" type="text_area" index-type="">
	<dynamic-content><![CDATA[utförlig]]></dynamic-content>
</dynamic-element>

<dynamic-element instance-id="10449" name="linkout" type="text" index-type="">
	<dynamic-content><![CDATA[homepage]]></dynamic-content>
</dynamic-element>

<dynamic-element instance-id="10450" name="contact" type="text" index-type="">

	<dynamic-element instance-id="10451" name="cnt-name" type="text" index-type="">
		<dynamic-content><![CDATA[titel]]></dynamic-content>
	</dynamic-element>

	<dynamic-element instance-id="10452" name="cnt-adress" type="text" index-type="">
		<dynamic-content><![CDATA[besöksadress]]></dynamic-content>
	</dynamic-element>

	<dynamic-element instance-id="10453" name="cnt-phone" type="text" index-type="">
		<dynamic-content><![CDATA[tel]]></dynamic-content>
	</dynamic-element>

	<dynamic-element instance-id="10454" name="cnt-mobile" type="text" index-type="">
		<dynamic-content><![CDATA[mobil]]></dynamic-content>
	</dynamic-element>

	<dynamic-element instance-id="10455" name="cnt-fax" type="text" index-type="">
		<dynamic-content><![CDATA[fax]]></dynamic-content>
	</dynamic-element>

	<dynamic-element instance-id="10456" name="cnt-email" type="text" index-type="">
		<dynamic-content><![CDATA[email]]></dynamic-content>
	</dynamic-element>

	<dynamic-element instance-id="10457" name="cnt-web" type="text" index-type="">
		<dynamic-content><![CDATA[homepage]]></dynamic-content>
	</dynamic-element>

	<dynamic-element instance-id="10458" name="cnt-pic" type="text" index-type="">
		<dynamic-content><![CDATA[HAR DENNA TEXT SÅ LÄNGE]]></dynamic-content>
	</dynamic-element><dynamic-content><![CDATA[kontaktperson]]></dynamic-content>

</dynamic-element>

<dynamic-element instance-id="10459" name="logo" type="text" index-type="">
	<dynamic-content><![CDATA[]]></dynamic-content>
</dynamic-element>

</root>
 ***/