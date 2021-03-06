package com.dhimas.githubsuserfinder.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.service.StackWidgetService

/**
 * Implementation of App Widget functionality.
 */
class FavoriteStackWidget : AppWidgetProvider() {
    companion object {
        private const val TOAST_ACTION = "com.dhimas.githubsuserfinder.TOAST_ACTION"
        const val APPWIDGET_UPDATE = "android.appwidget.action.APPWIDGET_UPDATE"
        const val EXTRA_ITEM = "com.dhimas.githubsuserfinder.EXTRA_ITEM"

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(
                context.packageName,
                R.layout.favorite_stack_widget
            )
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(
                R.id.stack_view,
                R.id.empty_view
            )

            val toastIntent = Intent(context, FavoriteStackWidget::class.java)
            toastIntent.action =
                TOAST_ACTION
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()
            val toastPendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                toastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(
                context,
                appWidgetManager,
                appWidgetId
            )
        }

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == TOAST_ACTION) {
                val bundle = intent.extras

                Toast.makeText(context, "Username: ${bundle?.get(EXTRA_ITEM)}", Toast.LENGTH_SHORT)
                    .show()
            }

            if (intent.action == APPWIDGET_UPDATE) {
                val ids = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS)
                if (ids != null) {
                    onUpdate(context, AppWidgetManager.getInstance(context), ids)
                }
            }
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}