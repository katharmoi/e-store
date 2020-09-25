package com.cabify.cabifystore.utils.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.cabify.domain.model.PerActivity
import javax.inject.Inject

@PerActivity
class ActivityUtilsImpl @Inject constructor() : ActivityUtils {

    override fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int) {
        if (!fragment.isAdded) {
            val transaction = fragmentManager.beginTransaction()
            transaction.add(containerId, fragment)
            transaction.commit()
        }
    }

    override fun addFragmentWithTagToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        containerId: Int,
        tag: String
    ) {
        if (!fragment.isAdded) {
            val transaction = fragmentManager.beginTransaction()
            transaction.add(containerId, fragment, tag)
            transaction.addToBackStack(tag)
            transaction.commit()
        }
    }

    override fun addFragmentWithTagToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String,
        containerId: Int,
        backStackName: String
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.add(containerId, fragment, tag)
        transaction.addToBackStack(backStackName)
        transaction.commit()
    }

    override fun setFragmentWithTagToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String,
        containerId: Int,
        backStackName: String
    ) {
        setFragmentWithTagToActivity(fragmentManager, fragment, tag, containerId, backStackName, true)
    }

    override fun setFragmentWithTagToActivity(
        fragmentManager: FragmentManager,
        fragment: Fragment,
        tag: String,
        containerId: Int,
        backStackName: String,
        animate: Boolean
    ) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(containerId, fragment, tag)
        transaction.addToBackStack(backStackName)
        transaction.commit()
    }

    override fun removeFragmentFromActivity(fragmentManager: FragmentManager, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }

}