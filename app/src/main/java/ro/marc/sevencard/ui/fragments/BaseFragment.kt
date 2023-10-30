package ro.marc.sevencard.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

open class BaseFragment<A: Activity, B: ViewBinding> : Fragment() {

    protected open val viewBindingGenericPosition = 1

    protected lateinit var activity: A

    protected var _binding: B? = null
    protected val binding: B
        get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        activity = requireActivity() as A
        _binding = createBindingInstance(inflater, container)

        return binding.root
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun createBindingInstance(inflater: LayoutInflater, container: ViewGroup?): B {
        val vbType = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[viewBindingGenericPosition]
        val vbClass = vbType as Class<B>

        val method = vbClass.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)

        // Call B.inflate(inflater, container, false) Java static method
        return method.invoke(null, inflater, container, false) as B
    }

}
