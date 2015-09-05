package org.cyborgsociety.panicbutton.app.model;

/**
 * Created by richy on 05.09.15.
 */
public class AppItem {



    private String id;
    private String appDescription;


    private String dataDir;
    private String appPath;
    private String appPackageName;
    private boolean deleteCache;
    private boolean deleteData;

    public AppItem(String id, String appDescription, boolean deleteCache, boolean deleteData, String dataDir ) {
        this.id = id;
        this.appDescription = appDescription;
        this.deleteCache = deleteCache;
        this.deleteData = deleteData;
        this.dataDir = dataDir;
    }

    @Override
    public String toString() {
        return appDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppDescription() {
        return appDescription;
    }

    public void setAppDescription(String appDescription) {
        this.appDescription = appDescription;
    }

    public boolean isDeleteCache() {
        return deleteCache;
    }

    public void setDeleteCache(boolean deleteCache) {
        this.deleteCache = deleteCache;
    }

    public boolean isDeleteData() {
        return deleteData;
    }

    public void setDeleteData(boolean deleteData) {
        this.deleteData = deleteData;
    }

    public String getAppPath() {
        return appPath;
    }

    public void setAppPath(String appPath) {
        this.appPath = appPath;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }


    public String getDataDir() {
        return dataDir;
    }

    public void setDataDir(String dataDir) {
        this.dataDir = dataDir;
    }


}
