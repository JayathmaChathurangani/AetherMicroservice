package JSONFormats;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LatestVersionJSONFormat {
    public  String groupID;
    public  String artifactID;

    public LatestVersionJSONFormat(){}

    public LatestVersionJSONFormat(String groupID, String artifactID){
        this.groupID = groupID;
        this.artifactID = artifactID;
    }


}