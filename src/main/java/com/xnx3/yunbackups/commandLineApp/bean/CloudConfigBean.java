package com.xnx3.yunbackups.commandLineApp.bean;

/**
 * 云端配置
 * @author 管雷鸣
 *
 */
public class CloudConfigBean {
	private String accessKeyId;
	private String secretAccessKey;
	private String bucketName;
	private String endpoint;	//格式如：  obs.cn-north-1.myhuaweicloud.com
	
	public String getAccessKeyId() {
		return accessKeyId;
	}
	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}
	public String getSecretAccessKey() {
		return secretAccessKey;
	}
	public void setSecretAccessKey(String secretAccessKey) {
		this.secretAccessKey = secretAccessKey;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	@Override
	public String toString() {
		return "CloudConfigBean [accessKeyId=" + accessKeyId + ", secretAccessKey=" + secretAccessKey + ", bucketName="
				+ bucketName + ", endpoint=" + endpoint + "]";
	}
	
	
}
