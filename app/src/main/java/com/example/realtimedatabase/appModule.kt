package com.example.realtimedatabase

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.Qualifier
import org.koin.core.qualifier.qualifier
import org.koin.core.scope.get
import org.koin.dsl.module

val appModule = module {


    single<DatabaseReference> {
        FirebaseDatabase.getInstance().reference
    } withOptions {
        qualifier("FirebaseDb")
    }
    single<RealTimeRepository> { RealTimeDbRepository(get()) }
    single {
        RealTimeViewModel(get())
    }

}