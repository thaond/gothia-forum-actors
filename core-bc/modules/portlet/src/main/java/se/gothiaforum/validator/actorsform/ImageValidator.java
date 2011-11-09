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

package se.gothiaforum.validator.actorsform;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * This is a validation class for images.
 * 
 * @author simgo3
 */
public class ImageValidator {
    private static final Log LOG = LogFactory.getLog(ImageValidator.class);
    private static final int IMAGE_MIN_SIZE = 0;
    private static final int IMAGE_MAX_SIZE = 5 * 1024 * 1024;

    /**
     * Validate if the image have valid size, file suffix and mime.
     * 
     * @param multipartFile
     *            the multipart file
     * @param errors
     *            the errors
     */
    public void isValidate(MultipartFile multipartFile, List<String> errors) {

        if (multipartFile.getSize() > IMAGE_MIN_SIZE && multipartFile.getSize() < IMAGE_MAX_SIZE) {
            String originalFileName = multipartFile.getOriginalFilename();

            String contentType = multipartFile.getContentType();

            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)
                    && !"image/x-png".equals(contentType) && !"image/pjpeg".equals(contentType)
                    && !"image/gif".equals(contentType)) {
                errors.add("wrong-type-of-content-type");
            }

            int fileExtensionIndex = originalFileName.lastIndexOf(".");
            String fileExtension = originalFileName.substring((fileExtensionIndex + 1), originalFileName.length());

            if (!fileExtension.equals("png") && !fileExtension.equals("jpg") && !fileExtension.equals("gif")) {
                errors.add("wrong-type-of-file-extension");
            }

        }
    }
}
