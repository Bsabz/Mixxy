/**
 * Copyright (C) 2012 the original author or authors.
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

import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.Param;
import www.purple.mixxy.conf.ObjectifyProvider;
import www.purple.mixxy.dao.UserDao;
import www.purple.mixxy.filters.UrlNormalizingFilter;
import www.purple.mixxy.helpers.GoogleAuthHelper;
import www.purple.mixxy.helpers.GoogleAuthResponse;
import www.purple.mixxy.models.User;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

@Singleton
@FilterWith({ AppEngineFilter.class, UrlNormalizingFilter.class })
public class LoginLogoutController {
    
    @Inject
    private UserDao userDao;

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
        context.getSession().clear();
        context.getFlashScope().success("login.logoutSuccessful");

        return Results.redirect("/");

    }

	///////////////////////////////////////////////////////////////////////////
	// Login
	///////////////////////////////////////////////////////////////////////////
    public Result login() {
    	GoogleAuthHelper helper = new GoogleAuthHelper();
    	return Results.redirect(helper.buildLoginUrl());
    }
    
    public Result validate(
    		@Param("state") String state,
    		@Param("code") String code,
    		Context context) {
    	
    	GoogleAuthHelper helper = new GoogleAuthHelper();
    	String data = "empty";
    	String provider = state.substring(0, state.indexOf(';'));
    	
    	try {
			data = helper.getUserInfoJson(code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	ObjectMapper mapper = new ObjectMapper();
		try {

			// Convert JSON string to Object
			GoogleAuthResponse userdata = mapper.readValue(data, GoogleAuthResponse.class);
			
			// At this point we can login or signup user
			String email = userdata.getEmail();
			String username = email.substring(0, email.indexOf('@'));
			boolean areCredentialsValid = userDao.isUserValid(username);
			
			if(areCredentialsValid) {
				context.getSession().put("username", userdata.getEmail());
				context.getFlashScope().success("login.loginSuccessful");
			} else {
				// Create new user
				ObjectifyProvider objectifyProvider = new ObjectifyProvider();
		        Objectify ofy = objectifyProvider.get();
		        
		        // Create a new user and save it
		        User user = new User(userdata.getEmail(), userdata.getGiven_name(), userdata.getFamily_name(), userdata.getEmail(),
		        		userdata.getPicture(), userdata.getLocale(), provider, userdata.getId());
		        ofy.save().entity(user).now();
		        
		        context.getSession().put("username", userdata.getEmail());
				context.getFlashScope().success("login.loginSuccessful");
				
		        // Redirect to profile
				return Results.redirect("/privacy");
			}

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return Results.redirect("/");
    }
}
