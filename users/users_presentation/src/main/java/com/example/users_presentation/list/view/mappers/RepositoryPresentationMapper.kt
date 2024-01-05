package com.example.users_presentation.list.view.mappers

import com.example.users_domain.model.Repository
import com.example.users_presentation.list.model.RepositoriesUI

fun Repository.toPresentation() = RepositoriesUI(
    name = name,
    url = url,
    language = language,
    forks = forks.toString(),
    watchers = watchers.toString(),
)