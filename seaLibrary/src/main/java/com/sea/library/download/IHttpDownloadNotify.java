package com.sea.library.download;

public interface IHttpDownloadNotify {
	public boolean IsHttpDownloadInterested(int nTaskType);
	public void httpDownloadTaskBegin(int nTaskType, long nTaskId);
	public void httpDownloadEnd(int nTaskType, long nTaskId);
	public void HttpDownloadEventNotify(int nTaskType, int nSubType, Object obj);
	public void httpDownloadError(int nTaskType, long nTaskId);
}
