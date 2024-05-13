package com.example.realtimedatabase.a

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.koin.core.module.dsl.withOptions
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val appModule1 = module {


    single<DatabaseReference> {
        FirebaseDatabase.getInstance().reference
    } withOptions {
        qualifier("FirebaseDb")
    }
    single<RealTimeRepository1> { RealTimeDbRepository1(get()) }
    single {
        RealTimeViewModel1(get())
    }

}