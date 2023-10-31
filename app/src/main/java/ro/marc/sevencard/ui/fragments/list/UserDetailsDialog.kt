package ro.marc.sevencard.ui.fragments.list

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.parameter.parametersOf
import ro.marc.sevencard.data.User
import ro.marc.sevencard.databinding.DialogUserBinding
import ro.marc.sevencard.ui.MainViewModel

class UserDetailsDialog: DialogFragment() {

    companion object {
        fun newInstance(user: User) = UserDetailsDialog().apply {
            this.arguments = Bundle().apply {
                putSerializable("user", user)
            }
        }
    }


    private val vm: MainViewModel by sharedViewModel()
    private val dialogVm: UserDialogViewModel by lazy {
        this@UserDetailsDialog.getViewModel {
            parametersOf(
                arguments!!.getSerializable("user") as User,
            )
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = DialogUserBinding.inflate(LayoutInflater.from(context))

        binding.vm = dialogVm
        binding.lifecycleOwner = this

        dialogVm.dismissEvent.observe(this) {
            vm.fetchUsers()
            dismiss()
        }

        return AlertDialog.Builder(context)
            .setView(binding.root)
            .create()
            .also {
                it.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
    }

}
