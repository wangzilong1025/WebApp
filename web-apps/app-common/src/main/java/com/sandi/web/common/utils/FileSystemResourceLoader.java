/**
 * $Id: FileSystemResourceLoader.java,v 1.0 2016/3/23 12:48 Qiaoy Exp $
 * <p/>
 * Copyright 2016 Asiainfo Technologies(China),Inc. All rights reserved.
 */
package com.sandi.web.common.utils;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * @author Qiaoy
 * @version $Id: FileSystemResourceLoader.java,v 1.1 2016/3/23 12:48 Qiaoy Exp $
 *          Created on 2016/3/23 12:48
 */
public class FileSystemResourceLoader extends DefaultResourceLoader {
    @Override
    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);
    }
}
