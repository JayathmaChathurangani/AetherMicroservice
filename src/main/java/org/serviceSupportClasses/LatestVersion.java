package org.serviceSupportClasses;

import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.Artifact;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.util.Booter;
import org.eclipse.aether.resolution.VersionRangeRequest;
import org.eclipse.aether.resolution.VersionRangeResult;
import org.eclipse.aether.version.Version;

public class LatestVersion {

    //public static void main( String[] args ) throws Exception{
    public static String getVersion( String groupID, String artifactID){
        System.out.println( "------------------------------------------------------------" );
        System.out.println( LatestVersion.class.getSimpleName() );

        RepositorySystem system = Booter.newRepositorySystem();

        RepositorySystemSession session = Booter.newRepositorySystemSession( system );

        String artifactRef = groupID+":"+artifactID+":[0,)";

        Artifact artifact = new DefaultArtifact(artifactRef);

        VersionRangeRequest rangeRequest = new VersionRangeRequest();
        rangeRequest.setArtifact( artifact );
        rangeRequest.setRepositories( Booter.newRepositories( system, session ) );

        String ans;
        ans = "{\"ErrorMsg\":\""+"Not Found\"}";
        try{
            VersionRangeResult rangeResult = system.resolveVersionRange( session, rangeRequest );

            Version newestVersion = rangeResult.getHighestVersion();


            System.out.println( "Newest version " + newestVersion + " from repository "
                    + rangeResult.getRepository( newestVersion ) );
            ans   =   "{\"Artifact\":\""+artifactID+"\",";
            if(newestVersion!=null){
                ans = ans.concat("\"NewestVersion\":\""+newestVersion.toString()+"\"}");
            }else{
                ans = ans.concat("\"ErrorMsg\":\"NotFound\"}");
            }

        }catch(Exception e){
            //System.out.println("Error is "+ e.getMessage());
            ans = "{\"ErrorMsg\":\""+e.getMessage()+"\"}";
            return ans;
        }

        return ans;
    }

}
