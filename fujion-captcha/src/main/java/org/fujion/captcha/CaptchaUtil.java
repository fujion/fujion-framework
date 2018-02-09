/*
 * #%L
 * fujion
 * %%
 * Copyright (C) 2008 - 2017 Regenstrief Institute, Inc.
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * #L%
 */
package org.fujion.captcha;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.fujion.servlet.DynamicResourceRegistry;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestTemplate;

public class CaptchaUtil {

    private static final Log log = LogFactory.getLog(CaptchaUtil.class);

    private static CaptchaUtil instance = new CaptchaUtil();
    
    public static CaptchaUtil getInstance() {
        return instance;
    }

    private CaptchaUtil() {
    }

    public void setCaptchaUrl(String captchaUrl) {
        try {
            Resource resource = new RestTemplate().getForObject(captchaUrl, Resource.class);
            DynamicResourceRegistry.getInstance().registerGlobal("reCAPTCHA.js", resource);
        } catch (Exception e) {
            log.error("Error loading Google reCAPTCHA API.", e);
        }
    }

}
