package ro.marc.sevencard

import android.app.Application
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ro.marc.sevencard.ui.MainViewModel

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                module {
                    viewModel {
                        MainViewModel()
                    }
                }
            )
        }
    }

}
