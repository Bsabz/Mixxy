package www.purple.mixxy.conf;

import ninja.appengine.AppEngineModule;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import www.purple.mixxy.helpers.ApiKeys;
import www.purple.mixxy.helpers.FacebookAuthHelper;
import www.purple.mixxy.helpers.FacebookGraph;
import www.purple.mixxy.helpers.GoogleAuthHelper;

@Singleton
public class Module extends AbstractModule {
    
    @Override
    protected void configure() {
        bind(ApiKeys.class);
        bind(StartupActions.class);
        bind(GoogleAuthHelper.class);
        bind(FacebookAuthHelper.class);
        bind(FacebookGraph.class);
        bind(Objectify.class).toProvider(ObjectifyProvider.class);
        install(new AppEngineModule());        
        
    }

}
