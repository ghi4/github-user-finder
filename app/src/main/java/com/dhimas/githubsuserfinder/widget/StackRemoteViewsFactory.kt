package com.dhimas.githubsuserfinder.widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.dhimas.githubsuserfinder.R
import com.dhimas.githubsuserfinder.database.FavoriteDatabase
import com.dhimas.githubsuserfinder.widget.FavoriteStackWidget.Companion.EXTRA_ITEM
import com.squareup.picasso.Picasso

class StackRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {
    private val listAvatarUser = ArrayList<Bitmap>()
    private val listUsername = ArrayList<String>()

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun onDataSetChanged() {
        val listUser = FavoriteDatabase.getInstance(context).userDao().getAll()
        val targetWidth = 480
        val targetHeight = 480

        listAvatarUser.clear()
        listUsername.clear()

        listUser.forEach {
            //Load bitmap photo
            listAvatarUser.add(
                Picasso.get()
                    .load(it.avatarUrl)
                    .resize(targetWidth, targetHeight)
                    .get()
            )

            //Load string for toast
            listUsername.add(it.username.toString())
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(
            context.packageName,
            R.layout.item_widget_favorite_user
        )

        if (listAvatarUser.size > 0) {
            rv.setImageViewBitmap(R.id.iv_banner, listAvatarUser[position])

            val extras = bundleOf(EXTRA_ITEM to listUsername[position])
            val intent = Intent()
            intent.putExtras(extras)

            rv.setOnClickFillInIntent(R.id.iv_banner, intent)
        }

        return rv
    }

    override fun getCount(): Int = listAvatarUser.size

    override fun getViewTypeCount(): Int = 1

    override fun getLoadingView(): RemoteViews? = null

    override fun hasStableIds(): Boolean = false

    override fun getItemId(p0: Int): Long = 0
}