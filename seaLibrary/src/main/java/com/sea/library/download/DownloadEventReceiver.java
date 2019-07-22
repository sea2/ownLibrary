package com.sea.library.download;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.ArrayList;

public class DownloadEventReceiver {
    private static DownloadEventReceiver instance = null;
    private static ArrayList<IHttpDownloadNotify> arrHttpNotify = null;

    public static final int NOTIFY_TASK_BEGIN = 1;
    public static final int NOTIFY_TASK_UPDATE = 2;
    public static final int NOTIFY_TASK_END = 3;
    public static final int NOTIFY_TASK_ERRO = 4;

    public DownloadEventReceiver() {
        super();
        arrHttpNotify = new ArrayList<IHttpDownloadNotify>();
    }

    public static DownloadEventReceiver getInstance() {
        if (instance == null) {
            synchronized (DownloadEventReceiver.class) {
                if (instance == null)
                    instance = new DownloadEventReceiver();
            }

        }
        return instance;
    }


    private Handler dataService_Handler_ = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOTIFY_TASK_BEGIN:
                    HandleMsg(msg.what, msg.arg1, msg.obj);
                    break;
                case NOTIFY_TASK_UPDATE:
                    HandleMsg(msg.what, msg.arg1, msg.obj);
                    break;
                case NOTIFY_TASK_END:
                    HandleMsg(msg.what, msg.arg1, msg.obj);
                    break;
                case NOTIFY_TASK_ERRO:
                    HandleMsg(msg.what, msg.arg1, msg.obj);
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected boolean hasListenser() {
        if (arrHttpNotify == null || arrHttpNotify.isEmpty())
            return false;

        return true;
    }

    public void OnTaskBegin(int TaskType, long TaskId) {
        if (hasListenser()) {
            Message msg = new Message();
            msg.what = NOTIFY_TASK_BEGIN;
            msg.arg1 = TaskType;
            msg.obj = TaskId;
            if (dataService_Handler_ != null)
                dataService_Handler_.sendMessage(msg);
        }
    }

    public void OnTaskEnd(int TaskType, long TaskId) {
        if (hasListenser()) {
            Message msg = new Message();
            msg.what = NOTIFY_TASK_END;
            msg.arg1 = TaskType;
            msg.obj = TaskId;
            if (dataService_Handler_ != null)
                dataService_Handler_.sendMessage(msg);
        }
    }

    public void OnTaskERRO(int TaskType, long TaskId) {
        if (hasListenser()) {
            Message msg = new Message();
            msg.what = NOTIFY_TASK_ERRO;
            msg.arg1 = TaskType;
            msg.obj = TaskId;
            if (dataService_Handler_ != null)
                dataService_Handler_.sendMessage(msg);
        }
    }

    public void OnUpdateDownProgress(int TaskType, long TaskId) {
        if (hasListenser()) {
            Message msg = new Message();
            msg.what = NOTIFY_TASK_UPDATE;
            msg.arg1 = TaskType;
            msg.obj = TaskId;
            if (dataService_Handler_ != null)
                dataService_Handler_.sendMessage(msg);
        }
    }

    public void HandleMsg(int NotifyType, int TaskType, Object objType) {
        if (TaskType > 0) {
            for (IHttpDownloadNotify obj : arrHttpNotify) {
                if (obj != null
                        && obj.IsHttpDownloadInterested(TaskType)) {
                    long taskID = 0;
                    if (objType != null) {
                        taskID = Long.valueOf(objType.toString());
                    }
                    if (NotifyType == NOTIFY_TASK_BEGIN)
                        obj.httpDownloadTaskBegin(TaskType, taskID);
                    else if (NotifyType == NOTIFY_TASK_END)
                        obj.httpDownloadEnd(TaskType, taskID);
                    else if (NotifyType == NOTIFY_TASK_ERRO)
                        obj.httpDownloadEnd(TaskType, taskID);
                    else if (NotifyType == NOTIFY_TASK_UPDATE)
                        obj.HttpDownloadEventNotify(TaskType, 0, objType);
                }
            }
        }
    }

    /**注册广播事件
     * @param notify
     */
    public void RegisiterHttpDownloadEventListener(IHttpDownloadNotify notify) {
        if (notify == null) {
            return;
        }

        for (int i = 0; i < arrHttpNotify.size(); i++) {
            if (i >= 0
                    && arrHttpNotify.get(i) != null
                    && arrHttpNotify.get(i).equals(notify)) {

                return;
            }
        }

        arrHttpNotify.add(notify);
    }

    public void UnRegisiterHttpDownloadEventListener(IHttpDownloadNotify notify) {
        if (notify == null) {
            return;
        }

        for (int i = 0; i < arrHttpNotify.size(); i++) {
            if (i >= 0
                    && arrHttpNotify.get(i) != null
                    && arrHttpNotify.get(i).equals(notify)) {
                arrHttpNotify.remove(i);
                i--;
            }
        }
    }

    public void ClearAllHttpDownloadEventListener() {
        arrHttpNotify.clear();
    }
}
