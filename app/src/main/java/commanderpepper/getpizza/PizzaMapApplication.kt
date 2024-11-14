package commanderpepper.getpizza

import android.app.Application
import com.example.androidutil.di.androidUtilModule
import com.example.permissions.di.permissionsModule
import commanderpepper.getpizza.di.remoteModule
import commanderpepper.getpizza.local.room.di.localModule
import commanderpepper.getpizza.map.pizzaMapScreenModule
import commanderpepper.getpizza.util.di.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class PizzaMapApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG){
            Timber.plant(tree = Timber.DebugTree())
        }

        startKoin {
            androidContext(this@PizzaMapApplication)
            modules(pizzaMapScreenModule, localModule, remoteModule, utilModule, androidUtilModule, permissionsModule)
        }
    }
}