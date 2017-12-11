package JSONFormats;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DependencyTreeJSONFormat {
    public  String groupID;
    public  String artifactID;
    public String version;


    public DependencyTreeJSONFormat(){}

    public DependencyTreeJSONFormat(String groupID, String artifactId, String version){
        this.groupID = groupID;
        this.artifactID = artifactId;
        this.version = version;
    }

}
