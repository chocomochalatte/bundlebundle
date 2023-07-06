package com.example.bundlebundle.Firebase

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.util.Log
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks

object DynamicLinkUtils {
    private const val TAG = "DynamicLinkUtils"

    private fun getDeepLink(scheme: String, key: String?, groupId: Int?): Uri {
        return if (key == null) {
            Uri.parse("https://bundlebundle.page.link/group-cart/$scheme")
        } else {
            Uri.parse("https://bundlebundle.page.link/?link=http://localhost:8080/tohome&apn=com.example.bundlebundle&afl=http://localhost:8080/bundlebundle")
        }
    }

    fun onDynamicLinkClick(
        activity: Activity,
        scheme: String,
        key: String? = null,
        groupId: Int? = null,
        onComplete: (Uri?) -> Unit
    ) {
        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(getDeepLink(scheme, key, groupId))
            .setDynamicLinkDomain("bundlebundle.page.link")
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder(activity.packageName)
                    .setMinimumVersion(1)
                    .build()
            )
            .buildShortDynamicLink()

        dynamicLink.addOnCompleteListener(activity) { task ->
            if (task.isSuccessful) {
                val shortLink: Uri? = task.result?.shortLink
                Log.i(TAG, "Short Link: $shortLink")
                onComplete(shortLink)
            } else {
                Log.i(TAG, "Error creating dynamic link: ${task.exception}")
                onComplete(null)
            }
        }
    }
}
