package com.dhimas.githubsuserfinder.service

import android.content.Intent
import android.widget.RemoteViewsService
import com.dhimas.githubsuserfinder.widget.StackRemoteViewsFactory

class StackWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(p0: Intent?): RemoteViewsFactory =
        StackRemoteViewsFactory(
            applicationContext
        )
}