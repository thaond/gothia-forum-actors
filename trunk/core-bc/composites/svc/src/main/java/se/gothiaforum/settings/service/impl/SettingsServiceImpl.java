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

package se.gothiaforum.settings.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import se.gothiaforum.settings.service.SettingsService;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalService;
import com.liferay.portlet.expando.service.ExpandoTableLocalService;
import com.liferay.portlet.expando.service.ExpandoValueLocalService;

public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private ExpandoColumnLocalService expandoColumnService;

    @Autowired
    private ExpandoTableLocalService expandoTableService;

    @Autowired
    private ExpandoValueLocalService expandoValueService;

    @Override
    public void setSetting(String data, String columnName, long companyId, long groupId) {
        try {
            expandoValueService.addValue(companyId, GROUP_CLASSNAME, ExpandoTableConstants.DEFAULT_TABLE_NAME,
                    columnName, groupId, data);
        } catch (PortalException e) {
            if (e instanceof com.liferay.portlet.expando.NoSuchTableException) { // If table don't exists we try to
                                                                                 // create it.
                try {
                    expandoTableService.addDefaultTable(companyId, GROUP_CLASSNAME);
                    setSetting(data, columnName, companyId, groupId);
                } catch (PortalException e1) {
                    e1.printStackTrace();
                } catch (SystemException e1) {
                    e1.printStackTrace();
                }
            } else if (e instanceof com.liferay.portlet.expando.NoSuchColumnException) { // If column don't exists
                                                                                         // we try to create it.
                try {
                    long tableId = expandoTableService.getDefaultTable(companyId, GROUP_CLASSNAME).getTableId();
                    expandoColumnService.addColumn(tableId, columnName, ExpandoColumnConstants.STRING);
                    setSetting(data, columnName, companyId, groupId);
                } catch (PortalException e2) {
                    e2.printStackTrace();
                } catch (SystemException e2) {
                    e2.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getSetting(String columnName, long companyId, long groupId) {

        String value = "0";
        try {

            value = expandoValueService.getData(companyId, GROUP_CLASSNAME,
                    ExpandoTableConstants.DEFAULT_TABLE_NAME, columnName, groupId, "0");
        } catch (PortalException e) {
            if (e instanceof com.liferay.portlet.expando.NoSuchTableException) { // If table don't exists we try to
                System.out.println("here2"); // create it.
                try {
                    expandoTableService.addDefaultTable(companyId, GROUP_CLASSNAME);
                    getSetting(columnName, companyId, groupId);
                } catch (PortalException e1) {
                    e1.printStackTrace();
                } catch (SystemException e1) {
                    e1.printStackTrace();
                }
            } else if (e instanceof com.liferay.portlet.expando.NoSuchColumnException) { // If column don't exists
                                                                                         // we try to create it.
                try {

                    long tableId = expandoTableService.getDefaultTable(companyId, GROUP_CLASSNAME).getTableId();
                    expandoColumnService.addColumn(tableId, columnName, ExpandoColumnConstants.STRING);
                    getSetting(columnName, companyId, groupId);
                } catch (PortalException e2) {
                    e2.printStackTrace();
                } catch (SystemException e2) {
                    e2.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        } catch (SystemException e) {
            e.printStackTrace();
        }

        return String.valueOf(value);
    }

    private static final String GROUP_CLASSNAME = Group.class.getName();

}
