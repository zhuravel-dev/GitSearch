package com.example.gitsearch.ui.extensions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnDetach
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

// usage
// private val binding by viewBinding(MainActivityBinding::inflate)
internal inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

// usage
// private val binding by viewBinding(FirstFragmentBinding::bind)
internal fun <T : ViewBinding?> Fragment.viewBinding(
    viewBindingFactory: (View) -> T
) = FragmentViewBindingDelegate(this, viewBindingFactory)

internal class FragmentViewBindingDelegate<T : ViewBinding?>(
    val fragment: Fragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T?> {
    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                    viewLifecycleOwner.lifecycle.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            binding = null
                        }
                    })
                }
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T? {
        if(thisRef.view == null) return null
        val binding = binding
        if (binding != null) {
            return binding
        }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
        }

        return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
    }
}

// usage
// private val binding: dLayoutSampleViewBining by viewBinding(LayoutSampleViewBinding::inflate)
internal fun <T : ViewBinding> View.viewBinding(
    viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> T
) = CustomViewBindingDelegate(this, viewBindingFactory)

internal fun <T : ViewBinding> ViewGroup.viewBindingVH(
    viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    viewBindingFactory(LayoutInflater.from(this.context), this, false)
}

internal class CustomViewBindingDelegate<out T : ViewBinding>(
    val view: View,
    val viewBindingFactory: (LayoutInflater, ViewGroup, Boolean) -> T
) : ReadOnlyProperty<View, T> {

    private var binding: T? = null

    override fun getValue(thisRef: View, property: KProperty<*>): T {
        val binding = binding
        if (binding != null) {
            return binding
        }

        thisRef.doOnDetach {
            this@CustomViewBindingDelegate.binding = null
        }
        return viewBindingFactory(
            LayoutInflater.from(thisRef.context),
            thisRef as ViewGroup,
            true
        ).also { this@CustomViewBindingDelegate.binding = it }
    }
}
