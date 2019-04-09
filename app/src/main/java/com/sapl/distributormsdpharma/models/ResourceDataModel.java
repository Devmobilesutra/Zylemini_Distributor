package com.sapl.distributormsdpharma.models;

public class ResourceDataModel {

    public String id;
    public String ResourceName;
    public String ParentResourceID;
    public String URL;
    public String Descreption;
    public String FileName;
    public String SequenceNo;
    public String IsDownloadable;
    public String ResourceType;
    public String CreatedDate;
    public String LastUpdatedDate;


    public ResourceDataModel(String id, String resourceName, String parentResourceID, String URL, String descreption,
                             String fileName, String sequenceNo, String isDownloadable, String resourceType,
                             String createdDate, String lastUpdatedDate) {
        this.id = id;
        ResourceName = resourceName;
        ParentResourceID = parentResourceID;
        this.URL = URL;
        Descreption = descreption;
        FileName = fileName;
        SequenceNo = sequenceNo;
        IsDownloadable = isDownloadable;
        ResourceType = resourceType;
        CreatedDate = createdDate;
        LastUpdatedDate = lastUpdatedDate;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceName() {
        return ResourceName;
    }

    public void setResourceName(String resourceName) {
        ResourceName = resourceName;
    }

    public String getParentResourceID() {
        return ParentResourceID;
    }

    public void setParentResourceID(String parentResourceID) {
        ParentResourceID = parentResourceID;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getDescreption() {
        return Descreption;
    }

    public void setDescreption(String descreption) {
        Descreption = descreption;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public String getSequenceNo() {
        return SequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        SequenceNo = sequenceNo;
    }

    public String getIsDownloadable() {
        return IsDownloadable;
    }

    public void setIsDownloadable(String isDownloadable) {
        IsDownloadable = isDownloadable;
    }

    public String getResourceType() {
        return ResourceType;
    }

    public void setResourceType(String resourceType) {
        ResourceType = resourceType;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getLastUpdatedDate() {
        return LastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        LastUpdatedDate = lastUpdatedDate;
    }


}
