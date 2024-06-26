/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package www.purple.mixxy.controllers;

import static org.junit.Assert.assertTrue;
import ninja.NinjaFluentLeniumTest;

import org.junit.Before;
import org.junit.Test;

public class ApplicationControllerFluentLeniumTest extends NinjaFluentLeniumTest {
    
    //@Before
    public void setup() {
        
        goTo(getServerAddress() + "setup");
        goTo(getServerAddress() + "/");
        
    }

    //@Test
    public void testThatHomepageWorks() {
        //assertTrue(title().contains("Home page"));
    }
    
    //@Test
    public void testNavigateToLogin() {
        //click("#login");
        
        //assertTrue(url().contains("login"));
    }

}
