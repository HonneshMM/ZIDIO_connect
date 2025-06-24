package com.zidioStudentService.dto;

public class ApplicationRequest {
    public Long jobId;
    public String resumeUrl;
    private String status;
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}
	public String getResumeUrl() {
		return resumeUrl;
	}
	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    
    
}
