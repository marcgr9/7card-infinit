package ro.marc.sevencard

import android.app.Application
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ro.marc.sevencard.generator.QrDataGenerator
import ro.marc.sevencard.generator.impl.SevenCardQrDataGeneratorImpl
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.qr.QRViewModel

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                module {
                    single<QrDataGenerator> {
                        SevenCardQrDataGeneratorImpl()
                    }
                },
                module {
                    viewModel {
                        MainViewModel()
                    }
                    viewModel {
                        QRViewModel(
                            get(),
                            it.get<Long>()
                        )
                    }
                },
            )
        }
    }

}
