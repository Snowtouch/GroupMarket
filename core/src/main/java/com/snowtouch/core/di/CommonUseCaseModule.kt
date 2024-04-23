package com.snowtouch.core.di

import com.snowtouch.core.domain.use_case.GetAdDetailsUseCase
import com.snowtouch.core.domain.use_case.GetAuthStateUseCase
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import org.koin.dsl.module

val commonUseCaseModule = module {
    single<GetAdDetailsUseCase> { GetAdDetailsUseCase(get()) }
    single<ToggleFavoriteAdUseCase> { ToggleFavoriteAdUseCase(get()) }
    single<GetAuthStateUseCase> { GetAuthStateUseCase(get()) }
    single<GetUserFavoriteAdsIdsFlowUseCase> { GetUserFavoriteAdsIdsFlowUseCase(get()) }
}