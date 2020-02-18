package com.core.itextwrapper.dto;

import java.io.File;

public class FileDTO {

  public FileDTO() {}


  private String fileName;
  private File file;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }
}
