package hector.ruiz.marvel.di

import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import hector.ruiz.marvel.ui.list.CharacterAdapter

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun providerCharacterAdapter(picasso: Picasso): CharacterAdapter {
        return CharacterAdapter(picasso)
    }
}
