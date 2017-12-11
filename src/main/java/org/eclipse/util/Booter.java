package org.eclipse.util;

import org.apache.maven.repository.internal.MavenRepositorySystemUtils;
import org.eclipse.aether.DefaultRepositorySystemSession;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.repository.LocalRepository;
import org.eclipse.aether.repository.RemoteRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.io.InputStream;

import org.eclipse.configPOJOS.RemoteRepo;
import org.yaml.snakeyaml.Yaml;


/**
 * A helper to boot the repository system and a repository system session.
 */
public class Booter
{

    public static RepositorySystem newRepositorySystem()
    {
        return org.eclipse.manual.ManualRepositorySystemFactory.newRepositorySystem();
    }

    public static DefaultRepositorySystemSession newRepositorySystemSession(RepositorySystem system )
    {
        DefaultRepositorySystemSession session = MavenRepositorySystemUtils.newSession();

        LocalRepository localRepo = new LocalRepository( "target/local-repo" );
        session.setLocalRepositoryManager( system.newLocalRepositoryManager( session, localRepo ) );

        session.setTransferListener( new ConsoleTransferListener() );
        session.setRepositoryListener( new ConsoleRepositoryListener() );

        // uncomment to generate dirty trees
        // session.setDependencyGraphTransformer( null );

        return session;
    }

    public static List<RemoteRepository> newRepositories(RepositorySystem system, RepositorySystemSession session)
    {
        return new ArrayList<RemoteRepository>( Arrays.asList( newCentralRepository() ) );
    }

    private static RemoteRepository newCentralRepository()
    {
       //return new RemoteRepository.Builder( "central", "default", "http://dist.wso2.org/maven2/" ).build();
        RemoteRepo config = null;

        Yaml yaml = new Yaml();
        try(InputStream in = ClassLoader.getSystemResourceAsStream("repoConfiguration.yaml")) {
            config = yaml.loadAs(in, RemoteRepo.class);
        } catch(Exception ex) {
            ex.printStackTrace();
        }

        String url =  config.getremoteRepository().get("url");
        String id =  config.getremoteRepository().get("id");
        String type =  config.getremoteRepository().get("type");

        return new RemoteRepository.Builder( id, type, url ).build();
    }

}