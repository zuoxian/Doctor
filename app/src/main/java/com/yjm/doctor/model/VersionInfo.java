package com.yjm.doctor.model;

import java.io.Serializable;

/**
 * Created by zx on 2018/2/2.
 */

public class VersionInfo implements Serializable {
    private static final long serialVersionUID = -3669306429093340674L;

    private String isForce;
    private String filePath;
    private String updateLog;
    private boolean updateMark;
    private String version;
    private String downloadPath;

    public String getDownloadPath() {
        return downloadPath;
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public String getIsForce() {
        return isForce;
    }

    public void setIsForce(String isForce) {
        this.isForce = isForce;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    public boolean isUpdateMark() {
        return updateMark;
    }

    public void setUpdateMark(boolean updateMark) {
        this.updateMark = updateMark;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
