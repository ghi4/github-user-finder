package com.dhimas.githubsuserfinder

import android.content.Context
import android.graphics.Bitmap
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.dhimas.githubsuserfinder.database.FavoriteDatabase
import com.squareup.picasso.Picasso

class StackRemoteViewsFactory(val context: Context): RemoteViewsService.RemoteViewsFactory {
    private val listFavoriteUser = ArrayList<Bitmap>()

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun onDataSetChanged() {
        val listUser = FavoriteDatabase.getInstance(context).userDao().getAll()
        val targetWidth = 480
        val targetHeight = 480

        listUser.forEach {
            listFavoriteUser.add(
                Picasso.get()
                    .load(it.avatarUrl)
                    .resize(targetWidth, targetHeight)
                    .get()
            )
        }
    }

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(context.packageName, R.layout.item_widget_favorite_user)
        rv.setImageViewBitmap(R.id.imageView, listFavoriteUser[position])

        return rv
    }

    override fun getCount(): Int = listFavoriteUser.size

    override fun getViewTypeCount(): Int = 1

    override fun getLoadingView(): RemoteViews? = null

    override fun hasStableIds(): Boolean = false

    override fun getItemId(p0: Int): Long = 0
}