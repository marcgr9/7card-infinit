package ro.marc.sevencard.ui.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.sevencard.databinding.FragmentListBinding
import ro.marc.sevencard.ui.MainActivity
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.BaseFragment
import ro.marc.sevencard.ui.fragments.home.HomeFragment

class UsersListFragment: BaseFragment<MainActivity, FragmentListBinding>() {

    private val vm: MainViewModel by sharedViewModel()
    private val parent: HomeFragment by lazy {
        parentFragment!!.parentFragment as HomeFragment
    }

    private val usersAdapter by lazy {
        UsersAdapter(
            onClick = {
                vm.userId.value = it.id.toString()
                this@UsersListFragment.parent.navigateToHome()
            },
            onLongClick = {
                // todo
            },
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding.rv.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = usersAdapter
        }

        vm.usersList.observe(viewLifecycleOwner) {
            usersAdapter.setUsers(it)
        }

        vm.fetchUsers()

        return binding.root
    }

}