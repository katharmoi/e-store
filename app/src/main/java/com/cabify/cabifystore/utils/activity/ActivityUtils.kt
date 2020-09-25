package com.cabify.cabifystore.utils.activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface ActivityUtils {

    fun addFragmentToActivity(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int)

    fun addFragmentWithTagToActivity(fragmentManager: FragmentManager, fragment: Fragment, containerId: Int, tag: String)

    fun addFragmentWithTagToActivity(fragmentManager: FragmentManager, fragment: Fragment, tag: String, containerId: Int, backStackName: String)

    fun setFragmentWithTagToActivity(fragmentManager: FragmentManager, fragment: Fragment, tag: String, containerId: Int, backStackName: String)

    fun setFragmentWithTagToActivity(fragmentManager: FragmentManager, fragment: Fragment, tag: String, containerId: Int, backStackName: String, animate: Boolean)

    fun removeFragmentFromActivity(fragmentManager: FragmentManager, fragment: Fragment)
}