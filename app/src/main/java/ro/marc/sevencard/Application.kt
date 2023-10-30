package ro.marc.sevencard

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ro.marc.sevencard.data.local.UserDatabase
import ro.marc.sevencard.data.repo.UsersRepo
import ro.marc.sevencard.data.repo.impl.UsersRepoImpl
import ro.marc.sevencard.generator.QrDataGenerator
import ro.marc.sevencard.generator.impl.SevenCardQrDataGeneratorImpl
import ro.marc.sevencard.ui.MainViewModel
import ro.marc.sevencard.ui.fragments.decrypt.DecryptViewModel
import ro.marc.sevencard.ui.fragments.qr.QRViewModel

class Application: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(
                module {
                    fun provideDatabase(application: Application): UserDatabase {
                        return Room
                            .databaseBuilder(application, UserDatabase::class.java, "users")
                            .fallbackToDestructiveMigration()
                            .build()
                    }

                    single { provideDatabase(androidApplication()) }
                    single { get<UserDatabase>().userDao() }
                },
                module {
                    single<QrDataGenerator> {
                        SevenCardQrDataGeneratorImpl()
                    }
                },
                module {
                    single<UsersRepo> {
                        UsersRepoImpl(
                            get(),
                        )
                    }
                },
                module {
                    viewModel {
                        MainViewModel()
                    }
                    viewModel {
                        QRViewModel(
                            get(),
                            get(),
                            it.get<Long>(),
                        )
                    }
                    viewModel {
                        DecryptViewModel(
                            get(),
                        )
                    }
                },
            )
        }
    }

}
