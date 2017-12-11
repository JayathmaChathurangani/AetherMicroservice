/*
 * Copyright (c) 2016, WSO2 Inc. (http://wso2.com) All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.service;

import JSONFormats.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.serviceSupportClasses.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * This is the Microservice resource class.
 * See <a href="https://github.com/wso2/msf4j#getting-started">https://github.com/wso2/msf4j#getting-started</a>
 * for the usage of annotations.
 *
 * @since 1.0-SNAPSHOT
 */
@Path("/wso2aetherService")
public class Service_DependencyManagerOfwso2 {

    @GET
    @Path("/")
    public String get() {
        // For the purpose of checking
        return "Hello, this is wso2 dependency manager";
    }

    @POST
    @Path("/getLatest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLatestVersion(LatestVersionJSONFormat jsonObj) {

        long startTime = System.currentTimeMillis();
        String latestVersion = LatestVersion.getVersion(jsonObj.groupID,jsonObj.artifactID);

        JsonObject reply;
        if(latestVersion.contains("Error")){
            return Response.status(Response.Status.NOT_FOUND).entity("Not Found").build();
        }else{
            JsonParser parser = new JsonParser();
            reply = parser.parse(latestVersion).getAsJsonObject();
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken:"+(endTime-startTime));
        return Response.ok(reply,MediaType.APPLICATION_JSON).build();
    }


    @POST
    @Path("/getDHeirarchy")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDHeirarchy(DependencyTreeJSONFormat jsonObj) {
        long startTime = System.currentTimeMillis();
        StringBuilder JsonStringBuilderTree = DependencyHeirarchy.getDependencyHeirarchy(jsonObj.groupID,jsonObj.artifactID,jsonObj.version);
        String JsonStringTree= JsonStringBuilderTree.toString();

        JsonObject reply;

        if(JsonStringTree.contains("Error")){
            return Response.status(Response.Status.NOT_FOUND).entity("Not Found").build();
        }else{
            JsonParser parser = new JsonParser();
            reply = parser.parse(JsonStringBuilderTree.deleteCharAt(JsonStringBuilderTree.length()-1).toString()).getAsJsonObject();

        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken:"+(endTime-startTime));
        return Response.ok(reply,MediaType.APPLICATION_JSON).build();
    }
}

