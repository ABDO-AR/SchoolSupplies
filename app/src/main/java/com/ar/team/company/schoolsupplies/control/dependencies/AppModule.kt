package com.ar.team.company.schoolsupplies.control.dependencies

import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.control.repository.MainRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton // Method(ProvideFirebaseAuthentication):
    fun provideFirebaseAuthentication(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton // Method(ProvideDatabaseManager):
    fun provideDatabaseManager(): DatabaseManager = DatabaseManager

    @Provides
    @Singleton // Method(ProvideSignRepository):
    fun provideSignRepository(auth: FirebaseAuth, manager: DatabaseManager): MainRepository = MainRepository(auth, manager)
}