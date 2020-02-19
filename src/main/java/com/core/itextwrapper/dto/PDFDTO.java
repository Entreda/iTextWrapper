package com.core.itextwrapper.dto;

import java.util.List;
import java.util.Map;

public class PDFDTO {

  private String report;
  private List<String> header;
  private List<Map<String, String>> data;
  private Integer days;
  private String filters;

  public String getReport() {
    return report;
  }

  public void setReport(String report) {
    this.report = report;
  }

  public List<String> getHeader() {
    return header;
  }

  public void setHeader(List<String> header) {
    this.header = header;
  }

  public List<Map<String, String>> getData() {
    return data;
  }

  public void setData(List<Map<String, String>> data) {
    this.data = data;
  }

  public Integer getDays() {
    return days;
  }

  public void setDays(Integer days) {
    this.days = days;
  }

  public String getFilters() {
    return filters;
  }

  public void setFilters(String filters) {
    this.filters = filters;
  }

}
