package hector.ruiz.marvel.di

import androidx.paging.PagingSource
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import hector.ruiz.domain.Character
import hector.ruiz.marvel.ui.detail.AppearancesAdapter
import hector.ruiz.marvel.ui.list.CharacterAdapter
import hector.ruiz.presentation.list.PagingDataSourceImpl
import hector.ruiz.usecase.usecases.GetCharactersUseCase

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun providerCharacterAdapter(picasso: Picasso): CharacterAdapter {
        return CharacterAdapter(picasso)
    }

    @Provides
    fun providerAppearancesAdapter(picasso: Picasso): AppearancesAdapter {
        return AppearancesAdapter(picasso)
    }

    @Provides
    fun providerPagingDataSourceImpl(getCharactersUseCase: GetCharactersUseCase): PagingSource<Int, Character> {
        return PagingDataSourceImpl(getCharactersUseCase)
    }
}
