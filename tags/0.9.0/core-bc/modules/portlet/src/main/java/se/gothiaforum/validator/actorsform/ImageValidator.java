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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author simgo3
 * 
 */
public class ImageValidator {
    private static final Log log = LogFactory.getLog(ImageValidator.class);

    public boolean isValidate(MultipartFile multipartFile) {

        final int IMAGE_MIN_SIZE = 0;
        final int IMAGE_MAX_SIZE = 5 * 1024 * 1024;

        //image Validator size, file suffix, mime.
        //TODO send appropriate validation message to the user

        boolean valid = true;

        if (multipartFile.getSize() > IMAGE_MIN_SIZE && multipartFile.getSize() < IMAGE_MAX_SIZE) {
            String originalFileName = multipartFile.getOriginalFilename();

            String contentType = multipartFile.getContentType();

            System.out.println("validation - contentType = " + contentType);

            if (!"image/jpeg".equals(contentType) && !"image/png".equals(contentType)
                    && !"image/x-png".equals(contentType) && !"image/pjpeg".equals(contentType)
                    && !"image/gif".equals(contentType)) {

                valid = false;
                System.out.println("validation - wrong type of content type - contentType = " + contentType);
            }

            int fileExtensionIndex = originalFileName.lastIndexOf(".");
            String fileExtension = originalFileName.substring((fileExtensionIndex + 1), originalFileName.length());

            if (!fileExtension.equals("png") && !fileExtension.equals("jpg") && !fileExtension.equals("gif")) {
                valid = false;
                System.out.println("validation - wrong type of fileExtension - fileExtension is = "
                        + fileExtension);
            }

        } else {
            valid = false;
            System.out.println("validation - wrong size - size is = " + multipartFile.getSize()
                    + ", size must be greater than " + IMAGE_MIN_SIZE + " and less than " + IMAGE_MAX_SIZE
                    + " Bytes.");

        }

        return valid;

    }
}
